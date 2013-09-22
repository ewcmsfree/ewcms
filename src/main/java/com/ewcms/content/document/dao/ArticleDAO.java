/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;
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
    
	public Long findArticleReleseCount(final Integer channelId){
    	String hql = "Select Count(m.id) From ArticleMain As m Where m.channelId=:channelId And m.article.status=:status And m.article.published<:currentDate";

    	TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
    	query.setParameter("channelId", channelId);
    	query.setParameter("status", Status.RELEASE);
    	query.setParameter("currentDate", new Date(), TemporalType.TIMESTAMP);

    	return query.getSingleResult();
    }
    
    public List<Article> findPublishArticles(final Integer channelId,final Boolean forceAgain,final Integer limit){
    	String hql = "Select m.article From ArticleMain As m Where m.reference=false And m.channelId=:channelId And m.article.status In (:status) And m.article.published<:currentDate";
    	List<Status> status = new ArrayList<Status>();
    	if (forceAgain) {
    		status.add(Status.RELEASE);
    	}
    	status.add(Status.PRERELEASE);

    	TypedQuery<Article> query = this.getEntityManager().createQuery(hql, Article.class);
    	query.setParameter("channelId", channelId);
    	query.setParameter("status", status);
    	query.setParameter("currentDate", new Date(), TemporalType.TIMESTAMP);
    	query.setMaxResults(limit);

    	return query.getResultList();
    }
    
    public List<Article> findArticleReleasePage(final Integer channelId, final Integer page, final Integer row, final Boolean top){
    	logger.debug("query channel is {}", channelId);
    	String hql = "Select m.article From ArticleMain As m Where m.channelId=:channelId And m.article.status=:status And m.top In (:tops) And m.article.published<:currentDate Order By m.sort Asc, m.article.published Desc, m.id Desc";
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
    	query.setParameter("currentDate", new Date(), TemporalType.TIMESTAMP);
    	query.setFirstResult(startRow);
    	query.setMaxResults(row);
    	// .setHint("org.hibernate.cacheable", true);

    	return query.getResultList();
    }
    
    public List<Article> findChildChannelArticleReleasePage(final List<Integer> channelIds, final Integer page, final Integer row, final Boolean top){
    	String hql = "Select m.article From ArticleMain As m Where m.channelId In (:channelIds) And m.article.status=:status And m.top In (:tops) And m.article.published<:currentDate Order By m.sort Asc, m.article.published Desc, m.id Desc";
    	int startRow = page * row;
    	List<Boolean> tops = new ArrayList<Boolean>();
    	if (top == null) {
    		tops.add(Boolean.FALSE);
    		tops.add(Boolean.TRUE);
    	} else {
    		tops.add(top);
    	}

    	TypedQuery<Article> query = this.getEntityManager().createQuery(hql, Article.class);
    	query.setParameter("channelIds", channelIds);
    	query.setParameter("status", Status.RELEASE);
    	query.setParameter("tops", tops);
    	query.setParameter("currentDate", new Date(), TemporalType.TIMESTAMP);
    	query.setFirstResult(startRow);
    	query.setMaxResults(row);
    	// .setHint("org.hibernate.cacheable", true);

    	return query.getResultList();
    }
}
