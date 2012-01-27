/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.manager.dao;

import java.util.ArrayList;
import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	public List<MsgReceive> findMsgReceiveByUserName(String userName){
		String hql = "From MsgReceive As r Where r.userName=?";
    	List<MsgReceive> list = this.getJpaTemplate().find(hql, userName);
    	if (list.isEmpty()) return new ArrayList<MsgReceive>();
    	return list;
	}

	@SuppressWarnings("unchecked")
	public MsgReceive findMsgReceiveByUserNameAndId(String userName, Long msgReceiveId){
		String hql = "From MsgReceive As r Where r.userName=? And r.id=?";
    	List<MsgReceive> list = this.getJpaTemplate().find(hql, userName, msgReceiveId);
    	if (list.isEmpty()) return new MsgReceive();
    	return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<MsgReceive> findMsgReceiveByUserNameAndUnRead(String userName){
		String hql = "From MsgReceive As r Where r.userName=? And r.read=false Order By r.id Desc";
		List<MsgReceive> list = this.getJpaTemplate().find(hql, userName);
    	if (list.isEmpty()) return new ArrayList<MsgReceive>();
    	return list;
	}
}
