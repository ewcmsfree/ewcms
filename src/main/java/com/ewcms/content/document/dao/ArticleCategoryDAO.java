/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.document.model.ArticleCategory;

/**
 * 文章分类属性DAO
 * 
 * @author 吴智俊
 */
@Repository
public class ArticleCategoryDAO extends JpaDAO<Integer, ArticleCategory> {
	
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> findArticleCategoryAll(){
		String hql = "From ArticleCategory c Order By c.id";
		List<ArticleCategory> list = this.getJpaTemplate().find(hql);
		if (list.isEmpty()) return new ArrayList<ArticleCategory>();
		return list;
	}
}
