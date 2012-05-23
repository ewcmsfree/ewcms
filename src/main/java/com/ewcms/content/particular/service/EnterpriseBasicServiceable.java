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
