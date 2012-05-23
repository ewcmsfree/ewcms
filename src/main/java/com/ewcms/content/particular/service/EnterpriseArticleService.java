/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.particular.dao.EnterpriseArticleDAO;
import com.ewcms.content.particular.dao.EnterpriseBasicDAO;
import com.ewcms.content.particular.dao.PublishingSectorDAO;
import com.ewcms.content.particular.model.EnterpriseArticle;
import com.ewcms.content.particular.model.EnterpriseBasic;
import com.ewcms.content.particular.model.PublishingSector;

@Service
public class EnterpriseArticleService implements EnterpriseArticleServiceable {

	@Autowired
	private EnterpriseBasicDAO enterpriseBasicDAO;
	@Autowired
	private EnterpriseArticleDAO enterpriseArticleDAO;
	@Autowired
	private PublishingSectorDAO publishingSectorDAO;
	
	@Override
	public Long addEnterpriseArticle(EnterpriseArticle enterpriseArticle) {
		setEnterpriseBasic(enterpriseArticle);
		setPublishingSector(enterpriseArticle);
		enterpriseArticleDAO.persist(enterpriseArticle);
		return enterpriseArticle.getId();
	}

	@Override
	public Long updEnterpriseArticle(EnterpriseArticle enterpriseArticle) {
		setEnterpriseBasic(enterpriseArticle);
		setPublishingSector(enterpriseArticle);
		enterpriseArticleDAO.merge(enterpriseArticle);
		return enterpriseArticle.getId();
	}
	
	private void setEnterpriseBasic(EnterpriseArticle enterpriseArticle){
		String projectBasic_yyzzzch = enterpriseArticle.getEnterpriseBasic().getYyzzzch();
		Assert.notNull(projectBasic_yyzzzch);
		EnterpriseBasic enterpriseBasic = enterpriseBasicDAO.findEnterpriseBasicByYyzzzch(projectBasic_yyzzzch);
		Assert.notNull(enterpriseBasic);
		enterpriseArticle.setEnterpriseBasic(enterpriseBasic);
	}
	
	private void setPublishingSector(EnterpriseArticle enterpriseArticle){
		String publishingSector_code = enterpriseArticle.getPublishingSector().getCode();
		PublishingSector publishingSector = publishingSectorDAO.findPublishingSectorByCode(publishingSector_code);
		enterpriseArticle.setPublishingSector(publishingSector);
	}

	@Override
	public void delEnterpriseArticle(Long id) {
		enterpriseArticleDAO.removeByPK(id);
	}

	@Override
	public EnterpriseArticle findEnterpriseArticleById(Long id) {
		return enterpriseArticleDAO.get(id);
	}

}
