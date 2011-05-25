/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.content.document.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.document.model.ShareArticle;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author 周冬初
 */
@Controller("share.query")
public class ShareQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = -8991147204596789617L;

	@Override
    protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
    	EntityQueryable query = queryFactory.createEntityQuery(ShareArticle.class).setPage(page).setRow(rows);
    	
    	query.eq("siteId", getCurrentSite().getId());
    	String refed = getParameterValue(String.class, "refed", "");
    	if(refed==null || refed.length()==0)
    	query.eq("refed", false);
    	Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
    	if (isNotNull(id)) query.eq("id", id);
    	        
    	String name = getParameterValue(String.class, "name", "");
    	if (isStringNotEmpty(name)) query.likeAnywhere("channelName", name);
    	        
    	String title = getParameterValue(String.class, "title", "");
    	if (isStringNotEmpty(title)) query.likeAnywhere("articleTitle", title);
    	entityOrder(query, order);
    	return query.queryCacheResult(cacheKey);
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
        return null;
    }
}
