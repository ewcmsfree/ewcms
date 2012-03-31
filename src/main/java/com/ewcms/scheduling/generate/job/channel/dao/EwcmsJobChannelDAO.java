/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.channel.dao;


import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.scheduling.generate.job.channel.model.EwcmsJobChannel;

/**
 * 频道定时任务DAO
 * 
 * @author 吴智俊
 */
@Repository
public class EwcmsJobChannelDAO extends JpaDAO<Long, EwcmsJobChannel> {
	
	public EwcmsJobChannel findJobChannelByChannelId(final Integer channelId) {
		String hql = "Select o From EwcmsJobChannel o Inner Join o.channel c Where c.id=:channelId";
		
		TypedQuery<EwcmsJobChannel> query = this.getEntityManager().createQuery(hql, EwcmsJobChannel.class);
    	query.setParameter("channelId", channelId);

    	EwcmsJobChannel ewcmsJobChannel = null;
    	try{
    		ewcmsJobChannel = (EwcmsJobChannel) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	return ewcmsJobChannel;
	}
}
