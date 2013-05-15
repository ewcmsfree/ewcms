/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.online.service;

import java.util.List;

import com.ewcms.plugin.online.model.Advisor;

/**
 * 
 * @author wangwei
 */
public interface AdvisorServiceable {

	public void release(Integer id, boolean pub);

	public void replay(Integer id, String replay);

	public Advisor get(Integer id);
	
	public void deleteAdvisor(List<Integer> ids);
}
