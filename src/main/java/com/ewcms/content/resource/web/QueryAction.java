/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.web;

import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;
import static com.ewcms.common.lang.EmptyUtil.isNotNull;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    private static final Date MINI_DATE ;
    
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -50);
        MINI_DATE = new Date(calendar.getTime().getTime());
    }
    
    private String type;
    
    public QueryAction(){
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
    }
    
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

        EntityQueryable query = queryFactory
                .createEntityQuery(Resource.class)
                .setPage(page)
                .setRow(rows)
                .orderDesc("createTime");

        query.eq("site", getSite());
        query.eq("type", Type.valueOf(StringUtils.upperCase(type)));
        query.in("state", new State[]{State.NORMAL,State.RELEASED});
        
        String name = getParameterValue(String.class, "name");
        if (isStringNotEmpty(name)) {
            query.likeAnywhere("name", name);
        }

        String description = getParameterValue(String.class, "description");
        if (isStringNotEmpty(description)) {
            query.likeAnywhere("description", description);
        }
        
        Date fromDate = getParameterValue(Date.class,"fromDate");
        Date toDate = getParameterValue(Date.class,"toDate");
        if(isNotNull(fromDate) || isNotNull(toDate)){
            fromDate = (fromDate == null ? MINI_DATE : fromDate);
            toDate = (toDate == null ? new Date(System.currentTimeMillis()) : toDate);
            query.between("createTime", fromDate,toDate);
        }
        
        return query.queryCacheResult(cacheKey);
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory,
            int rows, int page, String[] selections, Order order) {

        // not implement
        return null;
    }

    public void setType(String type) {
        this.type = type;
    }
}
