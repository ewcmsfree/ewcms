/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.manager.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ewcms.plugin.externalds.manager.dao.BaseDSDAO;
import com.ewcms.plugin.externalds.manager.service.BaseDSService;
import com.ewcms.plugin.externalds.model.BaseDS;
import com.ewcms.plugin.externalds.model.JdbcDS;
import com.ewcms.plugin.externalds.model.JndiDS;

public class BaseDSServiceTest {
	
	private BaseDSService baseDSService;
	private BaseDSDAO mockBaseDSDAO;

	@Before
	public void setUp() throws Exception {
		baseDSService = new BaseDSService();
		mockBaseDSDAO = mock(BaseDSDAO.class);
		baseDSService.setBaseDSDAO(mockBaseDSDAO);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSaveOrUpdateJdbcDS() {
		BaseDS baseDS = getJdbcDS();
		baseDSService.saveOrUpdateBaseDS(baseDS);
		
		ArgumentCaptor<JdbcDS> argument = ArgumentCaptor.forClass(JdbcDS.class);
		verify(mockBaseDSDAO).merge(argument.capture());
		
		assertEquals(argument.getValue().getName(), getJdbcDS().getName());
		assertEquals(argument.getValue().getUserName(), getJdbcDS().getUserName());
		assertEquals(argument.getValue().getPassWord(), getJdbcDS().getPassWord());
		assertEquals(argument.getValue().getConnUrl(), getJdbcDS().getConnUrl());
		assertEquals(argument.getValue().getDriver(), getJdbcDS().getDriver());
		assertEquals(argument.getValue().getRemarks(), getJdbcDS().getRemarks());
		
	}
	
	@Test
	public void testSaveOrUpdateJndiDS(){
		BaseDS baseDS = getJndiDS();
		baseDSService.saveOrUpdateBaseDS(baseDS);
		
		ArgumentCaptor<JndiDS> argument = ArgumentCaptor.forClass(JndiDS.class);
		verify(mockBaseDSDAO).merge(argument.capture());
		
		assertEquals(argument.getValue().getName(), getJndiDS().getName());
		assertEquals(argument.getValue().getJndiName(), getJndiDS().getJndiName());
		assertEquals(argument.getValue().getRemarks(), getJndiDS().getRemarks());
	}

	@Test
	public void testDeletedBaseDS() {
		fail("Not yet implemented");
	}

	private JdbcDS getJdbcDS(){
		JdbcDS jdbcDS = new JdbcDS();
		
		jdbcDS.setName("test-jdbc");
		jdbcDS.setUserName("postgres");
		jdbcDS.setPassWord("123456");
		jdbcDS.setConnUrl("jdbc:postgresql://localhost/ewcms");
		jdbcDS.setDriver("org.postgresql.Driver");
		jdbcDS.setRemarks("test-jdbc");
		
		return jdbcDS;
	}
	
	private JndiDS getJndiDS(){
		JndiDS jndiDS = new JndiDS();
		
		jndiDS.setJndiName("testJndi");
		jndiDS.setName("test-jndi");
		jndiDS.setRemarks("test-jndi");
		
		return jndiDS;
	}
}
