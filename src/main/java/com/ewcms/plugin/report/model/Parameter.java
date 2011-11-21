/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.plugin.report.model.data.Data;
import com.ewcms.plugin.report.model.view.ComponentView;

/**
 * 报表参数
 * 
 * <ul>
 * <li>id:参数编号</li>
 * <li>enName:参数名</li>
 * <li>className:参数类型</li>
 * <li>description:参数描述</li>
 * <li>chName:中文名</li>
 * <li>defaultValue:默认值</li>
 * <li>value:设置值</li>
 * <li>componentView:控件视图</li>
 * <li>data:数据</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "repo_parameter")
@SequenceGenerator(name = "seq_repo_text_parameter", sequenceName = "seq_repo_text_parameter_id", allocationSize = 1)
public class Parameter implements Serializable {

    private static final long serialVersionUID = 2283573904816876354L;
    
	@Id
    @GeneratedValue(generator = "seq_repo_text_parameter",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
    private Long id;
    @Column(name = "enname", nullable = false)
    private String enName;
    @Column(name = "classname", nullable = false)
    private String className;
    @Column(name = "description",columnDefinition = "text")
    private String description;
    @Column(name = "cnname")
    private String cnName;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParametersType type;
    @Column(name = "defaultvalue")
    private String defaultValue;
    @Column(name = "value")
    private String value;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ComponentView.class)
    @JoinColumn(name = "componentview_id")
    private ComponentView componentView = new ComponentView();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Data.class)
    @JoinColumn(name = "data_id")
    private Data data = new Data();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCnName() {
        return cnName == null ? "" : cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public ParametersType getType() {
        return type;
    }

	public String getTypeDescription(){
		if (type != null){
			return type.getDescription();
		}else{
			return ParametersType.TEXT.getDescription();
		}
	}
	
    public void setType(ParametersType type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public ComponentView getComponentView() {
        return componentView;
    }

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setComponentView(ComponentView componentView) {
        this.componentView = componentView;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((enName == null) ? 0 : enName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        final Parameter other = (Parameter) obj;
        if (enName == null) {
            if (other.enName != null) {
                return false;
            }
        } else if (!enName.equals(other.enName)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
