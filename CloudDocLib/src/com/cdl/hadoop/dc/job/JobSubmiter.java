package com.cdl.hadoop.dc.job;
/**
 * 提交元数据类
 * @author root
 *
 */
public class JobSubmiter {
	private JobService jobService;
	
	public void submit(String fileMetaData,String inputath,int jobType,int operateType,String extra){
		//WKJob是一个数据封装类，没有逻辑意义
		jobService.submitWKJob(new WKJob(inputath, jobType, fileMetaData, operateType, extra));
	}

	
	
	
	//get set 方法
	public JobService getJobService() {
		return jobService;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}
	
}
