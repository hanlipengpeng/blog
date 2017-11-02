package com.cdl.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.cdl.entity.IFileInfo;

/**
 * 对应HDFS_FILE_INFO�?
 */

public class HdfsFileInfo implements java.io.Serializable
{

    // Fields

    private String sid;
	private String systemId;
    private String userId;
    private String fileRealName;
    private String fileName;
    private String fileExtName;
    private String filePath;
    private Long fileSize;
    private Long fileReplicationNum;
    private Long fileBlockNum;
    private String fileUploadDate;
    private String createdBy;
    private String createdDate;
    private String lastUpdatedBy;
    private String lastUpdatedDate;
    private String enabledFlag;
    private String fileMd5;

    // Constructors

    @Override
	public String toString() {
		return "HdfsFileInfo [sid=" + sid + ", systemId=" + systemId
				+ ", userId=" + userId + ", fileRealName=" + fileRealName
				+ ", fileName=" + fileName + ", fileExtName=" + fileExtName
				+ ", filePath=" + filePath + ", fileSize=" + fileSize
				+ ", fileReplicationNum=" + fileReplicationNum
				+ ", fileBlockNum=" + fileBlockNum + ", fileUploadDate="
				+ fileUploadDate + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", lastUpdatedBy="
				+ lastUpdatedBy + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", enabledFlag=" + enabledFlag + ", fileMd5=" + fileMd5 + "]";
	}

	public String getFileMd5()
    {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5)
    {
        this.fileMd5 = fileMd5;
    }

    /** default constructor */
    public HdfsFileInfo()
    {
    }

    /** full constructor */
    public HdfsFileInfo(String systemId, String userId, String fileRealName, String fileName, String fileExtName,
        String filePath, Long fileSize, Long fileReplicationNum, Long fileBlockNum, String fileUploadDate,
        String createdBy, String createdDate, String lastUpdatedBy, String lastUpdatedDate, String enabledFlag)
    {
        this.systemId = systemId;
        this.userId = userId;
        this.fileRealName = fileRealName;
        this.fileName = fileName;
        this.fileExtName = fileExtName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileReplicationNum = fileReplicationNum;
        this.fileBlockNum = fileBlockNum;
        this.fileUploadDate = fileUploadDate;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDate = lastUpdatedDate;
        this.enabledFlag = enabledFlag;
    }

    // Property accessors

    public String getSid()
    {
        return this.sid;
    }

    public void setSid(String sid)
    {
        this.sid = sid;
    }

    public String getSystemId()
    {
        return this.systemId;
    }

    public void setSystemId(String systemId)
    {
        this.systemId = systemId;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getFileRealName()
    {
        return this.fileRealName;
    }

    public void setFileRealName(String fileRealName)
    {
        this.fileRealName = fileRealName;
    }

    public String getFileName()
    {
        return this.fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileExtName()
    {
        return this.fileExtName;
    }

    public void setFileExtName(String fileExtName)
    {
        this.fileExtName = fileExtName;
    }

    public String getFilePath()
    {
        return this.filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public Float getKbSize()
    {
        if(fileSize != null && fileSize.longValue() > 0)
        {
            BigDecimal size = new BigDecimal(fileSize);
            
            return size.divide(new BigDecimal(1024), 2, RoundingMode.HALF_DOWN).floatValue();
        }
        
        return 0.0f;
    }
    
    public Long getFileSize()
    {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
    }

    public Long getFileReplicationNum()
    {
        return this.fileReplicationNum;
    }

    public void setFileReplicationNum(Long fileReplicationNum)
    {
        this.fileReplicationNum = fileReplicationNum;
    }

    public Long getFileBlockNum()
    {
        return this.fileBlockNum;
    }

    public void setFileBlockNum(Long fileBlockNum)
    {
        this.fileBlockNum = fileBlockNum;
    }


    public String getCreatedBy()
    {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedDate()
    {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedBy()
    {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedDate()
    {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate)
    {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getEnabledFlag()
    {
        return this.enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag)
    {
        this.enabledFlag = enabledFlag;
    }

	public String getId() {
		return this.sid;
	}

	public void setId(String id) {
		this.sid = id;
		
	}
    public String getFileUploadDate() {
		return fileUploadDate;
	}

	public void setFileUploadDate(String fileUploadDate) {
		this.fileUploadDate = fileUploadDate;
	}


}