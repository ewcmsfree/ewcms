package com.ewcms.content.particular.service;

import com.ewcms.content.particular.model.EmployeArticle;

public interface EmployeArticleServiceable {
	public Long addEmployeArticle(EmployeArticle employeArticle);
	
	public Long updEmployeArticle(EmployeArticle employeArticle);
	
	public void delEmployeArticle(Long id);
	
	public EmployeArticle findEmployeArticleById(Long id);
}
