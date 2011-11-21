/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * 匹配块
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>regex:表达式</li>
 * <li>parent:父节点</li>
 * <li>sort:排序号</li>
 * </ul>
 * 
 * @author wuzhijun
 * 
 */
@Entity
@Table(name = "cwl_matchblock")
@SequenceGenerator(name = "seq_cwl_matchblock", sequenceName = "seq_cwl_matchblock_id", allocationSize = 1)
public class MatchBlock implements Serializable {

	private static final long serialVersionUID = 27792816081447537L;

	@Id
	@GeneratedValue(generator = "seq_cwl_matchblock", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "regex")
	private String regex;
	@ManyToOne(cascade = { CascadeType.REFRESH }, targetEntity = MatchBlock.class)
	@JoinColumn(name = "parent_id", nullable = true)
	private MatchBlock parent;
	@Column(name = "sort")
	private Long sort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public MatchBlock getParent() {
		return parent;
	}

	public void setParent(MatchBlock parent) {
		this.parent = parent;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
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
		MatchBlock other = (MatchBlock) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
