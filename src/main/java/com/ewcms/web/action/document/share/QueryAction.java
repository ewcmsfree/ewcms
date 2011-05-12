/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.document.share;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.ewcms.comm.jpa.query.EntityPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.comm.jpa.query.QueryFactory;
import com.ewcms.core.document.model.ShareArticle;
import com.ewcms.web.action.QueryBaseAction;

/**
 * 
 * @author 周冬初
 */
@Controller("share.query")
public class QueryAction extends QueryBaseAction {
    @Autowired
    private QueryFactory queryFactory;


	@Override
	protected PageQueryable constructNewQuery(Order order) {
		return null;
	}


	@Override
	protected PageQueryable constructQuery(Order order) {
        EntityPageQueryable<ShareArticle, ShareArticle> query = queryFactory.createEntityPageQuery(ShareArticle.class);

        query.eq("siteId", getCurrentSite().getId());
        String refed = getParameterValue(String.class, "refed", "");
        if(refed==null || refed.length()==0)
        query.eq("refed", false);
        Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
        if (isNotEmpty(id)) query.eq("id", id);
        
        String name = getParameterValue(String.class, "name", "");
        if (isNotEmpty(name)) query.likeAnywhere("channelName", name);
        
        String title = getParameterValue(String.class, "title", "");
        if (isNotEmpty(title)) query.likeAnywhere("articleTitle", title);
        simpleEntityOrder(query, order);
        return query;
	}
}
