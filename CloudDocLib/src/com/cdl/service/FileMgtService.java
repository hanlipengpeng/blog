package com.cdl.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.cdl.entity.LibFileTransTemp;
/**
 * 文件操作接口
 * @author root
 *
 */
public interface FileMgtService {
	String uploadFile(String fileName,InputStream is,String userId) throws IOException;
	
	void submitFile(List<LibFileTransTemp> fileList,String string) throws IOException;

}
