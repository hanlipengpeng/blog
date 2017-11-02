package com.cdl.hadoop.dc.service;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.SocketFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.FSConstants.DatanodeReportType;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.net.NetUtils;

import com.cdl.dao.FileMgtDao;
import com.cdl.entity.HdfsFileInfo;
import com.cdl.entity.LibFileTransTemp;
import com.cdl.hadoop.dao.HDFSOperation;
import com.cdl.hadoop.dc.job.JobSubmiter;
import com.cdl.hadoop.dc.model.DeamonStatus;
import com.cdl.hadoop.dc.model.XNG;
import com.cdl.hadoop.dc.model.YXFileMeta;
import com.cdl.util.Constants;


public class DCServiceImpl implements DCService{
	private JobSubmiter jobSubmiter;
	private HDFSOperation hdfsOperation;
	private FileMgtDao fileMgtDao;
	private static final Log log =LogFactory.getLog(DCServiceImpl.class);
	private ExecutorService threadPool;
	private Integer threadPoolSize = 50;
	/**
	 * 单例线程池
	 * @return
	 */
	private ExecutorService getThreadPool(){
		//单例
		if(threadPool==null){
			threadPool = Executors.newFixedThreadPool(threadPoolSize);
		}
		return threadPool;
	}
	
	
	@Override
	public void submitFileHandJob(final LibFileTransTemp fileInfo) throws Exception {
		ExecutorService pool = getThreadPool();
		if(pool!=null){
			pool.execute(new Runnable() {
				@Override
				public void run() {
					long retryTime =0l;//文件重复提交次数
					while(retryTime<4){
						try {
							retryTime = (Long) (fileInfo.getRetryTime()==null?0l:fileInfo.getRetryTime());
							System.out.println(fileMgtDao);
							//hfi是一个HDFS文件表实例
							HdfsFileInfo hfi = fileMgtDao.queryHdfsFileInfoById(fileInfo.getHdfsFileId());
							System.out.println(fileInfo.getSid());
							//判断hdfs上这个文件是否存在
							if(!checkHdfsFileExist(hfi)){
								System.out.println("文件不存在");
								return;
							}
							
							String filePath = hfi.getFilePath();
							filePath.replace("\\\\", "/");
							String fileMetaData = YXFileMeta.fileMetaData(fileInfo, null, hfi, XNG.YXLIB_JOB_TYPE_FILE_CONVERT);//这里需要改
							//提交元数据，在此之前需要对wps .et 等进行转化
							// TODO 这里有疑问    转换成pdf后的存储和源文件怎么保存？？？
							if(WpsToPdfService.isCanHandleWps(fileMetaData)){
								// TODO 这里的代码是在tomcat里面运行的，，，应该是在hadoop上运行？？？？？
								WpsToPdfService.handleWPS(fileMetaData);
								//转换完后从新拼接元数据
								String[] strs = fileMetaData.split(",");
								strs[2] = strs[2].substring(0, strs[2].lastIndexOf("."))+".pdf";
								strs[8] ="pdf";
								StringBuffer sb =new StringBuffer();
								for(int i = 0;i<strs.length;i++){
									sb.append(strs[i]);
									if(i!=strs.length-1){
										sb.append(",");
									}
								}
								fileMetaData = sb.toString();
							}
							
							//直接提交元数据
							// TODO 这里有问题    任务到底是干嘛的？？？？
							jobSubmiter.submit(fileMetaData, hfi.getFilePath(), XNG.YXLIB_JOB_TYPE_FILE_CONVERT, -1, null);
							fileInfo.setFileStatus(Constants.FILE_TRANS_STATUS_CONVERSION);
							fileMgtDao.updateFileTransTemp(fileInfo);
							System.out.println("文件转换成功，状态更新"+fileInfo);
							break;
						} catch (Exception e) {
							//如果失败，则重试
							e.printStackTrace();
							System.out.println("文件处理失败，重试一次");
							String excption = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
							retryTime++;
							//更新数据库
							fileInfo.setRetryTime(retryTime);//需要修改
							fileInfo.setExceptionMsg(excption);
							fileMgtDao.updateFileTransTemp(fileInfo);//需要修改
							
							
						}
					}
					
				}
			});
		}
		
	}
	
	//初始化方法，初始化主机，提交未提交的任务
	public void init(){
		try {
			FileSystem fs = hdfsOperation.defautFileSystem();
			if(fs instanceof DistributedFileSystem){
				DistributedFileSystem dfs = (DistributedFileSystem)fs;
				DatanodeInfo[] live = dfs.getClient().datanodeReport(DatanodeReportType.LIVE);
				DatanodeInfo[] dead = dfs.getClient().datanodeReport(DatanodeReportType.DEAD);
				log.info("存活的节点有："+live.length+"死亡的节点有："+dead.length);
				String host = null;
				for(DatanodeInfo ln : live){
					host = ln.getHost();
					hostIsLiving(host);
					log.info("正在探测live节点"+live);
				}
				for(DatanodeInfo ln : dead){
					host = ln.getHost();
					hostIsLiving(host);
					log.info("正在探测dead节点"+dead);
				}
				log.info("执行后的判断存活的节点有："+XNG.liveHost.size()+"死亡的节点有："+XNG.deadHost.size());
			}
			
		} catch (Exception e) {
			log.info("系统初始化探测数据节点失败",e);
		}
	}
	/**
	 * 探测节点或端口是否存活
	 * @param host
	 * @return
	 */
	private boolean hostIsLiving(String host){
		final int PORT = 9529;
		DCDeamonService idcds =null;
		InetSocketAddress socAddr = NetUtils.createSocketAddr(host,PORT);
		Configuration conf = new Configuration();
		
		try {
			//1:判断端口是不是开启
			if(portOpened(host,PORT)){
				idcds = (DCDeamonService)RPC.waitForProxy(DCDeamonService.class, DCDeamonService.versionID, socAddr, conf);
				idcds.reportStatus();
				RPC.stopProxy(idcds);
				
				if(!XNG.liveHost.containsKey(host)){
					XNG.liveHost.put(host,host);
				}
				if(XNG.deadHost.containsKey(host)){
					XNG.deadHost.remove(host);
				}
				
			}else{
				log.info("");
				if(XNG.liveHost.containsKey(host)){
					System.out.println("存活节点移除节点："+host);
					XNG.liveHost.remove(host);
				}
				if(!XNG.deadHost.containsKey(host)){
					System.out.println("死亡节点添加节点："+host);
					XNG.deadHost.put(host,host);
				}
				
			}
		} catch (Exception e) {
			System.out.println("判断是否存活出异常！！！！");
		}
		
		return true;
	}
	
	
	private boolean portOpened(String host,int port){
		boolean result = false;
		Socket socket = null;
		try {
			socket = SocketFactory.getDefault().createSocket();
			socket.setTcpNoDelay(false);
			NetUtils.connect(socket, NetUtils.createSocketAddr(host,port), 20000);
			result = true;
			
		} catch (Exception e) {
			log.error("端口关闭！！！！");
		}finally{
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean checkHdfsFileExist(HdfsFileInfo hfi){
		if(StringUtils.isBlank(hfi.getFilePath())){
			log.error("提交任务文件路径为空");
			return false;
		}
		try{
			if(!hdfsOperation.exist(hfi.getFilePath())){
				log.error("所提交文件HDFS不存在");
				return false;
			}
		}catch(Exception e){
			log.error("DCServiceImpl异常", e);
		}
		
		return true;
		
	}

	@Override
	public void submitUnsubmitFileHandJob() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitFileDeleteJob() throws Exception {
		// TODO Auto-generated method stub
		
	}


	public FileMgtDao getFileMgtDao() {
		return fileMgtDao;
	}


	public void setFileMgtDao(FileMgtDao fileMgtDao) {
		this.fileMgtDao = fileMgtDao;
	}


	public HDFSOperation getHdfsOperation() {
		return hdfsOperation;
	}


	public void setHdfsOperation(HDFSOperation hdfsOperation) {
		this.hdfsOperation = hdfsOperation;
	}


	public JobSubmiter getJobSubmiter() {
		return jobSubmiter;
	}


	public void setJobSubmiter(JobSubmiter jobSubmiter) {
		this.jobSubmiter = jobSubmiter;
	}

}
