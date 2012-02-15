/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Article.Status;
import com.ewcms.content.document.model.ArticleMain;

/**
 * 文章信息DAO
 *
 * @author 吴智俊
 */
@Repository
public class ArticleDAO extends JpaDAO<Long, Article> {
    
    private final static Logger logger = LoggerFactory.getLogger(ArticleDAO.class);
    
    @SuppressWarnings("unchecked")
	public Long findArticleReleseMaxSize(Integer channelId){
    	String hql = "Select Count(m.id) From ArticleMain As m Where m.channelId=? And m.article.status=?";
    	List<Long> list = this.getJpaTemplate().find(hql, channelId, Status.RELEASE);
    	if (list.isEmpty()) return 0L;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public ArticleMain findArticleMainByArticleId(Long articleId){
    	String hql = "Select m From ArticleMain As m Left Join m.article As a Where a.id=? And reference = false";
    	List<ArticleMain> list = this.getJpaTemplate().find(hql, articleId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    public List<Article> findPublishArticles(final Integer channelId,final Boolean forceAgain,final Integer limit){
        return this.getJpaTemplate().execute(new JpaCallback<List<Article>>(){
            @Override
            public List<Article> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Select m.article From ArticleMain As m Where m.reference=false And m.channelId=?1 And m.article.status In (?2)";
                List<Status> status = new ArrayList<Status>();
                if(forceAgain){
                    status.add(Status.RELEASE);
                }
                status.add(Status.PRERELEASE);
                return em.createQuery(hql,Article.class)
                                .setParameter(1, channelId)
                                .setParameter(2, status)
                                .setMaxResults(limit)
                                .getResultList();
            }
        });
    }
    
    public List<Article> findArticleReleasePage(final Integer channelId, final Integer page, final Integer row, final Boolean top){
        logger.debug("query channel is {}",channelId);
        return this.getJpaTemplate().execute(new JpaCallback<List<Article>>(){
            @Override
            public List<Article> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Select m.article From ArticleMain As m Where m.channelId=?1 And m.article.status=?2 And m.top In (?3) Order By m.sort Asc, m.article.published Desc, m.id Desc" ;
                int startRow = page * row;
                List<Boolean> tops = new ArrayList<Boolean>();
                if(top == null){
                    tops.add(Boolean.FALSE);
                    tops.add(Boolean.TRUE);
                }else{
                    tops.add(top);
                }
                TypedQuery<Article> query = em .createQuery(hql,Article.class)
                        .setParameter(1, channelId)
                        .setParameter(2, Status.RELEASE)
                        .setParameter(3, tops)
                        .setFirstResult(startRow)
                        .setMaxResults(row);
//                      .setHint("org.hibernate.cacheable", true);
                return query.getResultList();
            }
        });
    }
}
