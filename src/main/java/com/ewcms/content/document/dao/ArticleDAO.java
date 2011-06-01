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
import com.ewcms.content.document.model.Article;

/**
 *
 * @author 吴智俊
 */
@Repository
public class ArticleDAO extends JpaDAO<Long, Article> {
	
    @SuppressWarnings("unchecked")
    public List<Article> findArticleBySiteAndRef(Integer siteId, Long refId){
    	String hql = "FROM Article AS o WHERE o.refArticleRmc.id=? and o.channel.site.id=?";
    	List<Article> list = this.getJpaTemplate().find(hql,refId,siteId);
    	if (list.isEmpty()) return new ArrayList<Article>();
    	return list;    	
    }
    
    @SuppressWarnings("unchecked")
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Integer articleCategoryId){
    	String hql = "Select a From Article As a Left Join a.categories As c Where a.id=? And c.id=?";
    	List<Article> list = this.getJpaTemplate().find(hql, articleId, articleCategoryId);
    	if (list.isEmpty()) return false;
    	return true;
    }
}
