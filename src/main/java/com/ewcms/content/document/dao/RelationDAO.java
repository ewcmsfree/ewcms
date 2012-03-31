/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
	
	public List<Relation> findRelationByArticle(final Long articleId){
    	String hql = "Select r From Article AS o Right Join o.relations As r Where o.id=:articleId Order By r.sort";
        
        TypedQuery<Relation> query = this.getEntityManager().createQuery(hql, Relation.class);
        query.setParameter("articleId", articleId);

        return query.getResultList();
    }
    
	public Relation findRelationByArticleAndSort(final Long articleId, final Integer sort){
    	String hql = "Select r From Article AS o Right Join o.relations As r Where o.id=:articleId And r.sort=:sort";
        
        TypedQuery<Relation> query = this.getEntityManager().createQuery(hql, Relation.class);
        query.setParameter("articleId", articleId);
        query.setParameter("sor", sort);

        Relation relation = null;
        try{
        	relation = (Relation) query.getSingleResult();
        }catch(NoResultException e){
        }
        return relation;
    }
    
	public Relation findRelationByArticleAndRelation(final Long articleId, final Long relationArticleId){
    	String hql = "Select r From Article AS o Right Join o.relations As r Where o.id=:articleId And r.article.id=:relationArticleId";
        
        TypedQuery<Relation> query = this.getEntityManager().createQuery(hql, Relation.class);
        query.setParameter("articleId", articleId);
        query.setParameter("relationArticleId", relationArticleId);
       
        Relation relation = null;
        try{
        	relation = (Relation) query.getSingleResult();
        }catch(NoResultException e){
        }
        return relation;
    }
}
