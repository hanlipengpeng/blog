package com.cdl.entity;

import java.util.Date;

/**
 * LibDownloadInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class LibDownloadInfo implements java.io.Serializable
{

    // Fields

    private String sid;
    private String libUserId;
    private String fileId;
    private String createdBy;
    private String createdDate;
    private String lastUpdatedBy;
    private String lastUpdatedDate;
    private String enabledFlag;

    // Constructors

    /** default constructor */
    public LibDownloadInfo()
    {
    }

    /** full constructor */
    public LibDownloadInfo(String libUserId, String fileId, String createdBy, String createdDate, String lastUpdatedBy,
    		String lastUpdatedDate, String enabledFlag)
    {
        this.libUserId = libUserId;
        this.fileId = fileId;
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

    public String getLibUserId()
    {
        return this.libUserId;
    }

    public void setLibUserId(String libUserId)
    {
        this.libUserId = libUserId;
    }

    public String getFileId()
    {
        return this.fileId;
    }

    public void setFileId(String fileId)
    {
        this.fileId = fileId;
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