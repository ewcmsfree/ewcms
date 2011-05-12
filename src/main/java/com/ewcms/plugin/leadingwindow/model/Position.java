/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.leadingwindow.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 领导职务
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>parent:所属父栏目</li>
 * <li>children:包含子栏目集</li>
 * <li>name: 栏目名称</li>
 * <li>sort:排序
 * <li>channelId:频道编号</li>
 * <li>leaders:人员集合</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "plugin_position")
@SequenceGenerator(name = "seq_plugin_position", sequenceName = "seq_plugin_position_id", allocationSize = 1)
public class Position implements Serializable {

	private static final long serialVersionUID = 4122296255087627979L;

	@Id
	@GeneratedValue(generator = "seq_plugin_position", strategy = GenerationType.SEQUENCE)
	private Integer id;
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST }, targetEntity = Position.class)
	@JoinColumn(name = "parent_id")
	private Position parent;
	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH }, targetEntity = Position.class, mappedBy = "parent")
	@OrderBy("sort asc,id asc")
	private List<Position> children;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "sort")
	private Integer sort;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Leader.class)
	@JoinTable(name = "plugin_position_leader", joinColumns = @JoinColumn(name = "position_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "leader_id", referencedColumnName = "id"))
	@OrderBy(value = "sort,id")
	private List<Leader> leaders;
	@Column(name = "channel_id")
	private Integer channelId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Position getParent() {
		return parent;
	}

	public void setParent(Position parent) {
		this.parent = parent;
	}

	public List<Position> getChildren() {
		return children;
	}

	public void setChildren(List<Position> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<Leader> getLeaders() {
		return leaders;
	}

	public void setLeaders(List<Leader> leaders) {
		this.leaders = leaders;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
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
		Position other = (Position) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
