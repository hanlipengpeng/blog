package com.cdl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cdl.service.FileMgtService;
import com.cdl.util.SpringServiceUtil;

import common.Logger;

public class FileUploadServlet extends HttpServlet{
	
	private Logger log = Logger.getLogger(FileUploadServlet.class);
	private int maxPostSize = 64*1024*1024;  //设置文件的最大块
	private int hdfsFileNameLength = 200;    //上传的文件名字长度
	private FileMgtService fileMgtService;
	
	
	public FileUploadServlet(){
		super();
	}
	
	public void destroy(){
		super.destroy();//Just puts "destory" string in log
		//Put your code here
	}
	/**
	 * 页面的上传进行遍历，然后调用service的上传方法
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//注意，这里比较重要，获得实例的方法
		//request.getParameter("name")
		fileMgtService = (FileMgtService) SpringServiceUtil.getService("fileMgtService");
		String result = "";//定义一个输出结果
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		//把这个缓存工具传入servlet上传组件里
		ServletFileUpload upload =new ServletFileUpload(factory);
		upload.setSizeMax(maxPostSize);
		try{
			//获得到上传文件列表
			List<FileItem> fileItem = upload.parseRequest(request);
			Iterator iter = fileItem.iterator();
			while(iter.hasNext()){
				FileItem item = (FileItem)iter.next();
				if(!item.isFormField()){
					String fileName = item.getName();
					log.info("上传文件名称"+fileName);
					if(fileName!=null&&fileName.length()>hdfsFileNameLength){
						result = "文件名字过长";
					}else{
						//做文件上传操作   返回数据id
						result = fileMgtService.uploadFile(fileName, item.getInputStream(), "zhangsan");
					}
				}
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
			result = "文件上传过程异常";
		}
		response.setContentType("application/xml;charset=UTF-8");
		PrintWriter pw =response.getWriter();
		response.getWriter().write(result);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req,resp);
	}

	
	
	
	
	
	//自动生成的set 和 get 方法
	public FileMgtService getFileMgtService() {
		return fileMgtService;
	}

	public void setFileMgtService(FileMgtService fileMgtService) {
		this.fileMgtService = fileMgtService;
	}

}
