/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.message.model.MsgSend;
import com.ewcms.content.message.model.MsgType;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class MsgSendDAO extends JpaDAO<Long, MsgSend> {
	
	@SuppressWarnings("unchecked")
	public List<MsgSend> findMsgSendByUserName(String userName){
		String hql = "From MsgSend As s Left Join s.msgReceiveUsers As u Where u.userName=?";
    	List<MsgSend> list = this.getJpaTemplate().find(hql, userName);
    	if (list.isEmpty()) return new ArrayList<MsgSend>();
    	return list;
	}
	
	@SuppressWarnings("unchecked")
	public MsgSend findMsgSendByUserNameAndId(String userName, Long msgSendId){
		String hql = "From MsgSend As s Left Join s.msgReceiveUsers Where u.userName=? And s.id=?";
    	List<MsgSend> list = this.getJpaTemplate().find(hql, userName, msgSendId);
    	if (list.isEmpty()) return new MsgSend();
    	return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<MsgSend> findMsgSendByType(MsgType msgType){
		String hql = "From MsgSend As s Where s.type=? Order By s.sendTime Desc";
		List<MsgSend> list = this.getJpaTemplate().find(hql, msgType);
    	if (list.isEmpty()) return new ArrayList<MsgSend>();
    	return list;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean findUserHaveSubscribedByUserName(String userName){
		String hql = "From MsgSend As s Left Join s.msgReceiveUsers As u Where u.userName=?";
		List<MsgSend> list = this.getJpaTemplate().find(hql, userName);
    	if (list.isEmpty()) return false;
    	return true;
	}
}
