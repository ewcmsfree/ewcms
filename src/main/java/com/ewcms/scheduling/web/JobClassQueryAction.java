/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.common.jpa.query.EntityPageQueryable;
import com.ewcms.common.jpa.query.PageQueryable;
import com.ewcms.common.jpa.query.QueryFactory;
import com.ewcms.scheduling.model.AlqcJobClass;
import com.ewcms.web.action.QueryBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("scheduling.jobclass.query")
public class JobClassQueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = -8882837349113907705L;
	
	@Autowired
    private QueryFactory queryFactory;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructNewQuery(Order order) {
		EntityPageQueryable query = queryFactory.createEntityPageQuery(AlqcJobClass.class);
        //query.in("id", getNewIdAll(Integer.class));
        query.orderDesc("id");
        return query;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructQuery(Order order) {
		EntityPageQueryable query = queryFactory.createEntityPageQuery(AlqcJobClass.class);
		
        Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
        if (isNotEmpty(id)) query.eq("id", id);
        
        String name = getParameterValue(String.class, "className", "");
        if (isNotEmpty(name)) query.likeAnywhere("className", name);
        
        simpleEntityOrder(query, order);
        return query;
	}

}
