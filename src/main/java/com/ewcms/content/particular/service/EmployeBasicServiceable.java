/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import com.ewcms.content.particular.model.EmployeBasic;

public interface EmployeBasicServiceable {
	public Long addEmployeBasic(EmployeBasic employeBasic);
	
	public Long updEmployeBasic(EmployeBasic employeBasic);
	
	public void delEmployeBasic(Long id);
	
	public EmployeBasic findEmployeBasicById(Long id);
	
	public List<EmployeBasic> findEmployeBasicAll();
	
	public void pubEmployeBasic(List<Long> employeBasicIds);
	
	public void unPubEmployeBasic(List<Long> employeBasicIds);
}
