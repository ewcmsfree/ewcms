/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

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
}
