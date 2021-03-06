/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.util.List;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.particular.model.IndustryCode;
import com.ewcms.web.QueryBaseAction;

public class IndustryCodeQueryAction extends QueryBaseAction{

	private static final long serialVersionUID = -4149344019910643538L;

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,
			String cacheKey, int rows, int page, Order order) {
    	EntityQueryable query = queryFactory.createEntityQuery(IndustryCode.class).setPage(page).setRow(rows).orderDesc("id");
    	
    	String code = getParameterValue(String.class,"code", "");
    	if (isNotNull(code)) query.eq("code", code);
    	        
    	String name = getParameterValue(String.class, "name", "");
    	if (isStringNotEmpty(name)) query.likeAnywhere("name", name);
    	        
    	entityOrder(query, order);
    	return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
    	EntityQueryable query = queryFactory.createEntityQuery(IndustryCode.class).setPage(page).setRow(rows).orderDesc("id");
    	
        List<Long> ids = getIds(Long.class);
        query.in("id", ids);
        
        return query.queryResult();    
	}
}
