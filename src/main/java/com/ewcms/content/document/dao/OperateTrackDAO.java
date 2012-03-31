/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.document.model.OperateTrack;

@Repository
public class OperateTrackDAO extends JpaDAO<Long, OperateTrack> {
	
	public List<OperateTrack> findOperateTrackByArticleMainId(final Long articleMainId){
		String hql = "From OperateTrack AS t Where t.articleMainId=:articleMainId Order By t.id Desc";

		TypedQuery<OperateTrack> query = this.getEntityManager().createQuery(hql, OperateTrack.class);
		query.setParameter("articleMainId", articleMainId);

		return query.getResultList();
	}
	
    public void delOperateTrackByArticleMainId(final Long articleMainId){
    	String hql = "Delete OperateTrack As t Where t.articleMainId=:articleMainId";

    	Query query = this.getEntityManager().createQuery(hql);
    	query.setParameter("articleMainId", articleMainId);
    	query.executeUpdate();
    }
}
