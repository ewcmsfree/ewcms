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

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.core.site.model.TemplateSource;

/**
 * @author 周冬初
 * @author wuzhijun
 */
@Repository
public class TemplateSourceDAO extends JpaDAO<Integer, TemplateSource> {
	/**
	 * 获取节点子模板资源
	 * 
	 */
	public List<TemplateSource> getTemplateSourceChildren(final Integer parentId, final Integer siteId) {
		String hql;
		TypedQuery<TemplateSource> query;
		if (parentId == null) {
			hql = "From TemplateSource o Where o.parent is null and o.site.id=:siteId Order By o.id";

			query = this.getEntityManager().createQuery(hql, TemplateSource.class);
			query.setParameter("siteId", siteId);
		} else {
			hql = "From TemplateSource o Where o.parent.id=:parentId and o.site.id=:siteId Order By o.id";

			query = this.getEntityManager().createQuery(hql,TemplateSource.class);
			query.setParameter("parentId", parentId);
			query.setParameter("siteId", siteId);
		}
		return query.getResultList();
	}

	/**
	 * 获取站点专栏资源节点
	 * 
	 */
	public TemplateSource getChannelTemplateSource(final String siteSrcName, final Integer siteId, final Integer parentId) {
		String hql;
		TypedQuery<TemplateSource> query;
		if (parentId == null) {
			hql = "From TemplateSource o Where o.name=:siteSrcName and o.site.id=:siteId and o.parent is null";

			query = this.getEntityManager().createQuery(hql, TemplateSource.class);
			query.setParameter("siteSrcName", siteSrcName);
			query.setParameter("siteId", siteId);
		} else {
			hql = "From TemplateSource o Where o.name=:siteSrcName and o.site.id=:siteId and o.parent.id=:parentId";

			query = this.getEntityManager().createQuery(hql, TemplateSource.class);
			query.setParameter("siteSrcName", siteSrcName);
			query.setParameter("siteId", siteId);
			query.setParameter("parentId", parentId);
		}
		
		TemplateSource templateSource = null;
		try{
			templateSource = (TemplateSource) query.getSingleResult();
		}catch(NoResultException e){
		}
		return templateSource;
	}

	/**
	 * 获取需要发布的模板资源
	 * 
	 * @param siteId 站点编号
	 * @param forceAgain 再次发布
	 * @return
	 */
	public List<TemplateSource> getPublishTemplateSources(final Integer siteId,final Boolean forceAgain) {
		String hql = "From TemplateSource o Where o.release=false And o.site.id=:siteId And o.sourceEntity Is Not null";
		if (forceAgain) {
			hql = "From TemplateSource o Where o.site.id=:siteId And o.sourceEntity Is Not null";
		}

		TypedQuery<TemplateSource> query = this.getEntityManager().createQuery(hql, TemplateSource.class);
		query.setParameter("siteId", siteId);

		return query.getResultList();
	}
	
	public TemplateSource getTemplateSourceByPath(final String path){
		String hql = "From TemplateSource o Where o.uniquePath=:path";

		TypedQuery<TemplateSource> query = this.getEntityManager().createQuery(hql, TemplateSource.class);
		query.setParameter("path", path);

		TemplateSource templateSource = null;
		try{
			templateSource = (TemplateSource) query.getSingleResult();
		}catch(NoResultException e){
		}
		return templateSource;
	}	
}
