/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.plugin.leadingwindow.leaderchannel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.comm.jpa.query.HqlPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.comm.jpa.query.QueryFactory;
import com.ewcms.plugin.leadingwindow.model.LeaderChannel;
import com.ewcms.web.action.QueryBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("plugin.leadingwindow.leaderchannel.query")
public class QueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = 1152953035942478786L;
	
	@Autowired
    private QueryFactory queryFactory;
	
	private Integer leaderId;
	
	private Integer channelId;
	
	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected PageQueryable constructNewQuery(Order order) {
		String hql = "Select a From Leader AS o LEFT JOIN o.leaderChannels AS a Where o.id=:leaderId And o.channelId=:channelId And a.channelId=:channelId Order By a.sort,a.id";
		String countHql = "Select Count(a.id) From Leader AS o LEFT JOIN o.leaderChannels AS a Where o.id=:leaderId And o.channelId=:channelId And a.channelId=:channelId";
		
		HqlPageQueryable<LeaderChannel> query = queryFactory.createHqlPageQuery(hql, countHql);
//		query.setParameter("id", getNewIdAll(Integer.class));
		query.setParameter("leaderId", getLeaderId());
		query.setParameter("channelId", getChannelId());
		
		return query;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected PageQueryable constructQuery(Order order) {
		String hql = "Select a From Leader AS o LEFT JOIN o.leaderChannels AS a Where o.id=:leaderId And o.channelId=:channelId And a.channelId=:channelId ";
		String countHql = "Select Count(a.id) From Leader AS o LEFT JOIN o.leaderChannels AS a Where o.id=:leaderId And o.channelId=:channelId And a.channelId=:channelId ";
		
		Integer id = getParameterValue(Integer.class, "id", "查询编号错误，应该是整型");
		if (isNotEmpty(id)){
			hql += " And a.id=:id ";
			countHql += " And a.id=:id";
		}
		String title = getParameterValue(String.class, "title", "");
		if (isNotEmpty(title)){
			hql += " And a.name Like :title";
			countHql += " And a.name Like :title";
		}
		hql += " Order By a.sort,a.id";
		
		HqlPageQueryable<LeaderChannel> query = queryFactory.createHqlPageQuery(hql, countHql);
		if (isNotEmpty(id)){
			query.setParameter("id", id);
		}
		if (isNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		query.setParameter("leaderId", getLeaderId());
		query.setParameter("channelId", getChannelId());
		
		return query;
	}

}
