/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 文章操作记录
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>userName:操作员</li>
 * <li>statusDesc:状态描述</li>
 * <li>operateTime:操作时间</li>
 * <li>description:描述</li>
 * </ul>
 * 
 * @author wu_zhijun
 * 
 */
@Entity
@Table(name = "doc_article_operate_track")
@SequenceGenerator(name = "seq_doc_article_operate_track", sequenceName = "seq_doc_article_operate_track_id", allocationSize = 1)
public class ArticleOperateTrack implements Serializable {

	private static final long serialVersionUID = -1094223279369849132L;

	@Id
	@GeneratedValue(generator = "seq_doc_article_operate_track", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "username")
	private String userName;
	@Column(name= "statusdesc")
	private String statusDesc;
	@Column(name = "operatetime")
	private Date operateTime;
	@Column(name = "description", columnDefinition = "text")
	private String description;
	
	public ArticleOperateTrack(){
		operateTime = new Date(Calendar.getInstance().getTime().getTime());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
