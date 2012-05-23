package com.ewcms.content.particular.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.particular.dao.ProjectArticleDAO;
import com.ewcms.content.particular.dao.ProjectBasicDAO;
import com.ewcms.content.particular.dao.PublishingSectorDAO;
import com.ewcms.content.particular.model.ProjectArticle;
import com.ewcms.content.particular.model.ProjectBasic;
import com.ewcms.content.particular.model.PublishingSector;

@Service
public class ProjectArticleService implements ProjectArticleServiceable {

	@Autowired
	private ProjectBasicDAO projectBasicDAO;
	@Autowired
	private ProjectArticleDAO projectArticleDAO;
	@Autowired
	private PublishingSectorDAO publishingSectorDAO;
	
	@Override
	public Long addProjectArticle(ProjectArticle projectArticle) {
		setProjectBasic(projectArticle);
		setPublishingSector(projectArticle);
		projectArticleDAO.persist(projectArticle);
		return projectArticle.getId();
	}

	@Override
	public Long updProjectArticle(ProjectArticle projectArticle) {
		setProjectBasic(projectArticle);
		setPublishingSector(projectArticle);
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

	private void setPublishingSector(ProjectArticle projectArticle){
		String publishingSector_code = projectArticle.getPublishingSector().getCode();
		PublishingSector publishingSector = publishingSectorDAO.findPublishingSectorByCode(publishingSector_code);
		projectArticle.setPublishingSector(publishingSector);
	}
	
	@Override
	public void delProjectArticle(Long id) {
		projectArticleDAO.removeByPK(id);
	}

	@Override
	public ProjectArticle findProjectArticleById(Long id) {
		return projectArticleDAO.get(id);
	}
}
