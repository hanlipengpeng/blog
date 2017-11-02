package com.cdl.hadoop.dc.service;

import java.util.List;

import com.cdl.hadoop.dc.model.DeamonStatus;
import com.cdl.hadoop.dc.model.TaskStatus;

public interface DCDeamonMangeService {
	public List<DeamonStatus> listDeamons() throws Exception;
	boolean startDeamon(String host) throws Exception;
	boolean stopDeamon(String host) throws Exception;
	boolean restartDeamon(String host) throws Exception;
	String reportLog(String host) throws Exception;
	//返回守护进程制定队列信息
	List<TaskStatus> reportWorkUnitQueue(String host,int type) throws Exception;
	//杀死当前正在执行的任务
	void killCurrentWorkUnit(String host) throws Exception;
}
