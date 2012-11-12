/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.visit.model.VisitItem;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class VisitItemDAO extends JpaDAO<Long, VisitItem> {
	public VisitItem findVisitItemByVisitItemPK(String uniqueId, Integer siteId, Integer channelId, Long articleId, String url){
		String hql = "From VisitItem Where uniqueId=:uniqueId And siteId=:siteId And channelId=:channelId And articleId=:articleId And url=:url Order by visitDate, visitTime";
		TypedQuery<VisitItem> query = this.getEntityManager().createQuery(hql, VisitItem.class);
		query.setParameter("uniqueId", uniqueId);
		query.setParameter("siteId", siteId);
		query.setParameter("channelId", channelId);
		query.setParameter("articleId", articleId);
		query.setParameter("url", url);
		
		List<VisitItem> list = query.getResultList();
		if (list == null || list.isEmpty()) return null;
		return list.get(0);
	}
}
