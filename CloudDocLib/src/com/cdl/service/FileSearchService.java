package com.cdl.service;

import com.cdl.entity.LibFileInfo;
import com.cdl.entity.Page;

public interface FileSearchService {
	Page<LibFileInfo> searchFile(String keywords,String searchFileType,int sortType,Page<LibFileInfo> page);
}
