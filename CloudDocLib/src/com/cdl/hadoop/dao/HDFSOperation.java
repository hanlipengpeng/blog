package com.cdl.hadoop.dao;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;

import java.io.IOException;

public interface HDFSOperation {
	//得到操作HDFS的句柄
	public FileSystem defautFileSystem() throws IOException;
	//列出hdfs上面的文件
	public String[] listHDFSFiles() throws IOException;
	public String[] listHDFSFiles(String path) throws IOException;
	//文件是否存在
	public boolean exist(String filePath) throws IOException;
	//删除文件
	public boolean deleteHDFSFile(String hdfsFileId) throws IOException;
	//得到hdfs上面node节点的状态      ？？？？？？？
	// TODO 这里有疑问
	public DatanodeInfo[] getHDFSNodeStatus(String hdfsName) throws IOException;
	//得到输出流
	public OutputStream getOutputStream(String filePath) throws IOException;
	//得到输入流
	public InputStream getInputStream(String fileId,int type) throws IOException;

}
