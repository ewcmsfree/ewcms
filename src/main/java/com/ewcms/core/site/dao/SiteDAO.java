/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.core.site.model.Site;

/**
 * @author 周冬初
 * @author wuzhijun
 */
@Repository
public class SiteDAO extends JpaDAO<Integer, Site> {
	/**
	 * 获取机构集的站点集
	 * 
	 */
	public List<Site> getSiteListByOrgans(final Integer[] organs,final Boolean publicenable){	
		String hql;
		if (organs == null || organs.length == 0) {
			hql = "From Site o Where o.publicenable=:publicenable Order By o.id";

			TypedQuery<Site> query = this.getEntityManager().createQuery(hql, Site.class);
			query.setParameter("publicenable", publicenable);

			return query.getResultList();
		}

		String sqlStr = "?";
		for (int i = 1; i < organs.length; i++) {
			sqlStr += ",?";
		}
		hql = "From Site o Where o.publicenable=:publicenable and o.organ.id in(" + sqlStr + ") Order By o.id";

		TypedQuery<Site> query = this.getEntityManager().createQuery(hql, Site.class);
		query.setParameter("publicenable", publicenable);

		for (int i = 0; i < organs.length; i++) {
			query.setParameter(2 + i, organs[i]);
		}

		return query.getResultList();		
	}
	
	/**
	 * 获取机构的子站点集
	 * 
	 */	
	public List<Site> getSiteChildren(final Integer parentId,final Integer organId){
		String hql;
		if (parentId == null) {
			if (organId == null) {
				hql = "From Site o Where o.parent.id is null Order By o.id";

				TypedQuery<Site> query = this.getEntityManager().createQuery(hql, Site.class);

				return query.getResultList();
			} else {
				hql = "From Site o Where o.parent.id is null and o.organ.id=:organId Order By o.id";

				TypedQuery<Site> query = this.getEntityManager().createQuery(hql, Site.class);
				query.setParameter("organId", organId);

				return query.getResultList();
			}
		} else {
			if (organId == null) {
				hql = "From Site o Where o.parent.id=:parentId Order By o.id";

				TypedQuery<Site> query = this.getEntityManager().createQuery(hql, Site.class);
				query.setParameter("parentId", parentId);

				return query.getResultList();
			} else {
				hql = "From Site o Where o.parent.id=:parentId and o.organ.id=:organId Order By o.id";

				TypedQuery<Site> query = this.getEntityManager().createQuery(hql, Site.class);
				query.setParameter("parentId", parentId);
				query.setParameter("organId", organId);

				return query.getResultList();
			}
		}
	}

	public void updSiteParent(final Integer organId,final Integer parentId,final Integer newParentId){
		String hql = "update Site o set o.parent.id=:newParentId Where o.parent.id=:parentId and o.organ.id !=:organId";

		Query query = this.getEntityManager().createQuery(hql);

		query.setParameter("newParentId", newParentId);
		query.setParameter("parentId", parentId);
		query.setParameter("organId", organId);

		query.executeUpdate();	
	}	
}
