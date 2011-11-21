/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.channel.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.scheduling.generate.job.channel.model.EwcmsJobChannel;

/**
 * 频道定时任务DAO
 * 
 * @author 吴智俊
 */
@Repository
public class EwcmsJobChannelDAO extends JpaDAO<Integer, EwcmsJobChannel> {
	@SuppressWarnings("unchecked")
	public EwcmsJobChannel findJobChannelByChannelId(Integer channelId) {
		String hql = "Select o From EwcmsJobChannel o Inner Join o.channel c Where c.id=?";
		List<EwcmsJobChannel> list = this.getJpaTemplate().find(hql, channelId);
		if (list.isEmpty())
			return null;
		return list.get(0);
	}
}
