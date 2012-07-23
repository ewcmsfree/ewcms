/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Formula;

/**
 * <ul>
 * <li>id:编号</li>
 * <li>name:模板名称</li>
 * <li>describe:模板说明</li>
 * <li>updTime:模板最后修改时间</li>
 * <li>size:模板大小</li>
 * <li>parentId:模板父目录</li>
 * <li>templateEntity: 模板实体</li>
 * <li>channelId:模板所属专栏</li>
 * <li>path:模板路径</li>
 * <li>site:模板所属站点</li>
 * <li>uriPattern:路径模式 /${now?yyyy-MM-dd}/${id}_${page}.html</li>
 * <li>type:模板类型</li>
 * </ul>
 * 
 * @author 周冬初
 * 
 */
@Entity
@Table(name = "site_template")
@SequenceGenerator(name = "seq_site_template", sequenceName = "seq_site_template_id", allocationSize = 1)
public class Template implements Serializable {

	private static final long serialVersionUID = 3402146114224682928L;

	private static final String PATH_SEPARATOR = "/";
	
	public enum TemplateType {
	    HOME("专栏首页"),LIST("专栏列表"),DETAIL("文章内容");
		private String description;
		
		private TemplateType(String description){
			this.description = description;
		}
		
		public String getDescription(){
			return description;
		}
	}
	
	@Id
	@GeneratedValue(generator = "seq_site_template", strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column(length = 50)
	private String name;
	@Column(length = 200)
	private String describe;
	@Temporal(TemporalType.TIMESTAMP)
	@Column()
	private Date updTime = new Date();
	@Column(length = 20)
	private String size;
	@ManyToOne(cascade = { CascadeType.REFRESH }, targetEntity = Site.class)
	@JoinColumn(name = "site_id", nullable = false)
	private Site site;
    @ManyToOne(cascade = {CascadeType.REFRESH}, targetEntity = Template.class)
    @JoinColumn(name = "parent_id", nullable = true)
    private Template parent;
	@OneToOne(cascade = { CascadeType.ALL }, targetEntity = TemplateEntity.class)
	@JoinColumn(name = "tplEntityId", nullable = true)
	private TemplateEntity templateEntity;
	@Formula(value = "(Select count(o.id) From site_template o Where o.parent_id= id)")
	private int childrenCount = 0;
	@Column(name = "channel_Id")
	private Integer channelId;
	@Column(length = 150)
	private String path;
	@Column(length = 150,unique=true)
	private String uniquePath;
	@Column(length = 150)
	private String uriPattern;
	@Column(length = 15)
	@Enumerated(EnumType.STRING)
	private TemplateType type;
    @Column()
    private Boolean enabledUse = false;
    
	public Boolean getEnabledUse() {
		return enabledUse;
	}
	public void setEnabledUse(Boolean enabledUse) {
		this.enabledUse = enabledUse;
	}
	public String getUniquePath() {
		return uniquePath;
	}
	public void setUniquePath(String uniquePath) {
		this.uniquePath = uniquePath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Template getParent() {
		return parent;
	}

	public void setParent(Template parent) {
		this.parent = parent;
	}

	public static long getSerialversionuid() {
	    
		return serialVersionUID;
	}

	@JsonIgnore
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@JsonIgnore
	public TemplateEntity getTemplateEntity() {
		return templateEntity;
	}

	public void setTemplateEntity(TemplateEntity templateEntity) {
		this.templateEntity = templateEntity;
	}
	
	public boolean hasChildren() {
		return this.childrenCount > 0;
	}
	
    public String getUriPattern() {
		return uriPattern;
	}

	public void setUriPattern(String uriPattern) {
		this.uriPattern = uriPattern;
	}

	public TemplateType getType() {
        return type;
    }
	
	public String getTypeDescription(){
		if (type != null){
			return type.getDescription();
		}else{
			return "---请选择类型---";
		}
	}

    public void setType(TemplateType type) {
        this.type = type;
    }

	@PreUpdate
	@PrePersist
	public void afterPersist(){
		constructPath();
	}
	 
	private void constructPath() {
		StringBuilder builder = new StringBuilder();
		for (Template template = this; template != null; template = template.parent) {
			if (StringUtils.isBlank(template.getName())) {
				break;
			}
			String dir = removeStartAndEndPathSeparator(template.getName());
			builder.insert(0, dir);
			builder.insert(0, PATH_SEPARATOR);
		}
		path = removeStartAndEndPathSeparator(builder.toString());
		uniquePath = getSite().getId().toString()+ PATH_SEPARATOR + path;
	}

	private String removeStartAndEndPathSeparator(final String dir) {
		String path = dir;
		path = StringUtils.removeStart(path, PATH_SEPARATOR);
		path = StringUtils.removeEnd(path, PATH_SEPARATOR);

		return path;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Template other = (Template) obj;
		if (this.id != other.id
				&& (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}
}
