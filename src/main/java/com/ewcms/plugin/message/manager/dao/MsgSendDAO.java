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
import com.ewcms.plugin.message.model.MsgSend;
import com.ewcms.plugin.message.model.MsgSend.Type;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class MsgSendDAO extends JpaDAO<Long, MsgSend> {
	
	public List<MsgSend> findMsgSendByUserName(final String userName){
		String hql = "Select s From MsgSend As s Left Join s.msgReceiveUsers As u Where u.userName=:userName";
		
		TypedQuery<MsgSend> query = this.getEntityManager().createQuery(hql, MsgSend.class);
		query.setParameter("userName", userName);
		
		return query.getResultList();
	}
	
	public MsgSend findMsgSendByUserNameAndId(final String userName, final Long msgSendId){
		String hql = "From MsgSend As s Where s.userName=:userName And s.id=:msgSendId";
		
		TypedQuery<MsgSend> query = this.getEntityManager().createQuery(hql, MsgSend.class);
		query.setParameter("userName", userName);
		query.setParameter("msgSendId", msgSendId);
		
		MsgSend msgSend = null;
		try{
			msgSend = (MsgSend) query.getSingleResult();
		}catch(NoResultException e){
		}
		
		return msgSend;
	}
	
	public List<MsgSend> findMsgSendByType(final Type type, final Integer row){
		String hql = "From MsgSend As s Where s.type=:type Order By s.sendTime Desc";
		
		TypedQuery<MsgSend> query = this.getEntityManager().createQuery(hql, MsgSend.class);
		query.setParameter("type", type);
		query.setMaxResults(row);
		
		return query.getResultList();
	}
	
	public Boolean findUserHaveSubscribedByUserName(final Long msgSendId, final String userName){
		String hql = "Select s From MsgSend As s Left Join s.msgReceiveUsers As u Where s.id=:msgSendId And u.userName=:userName And s.type=:type";
		
		TypedQuery<MsgSend> query = this.getEntityManager().createQuery(hql, MsgSend.class);
		query.setParameter("msgSendId", msgSendId);
		query.setParameter("userName", userName);
		query.setParameter("type", Type.SUBSCRIPTION);
		
		List<MsgSend> list = query.getResultList();
		return list.isEmpty()? false : true;
	}
}
