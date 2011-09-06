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
 * <li>reason:原因</li>
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
	@Column(name = "userrealname")
	private String userRealName;
	@Column(name= "statusdesc")
	private String statusDesc;
	@Column(name = "operatetime")
	private Date operateTime;
	@Column(name = "description", columnDefinition = "text")
	private String description;
	@Column(name = "reason", columnDefinition = "text")
	private String reason;
	
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

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
		ArticleOperateTrack other = (ArticleOperateTrack) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
