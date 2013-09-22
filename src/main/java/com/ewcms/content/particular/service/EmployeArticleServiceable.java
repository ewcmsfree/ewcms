/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import com.ewcms.content.particular.model.EmployeArticle;

public interface EmployeArticleServiceable {
	public Long addEmployeArticle(EmployeArticle employeArticle);
	
	public Long updEmployeArticle(EmployeArticle employeArticle);
	
	public void delEmployeArticle(Long id);
	
	public EmployeArticle findEmployeArticleById(Long id);
	
	public void pubEmployeArticle(List<Long> employeArticleIds);
	
	public void unPubEmployeArticle(List<Long> employeArticleIds);
	
	public List<EmployeArticle> findEmployeArticleAll();
}
