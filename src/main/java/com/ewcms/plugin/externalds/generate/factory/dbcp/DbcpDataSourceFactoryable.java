/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.generate.factory.dbcp;

import com.ewcms.plugin.externalds.generate.service.dbcp.DbcpDataSourceable;

/**
 * @author 吴智俊
 */
public interface DbcpDataSourceFactoryable {
	/**
	 * 建立Pooled数据源
	 * 
	 * @param driverClass 驱动类
	 * @param url 连接数据源
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	public DbcpDataSourceable createPooledDataSource(String driverClass, String url, String username, String password);
}
