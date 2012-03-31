/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.manager.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.message.model.MsgReceive;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class MsgReceiveDAO extends JpaDAO<Long, MsgReceive> {
	
	public List<MsgReceive> findMsgReceiveByUserName(final String userName){
		String hql = "From MsgReceive As r Where r.userName=:userName";
		
		TypedQuery<MsgReceive> query = this.getEntityManager().createQuery(hql, MsgReceive.class);
		query.setParameter("userName", userName);
		
		return query.getResultList();
	}

	public MsgReceive findMsgReceiveByUserNameAndId(final String userName, final Long msgReceiveId){
		String hql = "From MsgReceive As r Where r.userName=:userName And r.id=:msgReceiveId";
		
		TypedQuery<MsgReceive> query = this.getEntityManager().createQuery(hql, MsgReceive.class);
		query.setParameter("userName", userName);
		query.setParameter("msgReceiveId", msgReceiveId);
		
		MsgReceive msgReceive = null;
		try{
			msgReceive = (MsgReceive) query.getSingleResult();
		}catch(NoResultException e){
		}
		return msgReceive;
	}

	public List<MsgReceive> findMsgReceiveByUserNameAndUnRead(final String userName){
		String hql = "From MsgReceive As r Where r.userName=:userName And r.read=false Order By r.id Desc";
		
		TypedQuery<MsgReceive> query = this.getEntityManager().createQuery(hql, MsgReceive.class);
		query.setParameter("userName", userName);
		
		return query.getResultList();
	}
	
	public Long findUnReadMessageCountByUserName(final String userName){
		String hql = "Select Count(r.id) From MsgReceive As r Where r.userName=:userName And r.read=false";
		
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("userName", userName);
		
		return query.getSingleResult();
	}
}
