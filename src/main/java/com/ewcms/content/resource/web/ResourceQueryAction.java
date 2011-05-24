/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.content.resource.web;

import com.ewcms.common.jpa.query.EntityPageQueryable;
import com.ewcms.common.jpa.query.PageQueryable;
import com.ewcms.common.jpa.query.QueryFactory;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.ResourceType;
import com.ewcms.util.EwcmsContextUtil;
import com.ewcms.web.action.QueryBaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import static com.ewcms.util.EmptyUtil.isStringNotEmpty;

/**
 *
 * @author wangwei
 */
public abstract class ResourceQueryAction extends QueryBaseAction {

    private String name;
    private String title;
    private String description;
    @Autowired
    private QueryFactory queryFactory;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected PageQueryable constructQuery(Order order) {
        EntityPageQueryable query = queryFactory.createEntityPageQuery(Resource.class);
        
        query.eq("siteId", getSiteId());
        if (isStringNotEmpty(name)) {
            query.likeAnywhere("name", name);
        }
        if (isStringNotEmpty(title)) {
            query.likeAnywhere("title", title);
        }
        if (isStringNotEmpty(description)) {
            query.likeAnywhere("description", description);
        }
        query.eq("type", resourceType());
        query.eq("deleteFlag", false);

        query.orderDesc("uploadTime");

        return query;
    }

    protected abstract ResourceType resourceType();

    private Integer getSiteId() {
        return EwcmsContextUtil.getCurrentSite().getId();
    }

    @Override
    protected PageQueryable constructNewQuery(Order order) {
        //没有新增数据查询
        return null;
    }
}
