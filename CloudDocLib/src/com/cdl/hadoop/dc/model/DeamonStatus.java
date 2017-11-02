package com.cdl.hadoop.dc.model;

/**
 * 守护进程状态
 * @author root
 *
 */
public class DeamonStatus {

	private String hostName;
	private String ip;
	private int total; //任务总数
	private int workQueueSize; //待转换的工作队列大小
	private int failureQueueSize; //已失败的工作队列大小
	private int successQueueSize; //成功的工作队列大小
	private int indexDeleteQueueSize; //需要删除的工作队列大小
	private int indexRebuildQueueSize; //需要重建索引的工作队列大小
	private int runningQueueSize; //正在运行的任务的工作队列大小
	private String status; //当前状态，存活|死亡
	public String getHostName() {
		return hostName;
	}


	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public int getWorkQueueSize() {
		return workQueueSize;
	}


	public void setWorkQueueSize(int workQueueSize) {
		this.workQueueSize = workQueueSize;
	}


	public int getFailureQueueSize() {
		return failureQueueSize;
	}


	public void setFailureQueueSize(int failureQueueSize) {
		this.failureQueueSize = failureQueueSize;
	}


	public int getSuccessQueueSize() {
		return successQueueSize;
	}


	public void setSuccessQueueSize(int successQueueSize) {
		this.successQueueSize = successQueueSize;
	}


	public int getIndexDeleteQueueSize() {
		return indexDeleteQueueSize;
	}


	public void setIndexDeleteQueueSize(int indexDeleteQueueSize) {
		this.indexDeleteQueueSize = indexDeleteQueueSize;
	}


	public int getIndexRebuildQueueSize() {
		return indexRebuildQueueSize;
	}


	public void setIndexRebuildQueueSize(int indexRebuildQueueSize) {
		this.indexRebuildQueueSize = indexRebuildQueueSize;
	}


	public int getRunningQueueSize() {
		return runningQueueSize;
	}


	public void setRunningQueueSize(int runningQueueSize) {
		this.runningQueueSize = runningQueueSize;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DeamonStatus [failureQueueSize=" + failureQueueSize
				+ ", hostName=" + hostName + ", indexDeleteQueueSize="
				+ indexDeleteQueueSize + ", indexRebuildQueueSize="
				+ indexRebuildQueueSize + ", ip=" + ip + ", runningQueueSize="
				+ runningQueueSize + ", status=" + status
				+ ", successQueueSize=" + successQueueSize + ", total=" + total
				+ ", workQueueSize=" + workQueueSize + "]";
	}

	
	public DeamonStatus(){}
	public DeamonStatus(String hostName, String ip, int total,
			int workQueueSize, int failureQueueSize, int successQueueSize,
			int indexDeleteQueueSize, int indexRebuildQueueSize,
			int runningQueueSize, String status) {
		super();
		this.hostName = hostName;
		this.ip = ip;
		this.total = total;
		this.workQueueSize = workQueueSize;
		this.failureQueueSize = failureQueueSize;
		this.successQueueSize = successQueueSize;
		this.indexDeleteQueueSize = indexDeleteQueueSize;
		this.indexRebuildQueueSize = indexRebuildQueueSize;
		this.runningQueueSize = runningQueueSize;
		this.status = status;
	}


	
}