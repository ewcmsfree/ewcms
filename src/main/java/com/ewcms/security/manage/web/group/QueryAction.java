/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web.group;

import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.util.Arrays;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.security.manage.model.Group;
import com.ewcms.web.QueryBaseAction;

/**
 * 用户组列表查询Action
 * 
 * @author wangwei
 */
@Controller("security.group.query.action")
public class QueryAction extends QueryBaseAction{

	private static final long serialVersionUID = -3228277322299014851L;

	@Override
    protected Resultable queryResult(QueryFactory queryFactory,
            String cacheKey, int rows, int page, Order order) {
        
        EntityQueryable query = 
            queryFactory.createEntityQuery(Group.class)
            .setPage(page)
            .setRow(rows);
        
        String name =  getParameterValue(String.class,"name");
        if(isStringNotEmpty(name)) query.likeAnywhere("name", name);
        String remark = getParameterValue(String.class,"remark");
        if(isStringNotEmpty(remark)) query.likeAnywhere("remark", remark);
        entityOrder(query, order);
        
        return query.queryCacheResult(cacheKey);
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory,
            int rows, int page, String[] selections, Order order) {
        
        EntityQueryable query = 
            queryFactory.createEntityQuery(Group.class)
            .setPage(page)
            .setRow(rows);
        
        query.in("name", Arrays.asList(selections));
        
        return query.queryResult();
    }
}
