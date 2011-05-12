/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.document.citizen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.comm.jpa.query.EntityPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.comm.jpa.query.QueryFactory;
import com.ewcms.core.document.model.Citizen;
import com.ewcms.web.action.QueryBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("document.citizen.query")
public class QueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = 8337990997120729624L;

	@Autowired
    private QueryFactory queryFactory;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructNewQuery(Order order) {
		EntityPageQueryable query = queryFactory.createEntityPageQuery(Citizen.class);
        //query.in("id", getNewIdAll(Integer.class));
        query.orderDesc("id");
        return query;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructQuery(Order order) {
		EntityPageQueryable query = queryFactory.createEntityPageQuery(Citizen.class);
		
        Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
        if (isNotEmpty(id)) query.eq("id", id);
        
        String name = getParameterValue(String.class, "name", "");
        if (isNotEmpty(name)) query.likeAnywhere("name", name);
        
        simpleEntityOrder(query, order);
        return query;
	}

}
