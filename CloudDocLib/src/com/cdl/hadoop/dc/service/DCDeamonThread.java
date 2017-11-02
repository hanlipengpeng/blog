package com.cdl.hadoop.dc.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;
import org.apache.hadoop.net.NetUtils;
import org.apache.hadoop.util.Shell.ShellCommandExecutor;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Searchable;

import com.cdl.hadoop.dc.model.WorkUnit;
import com.cdl.hadoop.dc.model.XNG;
import com.cdl.hadoop.dc.util.DataAccessor;
import com.cdl.hadoop.dc.util.FsDirectory;
import com.cdl.hadoop.dc.util.LibConfiguration;
import com.cdl.hadoop.dc.util.YXJESupport;
import com.cdl.hadoop.dc.util.YXRAMDirectory;
import com.cdl.hadoop.dc.util.YXRemoteSearchable;
import com.cdl.hadoop.dc.util.YxlibClientDbEnv;
import com.sleepycat.persist.EntityStore;
import com.sun.org.apache.xml.internal.security.Init;
/**
 * 守护进程
 * @author root
 *
 */
public class DCDeamonThread implements DCDeamonService{
	private static final Log log =LogFactory.getLog(DCDeamonThread.class);
	private BlockingQueue<WorkUnit> wuQueue=new LinkedBlockingDeque<WorkUnit>();
	private static YxlibClientDbEnv myDBEnv = new YxlibClientDbEnv();
	private YXJESupport jes;
	private DataAccessor da;
	private String address;
	private Server server;
	private InetSocketAddress serverAddress; //RPC进程端口及其ip
	private Configuration conf = new Configuration();
	private LibConfiguration libconf = new LibConfiguration(conf);
	File myDbEnvPath = new File(libconf.getDBDir());
	private WorkUnit[] correntWU = new WorkUnit[1];
	private Map killedWU = new HashMap();
	private Thread fileHandleThread;
	private static YXRAMDirectory ramd;  //索引缓存目录类
	private YXRemoteSearchable impl;
	private RemoteSearchServerThread remoteSearchServer;
	/**
	 * 初始化bdb目录
	 * 初始化工作目录
	 */
	private void init() {
		if(!myDbEnvPath.exists()){
			myDbEnvPath.mkdirs();
		}
		myDBEnv.setup(myDbEnvPath, false);
		if(da == null){
			da = new DataAccessor(new EntityStore[] {
				myDBEnv.getTocreate(),myDBEnv.getToupdate(),
				myDBEnv.getTodelete(),myDBEnv.getTocallback(),
				myDBEnv.getTointelligenceredo(),myDBEnv.getSuccessed(),
				myDBEnv.getFailed()
			});
			jes = new YXJESupport(myDBEnv, da);
			try {
				address = InetAddress.getLocalHost().getHostAddress();
			} catch (Exception e) {
				log.error(System.currentTimeMillis()+"本地解析异常", e);
			}
		}

	}
	/**
	 * 启动远程搜索服务线程
	 */
	private void startCacheRemoteSearchServer(){
		try {
			refreshIndex();
			//启动线程来绑定一个远程地址
			remoteSearchServer = new RemoteSearchServerThread(ramd, impl, XNG.YXLIB_SEARCHER_PORT);//9530
			Thread t = new Thread(remoteSearchServer);
			t.start();
			log.info("启动RMI线程成功-------------------");
		} catch (Exception e) {
			log.error("启动RMI线程失败");
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

	
	@Override
	public long getProtocolVersion(String arg0, long arg1) throws IOException {
		return DCDeamonService.versionID;
	}

	@Override
	public void reportStatus() {
		log.info(new Date().toString()+"汇报状态成功");
		
	}

	@Override
	public Text reportProgress() {
		long wait2FileTrans = jes.getDa().tocreate.count();
		long wait2UpdataIndex = jes.getDa().toupdate.count();
		long wait2DeletIndex = jes.getDa().todelete.count();
		long failed = jes.getDa().failed.count();
		long successed = jes.getDa().successed.count();
		long total = wait2FileTrans+wait2UpdataIndex+wait2DeletIndex +failed + successed;
		StringBuffer sb = new StringBuffer();
		sb.append(total);
		sb.append(",");
		sb.append(successed);
		sb.append(",");
		sb.append(wait2DeletIndex);
		sb.append(",");
		sb.append(wait2UpdataIndex);
		sb.append(",");
		sb.append(wait2FileTrans);
		sb.append(",");
		sb.append(failed);
		sb.append(",");
		if(correntWU[0]!=null){
			sb.append(1);
		}else{
			sb.append(0);
		}
		return new Text(sb.toString());
	}

	@Override
	public Text reportWorkUnitQueue(int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receiveWorkUnit(String fm, String callBackUri, String jobId,
			int workType) {
		WorkUnit wu = new WorkUnit();
		wu.setCreateDate(System.currentTimeMillis());
		wu.setFileMeta(fm);
		wu.setFinishedDate(System.currentTimeMillis());
		wu.setMemCost(0);
		wu.setStatus(0);
		wu.setCallBackUri(callBackUri);
		wu.setJobId(jobId);
		wu.setType(workType);
		wu.setRetry(1);
		wu.setSuccess(false);
		switch (workType) {
		case 1:
			wu.setQueueType(XNG.YXLIB_WU_QUEUE_WAIT_TRANS);
			//同时把workunit持久化
			jes.addWorkUnit2Delete(wu);
			break;

		default:
			break;
		}
		
		try {
			//把这个工作单元放入阻塞队列
			wuQueue.put(wu);
			log.info("将任务放到了wuQueue中了，wuQueue的大小"+wuQueue.size());
			
			
		} catch (Exception e) {
			log.error(e);
		}
		//把bdb内存里面的内容持久化到文件上
		myDBEnv.getEnv().sync();
		log.info(System.currentTimeMillis()+"接收到====任务，这个是DCDeamonThread类执行");   
	}
	private void run(){
		init();
		startCacheRemoteSearchServer();//索引使用
		startFileHandleThread();
		startRPCServer();
	}
	private void startRPCServer(){
		InetSocketAddress socAddr = NetUtils.createSocketAddr(address, conf.getInt("yxlib.deamod.port", 9529));
		Configuration config = new Configuration();
		try {
			server = RPC.getServer(this, socAddr.getHostName(), socAddr.getPort(), 1, false, config);
			serverAddress = server.getListenerAddress();
			server.start();
			
		} catch (Exception e) {
			log.error("启动rpc任务失败", e);
		}
		log.info("启动rpc任务成功");
		try {
			if(server!=null){
				server.join();
			}
		} catch (Exception e) {
			log.error(e);
		}
		
	}
	
	
	//开始处理文件
	private void startFileHandleThread(){
		FileHandleThread fileHandle = new FileHandleThread(conf, libconf, correntWU, da, myDBEnv, address, killedWU, wuQueue, jes);
		fileHandleThread = new Thread(fileHandle);
		fileHandleThread.start();
		log.info("DCDeamonThread启动任务执行时线程,启动FileHandleThread");
	}
	public static void main(String[] args) {
		DCDeamonThread dc =new DCDeamonThread();
		dc.run();
	}
	public Text reportLog(int n) {
		// TODO Auto-generated method stub
		File file=new File("/usr/tools/dcdaemon/work/stderr.log");
		System.out.println("---- report Log");
		if (!file.exists() || !file.canRead()) {
			return new Text("日志文件不存在或者不允许操作");
		}
		ShellCommandExecutor sh=new ShellCommandExecutor(new String[]{"bash","-c","exec 'tail' '-n' '1000' '" + file.getAbsolutePath() 
	              + "' 2>/dev/null"},new File("/usr/tools/dcdaemon/work"));
		
		try {
			sh.execute();
		} catch (Exception e) {
			log.error("DCDeamonThread reportLog error",e);
		}
		//如果日志文件大于30M.清空一次
		if (file.length()>30*1024*1024) {
			FileOutputStream logFileOS=null;
			try {
				logFileOS=new FileOutputStream(file);
				logFileOS.write(new String("").getBytes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					logFileOS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new Text(sh.getOutput());
	}

}
