/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.notes.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 备忘�?
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>title:标题</li>
 * <li>content:内容</li>
 * <li>noteDate:日期</li>
 * <li>userName:用户�?/li>
 * <li>warn:是否提醒</li>
 * <li>warnTime:提醒时间</li>
 * <li>frequency:提醒频率</li>
 * <li>before:提前时间</li>
 * <li>fireTime:触发时间</li>
 * <li>missRemind:错过是否提醒(true:�?false:�?</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "doc_memoranda")
@SequenceGenerator(name = "seq_doc_memoranda", sequenceName = "seq_doc_memoranda_id", allocationSize = 1)
public class Memoranda implements Serializable {

	private static final long serialVersionUID = -6072138684705920059L;

	@Id
    @GeneratedValue(generator = "seq_doc_memoranda",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "title", nullable = false, length = 50)
	private String title;
	@Column(name = "content", columnDefinition = "text")
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notedate", nullable = false)
	private Date noteDate;
	@Column(name = "username", nullable = false)
	private String userName;
	@Column(name = "warn", nullable = false)
	private Boolean warn;
	@Temporal(TemporalType.TIME)
	@Column(name = "warntime")
	private Date warnTime;
	@Column(name = "frequency")
	@Enumerated(EnumType.STRING)
	private FrequencyStatus frequency;
	@Column(name = "remind")
	@Enumerated(EnumType.STRING)
	private BeforeStatus before;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "firetime")
	private Date fireTime;
	@Column(name = "missremind")
	private Boolean missRemind;

	public Memoranda(){
		warn = false;
		missRemind = false;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getNoteDate() {
		return noteDate;
	}

	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getWarn() {
		return warn;
	}

	public void setWarn(Boolean warn) {
		this.warn = warn;
	}

	public Date getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(Date warnTime) {
		this.warnTime = warnTime;
	}

	public FrequencyStatus getFrequency() {
		return frequency;
	}

	public void setFrequency(FrequencyStatus frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyDescription(){
		if (frequency != null){
			return frequency.getDescription();
		}else{
			return FrequencyStatus.SINGLE.getDescription();
		}
	}
	
	public BeforeStatus getBefore() {
		return before;
	}

	public void setBefore(BeforeStatus before) {
		this.before = before;
	}

	public String getBeforeDescription(){
		if (before != null){
			return before.getDescription();
		}else{
			return BeforeStatus.NONE.getDescription();
		}
	}
	
	public Date getFireTime() {
		return fireTime;
	}

	public void setFireTime(Date fireTime) {
		this.fireTime = fireTime;
	}

	public Boolean getMissRemind() {
		return missRemind;
	}

	public void setMissRemind(Boolean missRemind) {
		this.missRemind = missRemind;
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
		Memoranda other = (Memoranda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
