package com.cdl.hadoop.dc.model;

/**
 * 任务状态
 * @author root
 *
 */
public class TaskStatus {

	private String jobId;
	private String jobType;
	private String createTime;
	private String costTime;
	private String currentStatus;
	private String processResult;
	private String docTitle;
	private String uploadPerson;
	private String uploadTime;
	private String docId;
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCostTime() {
		return costTime;
	}
	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getProcessResult() {
		return processResult;
	}
	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public String getUploadPerson() {
		return uploadPerson;
	}
	public void setUploadPerson(String uploadPerson) {
		this.uploadPerson = uploadPerson;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}

}
