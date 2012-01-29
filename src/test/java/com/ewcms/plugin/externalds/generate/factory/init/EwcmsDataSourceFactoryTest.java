/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.generate.factory.init;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ewcms.plugin.externalds.generate.factory.DataSourceFactoryable;
import com.ewcms.plugin.externalds.generate.factory.init.EwcmsDataSourceFactoryable;
import com.ewcms.plugin.externalds.generate.service.EwcmsDataSourceServiceable;
import com.ewcms.plugin.externalds.model.BaseDS;
import com.ewcms.plugin.externalds.model.JdbcDS;
import com.ewcms.plugin.externalds.model.JndiDS;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/plugin/datasource/generate/factory/init/applicationContext.xml"}, inheritLocations = true)
public class EwcmsDataSourceFactoryTest {
	
	private static final Logger logger = LoggerFactory.getLogger(EwcmsDataSourceFactoryTest.class);
	
	@Autowired
    private EwcmsDataSourceFactoryable ewcmsDataSourceFactory;
	@Autowired
	private DataSource dataSource;

    @Test
    public void testJdbc() throws Exception {
    	logger.info("JDBC测试开始...");
        Construction(getJdbcDataSource());
        logger.info("JDBC测试结束");
    }

    @Test
    public void testJndi() throws Exception {
    	logger.info("JNDI测试开始...");
    	//虚拟JNDI
    	SimpleNamingContextBuilder builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
        builder.bind("jndi:jdbc/test", dataSource);
        Construction(getJndiDataSource());
        logger.info("JNDI测试结束");
    }

//    @Test
//    public void testBean() throws Exception{
//        fail("未写测试过程!");
//    }
//
//    @Test
//    public void testCustom() throws Exception{
//        fail("未写测试过程!");
//    }
    
    private void Construction(BaseDS baseDS) throws SQLException {
        DataSourceFactoryable factory = (DataSourceFactoryable) ewcmsDataSourceFactory.getBean(baseDS.getClass());
        EwcmsDataSourceServiceable service = factory.createService(baseDS);
       
        Connection con = service.openConnection();
        
        if (!con.isClosed()){
        	service.closeConnection();
        }
    }

    private JdbcDS getJdbcDataSource() {
    	JdbcDS jdbcDS = new JdbcDS();

        jdbcDS.setName("jdbc测试");
        jdbcDS.setConnUrl("jdbc:postgresql://127.0.0.1/ewcms");
        jdbcDS.setDriver("org.postgresql.Driver");
        jdbcDS.setUserName("postgres");
        jdbcDS.setPassWord("");
        jdbcDS.setRemarks("测试使用");

        return jdbcDS;
    }

    private JndiDS getJndiDataSource() {
    	JndiDS jndiDS = new JndiDS();

        jndiDS.setName("jndi测试");
        jndiDS.setJndiName("jndi:jdbc/test");
        jndiDS.setRemarks("测试使用");

        return jndiDS;
    }
}
