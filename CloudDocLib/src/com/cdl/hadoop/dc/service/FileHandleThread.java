package com.cdl.hadoop.dc.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Searchable;

import com.cdl.hadoop.dc.model.WorkUnit;
import com.cdl.hadoop.dc.model.XNG;
import com.cdl.hadoop.dc.util.DataAccessor;
import com.cdl.hadoop.dc.util.FsDirectory;
import com.cdl.hadoop.dc.util.LibConfiguration;
import com.cdl.hadoop.dc.util.YXFileUtil;
import com.cdl.hadoop.dc.util.YXJESupport;
import com.cdl.hadoop.dc.util.YXRAMDirectory;
import com.cdl.hadoop.dc.util.YXRemoteSearchable;
import com.cdl.hadoop.dc.util.YXSimpleLock;
import com.cdl.hadoop.dc.util.YxlibClientDbEnv;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.persist.EntityCursor;

public class FileHandleThread implements Runnable{
	private static final Log log = LogFactory.getLog(FileHandleThread.class);
	private Configuration conf;
	private LibConfiguration libconf;
	private WorkUnit[] currentWU;
	private DataAccessor da;
	private YxlibClientDbEnv myDbEnv;
	private String address;
	private Map killedMU;
	private BlockingQueue<WorkUnit> wuQueue;
	private YXJESupport jes;
	private YXSimpleLock optimizelock;//文件处理锁
	private static volatile boolean indexShouldMerge = false;//索引是否需要合并
	private static volatile boolean indexShouldRefresh = false;//索引是否需要刷新
	private YXRAMDirectory ramd;
	private YXRemoteSearchable impl;
	
	@Override
	public void run() {
		log.info("开始执行FileHandleThread的run方法了");
		addStoreJob2Queue();
		log.info("测试optimizelock的锁:"+optimizelock.obtainLock());
		optimizelock.releaseLock();
		while(true){
			WorkUnit wu = null;
			try {
				//log.info(optimizelock.obtainLock()+"进入while循环了");
				if(optimizelock.obtainLock()){
					log.info("进入if里面了，开始向下执行 wuQueue的值"+wuQueue==null);
					optimizelock.setHolder(XNG.YXLIB_DAEMON_LOCK_HOLDER_FILEHANDLER);
					wu = wuQueue.take();
					log.info("文件转换获取锁"+wu.getFileMeta());
					//处理工作单元
					processWorkUnit(wu);
					log.info("FileHandleThread处理完成，现在需要释放文件所");
					optimizelock.setHolder(null);
					optimizelock.releaseLock();
					if(wuQueue.isEmpty()){
						if(indexShouldMerge){
							try {
								//执行索引合并
								mergeIndex();
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						if(indexShouldRefresh){
							//做内存索引刷新
							refreshIndex();
						}
					}
				}
			} catch (Exception e) {
				log.error("文件处理的工作单元是"+wu, e);
			}
			indexShouldMerge = true;
			
		}
		
	}
	
	
	 /**
     * 刷新内存索引
     * @throws IOException
     */
    private void refreshIndex() throws IOException
    {
        log.info(System.currentTimeMillis()+" >>>正在运行内存索引刷新任务...");
        boolean create = false;
        FileSystem fs = FileSystem.get(conf);
        String hdfsIndexPath = XNG.YXLIB_INDEX_MAIN_PATH.replace("{ip}", address);
        Path refreshPath = new Path(hdfsIndexPath);
        FileStatus fileStatus[] = fs.listStatus(refreshPath);
        if(fileStatus == null || fileStatus.length == 0)
        {
            create = true;
        }
        FsDirectory hdfsDisk = new FsDirectory(fs, new Path(hdfsIndexPath), create, fs.getConf());
        if(ramd == null || indexShouldRefresh)
        {
            if(ramd == null)
            {
                ramd = new YXRAMDirectory(hdfsDisk);
            }
            else
            {
            	//索引拷贝操作
                ramd.sync(hdfsDisk);
            }
            
            Searchable local = null;
            if(ramd.listAll().length>0)
            {
                local = new IndexSearcher(ramd, true);
            }
            if(impl == null)
            {
                impl = new YXRemoteSearchable(local);
            }
            impl.setLocal(new YXRemoteSearchable(local));
            
            log.info(System.currentTimeMillis()+" >>>内存索引刷新成功...");
            
            System.gc();
        }

        indexShouldRefresh = false;
    }

	
	/**
     * 合并索引目录
     * @throws IOException
     */
    private void mergeIndex() throws IOException
    {
        log.info(System.currentTimeMillis()+" >>>正在运行内存索引合并任务...");
        //更新状态
        indexShouldMerge = false;
        indexShouldRefresh = true;
        
        FileSystem fs = FileSystem.get(conf);
        boolean isNewCreate = false;
        Path indexPath = new Path(XNG.YXLIB_INDEX_MAIN_PATH.replace("{ip}", address));
        Path mergePath = new Path(XNG.YXLIB_INXEX_MERGE_PATH.replace("{ip}", address));
        FileStatus fileStatus[] = fs.listStatus(indexPath);
        FileStatus mergeStatus[] = fs.listStatus(mergePath);
        if(mergeStatus.length == 0)
        {
            log.info(System.currentTimeMillis()+" >>>索引合并目录无索引可合并,任务退出.");
            return;
        }
        //可能第一次fileStatus为null  updated by zemi
        if(fileStatus==null||fileStatus.length == 0)
        {
            isNewCreate = true;
        }
        FsDirectory indexDir=new FsDirectory(fs, indexPath, isNewCreate, fs.getConf());
        FsDirectory mergeDir=new FsDirectory(fs, mergePath, false, fs.getConf());
        IndexWriter writer = new IndexWriter(indexDir,null, isNewCreate, MaxFieldLength.UNLIMITED);
 //       writer.setInfoStream(LogUtil.getDebugStream(log));
        //此处不优化
        writer.addIndexesNoOptimize(mergeDir);
        writer.close();
        YXFileUtil.deleteIndexTempFiles(libconf);
        
        fs.delete(mergePath, true);
        fs.mkdirs(mergePath);
        log.info(System.currentTimeMillis()+" >>>索引合并完成.");
        
    }
	public void processWorkUnit(WorkUnit wu){
		try {
			currentWU[0] = wu;
			log.info("当前的工作单元"+wu+"开始处理");
			if(killedMU.containsKey(wu.getFileMeta())){
				wu.setStatus(XNG.WU_STATUS_KILLED);
				wu.setMsg(XNG.FTE_TASK_KILLED);
			}
			if(wu.getRetry()>4){
				
			}
			switch (wu.getType()){
			case XNG.YXLIB_JOB_INDEX_TYPE_CREATE:
				processCrrateIndex(wu);
				break;
			}
			
		} catch (Exception e) {
			currentWU[0] = null;
		}
		myDbEnv.getEnv().sync();
	}
	
	public void addStoreJob2Queue(){
		EntityCursor<WorkUnit> wus = null;
		TransactionConfig tc = new TransactionConfig();
		tc.setNoWait(true);
		CursorConfig cconfig = new CursorConfig();
		cconfig.setReadCommitted(true);
		
		Transaction txn = jes.getEnv().getEnv().beginTransaction(null, tc);
		wus = jes.getDa().tocreate.entities(txn,cconfig);
		for(WorkUnit wu:wus){
			wuQueue.offer(wu);
		}
		try {
			wus.close();
			txn.commit();
		} catch (Exception e) {
			txn.abort();
			txn = null;
			log.error("持久化后的任务放入阻塞队列异常", e);
		}
	}
	
	
	private void processCrrateIndex(WorkUnit wu){
		try {
			//处理文件
			log.info("FileHandleThread类开始处理文件，下面可能就要报classnotfond");
			
			Class<?> c=Class.forName("com.cdl.hadoop.dc.service.FileHandleService");
			FileHandleService fhs=(FileHandleService) c.newInstance();
			fhs.handle(wu);
			
			/*FileHandleService fhs =FileHandleService.getInstant();
			fhs.handle(wu);*/
			log.info("FileHandleThread类，任务已经执行完毕");
			storeSuccessWorkUnit(wu);
		} catch (Exception e) {
			log.info("文件处理失败",e);
			e.printStackTrace();
			//处理失败    storeFailedWorkUnit(wu)  没有实现，
		}

	}
	
	private void storeSuccessWorkUnit(WorkUnit wu){
		jes.delWorkUnit2Create(wu);
		jes.delWorkUnit2Update(wu);
		jes.delWorkUnit2Delete(wu);
		wu.setQueueType(XNG.YXLIB_WU_QUEUE_SUCCESSED);
		jes.addWorkUnit2Successed(wu);
		log.info(new Date()+"成功的处理----");
	}
	
	
	public FileHandleThread(Configuration conf, LibConfiguration libconf,
			WorkUnit[] currentWU, DataAccessor da, YxlibClientDbEnv myDbEnv,
			String address, Map killedMU, BlockingQueue<WorkUnit> wuQueue,
			YXJESupport jes) {
		super();
		this.conf = conf;
		this.libconf = libconf;
		this.currentWU = currentWU;
		this.da = da;
		this.myDbEnv = myDbEnv;
		this.address = address;
		this.killedMU = killedMU;
		this.wuQueue = wuQueue;
		this.jes = jes;
		optimizelock = new YXSimpleLock();
	}
}
