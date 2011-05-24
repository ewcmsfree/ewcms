/**
 * 
 */
package com.ewcms.core.site.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.jpa.query.EntityPageQueryable;
import com.ewcms.common.jpa.query.PageQueryable;
import com.ewcms.common.jpa.query.QueryFactory;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.web.action.QueryBaseAction;

/**
 * @author 周冬初
 *
 */
public class SourceQueryAction extends QueryBaseAction {
    @Autowired
    private QueryFactory queryFactory;

	@Override
	protected PageQueryable constructNewQuery(Order order) {
		EntityPageQueryable<TemplateSource, TemplateSource> query = queryFactory.createEntityPageQuery(TemplateSource.class);
        query.in("id", getNewIdAll(Integer.class));
        query.orderDesc("id");
        return query;
	}

	@Override
	protected PageQueryable constructQuery(Order order) {
		EntityPageQueryable<TemplateSource, TemplateSource> query = queryFactory.createEntityPageQuery(TemplateSource.class);

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
