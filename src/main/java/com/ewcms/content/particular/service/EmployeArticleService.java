/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.particular.dao.EmployeArticleDAO;
import com.ewcms.content.particular.dao.EmployeBasicDAO;
import com.ewcms.content.particular.model.EmployeArticle;
import com.ewcms.content.particular.model.EmployeBasic;
import com.ewcms.core.site.SiteFacable;
import com.ewcms.core.site.model.Organ;

@Service
public class EmployeArticleService implements EmployeArticleServiceable {

	@Autowired
	private EmployeBasicDAO employeBasicDAO;
	@Autowired
	private EmployeArticleDAO employeArticleDAO;
	@Autowired
	private SiteFacable siteFac;
	
	@Override
	public Long addEmployeArticle(EmployeArticle employeArticle) {
		setEmployeBasic(employeArticle);
		setOrgan(employeArticle);
		employeArticleDAO.persist(employeArticle);
		return employeArticle.getId();
	}

	@Override
	public Long updEmployeArticle(EmployeArticle employeArticle) {
		setEmployeBasic(employeArticle);
		setOrgan(employeArticle);
		employeArticle.setRelease(false);
		employeArticleDAO.merge(employeArticle);
		return employeArticle.getId();
	}

	private void setEmployeBasic(EmployeArticle employeArticle){
		String employeBasic_cardCode = employeArticle.getEmployeBasic().getCardCode();
		Assert.notNull(employeBasic_cardCode);
		EmployeBasic employeBasic = employeBasicDAO.findEmployeBasicByCardCode(employeBasic_cardCode);
		Assert.notNull(employeBasic);
		employeArticle.setEmployeBasic(employeBasic);
	}
	
	private void setOrgan(EmployeArticle employeArticle){
		Integer organId = employeArticle.getOrgan().getId();
		Organ organ = siteFac.getOrgan(organId);
		employeArticle.setOrgan(organ);
	}
	
	@Override
	public void delEmployeArticle(Long id) {
		employeArticleDAO.removeByPK(id);
	}

	@Override
	public EmployeArticle findEmployeArticleById(Long id) {
		return employeArticleDAO.get(id);
	}
	
	@Override
	public void pubEmployeArticle(List<Long> employeArticleIds) {
		if (employeArticleIds.isEmpty()) return;
		for (Long employeArticleId : employeArticleIds){
			EmployeArticle employeArticle = employeArticleDAO.get(employeArticleId);
			if (employeArticle.getRelease() || employeArticle.getOrgan() == null) continue;
			employeArticle.setRelease(true);
			employeArticleDAO.merge(employeArticle);
		}
	}
	
	@Override
	public void unPubEmployeArticle(List<Long> employeArticleIds){
		if (employeArticleIds.isEmpty()) return;
		for (Long employeArticleId : employeArticleIds){
			EmployeArticle employeBasic = employeArticleDAO.get(employeArticleId);
			if (!employeBasic.getRelease()) continue;
			employeBasic.setRelease(false);
			employeArticleDAO.merge(employeBasic);
		}
	}

	@Override
	public List<EmployeArticle> findEmployeArticleAll() {
		return employeArticleDAO.findEmployeArticleAll();
	}
}
