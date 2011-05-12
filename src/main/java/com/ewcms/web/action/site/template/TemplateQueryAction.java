/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.site.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.ewcms.comm.jpa.query.EntityPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.comm.jpa.query.QueryFactory;
import com.ewcms.core.site.model.Template;
import com.ewcms.web.action.QueryBaseAction;

/**
 * 
 * @author 周冬初
 */
@Controller("template.query")
public class TemplateQueryAction extends QueryBaseAction {
    @Autowired
    private QueryFactory queryFactory;

	@Override
	protected PageQueryable constructNewQuery(Order order) {
		EntityPageQueryable<Template, Template> query = queryFactory.createEntityPageQuery(Template.class);
        query.in("id", getNewIdAll(Integer.class));
        query.orderDesc("id");
        return query;
	}

	@Override
	protected PageQueryable constructQuery(Order order) {
		EntityPageQueryable<Template, Template> query = queryFactory.createEntityPageQuery(Template.class);

        Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
        if (isNotEmpty(id)) query.eq("id", id);
        
        String name = getParameterValue(String.class, "name", "");
        if (isNotEmpty(name)) query.likeAnywhere("name", name);
       
        Integer channelId = getParameterValue(Integer.class,"channelId", "");
        query.eq("channelId", channelId);        
        simpleEntityOrder(query, order);
        return query;
	}
}
