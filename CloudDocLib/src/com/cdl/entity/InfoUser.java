package com.cdl.entity;

import java.util.Date;

/**
 * InfoUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InfoUser implements java.io.Serializable {

	// Fields

	private Long sid;
	private Long version;
	private String userCode;
	private String userName;
	private String gender;
	private String password;
	private String phone;
	private String mobile;
	private Long orgId;
	private String email;
	private String remark;
	private Long picSid;
	private Long createdBy;
	private Date creatiedDate;
	private Long lastUpdatedBy;
	private Date lastUpdatedDate;
	private String enabledFlag;
	private Long postId;

	private String retMsg;
	private String orgName;
	// Constructors

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	/** default constructor */
	public InfoUser() {
	}

	/** full constructor */
	public InfoUser(Long version, String userCode, String userName,
			String gender, String password, String phone, String mobile,
			Long orgId, String email, String remark, Long picSid,
			Long createdBy, Date creatiedDate, Long lastUpdatedBy,
			Date lastUpdatedDate, String enabledFlag) {
		this.version = version;
		this.userCode = userCode;
		this.userName = userName;
		this.gender = gender;
		this.password = password;
		this.phone = phone;
		this.mobile = mobile;
		this.orgId = orgId;
		this.email = email;
		this.remark = remark;
		this.picSid = picSid;
		this.createdBy = createdBy;
		this.creatiedDate = creatiedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
		this.enabledFlag = enabledFlag;
	}

	// Property accessors

	public Long getSid() {
		return this.sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPicSid() {
		return this.picSid;
	}

	public void setPicSid(Long picSid) {
		this.picSid = picSid;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatiedDate() {
		return this.creatiedDate;
	}

	public void setCreatiedDate(Date creatiedDate) {
		this.creatiedDate = creatiedDate;
	}

	public Long getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getEnabledFlag() {
		return this.enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

}