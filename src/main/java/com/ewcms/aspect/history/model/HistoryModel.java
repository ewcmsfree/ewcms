/**
 * 创建日期 2011-3-31
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.aspect.history.model;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 记录model对象操作
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>className:类名(包括路径和类名)</li>
 * <li>methodName:操作方法</li>
 * <li>createDate:创建时间</li>
 * <li>idName:关键字名</li>
 * <li>idValue:关键字值</li>
 * <li>idType:关键字类型</li>
 * <li>modelObject:model对象</li>
 * <li>userName:用户名</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "aspect_history")
@SequenceGenerator(name = "seq_aspect_history", sequenceName = "seq_aspect_history_id", allocationSize = 1)
public class HistoryModel implements Serializable {

	private static final long serialVersionUID = 5018412502893297348L;

	@Id
	@GeneratedValue(generator = "seq_aspect_history", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "class_name", nullable = false)
	private String className;
	@Column(name = "method_name", nullable = false)
	private String methodName;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate = new Date(Calendar.getInstance().getTime().getTime());
	@Column(name = "id_name", nullable = false)
	private String idName;
	@Column(name = "id_value", nullable = false)
	private String idValue;
	@Column(name = "id_type", nullable = false)
	private String idType;
	@Column(name = "model_object")
	private byte[] modelObject;
	@Column(name = "user_name", nullable = false)
	private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getIdValue() {
		return idValue;
	}

	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@JsonIgnore
	public byte[] getModelObject() {
		return modelObject;
	}

	public void setModelObject(byte[] modelObject) {
		this.modelObject = modelObject;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
		HistoryModel other = (HistoryModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
