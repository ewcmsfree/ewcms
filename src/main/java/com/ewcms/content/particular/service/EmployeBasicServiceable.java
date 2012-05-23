package com.ewcms.content.particular.service;

import java.util.List;

import com.ewcms.content.particular.model.EmployeBasic;

public interface EmployeBasicServiceable {
	public Long addEmployeBasic(EmployeBasic employeBasic);
	
	public Long updEmployeBasic(EmployeBasic employeBasic);
	
	public void delEmployeBasic(Long id);
	
	public EmployeBasic findEmployeBasicById(Long id);
	
	public List<EmployeBasic> findEmployeBasicAll();
}
