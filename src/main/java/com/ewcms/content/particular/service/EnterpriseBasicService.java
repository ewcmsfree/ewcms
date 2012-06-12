/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.particular.dao.EnterpriseBasicDAO;
import com.ewcms.content.particular.model.EnterpriseBasic;
import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Organ;

@Service
public class EnterpriseBasicService implements EnterpriseBasicServiceable {

	@Autowired
	private EnterpriseBasicDAO enterpriseBasicDAO;
	@Autowired
	private SiteFac siteFac;
	
	@Override
	public Long addEnterpriseBasic(EnterpriseBasic enterpriseBasic) {
		setOrgan(enterpriseBasic);
		enterpriseBasicDAO.persist(enterpriseBasic);
		return enterpriseBasic.getId();
	}

	@Override
	public Long updEnterpriseBasic(EnterpriseBasic enterpriseBasic) {
		setOrgan(enterpriseBasic);
		enterpriseBasic.setRelease(false);
		enterpriseBasicDAO.merge(enterpriseBasic);
		return enterpriseBasic.getId();
	}

	private void setOrgan(EnterpriseBasic enterpriseBasic){
		Integer organId = enterpriseBasic.getOrgan().getId();
		Organ organ = siteFac.getOrgan(organId);
		enterpriseBasic.setOrgan(organ);
	}

	@Override
	public void delEnterpriseBasic(Long id) {
		enterpriseBasicDAO.removeByPK(id);
	}

	@Override
	public EnterpriseBasic findEnterpriseBasicById(Long id) {
		return enterpriseBasicDAO.get(id);
	}

	@Override
	public List<EnterpriseBasic> findEnterpriseBasicAll() {
		return enterpriseBasicDAO.findProjectBasicAll();
	}
	
	@Override
	public void pubEnterpriseBasic(List<Long> enterpriseBasicIds) {
		if (enterpriseBasicIds.isEmpty()) return;
		for (Long enterpriseBasicId : enterpriseBasicIds){
			EnterpriseBasic enterpriseBasic = enterpriseBasicDAO.get(enterpriseBasicId);
			if (enterpriseBasic.getRelease() || enterpriseBasic.getOrgan() == null) continue;
			enterpriseBasic.setRelease(true);
			enterpriseBasicDAO.merge(enterpriseBasic);
		}
	}
	
	@Override
	public void unPubEnterpriseBasic(List<Long> enterpriseBasicIds){
		if (enterpriseBasicIds.isEmpty()) return;
		for (Long projectArticleId : enterpriseBasicIds){
			EnterpriseBasic enterpriseBasic = enterpriseBasicDAO.get(projectArticleId);
			if (!enterpriseBasic.getRelease()) continue;
			enterpriseBasic.setRelease(false);
			enterpriseBasicDAO.merge(enterpriseBasic);
		}
	}

}
