/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import com.ewcms.content.particular.model.EnterpriseArticle;

public interface EnterpriseArticleServiceable {
	public Long addEnterpriseArticle(EnterpriseArticle enterpriseArticle);
	
	public Long updEnterpriseArticle(EnterpriseArticle enterpriseArticle);
	
	public void delEnterpriseArticle(Long id);
	
	public EnterpriseArticle findEnterpriseArticleById(Long id);
}
