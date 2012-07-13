/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.ewcms.core.site.model.Organ;
import com.ewcms.plugin.citizen.model.Citizen;

/**
 * 事项基本信息
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>name:名称</li>
 * <li>acceptedWay:受理方式</li>
 * <li>handleSite:办理地点</li>
 * <li>handleBasis:办理依据</li>
 * <li>handleWay:审批、服务数量及方式</li>
 * <li>acceptedCondition:受理条件</li>
 * <li>petitionMaterial:申请材料</li>
 * <li>handleCourse:办理程序</li>
 * <li>timeLimit:法定时限</li>
 * <li>deadline:承诺期限</li>
 * <li>fees:收费标准</li>
 * <li>feesBasis:收费依据</li>
 * <li>consultingTel:咨询电话</li>
 * <li>contactName:联系人</li>
 * <li>department:所在部门</li>
 * <li>contactTel:联系电话</li>
 * <li>email:E-Mail</li>
 * <li>sort:排序</li>
 * <li>workingBodys:网上办事对象集合</li>
 * <li>organ:组织机构对象</li>
 * <li>matterAnnexs:事项附件对象集合</li>
 * <li>citizens:公民对象集合</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "plugin_matter")
@SequenceGenerator(name = "seq_plugin_matter", sequenceName = "seq_plugin_matter_id", allocationSize = 1)
public class Matter implements Serializable {

	private static final long serialVersionUID = -3169015550175955635L;

	@Id
	@GeneratedValue(generator = "seq_plugin_matter", strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column(name = "matter_name", nullable = false)
	private String name;
	@Column(name = "accepted_way", columnDefinition = "text")
	private String acceptedWay;
	@Column(name = "handle_site", columnDefinition = "text")
	private String handleSite;
	@Column(name = "handle_basis", columnDefinition = "text")
	private String handleBasis;
	@Column(name = "handle_way", columnDefinition = "text")
	private String handleWay;
	@Column(name = "accepted_condition", columnDefinition = "text")
	private String acceptedCondition;
	@Column(name = "petition_material", columnDefinition = "text")
	private String petitionMaterial;
	@Column(name = "handle_course", columnDefinition = "text")
	private String handleCourse;
	@Column(name = "time_limit", columnDefinition = "text")
	private String timeLimit;
	@Column(name = "deadline", columnDefinition = "text")
	private String deadline;
	@Column(name = "fees", columnDefinition = "text")
	private String fees;
	@Column(name = "fees_basis", columnDefinition = "text")
	private String feesBasis;
	@Column(name = "consulting_tel", columnDefinition = "text")
	private String consultingTel;
	@Column(name = "contact_name", columnDefinition = "text")
	private String contactName;
	@Column(name = "department", columnDefinition = "text")
	private String department;
	@Column(name = "contact_tel", columnDefinition = "text")
	private String contactTel;
	@Column(name = "email", columnDefinition = "text")
	private String email;
	@Column(name = "sort")
	private Long sort;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "matters")
    @OrderBy(value = "sort,id")
    private List<WorkingBody> workingBodys;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Organ.class)
	@JoinColumn(name = "organ_id")
    private Organ organ;
	@OneToMany(cascade = CascadeType.ALL, targetEntity = MatterAnnex.class)
	@JoinColumn(name = "matter_id")
	@OrderBy(value = "sort,id")
	private List<MatterAnnex> matterAnnexs;
	@ManyToMany(cascade = {CascadeType.ALL}, targetEntity = Citizen.class)
	@JoinTable(name = "plugin_matter_citizen", joinColumns = @JoinColumn(name = "matter_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "citizen_id", referencedColumnName = "id"))
	@OrderBy(value = "id")
	private List<Citizen> citizens;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Citizen> getCitizens() {
		return citizens;
	}

	public void setCitizens(List<Citizen> citizens) {
		this.citizens = citizens;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcceptedWay() {
		return acceptedWay;
	}

	public void setAcceptedWay(String acceptedWay) {
		this.acceptedWay = acceptedWay;
	}

	public String getHandleSite() {
		return handleSite;
	}

	public void setHandleSite(String handleSite) {
		this.handleSite = handleSite;
	}

	public String getHandleBasis() {
		return handleBasis;
	}

	public void setHandleBasis(String handleBasis) {
		this.handleBasis = handleBasis;
	}

	public String getHandleWay() {
		return handleWay;
	}

	public void setHandleWay(String handleWay) {
		this.handleWay = handleWay;
	}

	public String getAcceptedCondition() {
		return acceptedCondition;
	}

	public void setAcceptedCondition(String acceptedCondition) {
		this.acceptedCondition = acceptedCondition;
	}

	public String getPetitionMaterial() {
		return petitionMaterial;
	}

	public void setPetitionMaterial(String petitionMaterial) {
		this.petitionMaterial = petitionMaterial;
	}

	public String getHandleCourse() {
		return handleCourse;
	}

	public void setHandleCourse(String handleCourse) {
		this.handleCourse = handleCourse;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getFeesBasis() {
		return feesBasis;
	}

	public void setFeesBasis(String feesBasis) {
		this.feesBasis = feesBasis;
	}

	public String getConsultingTel() {
		return consultingTel;
	}

	public void setConsultingTel(String consultingTel) {
		this.consultingTel = consultingTel;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	@JsonIgnore
	public List<WorkingBody> getWorkingBodys() {
		return workingBodys;
	}

	public void setWorkingBodys(List<WorkingBody> workingBodys) {
		this.workingBodys = workingBodys;
	}

	@JsonIgnore
	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	@JsonIgnore
	public List<MatterAnnex> getMatterAnnexs() {
		return matterAnnexs;
	}

	public void setMatterAnnexs(List<MatterAnnex> matterAnnexs) {
		this.matterAnnexs = matterAnnexs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Matter other = (Matter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
