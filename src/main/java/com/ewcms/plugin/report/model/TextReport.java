/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.ewcms.plugin.externalds.model.BaseDS;

/**
 * 报表对象
 * 
 * <ul>
 * <li>id:报表ID</li>
 * <li>name:报表名</li>
 * <li>type:报表类型</li>
 * <li>textEntity:报表实体</li>
 * <li>createDate:创建时间</li>
 * <li>updateDate:更新时间</li>
 * <li>hidden:隐藏</li>
 * <li>remarks:备注</li>
 * <li>parameters:参数列表（与Parameters对象一对多关联）</li>
 * <li>alqcDataSource:连接数据源对象（与AlqcDataSource对象一对一关联）</li>
 * <li>Category:分类列表(与Category对象多对多关联)</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "plugin_report_text")
@SequenceGenerator(name = "seq_plugin_report_text", sequenceName = "seq_plugin_report_text_id", allocationSize = 1)
public class TextReport implements Serializable {

    private static final long serialVersionUID = 2289611908936617074L;
    
    /**
     * 报表类型枚举
     * 
     * @author wuzhijun
     *
     */
    public enum Type {

        HTML("HTML"), PDF("PDF"), XLS("XLS"), RTF("RTF"), XML("XML");
        
        private String description;

        private Type(String description) {
            this.description = description;
        }

        /**
         * 描述状态
         *
         * @return
         */
        public String getDescription() {
            return this.description;
        }
    }
	@Id
    @GeneratedValue(generator = "seq_plugin_report_text",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
    private Long id;
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;
    @Column(name = "textentity")
    private byte[] textEntity;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdate")
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedate")
    private Date updateDate;
    @Column(name = "hidden", nullable = false)
    private Boolean hidden;
    @Column(name = "remarks",columnDefinition = "text")
    private String remarks;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Parameter.class, orphanRemoval = true)
    @JoinColumn(name = "text_id")
    @OrderBy("id")
    private Set<Parameter> parameters = new LinkedHashSet<Parameter>();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = BaseDS.class)
    @JoinColumn(name = "base_ds_id")
    private BaseDS baseDS = new BaseDS();
    
    public TextReport(){
    	createDate = new Date(Calendar.getInstance().getTime().getTime());
    	hidden = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public byte[] getTextEntity() {
        return textEntity;
    }

    public void setTextEntity(byte[] textEntity) {
        this.textEntity = textEntity;
    }

    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonIgnore
    public Set<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<Parameter> parameters) {
        this.parameters = parameters;
    }

    public BaseDS getBaseDS() {
        return baseDS;
    }

    public void setBaseDS(BaseDS baseDS) {
        this.baseDS = baseDS;
    }

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TextReport other = (TextReport) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
