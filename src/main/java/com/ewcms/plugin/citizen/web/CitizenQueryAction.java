/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.citizen.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.citizen.model.Citizen;
import com.ewcms.web.QueryBaseAction;

/**
 * @author wuzhijun
 */
public class CitizenQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 8312621630076161609L;

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,
			String cacheKey, int rows, int page, Order order) {
		EntityQueryable query = queryFactory.createEntityQuery(Citizen.class).setPage(page).setRow(rows).orderDesc("id");
    	
		Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
    	if (isNotNull(id)) query.eq("id", id);
    	
        String name = getParameterValue(String.class, "name", "");
        if (isStringNotEmpty(name)) query.likeAnywhere("name", name);
        
    	entityOrder(query, order);
    	return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory,
			int rows, int page, String[] selections, Order order) {
    	EntityQueryable query =  queryFactory.createEntityQuery(Citizen.class).setPage(page).setRow(rows).orderAsc("id");
        
        query.in("id", getIds(Integer.class));
        
        return query.queryResult();    
	}

}
