/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.content.resource.web;

import com.ewcms.common.query.Resultable;
import com.ewcms.content.resource.model.ResourceType;
import com.ewcms.web.QueryBaseAction;

/**
 *
 * @author wangwei
 */
public abstract class ResourceQueryAction extends QueryBaseAction {

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
    
//    private String name;
//    private String title;
//    private String description;
//    @Autowired
//    private QueryFactory queryFactory;
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    @Override
//    protected PageQueryable constructQuery(Order order) {
//        EntityPageQueryable query = queryFactory.createEntityPageQuery(Resource.class);
//        
//        query.eq("siteId", getSiteId());
//        if (isStringNotEmpty(name)) {
//            query.likeAnywhere("name", name);
//        }
//        if (isStringNotEmpty(title)) {
//            query.likeAnywhere("title", title);
//        }
//        if (isStringNotEmpty(description)) {
//            query.likeAnywhere("description", description);
//        }
//        query.eq("type", resourceType());
//        query.eq("deleteFlag", false);
//
//        query.orderDesc("uploadTime");
//
//        return query;
//    }

    protected abstract ResourceType resourceType();
//
//    private Integer getSiteId() {
//        return EwcmsContextUtil.getCurrentSite().getId();
//    }
//
//    @Override
//    protected PageQueryable constructNewQuery(Order order) {
//        //没有新增数据查询
//        return null;
//    }
}
