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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.ewcms.core.document.model.ArticleRmc;

/**
 * 领导频道
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>name:名称</li>
 * <li>sort:排序</li>
 * <li>channelId:频道编号</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "plugin_leaderchannel")
@SequenceGenerator(name = "seq_plugin_leaderchannel", sequenceName = "seq_plugin_leaderchannel_id", allocationSize = 1)
public class LeaderChannel implements Serializable {

	private static final long serialVersionUID = 9185137504648419217L;

	@Id
	@GeneratedValue(generator = "seq_plugin_leaderchannel", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;
	@Column(name = "leaderchannel_name", nullable = false)
	private String name;
	@Column(name = "sort")
	private Integer sort;
	@ManyToMany(cascade = {CascadeType.ALL}, targetEntity = ArticleRmc.class, fetch = FetchType.LAZY)
	@JoinTable(name = "plugin_leaderchannel_articlermc", joinColumns = @JoinColumn(name = "leaderchannel_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "articleRmc_id", referencedColumnName = "id"))
	@OrderBy("published desc")
	private List<ArticleRmc> articleRmcs;
	@Column(name = "channel_id")
	private Integer channelId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@JsonIgnore
	public List<ArticleRmc> getArticleRmcs() {
		return articleRmcs;
	}

	public void setArticleRmcs(List<ArticleRmc> articleRmcs) {
		this.articleRmcs = articleRmcs;
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
		LeaderChannel other = (LeaderChannel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
