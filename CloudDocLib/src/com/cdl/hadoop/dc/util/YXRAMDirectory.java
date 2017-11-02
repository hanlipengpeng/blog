package com.cdl.hadoop.dc.util;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 * �ڴ�����ˢ��
 * @author root
 *
 */
public class YXRAMDirectory extends RAMDirectory implements Serializable{

	private static final Log log = LogFactory.getLog(YXRAMDirectory.class);
	public YXRAMDirectory(){super();}
	public YXRAMDirectory(Directory dir) throws IOException{
		super(dir);
	}
	//1：删除原有的缓存索引
	//2：同步目录下的索引文件到内存
	synchronized public void sync(Directory dir) throws IOException{
		String[] all = listAll();
		for(int j=0;j<all.length;j++){
			deleteFile(all[j]);
		}
		Directory.copy(dir, this, false);
	}
}
