package com.cdl.hadoop.dc.service;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * 自定义进程接口rpc接口
 * @author root
 *
 */
public interface DCDeamonService extends VersionedProtocol{
	public static final long versionID = 95280000000000L;
	void reportStatus();  //汇报任务状态
	Text reportProgress();  //汇报进度
	Text reportWorkUnitQueue(int type); //返回指定的队列信息
	void receiveWorkUnit(String fm,String callBackUri,String jobId,int workType);
	Text reportLog(int yxlibDeamonDefaultLogSize);
}
