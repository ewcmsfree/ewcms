package com.ewcms.content.particular.service;

import com.ewcms.content.particular.model.ProjectArticle;

public interface ProjectArticleServiceable {
	public Long addProjectArticle(ProjectArticle projectArticle);
	
	public Long updProjectArticle(ProjectArticle projectArticle);
	
	public void delProjectArticle(Long id);
	
	public ProjectArticle findProjectArticleById(Long id);
}
