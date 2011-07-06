/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 调查问卷主体
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>title:调查主题</li>
 * <li>number:投票人数</li>
 * <li>startTime:开始时间</li>
 * <li>endTime:结束时间</li>
 * <li>channelId:频道编号</li>
 * <li>questionnaireStatus:查看状态</li>
 * <li>verifiCode:验证码</li>
 * <li>subjects:调查与投票明细对象集合</li>
 * <li>sort:排序</li>
 * <li>voteFalg:结束标志</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "vote_questionnaire")
@SequenceGenerator(name = "seq_vote_questionnaire", sequenceName = "seq_vote_questionnaire_id", allocationSize = 1)
public class Questionnaire implements Serializable {

	private static final long serialVersionUID = -6666565689620227616L;

	@Id
	@GeneratedValue(generator = "seq_vote_questionnaire", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "title", nullable = false, length = 100)
	private String title;
	@Column(name = "number", nullable = false)
	private Long number;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "starttime")
	private Date startTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endtime")
	private Date endTime;
	@Column(name = "channel_id", nullable = false)
	private Integer channelId;
	@Enumerated(EnumType.STRING)
	@Column(name = "questionnaire_status", nullable = false)
	private QuestionnaireStatus questionnaireStatus;
	@Column(name = "verificode")
	private Boolean verifiCode;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Subject.class, orphanRemoval = true)
	@JoinColumn(name = "questionnaire_id")
	@OrderBy(value = "sort")
	private List<Subject> subjects = new ArrayList<Subject>();
	@Column(name = "sort")
	private Long sort;
	@Column(name = "voteflag")
	private Boolean voteFlag;
	
	public Questionnaire(){
		number = 0L;
		verifiCode = false;
		questionnaireStatus = QuestionnaireStatus.VIEW;
		voteFlag = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public QuestionnaireStatus getQuestionnaireStatus() {
		return questionnaireStatus;
	}

	public void setQuestionnaireStatus(QuestionnaireStatus questionnaireStatus) {
		this.questionnaireStatus = questionnaireStatus;
	}

	public String getQuestionnaireStatusDescription(){
		if (questionnaireStatus != null){
			return questionnaireStatus.getDescription();
		}else{
			return QuestionnaireStatus.VIEW.getDescription();
		}
	}
	
	public Boolean getVerifiCode() {
		return verifiCode;
	}

	public void setVerifiCode(Boolean verifiCode) {
		this.verifiCode = verifiCode;
	}

	@JsonIgnore
	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Boolean getVoteFlag() {
		return voteFlag;
	}

	public void setVoteFlag(Boolean voteFlag) {
		this.voteFlag = voteFlag;
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
		Questionnaire other = (Questionnaire) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
