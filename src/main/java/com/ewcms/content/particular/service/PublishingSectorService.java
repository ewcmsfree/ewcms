/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.particular.dao.PublishingSectorDAO;
import com.ewcms.content.particular.model.PublishingSector;

@Service
public class PublishingSectorService implements PublishingSectorServiceable {

	@Autowired
	private PublishingSectorDAO publishingSectorDAO;
	
	@Override
	public Long addPublishingSector(PublishingSector publishingSector) {
		publishingSectorDAO.persist(publishingSector);
		return publishingSector.getId();
	}

	@Override
	public Long updPublishingSector(PublishingSector publishingSector) {
		publishingSectorDAO.merge(publishingSector);
		return publishingSector.getId();
	}

	@Override
	public void delPublishingSector(Long id) {
		publishingSectorDAO.removeByPK(id);
	}

	@Override
	public PublishingSector findPublishingSectorById(Long id) {
		return publishingSectorDAO.get(id);
	}

	@Override
	public List<PublishingSector> findPublishingSectorAll() {
		return publishingSectorDAO.findPublishingSectorAll();
	}

	@Override
	public Boolean findPublishingSectorSelectedByPBId(Long projectBasicId,
			String publishingSectorCode) {
		return publishingSectorDAO.findPublishingSectorSelectedByPBId(projectBasicId, publishingSectorCode);
	}

	@Override
	public Boolean findPublishingSectorSelectedByPAId(
			Long projectArticleId, String publishingSectorCode) {
		return publishingSectorDAO.findPublishingSectorSelectedByPAId(projectArticleId, publishingSectorCode);
	}

	@Override
	public Boolean findPublishingSectorSelectedByEBId(Long enterpriseBasicId,
			String publishingSectorCode) {
		return publishingSectorDAO.findPublishingSectorSelectedByEBId(enterpriseBasicId, publishingSectorCode);
	}

	@Override
	public Boolean findPublishingSectorSelectedByEAId(Long enterpriseArticleId,
			String publishingSectorCode) {
		return publishingSectorDAO.findPublishingSectorSelectedByEAId(enterpriseArticleId, publishingSectorCode);
	}

	@Override
	public Boolean findPublishingSectorSelectedByMBId(Long employeBasicId,
			String publishingSectorCode) {
		return publishingSectorDAO.findPublishingSectorSelectedByMBId(employeBasicId, publishingSectorCode);
	}

	@Override
	public Boolean findPublishingSectorSelectedByMAId(Long employeArticleId,
			String publishingSectorCode) {
		return publishingSectorDAO.findPublishingSectorSelectedByMAId(employeArticleId, publishingSectorCode);
	}

}
