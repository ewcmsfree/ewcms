/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Article.Status;

/**
 * 文章信息DAO
 *
 * @author 吴智俊
 */
@Repository
public class ArticleDAO extends JpaDAO<Long, Article> {
    
    private final static Logger logger = LoggerFactory.getLogger(ArticleDAO.class);
    
	public Long findArticleReleseMaxSize(final Integer channelId){
    	String hql = "Select Count(m.id) From ArticleMain As m Where m.channelId=:channelId And m.article.status=:status";

    	TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
    	query.setParameter("channelId", channelId);
    	query.setParameter("status", Status.RELEASE);

    	return query.getSingleResult();
    }
    
    public List<Article> findPublishArticles(final Integer channelId,final Boolean forceAgain,final Integer limit){
    	String hql = "Select m.article From ArticleMain As m Where m.reference=false And m.channelId=:channelId And m.article.status In (:status)";
    	List<Status> status = new ArrayList<Status>();
    	if (forceAgain) {
    		status.add(Status.RELEASE);
    	}
    	status.add(Status.PRERELEASE);

    	TypedQuery<Article> query = this.getEntityManager().createQuery(hql, Article.class);
    	query.setParameter("channelId", channelId);
    	query.setParameter("status", status);
    	query.setMaxResults(limit);

    	return query.getResultList();
    }
    
    public List<Article> findArticleReleasePage(final Integer channelId, final Integer page, final Integer row, final Boolean top){
    	logger.debug("query channel is {}", channelId);
    	String hql = "Select m.article From ArticleMain As m Where m.channelId=:channelId And m.article.status=:status And m.top In (:tops) Order By m.sort Asc, m.article.published Desc, m.id Desc";
    	int startRow = page * row;
    	List<Boolean> tops = new ArrayList<Boolean>();
    	if (top == null) {
    		tops.add(Boolean.FALSE);
    		tops.add(Boolean.TRUE);
    	} else {
    		tops.add(top);
    	}

    	TypedQuery<Article> query = this.getEntityManager().createQuery(hql, Article.class);
    	query.setParameter("channelId", channelId);
    	query.setParameter("status", Status.RELEASE);
    	query.setParameter("tops", tops);
    	query.setFirstResult(startRow);
    	query.setMaxResults(row);
    	// .setHint("org.hibernate.cacheable", true);

    	return query.getResultList();
    }
}
