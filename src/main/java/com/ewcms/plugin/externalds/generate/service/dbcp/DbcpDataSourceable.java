/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.generate.service.dbcp;

import javax.sql.DataSource;

/**
 * @author 吴智俊
 */
public interface DbcpDataSourceable {

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
    public DataSource getDataSource();

    /**
     * 关闭数据源 
     */
    public void release();
}
