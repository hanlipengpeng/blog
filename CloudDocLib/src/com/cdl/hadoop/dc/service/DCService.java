package com.cdl.hadoop.dc.service;

import com.cdl.entity.LibFileTransTemp;

public interface DCService {
	
	/**
	 * 提交一个文件处理任务，
	 * 包括文件类型转换，缩略图，第一次索引建立
	 * @param fileInfo
	 * @throws Exception
	 */
	void submitFileHandJob(LibFileTransTemp fileInfo) throws Exception;
	/**
	 * 提交已经提交失败的文件处理任务
	 * @throws Exception
	 */
	void submitUnsubmitFileHandJob() throws Exception;
	
	/**
	 * 提交一个文件删除任务
	 * @throws Exception
	 */
	void submitFileDeleteJob() throws Exception;
	
	
}
