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
import com.ewcms.content.document.model.Related;

/**
 * 相关文章DAO
 *
 * @author 吴智俊
 */
@Repository
public class RelatedDAO extends JpaDAO<Long, Related> {
	
    @SuppressWarnings("unchecked")
	public List<Related> findRelatedByArticle(Long articleId){
    	String hql = "Select r FROM Article AS o RIGHT JOIN o.relateds AS r WHERE o.id=? ORDER BY r.sort";
    	List<Related> list = this.getJpaTemplate().find(hql,articleId);
    	if (list.isEmpty()) return new ArrayList<Related>();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public Related findRelatedByArticleAndSort(Long articleId, Integer sort){
    	String hql = "Select r FROM Article AS o RIGHT JOIN o.relateds AS r WHERE o.id=? AND r.sort=?";
    	List<Related> list = this.getJpaTemplate().find(hql,articleId,sort);
    	if (list.isEmpty()) return new Related();
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public Related findRelatedByArticleAndRelated(Long articleId, Long relatedId){
    	String hql = "Select r From Article As o Right Join o.relateds As r Where o.id=? And r.id=?";
    	List<Related> list = this.getJpaTemplate().find(hql, articleId, relatedId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
}
