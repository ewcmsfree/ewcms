/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.core.site.web;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author 周冬初
 */
@Controller("template.query")
public class TemplateQueryAction extends QueryBaseAction {

    @Override
    protected Resultable queryResult(
            com.ewcms.common.query.jpa.QueryFactory queryFactory,
            String cacheKey, int rows, int page, Order order) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Resultable querySelectionsResult(
            com.ewcms.common.query.jpa.QueryFactory queryFactory, int rows,
            int page, String[] selections, Order order) {
        // TODO Auto-generated method stub
        return null;
    }
//    @Autowired
//    private QueryFactory queryFactory;
//
//	@Override
//	protected PageQueryable constructNewQuery(Order order) {
//		EntityPageQueryable<Template, Template> query = queryFactory.createEntityPageQuery(Template.class);
//        query.in("id", getIds(Integer.class));
//        query.orderDesc("id");
//        return query;
//	}
//
//	@Override
//	protected PageQueryable constructQuery(Order order) {
//		EntityPageQueryable<Template, Template> query = queryFactory.createEntityPageQuery(Template.class);
//
//        Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
//        if (isNotEmpty(id)) query.eq("id", id);
//        
//        String name = getParameterValue(String.class, "name", "");
//        if (isNotEmpty(name)) query.likeAnywhere("name", name);
//       
//        Integer channelId = getParameterValue(Integer.class,"channelId", "");
//        query.eq("channelId", channelId);        
//        simpleEntityOrder(query, order);
//        return query;
//	}
}
