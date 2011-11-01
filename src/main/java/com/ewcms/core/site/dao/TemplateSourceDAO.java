/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;
import com.ewcms.common.dao.JpaDAO;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateSource;

/**
 * @author 周冬初
 * 
 */
@Repository
public class TemplateSourceDAO extends JpaDAO<Integer, TemplateSource> {
	/**
	 * 获取节点子模板资源
	 * 
	 */
	public List<TemplateSource> getTemplateSourceChildren(final Integer parentId, final Integer siteId) {
		@SuppressWarnings("unchecked")
		Object res = this.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String hql;
				Query query;
				if (parentId == null) {
					hql = "From TemplateSource o Where o.parent is null and o.site.id=? Order By o.id";
					query = em.createQuery(hql);
					query.setParameter(1, siteId);
				} else {
					hql = "From TemplateSource o Where o.parent.id=? and o.site.id=? Order By o.id";
					query = em.createQuery(hql);
					query.setParameter(1, parentId);
					query.setParameter(2, siteId);
				}
				return query.getResultList();
			}
		});
		return (List<TemplateSource>) res;
	}

	/**
	 * 获取站点专栏资源节点
	 * 
	 */
	public TemplateSource getChannelTemplateSource(final String siteSrcName, final Integer siteId,
			final Integer parentId) {
		@SuppressWarnings("unchecked")
		Object res = this.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String hql;
				Query query;
				if (parentId == null) {
					hql = "From TemplateSource o Where  o.name=? and o.site.id=? and o.parent is null";
					query = em.createQuery(hql);
					query.setParameter(1, siteSrcName);
					query.setParameter(2, siteId);
				} else {
					hql = "From TemplateSource o Where  o.name=? and o.site.id=? and o.parent.id=?";
					query = em.createQuery(hql);
					query.setParameter(1, siteSrcName);
					query.setParameter(2, siteId);
					query.setParameter(3, parentId);
				}
				return query.getResultList();
			}
		});
		List<TemplateSource> templateList = (List<TemplateSource>) res;
		if (templateList == null || templateList.size() == 0)
			return null;
		return templateList.get(0);
	}

	/**
	 * 获取站点未 发布模板资源
	 * 
	 */
	public List<TemplateSource> getNotReleaseTemplateSource(final Integer siteId) {
		@SuppressWarnings("unchecked")
		Object res = this.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String hql;
				Query query;
				hql = "From TemplateSource o Where o.release=false and o.site.id=? Order By o.id";
				query = em.createQuery(hql);
				query.setParameter(1, siteId);
				return query.getResultList();
			}
		});
		return (List<TemplateSource>) res;
	}
	
	@SuppressWarnings("unchecked")
	public void updateNotRelease(final Integer siteId){
        this.getJpaTemplate().execute(new JpaCallback() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
            	String hql = "update TemplateSource o set o.release=false Where o.site.id=?";
                Query query = em.createQuery(hql);
                query.setParameter(1, siteId);
                query.executeUpdate();	
                return null;
            }
        });	
	}
	
	public TemplateSource getTemplateSourceByPath(final Integer siteId,final String path){
	    List<TemplateSource> res = this.getJpaTemplate().execute(new JpaCallback<List<TemplateSource>>() {
            @Override
            public List<TemplateSource> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "From TemplateSource o Where  o.path=? and o.site.id=?";
                TypedQuery<TemplateSource> query = em.createQuery(hql, TemplateSource.class);
                query.setParameter(1,path);
                query.setParameter(2, siteId);
                return query.getResultList();
            }
        });
	    return res.isEmpty() ? null : res.get(0);
	}	
}
