/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.datasource.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 自定义数据源
 *
 * <ul>
 * <li>customName:自定义名称</li>
 * <li>customMethod:自定义方法</li>
 * <li>serviceClass:服务类</li>
 * <li>propertyMap:属性集合(不映射)</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "ds_base_custom")
@PrimaryKeyJoinColumn(name = "ds_id")
public class CustomDS extends BaseDS {

    private static final long serialVersionUID = 6750456928110573902L;
    
    @Column(name = "customname", length = 100, nullable = true)
    private String customName;
    @Column(name = "custommethod", length = 100)
    private String customMethod;
    @Transient
    private String serviceClass;
	@Transient
    @SuppressWarnings("rawtypes")
	private Map propertyMap;

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomMethod() {
        return customMethod;
    }

    public void setCustomMethod(String customMethod) {
        this.customMethod = customMethod;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    @SuppressWarnings("rawtypes")
    public Map getPropertyMap() {
        return propertyMap;
    }

    @SuppressWarnings("rawtypes")
	public void setPropertyMap(Map propertyMap) {
        this.propertyMap = propertyMap;
    }
}
