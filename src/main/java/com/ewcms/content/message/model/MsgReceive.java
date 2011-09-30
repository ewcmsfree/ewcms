/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * 信息接收
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>userName:用户</li>
 * <li>read:读取标志</li>
 * <li>status:状态</li>
 * <li>readTime:读取时间</li>
 * <li>msgContent:消息内容对象</li>
 * <li>sendUserName:发送者</li>
 * <li>subscription:订阅标志</li>
 * </ul>
 * 
 * @author wu_zhijun
 * 
 */
@Entity
@Table(name = "message_receive")
@SequenceGenerator(name = "seq_message_receive", sequenceName = "seq_message_receive_id", allocationSize = 1)
public class MsgReceive implements Serializable {

	private static final long serialVersionUID = -8925782877706243408L;

	@Id
    @GeneratedValue(generator = "seq_message_receive",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "username", nullable = false)
	private String userName;
	@Column(name = "read")
	private Boolean read;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private MsgStatus status;
	@Column(name = "readtime")
	private Date readTime;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = MsgContent.class)
	@JoinColumn(name="receive_content_id")
	private MsgContent msgContent;
	@Column(name = "send_username")
	private String sendUserName;
	@Column(name = "subscription")
	private Boolean subscription;

	public MsgReceive(){
		read = false;
		subscription = false;
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

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public MsgStatus getStatus() {
		return status;
	}

	public void setStatus(MsgStatus status) {
		this.status = status;
	}
	
	public String getStatusDescription(){
		return status.getDescription();
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public MsgContent getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(MsgContent msgContent) {
		this.msgContent = msgContent;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public Boolean getSubscription() {
		return subscription;
	}

	public void setSubscription(Boolean subscription) {
		this.subscription = subscription;
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
		MsgReceive other = (MsgReceive) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
