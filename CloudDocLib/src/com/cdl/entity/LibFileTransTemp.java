package com.cdl.entity;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class LibFileTransTemp implements java.io.Serializable
{

    // 与数据库对应的表的类

    private String sid;
    private String secretLvlId;
    private String isOrgIsolated;
    private String hdfsFileId;
    private String fileTitle;
    private String fileDesc;
    private String fileKeyword;
    private String uploadBy;
    private String fileCategoryId;
    private String uploadDate;
    private Long  fileStatus;
    private String createdBy;
    private String createdDate;
    private String lastUpdatedBy;
    private String lastUpdatedDate;
    private String enabledFlag;
    private String fileExtName;
    private Long price;
    private Long retryTime;
    private String exceptionMsg;
    private String emailId;
    private String orgId;
    
    // Constructors
    
    /**
     * �������ԣ�����Ⱥ��id
     */
    private String groupIds;
    /**
     * �������ԣ������û�id
     */
    private String userIds;
    

    /** default constructor */
    public LibFileTransTemp()
    {
    }

    /** full constructor */
    public LibFileTransTemp(String hdfsFileId, String fileTitle, String fileDesc, String fileKeyword, String uploadBy,
    		String fileCategoryId, String uploadDate, Long fileStatus, String createdBy, String createdDate, String lastUpdatedBy,
    		String lastUpdatedDate, String enabledFlag, String secretLvlId, String isOrgIsolated, String orgId)
    {
        this.hdfsFileId = hdfsFileId;
        this.fileTitle = fileTitle;
        this.fileDesc = fileDesc;
        this.fileKeyword = fileKeyword;
        this.uploadBy = uploadBy;
        this.fileCategoryId = fileCategoryId;
        this.uploadDate = uploadDate;
        this.fileStatus = fileStatus;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDate = lastUpdatedDate;
        this.enabledFlag = enabledFlag;
        this.secretLvlId = secretLvlId;
        this.isOrgIsolated = isOrgIsolated;
        this.orgId = orgId;
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

    public String getHdfsFileId()
    {
        return this.hdfsFileId;
    }

    public void setHdfsFileId(String hdfsFileId)
    {
        this.hdfsFileId = hdfsFileId;
    }

    public String getFileTitle()
    {
        return this.fileTitle;
    }

    public void setFileTitle(String fileTitle)
    {
        this.fileTitle = fileTitle;
    }

    public String getFileDesc()
    {
        return this.fileDesc;
    }

    public void setFileDesc(String fileDesc)
    {
        this.fileDesc = fileDesc;
    }

    public String getFileKeyword()
    {
        return this.fileKeyword;
    }

    public void setFileKeyword(String fileKeyword)
    {
        this.fileKeyword = fileKeyword;
    }

    public String getUploadBy()
    {
        return this.uploadBy;
    }

    public void setUploadBy(String uploadBy)
    {
        this.uploadBy = uploadBy;
    }

    public String getFileCategoryId()
    {
        return this.fileCategoryId;
    }

    public void setFileCategoryId(String fileCategoryId)
    {
        this.fileCategoryId = fileCategoryId;
    }

    public String getUploadDate()
    {
        return this.uploadDate;
    }

    public void setUploadDate(String uploadDate)
    {
        this.uploadDate = uploadDate;
    }

    public Long getFileStatus()
    {
        return this.fileStatus;
    }

    public void setFileStatus(Long fileStatus)
    {
        this.fileStatus = fileStatus;
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
    @Override
	public String toString() {
		return "LibFileTransTemp [sid=" + sid + ", secretLvlId=" + secretLvlId
				+ ", isOrgIsolated=" + isOrgIsolated + ", hdfsFileId="
				+ hdfsFileId + ", fileTitle=" + fileTitle + ", fileDesc="
				+ fileDesc + ", fileKeyword=" + fileKeyword + ", uploadBy="
				+ uploadBy + ", fileCategoryId=" + fileCategoryId
				+ ", uploadDate=" + uploadDate + ", fileStatus=" + fileStatus
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedDate="
				+ lastUpdatedDate + ", enabledFlag=" + enabledFlag
				+ ", fileExtName=" + fileExtName + ", price=" + price
				+ ", retryTime=" + retryTime + ", exceptionMsg=" + exceptionMsg
				+ ", emailId=" + emailId + ", orgId=" + orgId + ", groupIds="
				+ groupIds + ", userIds=" + userIds + "]";
	}

    public String getFileExtName()
    {
        return fileExtName;
    }

    public void setFileExtName(String fileExtName)
    {
        this.fileExtName = fileExtName;
    }

    public Long getPrice()
    {
        return price;
    }

    public void setPrice(Long price)
    {
        this.price = price;
    }

    public Long getRetryTime()
    {
        return retryTime;
    }

    public void setRetryTime(Long retryTime)
    {
        this.retryTime = retryTime;
    }

    public String getExceptionMsg()
    {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg)
    {
        this.exceptionMsg = exceptionMsg;
    }

    public String getEmailId()
    {
        return emailId;
    }

    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}


	public String getSecretLvlId() {
		return secretLvlId;
	}

	public void setSecretLvlId(String secretLvlId) {
		this.secretLvlId = secretLvlId;
	}

	public String getIsOrgIsolated() {
		return isOrgIsolated;
	}

	public void setIsOrgIsolated(String isOrgIsolated) {
		this.isOrgIsolated = isOrgIsolated;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String string) {
		this.orgId = string;
	}
	
}
