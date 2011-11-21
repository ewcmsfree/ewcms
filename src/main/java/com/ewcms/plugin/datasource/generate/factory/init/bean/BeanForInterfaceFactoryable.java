/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.datasource.generate.factory.init.bean;

import java.util.Map;

/**
 * 根据的数据源映射对象,查询相对应的数据源工厂
 * 
 * 
 * @author 吴智俊
 */
public interface BeanForInterfaceFactoryable {

    @SuppressWarnings("rawtypes")
    public String getBeanName(Map classToBeanMappings, Class _class);

    @SuppressWarnings("rawtypes")
    public Object getBean(Map classToBeanMappings, Class itfClass);
}
