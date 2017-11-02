package com.cdl.entity;


public class InfoCode implements java.io.Serializable {

	// Fields

	private String sid;
	private Long version;
	private String describe;
	private String codeType;
	private String code;
	private Long isLeaf;
	private String parentId;
	private Long levelNo;
	private String codeValue;
	private String remark;
	private String createdBy;
	private String createdDate;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
	private String enabledFlag;

	//以下字段为页面显�?不予数据库关�?
	private String isChecked;
	
	// Constructors

	/** default constructor */
	public InfoCode() {
	}

	/** full constructor */
	public InfoCode(Long version, String describe, String codeType,
			String code, Long isLeaf, String parentId, Long levelNo,
			String codeValue, String remark, String createdBy, String createdDate,
			String lastUpdatedBy, String lastUpdatedDate, String enabledFlag) {
		this.version = version;
		this.describe = describe;
		this.codeType = codeType;
		this.code = code;
		this.isLeaf = isLeaf;
		this.parentId = parentId;
		this.levelNo = levelNo;
		this.codeValue = codeValue;
		this.remark = remark;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
		this.enabledFlag = enabledFlag;
	}

	// Property accessors

	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getIsLeaf() {
		return this.isLeaf;
	}

	public void setIsLeaf(Long isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Long getLevelNo() {
		return this.levelNo;
	}

	public void setLevelNo(Long levelNo) {
		this.levelNo = levelNo;
	}

	public String getCodeValue() {
		return this.codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getEnabledFlag() {
		return this.enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

}