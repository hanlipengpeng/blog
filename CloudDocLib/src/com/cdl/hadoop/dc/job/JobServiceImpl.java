package com.cdl.hadoop.dc.job;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;


import com.cdl.hadoop.dc.service.DCServiceImpl;


public class JobServiceImpl implements JobService{
	private InternalSubmiter internalSubmiter;
	private static final Log log =LogFactory.getLog(DCServiceImpl.class);
	private BlockingQueue<String> sq = new LinkedBlockingDeque<String>();
	private JobJESupport jobJESupport = new JobJESupport();
	@Override
	public void runDiskJob() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 持久化
	 */
	public void persistJob(WKJob job) {
		jobJESupport.addJob(job);
		log.debug("持久化一个作业单元"+job);
		
	}

	/**
	 * 唤醒worker
	 */
	public void wakeWorker(String worker) {
		sq.offer(worker);
		log.debug("阻塞队列放入元数据"+worker);
		
	}
	/**
	 * 提交任务元数据
	 */
	@Override
	public void submitWKJob(WKJob job) {
		try {
			//考虑数据缓冲，和集群的能力
			JobConf jcf = new JobConf();
			JobClient jc = new JobClient(jcf);
			if(jc.jobsToComplete().length>jcf.getInt("yxlib.jobtrack.capacity1", 100000)){
				//如果集群待处理的任务数大于能够处理的任务数，吧作业持久化，并把作业放到等待队列开始等待
				//持久化
				// TODO 这个持久化是将任务写到BDB数据库了吗？？？？  还有下面wakeWorker是干嘛的？？？   下次看的时候需要往里面看看实现的方式
				persistJob(job);
				//把job元数据放入到阻塞队列
				wakeWorker(job.getFileMetaData());
				return;
			}
		} catch (Exception e) {
			log.error("持久化失败", e);
		}
		
		//正式提交任务
		try {
			//job 封装的提交数据
			internalSubmiter.submitJob(job);
		} catch (Exception e) {
			log.error(e);
		}
		
		System.out.println("提交任务完成");
		
		
		
	}
	
	
	
	
	//自动生成的get和set方法
	public InternalSubmiter getInternalSubmiter() {
		return internalSubmiter;
	}

	public void setInternalSubmiter(InternalSubmiter internalSubmiter) {
		this.internalSubmiter = internalSubmiter;
	}

	public JobJESupport getJobJESupport() {
		return jobJESupport;
	}

	public void setJobJESupport(JobJESupport jobJESupport) {
		this.jobJESupport = jobJESupport;
	}

}
