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

import com.ewcms.content.particular.dao.ProjectArticleDAO;
import com.ewcms.content.particular.dao.ProjectBasicDAO;
import com.ewcms.content.particular.model.ProjectArticle;
import com.ewcms.content.particular.model.ProjectBasic;
import com.ewcms.core.site.SiteFacable;
import com.ewcms.core.site.model.Organ;

@Service
public class ProjectArticleService implements ProjectArticleServiceable {

	@Autowired
	private ProjectBasicDAO projectBasicDAO;
	@Autowired
	private ProjectArticleDAO projectArticleDAO;
	@Autowired
	private SiteFacable siteFac;
	
	@Override
	public Long addProjectArticle(ProjectArticle projectArticle) {
		setProjectBasic(projectArticle);
		setOrgan(projectArticle);
		projectArticleDAO.persist(projectArticle);
		return projectArticle.getId();
	}

	@Override
	public Long updProjectArticle(ProjectArticle projectArticle) {
		setProjectBasic(projectArticle);
		setOrgan(projectArticle);
		projectArticle.setRelease(false);
		projectArticleDAO.merge(projectArticle);
		return projectArticle.getId();
	}
	
	private void setProjectBasic(ProjectArticle projectArticle){
		String projectBasic_code = projectArticle.getProjectBasic().getCode();
		Assert.notNull(projectBasic_code);
		ProjectBasic projectBasic = projectBasicDAO.findProjectBasicByCode(projectBasic_code);
		Assert.notNull(projectBasic);
		projectArticle.setProjectBasic(projectBasic);
	}

	private void setOrgan(ProjectArticle projectArticle){
		Integer organId = projectArticle.getOrgan().getId();
		Organ organ = siteFac.getOrgan(organId);
		projectArticle.setOrgan(organ);
	}
	
	@Override
	public void delProjectArticle(Long id) {
		projectArticleDAO.removeByPK(id);
	}

	@Override
	public ProjectArticle findProjectArticleById(Long id) {
		return projectArticleDAO.get(id);
	}
	
	@Override
	public void pubProjectArticle(List<Long> projectArticleIds) {
		if (projectArticleIds.isEmpty()) return;
		for (Long projectArticleId : projectArticleIds){
			ProjectArticle projectArticle = projectArticleDAO.get(projectArticleId);
			if (projectArticle.getRelease() || projectArticle.getOrgan() == null) continue;
			projectArticle.setRelease(true);
			projectArticleDAO.merge(projectArticle);
		}
	}
	
	@Override
	public void unPubProjectArticle(List<Long> projectArticleIds){
		if (projectArticleIds.isEmpty()) return;
		for (Long projectArticleId : projectArticleIds){
			ProjectArticle projectArticle = projectArticleDAO.get(projectArticleId);
			if (!projectArticle.getRelease()) continue;
			projectArticle.setRelease(false);
			projectArticleDAO.merge(projectArticle);
		}
	}

	@Override
	public List<ProjectArticle> findProjectArticleAll() {
		return projectArticleDAO.findProjectArticleAll();
	}
}
