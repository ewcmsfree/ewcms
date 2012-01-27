/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.generate.factory.dbcp;

import org.apache.commons.pool.ObjectPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.BaseRuntimeExceptionWrapper;
import com.ewcms.plugin.externalds.generate.service.dbcp.DbcpDataSource;
import com.ewcms.plugin.externalds.generate.service.dbcp.DbcpDataSourceable;

/**
 * 
 * @author 吴智俊
 */
@Service
public class DbcpDataSourceFactory implements DbcpDataSourceFactoryable {

	private static final Logger logger = LoggerFactory.getLogger(DbcpDataSourceFactory.class);
	
    @Autowired
    private ObjectPoolFactory objectPoolFactory;

    public void setObjectPoolFactory(ObjectPoolFactory genericObjectPoolFactory) {
        this.objectPoolFactory = genericObjectPoolFactory;
    }

    public ObjectPoolFactory getObjectPoolFactory() {
        return objectPoolFactory;
    }

    @Override
    public DbcpDataSourceable createPooledDataSource(String driverClass, String url, String username, String password) {
        registerDriver(driverClass);
        return new DbcpDataSource(objectPoolFactory, url, username, password);
    }

    /**
     * 注册数据源驱动
     * @param driverClass
     */
    protected void registerDriver(String driverClass) {
        try {
            Class.forName(driverClass, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
        	logger.error("ClassNotFoundException", e);
            throw new BaseRuntimeExceptionWrapper(e);
        }
    }
}
