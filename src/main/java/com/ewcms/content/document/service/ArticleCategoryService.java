/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.document.dao.ArticleCategoryDAO;
import com.ewcms.content.document.model.ArticleCategory;

/**
 * @author 吴智俊
 */
@Service
public class ArticleCategoryService implements ArticleCategoryServiceable {
	
	@Autowired
	private ArticleCategoryDAO articleCategoryDAO;

	@Override
	public Integer addArticleCategory(ArticleCategory articleCategory) {
		Assert.notNull(articleCategory);
		articleCategoryDAO.persist(articleCategory);
		return articleCategory.getId();
	}

	@Override
	public Integer updArticleCategory(ArticleCategory articleCategory) {
		Assert.notNull(articleCategory);
		articleCategoryDAO.merge(articleCategory);
		return articleCategory.getId();
	}

	@Override
	public void delArticleCategory(Integer articleCategoryId) {
		articleCategoryDAO.removeByPK(articleCategoryId);
	}

	@Override
	public ArticleCategory findArticleCategory(Integer articleCategoryId) {
		return articleCategoryDAO.get(articleCategoryId);
	}

	@Override
	public List<ArticleCategory> findArticleCategoryAll() {
		return articleCategoryDAO.findArticleCategoryAll();
	}

}
