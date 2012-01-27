/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 信息内容
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>title:标题</li>
 * <li>detail:内容</li>
 * </ul>
 * 
 * @author wu_zhijun
 * 
 */
@Entity
@Table(name = "plugin_message_content")
@SequenceGenerator(name = "seq_plugin_message_content", sequenceName = "seq_plugin_message_content_id", allocationSize = 1)
public class MsgContent implements Serializable {

	private static final long serialVersionUID = 8153404292985294025L;

	@Id
    @GeneratedValue(generator = "seq_plugin_message_content",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "title", nullable = false, length = 200)
	private String title;
	@Column(name = "detail", columnDefinition = "text")
	private String detail;

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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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
		MsgContent other = (MsgContent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
