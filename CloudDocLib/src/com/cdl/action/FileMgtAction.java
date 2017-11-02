package com.cdl.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.cdl.entity.LibFileTransTemp;
import com.cdl.service.FileMgtService;
import com.cdl.util.SpringServiceUtil;
import com.opensymphony.xwork2.ActionSupport;

public class FileMgtAction extends ActionSupport{
	private Logger log = Logger.getLogger(FileMgtAction.class);
	private FileMgtService fileMgtService;
	private String sidStr;
	private String fileType;
	private String fileName;
	private String fileInfo;
	
	private List<LibFileTransTemp> fileList;
	/**
	 * 提交文件信息
	 * @return
	 */
	public String submitFileInfo(){
		//fileMgtService = (FileMgtService) SpringServiceUtil.getService("fileMgtService");
		if(fileList !=null && !fileList.isEmpty()){
			try{
				//提交
				this.fileMgtService.submitFile(fileList, "zhangsan");
			}catch(Exception e){
				e.printStackTrace();
				log.error("提交文件异常",e);
			}
		}
		return "submit_success";
	}

	
	
	
	
	
	
	
	
	//自动生成的get和set方法
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public FileMgtService getFileMgtService() {
		return fileMgtService;
	}

	public void setFileMgtService(FileMgtService fileMgtService) {
		this.fileMgtService = fileMgtService;
	}

	public String getSidStr() {
		return sidStr;
	}

	public void setSidStr(String sidStr) {
		this.sidStr = sidStr;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<LibFileTransTemp> getFileList() {
		return fileList;
	}

	public void setFileList(List<LibFileTransTemp> fileList) {
		this.fileList = fileList;
	}

}
