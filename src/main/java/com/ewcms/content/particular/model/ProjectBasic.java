/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 项目基本数据
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>code:项目编号(系统自动生成,格式为：行政区划代码+组织机构代码+年份+建设单位项目编号)</li>
 * <li>name:项目名称</li>
 * <li>buildTime:建设时间</li>
 * <li>investmentScale:投资规模</li>
 * <li>overview:项目概况</li>
 * <li>buildUnit:建设单位</li>
 * <li>unitId:建设单位项目编号</li>
 * <li>unitPhone:建设单位联系电话</li>
 * <li>unitAddress:建设单位地址</li>
 * <li>zoningCode:行政区划代码</li>
 * <li>organizationCode:组织机构代码</li>
 * <li>industryCode:行业编码</li>
 * <li>category:项目类别</li>
 * <li>approvalRecord:审批备案机关编号</li>
 * <li>contact:项目联系人</li>
 * <li>phone:项目联系人电话</li>
 * <li>email:项目联系人电子邮箱</li>
 * <li>address:项目地址</li>
 * <li>bildNature:建设性质</li>
 * <li>shape:形式</li>
 * <li>documentId:文号</li>
 * <li>participation:参建单位</li>
 * <li>publishingSector:发布部门</li>
 * <li>channelId:专栏编号</li>
 * </ul>
 * 
 * @author wuzhijun
 */

@Entity
@Table(name = "particular_project_basic")
@SequenceGenerator(name = "seq_particular_project_basic", sequenceName = "seq_particular_project_basic_id", allocationSize = 1)
public class ProjectBasic implements Serializable {

	private static final long serialVersionUID = -2278978849489626058L;

	/**
	 * 建设性质
	 */
	public enum Nature {
		NEW("新建"),EXPANSION("扩建"),TRANSFORM("改建和技术改造"),FACILITY("单纯建造生活设施"),RESTORATION("迁建恢复"),PURCHASE("单纯购置");
		
		private String description;
		
		private Nature(String description){
			this.description = description;
		}
		
		public String getDescription(){
			return description;
		}
	}
	
	@Id
	@GeneratedValue(generator = "seq_particular_project_basic",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "code", length = 23, nullable = false, unique = true)
	private String code;
	@Column(name = "name", length = 200, nullable = false)
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "build_time")
	private Date buildTime;
	@Column(name = "investment_scale", length = 200)
	private String investmentScale;
	@Column(name = "overview", length = 1000)
	private String overview;
	@Column(name = "build_unit", length = 200, nullable = false)
	private String buildUnit;
	@Column(name = "unit_id", length = 4, nullable = false)
	private String unitId;
	@Column(name = "unit_phone", length = 200, nullable = false)
	private String unitPhone;
	@Column(name = "unit_address", length = 200, nullable = false)
	private String unitAddress;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = ZoningCode.class)
	@JoinColumn(name="zoning_code", nullable = false, referencedColumnName = "code")
	private ZoningCode zoningCode;
	@Column(name = "organization_code", length = 9, nullable = false)
	private String organizationCode;
	@Column(name = "category")
	private String category;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = IndustryCode.class)
	@JoinColumn(name="industry_code", nullable = false, referencedColumnName = "code")
	private IndustryCode industryCode;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = ApprovalRecord.class)
	@JoinColumn(name="approval_code", nullable = false, referencedColumnName = "code")
	private ApprovalRecord approvalRecord;
	@Column(name = "contact", length = 200, nullable = false)
	private String contact;
	@Column(name = "phone", length = 200, nullable = false)
	private String phone;
	@Column(name = "email", length = 200)
	private String email;
	@Column(name = "address", length = 200, nullable = false)
	private String address;
	@Column(name = "build_nature", nullable = false)
	@Enumerated(EnumType.STRING)	
	private Nature bildNature;
	@Column(name = "shape")
	private String shape;
	@Column(name = "document_id", length = 200)
	private String documentId;
	@Column(name = "participation", length = 200)
	private String participation;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = PublishingSector.class)
	@JoinColumn(name="publishing_sector", referencedColumnName = "code")
	private PublishingSector publishingSector;
	@Column(name = "channel_id")
	private Integer channelId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}

	public String getInvestmentScale() {
		return investmentScale;
	}

	public void setInvestmentScale(String investmentScale) {
		this.investmentScale = investmentScale;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getBuildUnit() {
		return buildUnit;
	}

	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitPhone() {
		return unitPhone;
	}

	public void setUnitPhone(String unitPhone) {
		this.unitPhone = unitPhone;
	}

	public String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	public ZoningCode getZoningCode() {
		return zoningCode;
	}

	public void setZoningCode(ZoningCode zoningCode) {
		this.zoningCode = zoningCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public IndustryCode getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(IndustryCode industryCode) {
		this.industryCode = industryCode;
	}

	public ApprovalRecord getApprovalRecord() {
		return approvalRecord;
	}

	public void setApprovalRecord(ApprovalRecord approvalRecord) {
		this.approvalRecord = approvalRecord;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Nature getBildNature() {
		return bildNature;
	}

	public String getNatureDescription(){
		if (bildNature != null){
			return bildNature.getDescription();
		}else{
			return Nature.NEW.getDescription();
		}
	}
	
	public void setBildNature(Nature bildNature) {
		this.bildNature = bildNature;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getParticipation() {
		return participation;
	}

	public void setParticipation(String participation) {
		this.participation = participation;
	}

	public PublishingSector getPublishingSector() {
		return publishingSector;
	}

	public void setPublishingSector(PublishingSector publishingSector) {
		this.publishingSector = publishingSector;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectBasic other = (ProjectBasic) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
}
