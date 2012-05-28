/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.interaction.dao;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.interaction.model.Interaction;
import com.ewcms.plugin.interaction.model.InteractionRatio;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author wangwei
 */
@Repository
public class InteractionDAO extends JpaDAO<Integer, Interaction> {

	private final static Integer BACK_STATE = 1;
	private final static Integer INIT_STATE = 0;

	public void interactionBackRatio(Integer id) {
		int all = getBackInteractionCount(null);
		int ratio = -1;
		if (all != 0) {
			int back = getBackInteractionCount(id);
			ratio = (back * 100) / all;
		}

		all = getNoBackInteractionCount(null);
		int noRatio = -1;
		if (all != 0) {
			int noBack = getNoBackInteractionCount(id);
			noRatio = (noBack * 100) / all;
		}

		InteractionRatio interactionRatio = getEntityManager().find(
				InteractionRatio.class, id);
		if (interactionRatio != null) {
			interactionRatio.setNoRatio(noRatio);
			interactionRatio.setRatio(ratio);
		} else {
			interactionRatio = new InteractionRatio();
			interactionRatio.setId(id);
			interactionRatio.setNoRatio(noRatio);
			interactionRatio.setRatio(ratio);
		}
		getEntityManager().persist(interactionRatio);
	}

	private Integer getBackInteractionCount(final Integer id) {
		if (id == null) {
			String hql = "Select count(o.id) From Interaction o Where o.state =:state";
			TypedQuery<Number> query = this.getEntityManager().createQuery(hql,
					Number.class);
			query.setParameter("state", BACK_STATE);
			return query.getSingleResult().intValue();
		} else {
			String hql = "Select count(o.id) From Interaction o Where o.state =:state and o.organId=:organId";
			TypedQuery<Number> query = this.getEntityManager().createQuery(hql,
					Number.class);
			query.setParameter("state", BACK_STATE);
			query.setParameter("organId", id);
			return query.getSingleResult().intValue();
		}
	}

	private Integer getNoBackInteractionCount(final Integer id) {
		if (id == null) {
			String hql = "Select count(o.id) From Interaction o Where o.state =:state";
			TypedQuery<Number> query = this.getEntityManager().createQuery(hql,
					Number.class);
			query.setParameter("state", INIT_STATE);
			return query.getSingleResult().intValue();
		} else {
			String hql = "Select count(o.id) From Interaction o Where o.state =:state and o.organId=:organId";
			TypedQuery<Number> query = this.getEntityManager().createQuery(hql,
					Number.class);
			query.setParameter("state", INIT_STATE);
			query.setParameter("organId", id);
			return query.getSingleResult().intValue();
		}
	}
}
