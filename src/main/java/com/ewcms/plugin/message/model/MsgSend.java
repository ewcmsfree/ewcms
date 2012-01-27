/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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

/**
 * 消息发送
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>userName:用户</li>
 * <li>title:标题</li>
 * <li>sendTime:发送时间</li>
 * <li>type:类型</li>
 * <li>status:状态</li>
 * <li>msgContents:内容对象集合</li>
 * <li>receiveUserNames:接收者</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "plugin_message_send")
@SequenceGenerator(name = "seq_plugin_message_send", sequenceName = "seq_plugin_message_send_id", allocationSize = 1)
public class MsgSend implements Serializable {

	private static final long serialVersionUID = 8470154441934582608L;

	/**
	 * 消息类型枚举
	 * @author wuzhijun
	 */
	public enum Type{
		GENERAL("消息"),	NOTICE("公告"),SUBSCRIPTION("订阅");
		
		private String description;

		private Type(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
	
	@Id
    @GeneratedValue(generator = "seq_plugin_message_send",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "username", nullable = false)
	private String userName;
	@Column(name = "title", length = 200)
	private String title;
	@Column(name = "sendtime")
	private Date sendTime;
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private Type type;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private MsgStatus status;
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = MsgContent.class)
	@JoinColumn(name = "send_id")
	@OrderBy(value = "id Desc")
	private List<MsgContent> msgContents = new ArrayList<MsgContent>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = MsgReceiveUser.class, orphanRemoval = true)
	@JoinColumn(name = "send_id")
	@OrderBy(value = "id")
	private List<MsgReceiveUser> msgReceiveUsers = new ArrayList<MsgReceiveUser>();

	public MsgSend(){
		sendTime = new Date(Calendar.getInstance().getTime().getTime());
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public String getTypeDescription(){
		return type.getDescription();
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
	
	public List<MsgContent> getMsgContents() {
		return msgContents;
	}

	public void setMsgContents(List<MsgContent> msgContents) {
		this.msgContents = msgContents;
	}

	public List<MsgReceiveUser> getMsgReceiveUsers() {
		return msgReceiveUsers;
	}

	public void setMsgReceiveUsers(List<MsgReceiveUser> msgReceiveUsers) {
		this.msgReceiveUsers = msgReceiveUsers;
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
		MsgSend other = (MsgSend) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
