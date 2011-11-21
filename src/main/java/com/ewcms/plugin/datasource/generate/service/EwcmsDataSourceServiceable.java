/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.datasource.generate.service;

import java.sql.Connection;

/**
 * 数据源服务
 * 
 * @author 吴智俊
 */
public interface EwcmsDataSourceServiceable {

    /**
     * 关闭数据源连接
     */
    public void closeConnection();

    /**
     * 打开数据源连接
     * @return Connection
     */
    public Connection openConnection();
}
