/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Formula;

/**
 * <ul>
 * <li>id:栏目编号</li>
 * <li>parent:所属父栏目</li>
 * <li>site:栏目所属站点</li>
 * <li>name: 栏目名称</li>
 * <li>dir: 栏目目录</li>
 * <li>pubPath:栏目发布路径</li>
 * <li>url:栏目域名</li>
 * <li>absUrl:栏目访问地址</li>
 * <li>listSize:列表显示最大条数</li>
 * <li>maxSize:最大显示条数</li>
 * <li>publicenable:是否允许发布</li>
 * <li>describe:专栏说明</li>
 * <li>channelEntity:专栏引导图</li>
 * <li>channelType:专栏类型</li>
 * <li>sort:排序号</li>
 * <li>appChannel:应用于频道</li>
 * </ul>
 * 
 * @author 周冬初
 * @author wuzhijun
 */
@Entity
@Table(name = "site_channel")
@SequenceGenerator(name = "seq_site_channel", sequenceName = "seq_site_channel_id", allocationSize = 1)
public class Channel implements Serializable {

	private static final long serialVersionUID = 7813916065025966481L;

	public enum Type {
		NODE("节点"), 
		ARTICLE("文章信息"), 
		RETRIEVAL("文章信息(公文)"),
		LEADERARTICLE("文章信息(引用)"), 
		LEADER("领导信息"),
		ONLINE("网上办事"),
		PROJECT("项目基本信息"), 
		PROJECTARTICLE("项目文章信息"), 
		ENTERPRISE("企业基本信息"), 
		ENTERPRISEARTICLE("企业文章信息"), 
		EMPLOYE("从业人员基本信息"), 
		EMPLOYEARTICLE("从业人员文章信息");

		private String description;

		private Type(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

	}

	private static final String PATH_SEPARATOR = "/";
	@Id
	@GeneratedValue(generator = "seq_site_channel", strategy = GenerationType.SEQUENCE)
	private Integer id;
	@ManyToOne(cascade = { CascadeType.REFRESH }, targetEntity = Channel.class)
	@JoinColumn(name = "parent_id", nullable = true)
	private Channel parent;
	@ManyToOne(cascade = { CascadeType.REFRESH }, targetEntity = Site.class)
	@JoinColumn(name = "site_id", nullable = false)
	private Site site;
	@Column(length = 100)
	private String name;
	@Column(length = 50)
	private String dir;
	@Column(length = 150)
	private String url;
	@Column(length = 400)
	private String absUrl;
	@Column(length = 400)
	private String iconUrl;
	@Column(length = 400)
	private String pubPath;
	@Column()
	private Integer listSize = 20;
	@Column()
	private Integer maxSize = 9999;
	@Column()
	private Boolean publicenable = false;
	@Column()
	private Integer internalSort;
	@Formula(value = "(Select count(o.id) From site_channel o Where o.parent_id= id)")
	private int childrenCount = 0;
	@Column(length = 200)
	private String describe;
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;
	@Column(name = "sort")
	private Long sort = 0L;
	@Column(name = "app_channel", columnDefinition="text")
	private String appChannel;

	public Channel() {
		type = Type.NODE;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInternalSort() {
		return internalSort;
	}

	public void setInternalSort(Integer internalSort) {
		this.internalSort = internalSort;
	}

	@JsonIgnore
	public Channel getParent() {
		return parent;
	}

	public void setParent(Channel parent) {
		this.parent = parent;
	}

	@JsonIgnore
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDir() {
		return dir == null ? "" : dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getUrl() {
		return url == null ? "" : url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAbsUrl() {
		return absUrl;
	}

	public void setAbsUrl(String realDir) {
		this.absUrl = realDir;
	}

	public String getPubPath() {
		return pubPath;
	}

	public void setPubPath(String pubPath) {
		this.pubPath = pubPath;
	}

	public Integer getListSize() {
		return listSize;
	}

	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Boolean getPublicenable() {
		return publicenable;
	}

	public void setPublicenable(Boolean publicenable) {
		this.publicenable = publicenable;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public boolean hasChildren() {
		return this.childrenCount > 0;
	}

	@PostUpdate
	public void afterPersist() {
		constructAbsUrl();
		constructPubPath();
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	private void constructAbsUrl() {
		if (StringUtils.isNotBlank(url)) {
			this.absUrl = url;
		} else {
			StringBuilder builder = new StringBuilder();
			for (Channel channel = this; channel != null; channel = channel.parent) {
				if (StringUtils.isNotBlank(channel.getUrl())
						|| StringUtils.isBlank(channel.getDir())) {
					break;
				}
				String dir = removeStartAndEndPathSeparator(channel.getDir());
				builder.insert(0, dir);
				builder.insert(0, PATH_SEPARATOR);
			}
			this.absUrl = builder.toString();
		}
	}

	private void constructPubPath() {
		StringBuilder builder = new StringBuilder();
		for (Channel channel = this; channel != null; channel = channel.parent) {
			if (StringUtils.isBlank(channel.getDir())) {
				break;
			}
			String dir = removeStartAndEndPathSeparator(channel.getDir());
			builder.insert(0, dir);
			builder.insert(0, PATH_SEPARATOR);
		}
		pubPath = removeStartAndEndPathSeparator(builder.toString());
	}

	private String removeStartAndEndPathSeparator(final String dir) {
		String path = dir;
		path = StringUtils.removeStart(path, PATH_SEPARATOR);
		path = StringUtils.removeEnd(path, PATH_SEPARATOR);

		return path;
	}

	public Type getType() {
		return type;
	}

	public String getTypeDescription() {
		if (type != null) {
			return type.getDescription();
		} else {
			return Type.NODE.getDescription();
		}
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getAppChannel() {
		return appChannel;
	}

	public void setAppChannel(String appChannel) {
		this.appChannel = appChannel;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Channel other = (Channel) obj;
		if (this.id != other.id
				&& (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}
}
