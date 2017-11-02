package com.cdl.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cdl.hadoop.dc.model.DeamonStatus;
import com.cdl.hadoop.dc.model.TaskStatus;
import com.cdl.hadoop.dc.service.DCDeamonMangeService;
import com.opensymphony.xwork2.ActionSupport;

public class XngAction extends ActionSupport{
	
	private DCDeamonMangeService dcDeamonMangeService;
	
	private static final Log loger = LogFactory.getLog(XngAction.class);
	private String log;//进程日志
	private String host;
	private String msg;
	private boolean success;
	private List<DeamonStatus> list; //任务列表
	private List<TaskStatus> taskList;
	private int jobListType;
	
	//private String name;
	//列出进程列表
	
	public String listDeamons(){
		try {
			list = dcDeamonMangeService.listDeamons();
			success = true;
		} catch (Exception e) {
			success = false;
			loger.error("获取守护进程列表出错", e);
		}
		
		return "json";
		
	}
	
	//启动进程
	public String startDeamon(){
		try {
			success = dcDeamonMangeService.startDeamon(host);
		} catch (Exception e) {
			success = false;
			loger.error("启动已定义进程出错", e);
		}
		return "json";
	}
	//获取日志
	public String deamonLog(){
		try {
			log = dcDeamonMangeService.reportLog(host);
			success = true;
		} catch (Exception e) {
			msg = e.getMessage();
		}
		return "json";
	}
	
	
	
	//自动生成的get 和set方法
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<DeamonStatus> getList() {
		return list;
	}
	public void setList(List<DeamonStatus> list) {
		this.list = list;
	}
	public List<TaskStatus> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<TaskStatus> taskList) {
		this.taskList = taskList;
	}
	public int getJobListType() {
		return jobListType;
	}
	public void setJobListType(int jobListType) {
		this.jobListType = jobListType;
	}


	public DCDeamonMangeService getDcDeamonMangeService() {
		return dcDeamonMangeService;
	}


	public void setDcDeamonMangeService(DCDeamonMangeService dcDeamonMangeService) {
		this.dcDeamonMangeService = dcDeamonMangeService;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

}
