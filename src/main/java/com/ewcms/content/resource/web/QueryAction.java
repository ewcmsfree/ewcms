/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.web;

import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.Resource.Type;
import com.ewcms.content.resource.model.Resource.State;
import com.ewcms.core.site.model.Site;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 查询资源
 * 
 * @author wangwei
 */
@Controller("resource.query.action")
public class QueryAction extends QueryBaseAction {

    /**
     * 得到当前站点
     * 
     * @return 
     */
    private Site getSite() {
        return EwcmsContextUtil.getCurrentSite();
    }

    @Override
    protected Resultable queryResult(QueryFactory queryFactory,
            String cacheKey, int rows, int page, Order order) {

        EntityQueryable query = queryFactory.createEntityQuery(Resource.class)
                .setPage(page - 1).setRow(rows).orderAsc("createTime");

        query.eq("site", getSite());

        String name = getParameterValue(String.class, "name");
        if (isStringNotEmpty(name)) {
            query.likeAnywhere("name", name);
        }

        String description = getParameterValue(String.class, "description");
        if (isStringNotEmpty(description)) {
            query.likeAnywhere("description", description);
        }

        String type = getParameterValue(String.class, "type");
        Type resourceType = (isStringNotEmpty(type) ? Type.ANNEX
                : Type.valueOf(StringUtils.upperCase(type)));
        query.eq("type", resourceType);
        
        query.in("state", new State[]{State.NORMAL,State.RELEASED});

        return query.queryCacheResult(cacheKey);
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory,
            int rows, int page, String[] selections, Order order) {

        // not implement
        return null;
    }
}
