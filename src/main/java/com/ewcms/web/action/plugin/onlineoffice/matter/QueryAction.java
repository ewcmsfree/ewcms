/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.plugin.onlineoffice.matter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.comm.jpa.query.EntityPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.comm.jpa.query.QueryFactory;
import com.ewcms.plugin.onlineoffice.model.Matter;
import com.ewcms.web.action.QueryBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("plugin.onlineoffice.matter.query")
public class QueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = 1152953035942478786L;
	
	@Autowired
    private QueryFactory queryFactory;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructNewQuery(Order order) {
		EntityPageQueryable query = queryFactory.createEntityPageQuery(Matter.class);
        //query.in("id", getNewIdAll(Integer.class));
        query.orderAsc("sort");
        query.orderDesc("id");
        return query;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructQuery(Order order) {
		EntityPageQueryable query = queryFactory.createEntityPageQuery(Matter.class);
		
        Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
        if (isNotEmpty(id)) query.eq("id", id);
        
        String name = getParameterValue(String.class, "name", "");
        if (isNotEmpty(name)) query.likeAnywhere("name", name);
        
        query.orderAsc("sort");
        
        simpleEntityOrder(query, order);
        return query;
	}

}
