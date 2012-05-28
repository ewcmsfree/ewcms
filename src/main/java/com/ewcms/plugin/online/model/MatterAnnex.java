/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 事项附件
 * 
 * <ul>
 * <li>id:编号
 * <li>url:地址</li>
 * <li>legend:说明</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "plugin_matter_annex")
@SequenceGenerator(name = "seq_plugin_matter_annex", sequenceName = "seq_plugin_matter_annex_id", allocationSize = 1)
public class MatterAnnex implements Serializable {

	private static final long serialVersionUID = 4831479226554897700L;

	@Id
	@GeneratedValue(generator = "seq_plugin_matter_annex", strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column(name = "url", columnDefinition = "text")
	private String url;
	@Column(name = "legend", columnDefinition = "text")
	private String legend;
	@Column(name = "sort")
	private Integer sort;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLegend() {
		return legend;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
		MatterAnnex other = (MatterAnnex) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
