/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 投票人员信息
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>ip:IP</li>
 * <li>questionnaireId:问卷调查编号</li>
 * <li>records:用户填写问卷调查结果对象集合</li>
 * <li>recordTime:投票时间</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "vote_person")
@SequenceGenerator(name = "seq_vote_person", sequenceName = "seq_vote_person_id", allocationSize = 1)
public class Person implements Serializable {

	private static final long serialVersionUID = 4350370572885048704L;

	@Id
	@GeneratedValue(generator = "seq_vote_person", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "ip", nullable = false)
	private String ip;
	@Column(name = "questionnaire_id")
	private Long questionnaireId;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Record.class, orphanRemoval = true)
	@JoinColumn(name = "person_id")
	@OrderBy(value = "id")
	private List<Record> records = new ArrayList<Record>();
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "recordtime")
	private Date recordTime;
	
	public Person(){
		recordTime = new Date(Calendar.getInstance().getTime().getTime());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
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
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
