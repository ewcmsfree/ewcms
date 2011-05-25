/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.scheduling.model.AlqcJobClass;
import com.ewcms.web.QueryBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("scheduling.jobclass.query")
public class JobClassQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = -8882837349113907705L;
	
    @Override
    protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
    	EntityQueryable query =  queryFactory.createEntityQuery(AlqcJobClass.class).setPage(page).setRow(rows);
		
        Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
        if (isNotNull(id)) query.eq("id", id);
        
        String name = getParameterValue(String.class, "className", "");
        if (isStringNotEmpty(name)) query.likeAnywhere("className", name);
        
        entityOrder(query, order);
        return query.queryCacheResult(cacheKey);
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
    	EntityQueryable query =  queryFactory.createEntityQuery(AlqcJobClass.class).setPage(page).setRow(rows);
        
        List<Integer> ids = getIds(Integer.class);
        query.in("id", ids);
        
        return query.queryResult();    
     }
}
