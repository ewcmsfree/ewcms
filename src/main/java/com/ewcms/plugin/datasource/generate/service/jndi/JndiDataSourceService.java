/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.datasource.generate.service.jndi;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.plugin.BaseRuntimeExceptionWrapper;
import com.ewcms.plugin.datasource.generate.service.BaseDataSourceServiceable;

/**
 * JNDI数据库连接服务
 *
 * @author 吴智俊
 */
public class JndiDataSourceService extends BaseDataSourceServiceable {

	private static final Logger logger = LoggerFactory.getLogger(JndiDataSourceService.class);
	
    private final String jndiName;

    public JndiDataSourceService(String jndiName) {
        this.jndiName = jndiName;
    }

    protected Connection createConnection() {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(jndiName);
            return ds.getConnection();
        } catch (NamingException e) {
            logger.error("NamingException", e);
            throw new BaseRuntimeExceptionWrapper(e);
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new BaseRuntimeExceptionWrapper(e);
        }
    }
}
