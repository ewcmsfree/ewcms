/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Formula;


/**
 * <ul>
 * <li>id:站点编号
 * <li>siteName:站点名称
 * <li>siteRoot:站点目录
 * <li>describe:站点描述
 * <li>siteURL: 站点访问URL地址 
 * <li>metaKey:meta搜索关键字 
 * <li>metaDescripe:meta关键字说明
 * <li>siteConfig:站点配置
 * <li>siteChannel:站点栏目
 * <li>siteTemplateList:站点模板集
 * <li>createTime:站点创建时间
 * <li>updateTime:最后修改时间
 * <li>extraFile:生成扩展文件名称
 * <li>parent:父站点
 * <li>publicenable:是否允许发布
 * <li>serverDir:站点发布服务器 绝对目录
 * <li>resourceDir:站点资源发布 绝对目录
 * </ul>
 * 
 * @author 周冬初
 */
@Entity
@Table(name = "site_site")
@SequenceGenerator(name = "seq_site", sequenceName = "seq_site_id", allocationSize = 1)
public class Site implements Serializable {
    @Id
    @GeneratedValue(generator = "seq_site", strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(length = 100)
    private String siteName;
    @Column(length = 20)
    private String siteRoot;
    @Column(length = 100)
    private String describe;
    @Column(length = 150)
    private String siteURL;
    @Column()
    private String metaKey;
    @Column()
    private String metaDescripe;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createTime = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime = new Date();
    @Column(length = 15)
    private String extraFile;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST}, targetEntity = Site.class)
    @JoinColumn(name = "parent_id", nullable = true)
    private Site parent;
    @Column()
    private Boolean publicenable = false;
    @Column()
    private String serverDir;
    @Column()
    private String resourceDir;    
    @ManyToOne(cascade = {CascadeType.REFRESH}, targetEntity = Organ.class)
    @JoinColumn(name = "organ_id", nullable = true)
    private Organ organ;
    @Formula(value="(Select count(o.id) From site_site o Where o.parent_id= id)")
    private int childrenCount = 0;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteRoot() {
        return siteRoot;
    }

    public void setSiteRoot(String siteRoot) {
        this.siteRoot = siteRoot;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSiteURL() {
        return siteURL;
    }

    public void setSiteURL(String siteURL) {
        this.siteURL = siteURL;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaDescripe() {
        return metaDescripe;
    }

    public void setMetaDescripe(String metaDescripe) {
        this.metaDescripe = metaDescripe;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getExtraFile() {
        return extraFile;
    }

    public void setExtraFile(String extraFile) {
        this.extraFile = extraFile;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Site getParent() {
        return parent;
    }

    @JsonIgnore
    public void setParent(Site parent) {
        this.parent = parent;
    }
    public Boolean getPublicenable() {
        return publicenable;
    }

    public void setPublicenable(Boolean publicenable) {
        this.publicenable = publicenable;
    }

    public String getServerDir() {
        return serverDir;
    }

    public void setServerDir(String serverDir) {
        this.serverDir = serverDir;
    }

    public String getResourceDir() {
        return resourceDir;
    }

    public void setResourceDir(String resourceDir) {
        this.resourceDir = resourceDir;
    }

    @JsonIgnore
    public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}
    public boolean hasChildren(){
        return this.childrenCount > 0;
    }
	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Site other = (Site) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
