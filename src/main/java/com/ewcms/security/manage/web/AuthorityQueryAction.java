/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web;

import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.security.manage.model.Authority;
import com.ewcms.web.QueryBaseAction;

@Controller
public class AuthorityQueryAction extends QueryBaseAction{

    @Override
    protected Resultable queryResult(QueryFactory queryFactory,String cacheKey, int rows,int page, Order order) {
        EntityQueryable query = 
            queryFactory.createEntityQuery(Authority.class)
            .setPage(page)
            .setRow(rows);
        
        String name =  getParameterValue(String.class,"name");
        if(isStringNotEmpty(name)) query.likeAnywhere("name", name);
        entityOrder(query, order);
        
        return query.queryCacheResult(cacheKey);
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory,int rows, int page, String[] selections, Order order) {
        return null;
    }
}
