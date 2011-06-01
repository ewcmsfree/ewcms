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
import com.ewcms.content.document.model.Recommend;

/**
 * 推荐文章DAO
 * @author 吴智俊
 */
@Repository
public class RecommendDAO extends JpaDAO<Long, Recommend> {
	
    @SuppressWarnings("unchecked")
	public List<Recommend> findRecommendByArticle(Long articleId){
    	String hql = "SELECT r FROM Article AS o RIGHT JOIN o.recommends AS r WHERE o.id=? ORDER BY r.sort";
    	List<Recommend> list = this.getJpaTemplate().find(hql,articleId);
    	if (list.isEmpty()) return new ArrayList<Recommend>();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public Recommend findRecommendByArticleAndSort(Long articleId, Integer sort){
    	String hql = "Select r FROM Article AS o RIGHT JOIN o.recommends AS r WHERE o.id=? AND r.sort=?";
    	List<Recommend> list = this.getJpaTemplate().find(hql,articleId,sort);
    	if (list.isEmpty()) return new Recommend();
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public Recommend findRecommendByArticleAndRecommend(Long articleId, Long recommendId){
    	String hql = "Select r From Article As o Right Join o.recommends As r Where o.id=? And r.id=?";
    	List<Recommend> list = this.getJpaTemplate().find(hql, articleId, recommendId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
}
