/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import com.ewcms.content.particular.model.ProjectArticle;

public interface ProjectArticleServiceable {
	public Long addProjectArticle(ProjectArticle projectArticle);
	
	public Long updProjectArticle(ProjectArticle projectArticle);
	
	public void delProjectArticle(Long id);
	
	public ProjectArticle findProjectArticleById(Long id);
}
