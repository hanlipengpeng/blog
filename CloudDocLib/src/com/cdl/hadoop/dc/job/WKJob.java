package com.cdl.hadoop.dc.job;

import java.io.Serializable;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class WKJob implements Serializable{

	private static final long serialVersionUID = 1L;
 
	private String input; //任务所操作的文件HDFS路径
    private int jobType; //任务类型
    
    @PrimaryKey
    private String fileMetaData; //文件元数据信息
    private int indexType;
    private String extra;
    @Override
	public String toString() {
		return "WKJob [extra=" + extra + ", fileMetaData=" + fileMetaData
				+ ", indexType=" + indexType + ", input=" + input
				+ ", jobType=" + jobType + "]";
	}
	public WKJob(){}
	public WKJob(String input, int jobType, String fileMetaData, int indexType,
			String extra) {
		super();
		this.input = input;
		this.jobType = jobType;
		this.fileMetaData = fileMetaData;
		this.indexType = indexType;
		this.extra = extra;
	}
	   public String getInput() {
			return input;
		}
		public void setInput(String input) {
			this.input = input;
		}
		public int getJobType() {
			return jobType;
		}
		public void setJobType(int jobType) {
			this.jobType = jobType;
		}
		public String getFileMetaData() {
			return fileMetaData;
		}
		public void setFileMetaData(String fileMetaData) {
			this.fileMetaData = fileMetaData;
		}
		public int getIndexType() {
			return indexType;
		}
		public void setIndexType(int indexType) {
			this.indexType = indexType;
		}
		public String getExtra() {
			return extra;
		}
		public void setExtra(String extra) {
			this.extra = extra;
		}
	
}
