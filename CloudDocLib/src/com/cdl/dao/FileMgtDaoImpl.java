package com.cdl.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.cdl.entity.HdfsFileInfo;
import com.cdl.entity.LibFileTransTemp;

public class FileMgtDaoImpl extends SqlMapClientDaoSupport implements FileMgtDao{

	@Override
	public LibFileTransTemp queryFileByHdfsId(String hdfsFileId) {
		return (LibFileTransTemp)this.getSqlMapClientTemplate().queryForObject("libfileinfo.queryFileByHdfsId",hdfsFileId);
	}

	@Override
	public void saveOrUpdateFileTransTemp(LibFileTransTemp tempDoc) {
		System.out.println("start-----------");
		this.getSqlMapClientTemplate().update("libfileinfo.saveOrUpdateFileTransTemp",tempDoc);
		System.out.println("end---------------");
	}

	@Override
	public void saveHdfsFileInfo(HdfsFileInfo file) {
		this.getSqlMapClientTemplate().insert("libfileinfo.saveHdfsFileInfo",file);
		
	}

	@Override
	public void saveLibTransTemp(LibFileTransTemp libFileTmp) {
		this.getSqlMapClientTemplate().insert("libfileinfo.saveLibTransTemp",libFileTmp);
		
	}

	@Override
	public HdfsFileInfo queryHdfsFileInfoById(String sid) {
		//从HDFS_FILE_INFO表里根据sid查询
		return (HdfsFileInfo) this.getSqlMapClientTemplate().queryForObject("libfileinfo.queryHdfsFileInfoById",sid);
	}

	@Override
	public void updateFileTransTemp(LibFileTransTemp fileInfo) {
		// TODO Auto-generated method stub
		
	}
	

}
