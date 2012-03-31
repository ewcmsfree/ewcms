/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 通用权限
 *
 * <ul>
 * <li>name:权限名称
 * <li>remark:备注
 * </ul>
 *
 * @author 王伟
 */
@Entity
@Table(name = "auth_authority")
public class Authority implements Serializable {

	private static final long serialVersionUID = -8680793349779910408L;

	@Id
    @Column(name = "name", length = 50 , nullable=false)
    private String name;
    
    @Column(name = "remark", length = 200)
    private String remark;

    public Authority(){}
    
    public Authority(String name){
        this(name,null);
    }
    
    public Authority(String name,String remark){
        this.name = name;
        this.remark = remark;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String description) {
        this.remark = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Authority other = (Authority) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        
        StringBuilder builder = new StringBuilder();
        builder.append("Authority{");
        builder.append("name:").append(name).append(";");
        builder.append("remark:").append(remark).append(";");
        builder.append("}");
        
        return builder.toString();
    }
}
