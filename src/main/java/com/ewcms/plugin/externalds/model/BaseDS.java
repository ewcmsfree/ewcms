/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 数据源父类
 * 
 * <ul>
 * <li>id:数据源编号</li>
 * <li>name:名称</li>
 * <li>timeZone:时区偏移量</li>
 * <li>remarks:备注</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "plugin_ds_base")
@SequenceGenerator(name = "seq_plugin_ds_base", sequenceName = "seq_plugin_ds_base_id", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseDS implements Serializable {

    private static final long serialVersionUID = 3846532575959285040L;
    
	@Id
    @GeneratedValue(generator = "seq_plugin_ds_base",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
    private Long id;
    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;
    @Column(name = "timezone")
    private String timeZone = "Asia/Shanghai";
    @Column(name = "remarks",columnDefinition = "text")
    private String remarks;

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

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        final BaseDS other = (BaseDS) obj;
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
