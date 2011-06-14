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
import com.ewcms.content.document.model.Relation;

/**
 * 相关文章DAO
 *
 * @author 吴智俊
 */
@Repository
public class RelationDAO extends JpaDAO<Long, Relation> {
	
    @SuppressWarnings("unchecked")
	public List<Relation> findRelationByArticle(Long articleId){
    	String hql = "Select r FROM Article AS o RIGHT JOIN o.relations AS r WHERE o.id=? ORDER BY r.sort";
    	List<Relation> list = this.getJpaTemplate().find(hql,articleId);
    	if (list.isEmpty()) return new ArrayList<Relation>();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public Relation findRelationByArticleAndSort(Long articleId, Integer sort){
    	String hql = "Select r FROM Article AS o RIGHT JOIN o.relations AS r WHERE o.id=? AND r.sort=?";
    	List<Relation> list = this.getJpaTemplate().find(hql,articleId,sort);
    	if (list.isEmpty()) return new Relation();
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public Relation findRelationByArticleAndRelation(Long articleId, Long relationArticleId){
    	String hql = "Select r FROM Article AS o RIGHT JOIN o.relations AS r WHERE o.id=? AND r.article.id=?";
    	List<Relation> list = this.getJpaTemplate().find(hql, articleId, relationArticleId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
}
