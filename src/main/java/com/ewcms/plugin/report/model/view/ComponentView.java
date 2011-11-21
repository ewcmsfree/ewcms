/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.model.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 组件视图父类
 * 
 * <ul>
 * <li>id:组件视图编号</li>
 * <li>isMandatory:是否强制</li>
 * <li>isReadOnly:是否是读</li>
 * <li>isVisible:是否可见</li>
 * <ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "repo_view")
@SequenceGenerator(name = "seq_repo_view", sequenceName = "seq_repo_view_id", allocationSize = 1)
public class ComponentView implements Serializable {

    private static final long serialVersionUID = 6470965538592594049L;
    
	@Id
    @GeneratedValue(generator = "seq_repo_view",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
    private Integer id = null;
    @Column(name = "mandatory")
    private Boolean isMandatory;
    @Column(name = "readonly")
    private Boolean isReadOnly;
    @Column(name = "visible")
    private Boolean isVisible;

    public ComponentView(){
    	isMandatory = false;
    	isReadOnly = false;
    	isVisible = true;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public Boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(Boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    public Boolean isVisible() {
        return isVisible;
    }

    public void setVisible(Boolean isVisible) {
        this.isVisible = isVisible;
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ComponentView other = (ComponentView) obj;
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
