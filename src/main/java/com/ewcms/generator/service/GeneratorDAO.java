/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.TemplateSource;

/**
 *
 *
 * @author wangwei
 * @deprecated
 */
@Repository
public class GeneratorDAO extends JpaDaoSupport implements GeneratorDAOable {

    @Override
    public Article getArticle(final int id) {
        return this.getJpaTemplate().find(Article.class, id);
    }

    @Override
    public void articlePublish(final int id, final String url) {
        Article article = getArticle(id);
        article.setStatus(ArticleStatus.RELEASE);
        article.setUrl(url);
        if(article.getPublished() == null){
            article.setPublished(new Date());
        }
        getJpaTemplate().persist(article);
    }

    @Override
    public Channel getChannel(final int id) {
        return this.getJpaTemplate().find(Channel.class, id);
    }

    @Override
    public List<Article> findPreReleaseArticle(final int channelId) {
       return getJpaTemplate().execute(new JpaCallback<List<Article>>() {
            @Override
            public List<Article> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "From ArticleRmc o Where o.channel.id= ? And o.status=? And o.deleteFlag=? Order By o.published";
                TypedQuery<Article> query = em.createQuery(hql, Article.class);
                query.setParameter(1, channelId);
                query.setParameter(2, ArticleStatus.PRERELEASE);
                query.setParameter(3,Boolean.FALSE);
                
                return query.getResultList();
            }
        });
    }

    @Override
    public List<Article> findArticlePage(final int channelId, final int page, final int row) {
        return getJpaTemplate().execute(new JpaCallback<List<Article>>() {

            @Override
            public List<Article> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Select o From ArticleRmc o Left Join o.refChannel r Where (o.channel.id= ? Or r.id= ?) And o.status=? And o.deleteFlag=? And o.published< ? Order By o.published Desc";
                TypedQuery<Article> query = em.createQuery(hql, Article.class);
                query.setParameter(1, channelId);
                query.setParameter(2, channelId);
                query.setParameter(3, ArticleStatus.RELEASE);
                query.setParameter(4, false);
                query.setParameter(5, new Date(), TemporalType.TIMESTAMP);
                int start = page * row;
                query.setFirstResult(start);
                query.setMaxResults(row);
                return query.getResultList();
            }
        });
    }

    @Override
    public List<Article> findArticlePageTop(final int channelId,final int row,final boolean top) {
        return getJpaTemplate().execute(new JpaCallback<List<Article>>() {

            @Override
            public List<Article> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "From ArticleRmc o Where o.channel.id= ? And o.status=? And o.deleteFlag=? And o.article.topFlag=? And o.published< ? Order By o.published Desc";
                TypedQuery<Article> query = em.createQuery(hql, Article.class);
                query.setParameter(1, channelId);
                query.setParameter(2, ArticleStatus.RELEASE);
                query.setParameter(3, false);
                query.setParameter(4, top);
                query.setParameter(5, new Date(), TemporalType.TIMESTAMP);
                query.setFirstResult(0);
                query.setMaxResults(row);
                return query.getResultList();
            }
        });
    }

    @Override
    public int getArticleCount(final int channelId) {
        return getJpaTemplate().execute(new JpaCallback<Integer>() {

            @Override
            public Integer doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Select count(o.id) From ArticleRmc o Left Join o.refChannel r Where (o.channel.id= ? Or r.id= ?) And o.status=? And o.deleteFlag=? And o.published< ?";
                Query query = em.createQuery(hql);
                query.setParameter(1, channelId);
                query.setParameter(2, channelId);
                query.setParameter(3, ArticleStatus.RELEASE);
                query.setParameter(4, false);
                query.setParameter(5, new Date(), TemporalType.TIMESTAMP);
                Number number = (Number) query.getSingleResult();
                return number.intValue();
            }
        });
    }

    @Override
    public Channel getChannelByUrlOrDir(final int siteId, final String url) {
        return getJpaTemplate().execute(new JpaCallback<Channel>() {

            @Override
            public Channel doInJpa(EntityManager em) throws PersistenceException {
                if (url.equals("/")) {
                    String hql = "From Channel o Where o.site.id=? And o.parent is null";
                    TypedQuery<Channel> query = em.createQuery(hql, Channel.class);
                    query.setParameter(1, siteId);
                    return query.getSingleResult();
                } else {
                    String hql = "From Channel o Where o.site.id= ? And (o.absUrl=? Or o.pubPath=?)";
                    TypedQuery<Channel> query = em.createQuery(hql, Channel.class);
                    query.setParameter(1, siteId);
                    query.setParameter(2, url);
                    query.setParameter(3, url);
                    return query.getSingleResult();
                }
            }
        });
    }

    @Override
    public String getSiteServerDir(int id) {
        Site site = getJpaTemplate().find(Site.class, id);
        return site == null ? "" : site.getServerDir();
    }
 
    @Override
    public List<Resource> getReleaseResource(final int siteId) {
        return getJpaTemplate().execute(new JpaCallback<List<Resource>>() {
            @Override
            public List<Resource> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "From Resource o Where o.siteId= ? And o.release = ?";
                TypedQuery<Resource> query = em.createQuery(hql, Resource.class);
                query.setParameter(1, siteId);
                query.setParameter(2, Boolean.FALSE);
                return query.getResultList();
            }
        });
    }

    @Override
    public Resource getResource(int id){
        return getJpaTemplate().find(Resource.class, id);
    }

    @Override
    public void releaseResource(final Resource resource) {
        resource.setRelease(true);
        getJpaTemplate().persist(resource);
    }

    @Override
    public void cleanArticlePublish(final int channelId) {
       getJpaTemplate().execute(new JpaCallback<Object>(){
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Update ArticleRmc o Set o.status = ? , o.url = Null Where o.channel.id=?";
                em.createQuery(hql)
                    .setParameter(1, ArticleStatus.PRERELEASE)
                    .setParameter(2, channelId)
                    .executeUpdate();
                
                return null;
            }
       });
    }

    @Override
    public List<TemplateSource> findNotReleaseTemplateSources(final int siteId) {
        
        return getJpaTemplate().execute(new JpaCallback<List<TemplateSource>>(){
            @Override
            public List<TemplateSource> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "From TemplateSource o Where o.release = ? And o.site.id = ? ";
                TypedQuery<TemplateSource> query = em.createQuery(hql, TemplateSource.class);
                query.setParameter(1, Boolean.FALSE);
                query.setParameter(2, siteId);
                
                return query.getResultList();
            }
            
        });
    }

    @Override
    public TemplateSource getTemplateSource(int id) {
        return getJpaTemplate().find(TemplateSource.class, id);
    }

    @Override
    public void releaseTemplateSource(final int id) {
        getJpaTemplate().execute(new JpaCallback<Object>(){
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Update TemplateSource o Set o.release = ? Where o.id = ? ";
                em.createQuery(hql)
                    .setParameter(1, Boolean.TRUE)
                    .setParameter(2, id)
                    .executeUpdate();
                
                return null;
            }
        });
    }

    @Override
    public List<TemplateSource> findChildrenTemplateSource(final int parentId) {
        return getJpaTemplate().execute(new JpaCallback<List<TemplateSource>>(){
            @Override
            public List<TemplateSource> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "From TemplateSource o Where o.parent.id = ?";
                TypedQuery<TemplateSource> query = em.createQuery(hql,TemplateSource.class);
                 query.setParameter(1, parentId);
                
                return query.getResultList();
            }
        });
    }

}
