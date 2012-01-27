/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.generate.factory;

import com.ewcms.plugin.externalds.generate.service.EwcmsDataSourceServiceable;
import com.ewcms.plugin.externalds.model.BaseDS;

/**
 * 数据源工厂接口
 *
 * @author 吴智俊
 */
public interface DataSourceFactoryable {

	/**
	 * 建立数据源服务
	 * 
	 * @param dataSource 
	 * @return AlqcDataSourceServiceable
	 */
    public EwcmsDataSourceServiceable createService(BaseDS dataSource);
}
