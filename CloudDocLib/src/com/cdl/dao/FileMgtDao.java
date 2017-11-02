package com.cdl.dao;

import com.cdl.entity.HdfsFileInfo;
import com.cdl.entity.LibFileTransTemp;

/**
 * 文件的数据库操作
 * @author root
 *
 */
public interface FileMgtDao {
	
	LibFileTransTemp queryFileByHdfsId(String hdfsFileId);
	
	void saveOrUpdateFileTransTemp(LibFileTransTemp tempDoc);
	void saveHdfsFileInfo(HdfsFileInfo file);

	void saveLibTransTemp(LibFileTransTemp libFileTmp);

	HdfsFileInfo queryHdfsFileInfoById(String sid);

	void updateFileTransTemp(LibFileTransTemp fileInfo);
	
	
}
