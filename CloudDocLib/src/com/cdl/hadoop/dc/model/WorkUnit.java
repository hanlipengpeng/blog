package com.cdl.hadoop.dc.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;


import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;



/**
 * 工作单元
 * @author root
 *
 */

@Entity
public class WorkUnit {

	private String fileId;
	private int pageNo;
	private String msg;//附加信息，用于向服务器报告状态
	private int callBackType;
	private int type;//任务类型 ，1:文档转换  2:索引更新 3：索引删除
	private long createDate; //创建时间
	private long finishedDate; 
	private long checkPointDate; //最后检查点发生时间
	private byte[] checkPointData; //在检查点检查时候保存的数据
	
	@PrimaryKey
	private String fileMeta;//文件元数据
	
	private String callBackUri; //任务回调地址
	private String jobId; 
	private String workDir; //工作目录
	private int status; //任务状态 [文件处于任务那个阶段的状态]
	private int retry; //重试次数
	private String hosts; //运行任务的节点
	private String successHost; //成功完成任务的主机
	private double cpuCost; //cpu花费
	private double memCost; //内存花费
	private int queueType; //队列类型
	private boolean success; //任务是不是执行成功
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCallBackType() {
		return callBackType;
	}
	public void setCallBackType(int callBackType) {
		this.callBackType = callBackType;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
	public long getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(long finishedDate) {
		this.finishedDate = finishedDate;
	}
	public long getCheckPointDate() {
		return checkPointDate;
	}
	public void setCheckPointDate(long checkPointDate) {
		this.checkPointDate = checkPointDate;
	}
	public byte[] getCheckPointData() {
		return checkPointData;
	}
	public void setCheckPointData(byte[] checkPointData) {
		this.checkPointData = checkPointData;
	}
	public String getFileMeta() {
		return fileMeta;
	}
	public void setFileMeta(String fileMeta) {
		this.fileMeta = fileMeta;
	}
	public String getCallBackUri() {
		return callBackUri;
	}
	public void setCallBackUri(String callBackUri) {
		this.callBackUri = callBackUri;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getWorkDir() {
		return workDir;
	}
	public void setWorkDir(String workDir) {
		this.workDir = workDir;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getRetry() {
		return retry;
	}
	public void setRetry(int retry) {
		this.retry = retry;
	}
	public String getHosts() {
		return hosts;
	}
	public void setHosts(String hosts) {
		this.hosts = hosts;
	}
	public String getSuccessHost() {
		return successHost;
	}
	public void setSuccessHost(String successHost) {
		this.successHost = successHost;
	}
	public double getCpuCost() {
		return cpuCost;
	}
	public void setCpuCost(double cpuCost) {
		this.cpuCost = cpuCost;
	}
	public double getMemCost() {
		return memCost;
	}
	public void setMemCost(double memCost) {
		this.memCost = memCost;
	}
	public int getQueueType() {
		return queueType;
	}
	public void setQueueType(int queueType) {
		this.queueType = queueType;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
}
