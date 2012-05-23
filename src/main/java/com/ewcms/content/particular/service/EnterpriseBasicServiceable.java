/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import com.ewcms.content.particular.model.EnterpriseBasic;

public interface EnterpriseBasicServiceable {
	public Long addEnterpriseBasic(EnterpriseBasic enterpriseBasic);
	
	public Long updEnterpriseBasic(EnterpriseBasic enterpriseBasic);
	
	public void delEnterpriseBasic(Long id);
	
	public EnterpriseBasic findEnterpriseBasicById(Long id);
	
	public List<EnterpriseBasic> findEnterpriseBasicAll();
}
