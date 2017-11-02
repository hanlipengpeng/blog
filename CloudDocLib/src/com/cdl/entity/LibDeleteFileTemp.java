package com.cdl.entity;

import java.util.Date;

/**
 * LibDeleteFileTemp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class LibDeleteFileTemp implements java.io.Serializable
{

    // Fields

    private String sid;
    private String hdfsFileId;
    private String uploadBy;
    private String fileCategoryId;
    private String createdBy;
    private String createdDate;
    private String lastUpdatedBy;
    private String lastUpdatedDate;
    private String enabledFlag;

    // Constructors

    /** default constructor */
    public LibDeleteFileTemp()
    {
    }

    /** full constructor */
    public LibDeleteFileTemp(String hdfsFileId, String uploadBy, String fileCategoryId, String createdBy, String createdDate,
    		String lastUpdatedBy, String lastUpdatedDate, String enabledFlag)
    {
        this.hdfsFileId = hdfsFileId;
        this.uploadBy = uploadBy;
        this.fileCategoryId = fileCategoryId;
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

    public String getHdfsFileId()
    {
        return this.hdfsFileId;
    }

    public void setHdfsFileId(String hdfsFileId)
    {
        this.hdfsFileId = hdfsFileId;
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

}