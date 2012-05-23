/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import com.ewcms.content.particular.model.EmployeArticle;

public interface EmployeArticleServiceable {
	public Long addEmployeArticle(EmployeArticle employeArticle);
	
	public Long updEmployeArticle(EmployeArticle employeArticle);
	
	public void delEmployeArticle(Long id);
	
	public EmployeArticle findEmployeArticleById(Long id);
}
