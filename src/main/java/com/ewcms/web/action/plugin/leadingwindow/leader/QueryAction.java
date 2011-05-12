/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.plugin.leadingwindow.leader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.comm.jpa.query.EntityPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.comm.jpa.query.QueryFactory;
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.web.action.QueryBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("plugin.leadingwindow.leader.query")
public class QueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = 1152953035942478786L;
	
	@Autowired
    private QueryFactory queryFactory;
	
	private Integer channelId;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected PageQueryable constructNewQuery(Order order) {
		EntityPageQueryable query = queryFactory.createEntityPageQuery(Leader.class);
        //query.in("id", getNewIdAll(Integer.class));
        query.orderAsc("sort");
        query.eq("channelId", getChannelId());
        return query;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected PageQueryable constructQuery(Order order) {
		EntityPageQueryable query = queryFactory.createEntityPageQuery(Leader.class);
		
		query.eq("channelId", getChannelId());
        Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
        if (isNotEmpty(id)) query.eq("id", id);
        
        String name = getParameterValue(String.class, "name", "");
        if (isNotEmpty(name)) query.likeAnywhere("name", name);
        
        query.orderAsc("sort");
        
        simpleEntityOrder(query, order);
        return query;
	}

}
