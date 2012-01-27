/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.generate.factory.init;

/**
 * 数据库服务工厂接口
 * 
 * @author 吴智俊
 */
public interface EwcmsDataSourceFactoryable {
	@SuppressWarnings("rawtypes")
	public Object getBean(Class itfClass);
	
	@SuppressWarnings("rawtypes")
	public String getBeanName(Class itfClass);
}
