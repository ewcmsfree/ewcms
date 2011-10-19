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

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.document.model.OperateTrack;

@Repository
public class OperateTrackDAO extends JpaDAO<Long, OperateTrack> {
	
	@SuppressWarnings("unchecked")
	public List<OperateTrack> findOperateTrackByArticleMainId(Long articleMainId){
    	String hql = "From OperateTrack AS t Where t.articleMainId=? Order By t.id Desc";
    	List<OperateTrack> list = this.getJpaTemplate().find(hql, articleMainId);
    	if (list.isEmpty()) return new ArrayList<OperateTrack>();
    	return list;
	}
	
    public void delOperateTrackByArticleMainId(final Long articleMainId){
    	this.getJpaTemplate().execute(new JpaCallback<Object>(){
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String hql = "Delete OperateTrack As t Where t.articleMainId=?";
				em.createQuery(hql).setParameter(1, articleMainId).executeUpdate();
				return null;
			}
    	});
    }
}
