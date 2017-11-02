package com.cdl.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.cdl.dao.FileMgtDao;
import com.cdl.entity.HdfsFileInfo;
import com.cdl.entity.LibFileTransTemp;
import com.cdl.hadoop.dao.HDFSOperation;
import com.cdl.hadoop.dc.service.DCService;
import com.cdl.util.Constants;
import com.cdl.util.UUIDCreate;

import common.Logger;

public class FileMgtServiceImpl implements FileMgtService{
	private Logger log = Logger.getLogger(FileMgtServiceImpl.class);
	private FileMgtDao fileMgtDao;
	private HDFSOperation hdfsOperation;
	private DCService dcService;
	
	public void setHdfsOperation(HDFSOperation hdfsOperation){
		this.hdfsOperation = hdfsOperation;
	}
	
	/**
	 * 拼接hdfs的路径，上传文件的位置          hdfs路径为 /root/fileType/date/fileUUIDName.fileType
	 */
	private String getFilePath(String rootPath,String fileType,String fileUUIDName){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String date = sdf.format(new Date());
		return rootPath+"/"+fileType+"/"+date+"/"+fileUUIDName+"."+fileType;
		
	}
	
	/**
	 * 文件上传的核心操作
	 */
	@Override
	public String uploadFile(String fileName, InputStream is, String userId)
			throws IOException {
		String rootPath="/root";
		//截取文件名
		String realFileName = fileName.substring(0,fileName.lastIndexOf("."));
		//截取文件的类型
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
		//获取用户名唯一名称，生成字符串
		String fileUUIDName =UUID.randomUUID().toString();
		//调用本类的拼接路径方法
		String hdfsFilePath = this.getFilePath(rootPath, fileType, fileUUIDName);
		//获得输出流，获得向hdfs上面写文件的文件流
		OutputStream os = this.hdfsOperation.getOutputStream(hdfsFilePath);
		
		//两个流对接，上传文件
		byte[] buffer = new byte[1024];
		int ins=0;
		long fileSize = 0;
		try{
			while(true){
				if((ins=is.read(buffer))==-1){
					is.close();
					os.flush();
					os.close();
					break;
				}else{
					os.write(buffer,0,ins);
					fileSize+=ins;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//上面的代码已完成上传操作，下面是对 HDFS文件表   进行更新
		//首先获取   HdfsFileInfo  对象
		HdfsFileInfo file = new HdfsFileInfo();
	    String sid = UUIDCreate.uuidStr();
	    file.setSid(sid);
	    file.setFileRealName(fileUUIDName);
	    file.setFileName(fileName);
	    file.setFileSize(fileSize);
	    file.setFilePath(hdfsFilePath);
	    file.setFileReplicationNum(00l);
	    file.setFileBlockNum(11l);
	    file.setFileUploadDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	    file.setCreatedBy("zhangsan");
	    file.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	    file.setUserId("zhangsan");
	    file.setFileMd5(UUID.randomUUID().toString());
	    file.setEnabledFlag("Y");
	    try {
	    	
	    	//hdfs文件表的保存
			this.fileMgtDao.saveHdfsFileInfo(file);
		} catch (Exception e) {
			log.error("保存hdfs文件信息失败",e);
		}
	    
	    //文件转换中间表对象的设置，以及保存
	    LibFileTransTemp libFileTmp = new LibFileTransTemp();
	    libFileTmp.setSid(UUIDCreate.uuidStr());
	    libFileTmp.setFileTitle(fileName);
	    libFileTmp.setFileExtName(fileType);
	    libFileTmp.setHdfsFileId(sid);
	    libFileTmp.setUploadBy("zhangsan");
	    libFileTmp.setUploadDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	    libFileTmp.setFileStatus(Constants.FILE_TRANS_STATUS_UNCONVERSION);
	    libFileTmp.setEnabledFlag("Y");
	    try{
	    	
	    	//中间表信息的保存
	    	this.fileMgtDao.saveLibTransTemp(libFileTmp);
	    }catch(Exception e){
	    	log.error("文件保存中间表失败", e);
	    }
	    System.out.println("上传的文件sid："+sid);
		return sid;
		
		
	}
	
	/**
	 * 提交上传 完善文件转换中间表LibFileTransTemp
	 */
	@Override
	public void submitFile(List<LibFileTransTemp> fileList, String string)
			throws IOException {
		try {
			for(LibFileTransTemp tmp : fileList){
				if(tmp!=null){
					//查询
					LibFileTransTemp tempDoc = this.fileMgtDao.queryFileByHdfsId(tmp.getHdfsFileId());
					if(tempDoc !=null){
						tempDoc.setFileTitle(tmp.getFileTitle());
						tempDoc.setFileDesc(tmp.getFileDesc());
						tempDoc.setFileKeyword(tmp.getFileKeyword());
						tempDoc.setFileCategoryId(tmp.getFileCategoryId());
						tempDoc.setFileStatus(Constants.FILE_TRANS_STATUS_SUBMITTING);
						
						this.fileMgtDao.saveOrUpdateFileTransTemp(tempDoc);
						
						//提交一个文件处理任务    参数是  中间表信息
						dcService.submitFileHandJob(tempDoc);
						
					}
				}
				
			}
		} catch (Exception e) {
			log.error("文件提交异常",e);
		}
		
	}
	
	
	//自动生成的get和set方法
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public FileMgtDao getFileMgtDao() {
		return fileMgtDao;
	}

	public void setFileMgtDao(FileMgtDao fileMgtDao) {
		this.fileMgtDao = fileMgtDao;
	}

	public HDFSOperation getHdfsOperation() {
		return hdfsOperation;
	}

	public DCService getDcService() {
		return dcService;
	}

	public void setDcService(DCService dcService) {
		this.dcService = dcService;
	}

}
