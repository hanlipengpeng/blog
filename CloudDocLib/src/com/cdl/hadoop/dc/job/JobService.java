package com.cdl.hadoop.dc.job;

public interface JobService {
	
	//运行磁盘上没有运行的job
	void runDiskJob();
	
	void persistJob(WKJob job);
	
	void wakeWorker(String worker);
	//提交作业
	void submitWKJob(WKJob job);
}
