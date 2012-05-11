/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.manager.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.crawler.model.Storage;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author wuzhijun
 *
 */
public class StorageQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = -6105582233144966796L;
	
	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
    	EntityQueryable query = queryFactory.createEntityQuery(Storage.class).setPage(page).setRow(rows).orderAsc("id");
    	
    	Long id = getParameterValue(Long.class,"id", "查询编号错误，应该是整型");
    	if (isNotNull(id)) query.eq("id", id);
    	        
    	String name = getParameterValue(String.class, "title", "");
    	if (isStringNotEmpty(name)) query.likeAnywhere("title", name);
    	
    	String gatherName = getParameterValue(String.class, "gatherName", "");
    	if (isStringNotEmpty(gatherName)) query.likeAnywhere("gatherName", gatherName);
    	
    	entityOrder(query, order);
    	return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
    	EntityQueryable query = queryFactory.createEntityQuery(Storage.class).setPage(page).setRow(rows).orderAsc("id");
    	
    	Long id = getParameterValue(Long.class,"id", "查询编号错误，应该是整型");
    	if (isNotNull(id)) query.eq("id", id);
    	        
    	String name = getParameterValue(String.class, "title", "");
    	if (isStringNotEmpty(name)) query.likeAnywhere("title", name);
    	
    	String gatherName = getParameterValue(String.class, "gatherName", "");
    	if (isStringNotEmpty(gatherName)) query.likeAnywhere("gatherName", gatherName);
    	
    	entityOrder(query, order);
		
		return query.setRow(rows).setPage(page).queryResult();
	}
}
