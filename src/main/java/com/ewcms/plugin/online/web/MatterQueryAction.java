/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online.web;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.online.model.Matter;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.common.lang.EmptyUtil;
/**
 *
 * @author 吴智俊
 */
public class MatterQueryAction extends QueryBaseAction {
	private static final long serialVersionUID = 1152953035942478786L;

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,
			String cacheKey, int rows, int page, Order order) {
		EntityQueryable query = queryFactory.createEntityQuery(Matter.class).setPage(page).setRow(rows).orderDesc("id");
		Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
        if (EmptyUtil.isNotNull(id)) query.eq("id", id);
        
        String name = getParameterValue(String.class, "name", "");
        if (EmptyUtil.isNotNull(name)) query.likeAnywhere("name", name);
        
        query.orderAsc("sort");
        return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory,
			int rows, int page, String[] selections, Order order) {
		EntityQueryable query = queryFactory.createEntityQuery(Matter.class).setPage(page).setRow(rows).orderDesc("id");
        query.in("id", getIds(Integer.class));
        query.orderAsc("sort");
        query.orderDesc("id");
        return query.queryResult();
	}

}
