package com.cdl.entity;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import com.cdl.entity.HdfsFileInfo;

/**
 * LibFileInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class LibFileInfo implements java.io.Serializable
{

    // Fields

    private String sid;
    //密级ID
    private String secretLvlId;
    private String isOrgIsolated;
    private String orgId;
    private String hdfsFileId;
    private String fileTitle;
    private String fileDesc;
    private String fileKeyword;
    private String uploadBy;
    private String fileCategoryId;
    private Float evalValue;
    private Long evalNum;
    private Long browseNum;
    private Long downloadNum;
    private String uploadDate;
    private String createdBy;
    private String createdDate;
    private String lastUpdatedBy;
    private String lastUpdatedDate;
    private String enabledFlag;
    private Long pageNum;
    private String fileExtName;
    private Long updateIndexFlag;
    private Long price;
    
    //显示�?
    private String bestFragment;

    private String uploadDateStr;
    
    private String uploadByName;
    
    private HdfsFileInfo fileInfo;
    private String userName;
    private String halfStarFlag;
    
    /**
     * 界面属�?，分享群组id
     */
    private String groupIds;
    /**
     * 界面属�?，分享用户id
     */
    private String userIds;
    
    // Constructors

    public String getHalfStarFlag()
    {
        return halfStarFlag;
    }

    public void setHalfStarFlag(String halfStarFlag)
    {
        this.halfStarFlag = halfStarFlag;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /** default constructor */
    public LibFileInfo()
    {
    }

    /** full constructor */
    public LibFileInfo(String hdfsFileId, String fileTitle, String fileDesc, String fileKeyword, String uploadBy,
    		String fileCategoryId, Float evalValue, Long evalNum, Long browseNum, Long downloadNum,
    		String uploadDate, String createdBy, String createdDate, String lastUpdatedBy, String lastUpdatedDate,
        String enabledFlag, Long editFlag, Long downloadFlag, Long pageNum, String secretLvlId, String isOrgIsolated,
        String orgId)
    {
        this.hdfsFileId = hdfsFileId;
        this.fileTitle = fileTitle;
        this.fileDesc = fileDesc;
        this.fileKeyword = fileKeyword;
        this.uploadBy = uploadBy;
        this.fileCategoryId = fileCategoryId;
        this.evalValue = evalValue;
        this.evalNum = evalNum;
        this.browseNum = browseNum;
        this.downloadNum = downloadNum;
        this.uploadDate = uploadDate;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDate = lastUpdatedDate;
        this.enabledFlag = enabledFlag;
        this.pageNum = pageNum;
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

    public Float getEvalValue()
    {
        return this.evalValue;
    }

    public void setEvalValue(Float evalValue)
    {
        this.evalValue = evalValue;
    }
    
    public int getOncounter()
    {
        if(evalValue==null)
        {
            return 0;   
        }
        
        if(evalValue>5.0)
        {
            return 5;
        }
        
        if(evalValue==null)
        {
            return 0;
        }
        
        int count = 0 ;
        if(evalValue>=(evalValue.intValue()+0.5f))
        {
            evalValue = evalValue.intValue()+0.5F;
            count = evalValue.intValue();
        }
        
        if(evalValue<(evalValue.intValue()+0.5f))
        {
            evalValue = evalValue.intValue()+0.0F;
            count = evalValue.intValue();
        }
        
        return count;
    }
    
    public int getHalfcounter()
    {
        if(evalValue!=null)
        {
            if(evalValue==evalValue.intValue())
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
        else
        {
            return 0;
        }
    }
    
    public int getOffcounter()
    {
        return evalValue != null ? 5 - getOncounter() - getHalfcounter() : 5;
    }

    public Long getEvalNum()
    {
        return this.evalNum;
    }

    public void setEvalNum(Long evalNum)
    {
        this.evalNum = evalNum;
    }

    public Long getBrowseNum()
    {
        return this.browseNum;
    }

    public void setBrowseNum(Long browseNum)
    {
        this.browseNum = browseNum;
    }

    public Long getDownloadNum()
    {
        return this.downloadNum;
    }

    public void setDownloadNum(Long downloadNum)
    {
        this.downloadNum = downloadNum;
    }

    public String getUploadDate()
    {
        return this.uploadDate;
    }

    public void setUploadDate(String uploadDate)
    {
        this.uploadDate = uploadDate;
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

    public Long getPageNum()
    {
        return this.pageNum;
    }

    public void setPageNum(Long pageNum)
    {
        this.pageNum = pageNum;
    }
    @Override
	public String toString() {
		return "LibFileInfo [sid=" + sid + ", secretLvlId=" + secretLvlId
				+ ", isOrgIsolated=" + isOrgIsolated + ", orgId=" + orgId
				+ ", hdfsFileId=" + hdfsFileId + ", fileTitle=" + fileTitle
				+ ", fileDesc=" + fileDesc + ", fileKeyword=" + fileKeyword
				+ ", uploadBy=" + uploadBy + ", fileCategoryId="
				+ fileCategoryId + ", evalValue=" + evalValue + ", evalNum="
				+ evalNum + ", browseNum=" + browseNum + ", downloadNum="
				+ downloadNum + ", uploadDate=" + uploadDate + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedDate="
				+ lastUpdatedDate + ", enabledFlag=" + enabledFlag
				+ ", pageNum=" + pageNum + ", fileExtName=" + fileExtName
				+ ", updateIndexFlag=" + updateIndexFlag + ", price=" + price
				+ ", bestFragment=" + bestFragment + ", uploadDateStr="
				+ uploadDateStr + ", uploadByName=" + uploadByName
				+ ", fileInfo=" + fileInfo + ", userName=" + userName
				+ ", halfStarFlag=" + halfStarFlag + ", groupIds=" + groupIds
				+ ", userIds=" + userIds + "]";
	}

    public String getFileExtName()
    {
        return fileExtName;
    }

    public void setFileExtName(String fileExtName)
    {
        this.fileExtName = fileExtName;
    }

    public String getUploadDateStr()
    {
        return uploadDateStr;
    }

    public void setUploadDateStr(String uploadDateStr)
    {
        this.uploadDateStr = uploadDateStr;
    }

    public String getBestFragment()
    {
        return bestFragment;
    }

    public void setBestFragment(String bestFragment)
    {
        this.bestFragment = bestFragment;
    }

    public String getUploadByName()
    {
        return uploadByName;
    }

    public void setUploadByName(String uploadByName)
    {
        this.uploadByName = uploadByName;
    }

    public Long getUpdateIndexFlag()
    {
        return updateIndexFlag;
    }

    public void setUpdateIndexFlag(Long updateIndexFlag)
    {
        this.updateIndexFlag = updateIndexFlag;
    }

    public HdfsFileInfo getFileInfo()
    {
        return fileInfo;
    }

    public void setFileInfo(HdfsFileInfo fileInfo)
    {
        this.fileInfo = fileInfo;
    }

    public Long getPrice()
    {
        return price;
    }

    public void setPrice(Long price)
    {
        this.price = price;
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

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
