/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.manager.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;
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
	
	@SuppressWarnings("unchecked")
	public List<MsgSend> findMsgSendByUserName(String userName){
		String hql = "Select s From MsgSend As s Left Join s.msgReceiveUsers As u Where u.userName=?";
    	List<MsgSend> list = this.getJpaTemplate().find(hql, userName);
    	if (list.isEmpty()) return new ArrayList<MsgSend>();
    	return list;
	}
	
	@SuppressWarnings("unchecked")
	public MsgSend findMsgSendByUserNameAndId(String userName, Long msgSendId){
		String hql = "From MsgSend As s Where s.userName=? And s.id=?";
    	List<MsgSend> list = this.getJpaTemplate().find(hql, userName, msgSendId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<MsgSend> findMsgSendByType(final Type type, final Integer row){
		Object result = this.getJpaTemplate().execute(new JpaCallback<Object>(){
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String hql = "From MsgSend As s Where s.type=? Order By s.sendTime Desc";
				return em.createQuery(hql).setParameter(1, type).setMaxResults(row).getResultList();
			}
    	});
    	return (List<MsgSend>)result;	
	}
	
	@SuppressWarnings("unchecked")
	public Boolean findUserHaveSubscribedByUserName(Long msgSendId, String userName){
		String hql = "Select s From MsgSend As s Left Join s.msgReceiveUsers As u Where s.id=? And u.userName=? And s.type=?";
		List<MsgSend> list = this.getJpaTemplate().find(hql, msgSendId, userName, Type.SUBSCRIPTION);
    	if (list.isEmpty()) return false;
    	return true;
	}
}
