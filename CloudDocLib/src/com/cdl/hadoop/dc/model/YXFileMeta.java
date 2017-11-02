package com.cdl.hadoop.dc.model;

import com.cdl.entity.HdfsFileInfo;
import com.cdl.entity.LibFileInfo;
import com.cdl.entity.LibFileTransTemp;

/**
 * 文件元数据类
 * @author root
 *
 */
public class YXFileMeta {

	public static String fileMetaData(LibFileTransTemp tmpInfo,LibFileInfo fileInfo,HdfsFileInfo hfi,int jobType){
		StringBuffer sb = new StringBuffer();
		if(jobType == XNG.YXLIB_JOB_TYPE_FILE_CONVERT){
			
			//一共9项
			sb.append(tmpInfo.getSid());
			sb.append(",");
			sb.append(hfi.getFileName());
			sb.append(",");
			sb.append(hfi.getFilePath());
			sb.append(",");
			sb.append("zhangsan");
			sb.append(",");
			sb.append("张三");
			sb.append(",");
			sb.append(tmpInfo.getUploadDate());
			sb.append(",");
			sb.append("0");//页数
			sb.append(",");
			sb.append(tmpInfo.getFileTitle());
			sb.append(",");
			sb.append(hfi.getFileExtName());
			
		}else if(jobType == XNG.YXLIB_JOB_TYPE_INDEX_BUILD){
			sb.append(fileInfo.getSid());
			sb.append(",");
			sb.append(hfi.getFileName());
			sb.append(",");
			sb.append(hfi.getFilePath());
			sb.append(",");
			sb.append("zhangsan");
			sb.append(",");
			sb.append("张三");
			sb.append(",");
			sb.append(fileInfo.getUploadDate());
			sb.append(",");
			sb.append(fileInfo.getPageNum());//页数
			sb.append(",");
			sb.append(fileInfo.getFileTitle());
			sb.append(",");
			sb.append(hfi.getFileExtName());
		}
		sb.append(",");
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}
	
}
