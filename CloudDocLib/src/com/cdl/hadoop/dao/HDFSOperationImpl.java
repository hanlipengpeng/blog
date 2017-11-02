package com.cdl.hadoop.dao;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;

import java.io.IOException;
import common.Logger;

public class HDFSOperationImpl implements HDFSOperation{
	private Logger log = Logger.getLogger(HDFSOperationImpl.class);
	private Configuration conf =null;
	private FileSystem fs =null;
	/**
	 * 初始化方法，只进行一次
	 */
	public void init(){
		System.out.println("执行初始化--------------");
		try {
			conf=new Configuration();
			fs =FileSystem.get(conf);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	
	private String translatePath(String path){
		// TODO 这里可能有问题
		URI uri = FileSystem.getDefaultUri(this.conf);
		String hdfsPath =uri.toString()+path;
		return hdfsPath;
	}
	@Override
	public FileSystem defautFileSystem() throws IOException {
		// TODO Auto-generated method stub
		return fs;
	}

	@Override
	public String[] listHDFSFiles() throws IOException {
		// TODO Auto-generated method stub`
		return null;
	}

	@Override
	public String[] listHDFSFiles(String path) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exist(String filePath) throws IOException {
		// TODO Auto-generated method stub
		return fs.exists(new Path(filePath));
	}

	@Override
	public boolean deleteHDFSFile(String hdfsFileId) throws IOException {
		return false;
	}

	@Override
	public DatanodeInfo[] getHDFSNodeStatus(String hdfsName) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获得输出流
	 */
	public OutputStream getOutputStream(String filePath) throws IOException {
		String hdfsPath = translatePath(filePath);
		OutputStream out = this.fs.create(new Path(hdfsPath),true,2048);
		return out;
	}

	@Override
	public InputStream getInputStream(String fileId, int type)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
