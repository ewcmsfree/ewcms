/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.web;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.web.QueryBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("scheduling.jobclass.query")
public class JobClassQueryAction extends QueryBaseAction {

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
	
//	private static final long serialVersionUID = -8882837349113907705L;
//	
//	@Autowired
//    private QueryFactory queryFactory;
//	
//	@SuppressWarnings("rawtypes")
//	@Override
//	protected PageQueryable constructNewQuery(Order order) {
//		EntityPageQueryable query = queryFactory.createEntityPageQuery(AlqcJobClass.class);
//        //query.in("id", getNewIdAll(Integer.class));
//        query.orderDesc("id");
//        return query;
//	}
//
//	@SuppressWarnings("rawtypes")
//	@Override
//	protected PageQueryable constructQuery(Order order) {
//		EntityPageQueryable query = queryFactory.createEntityPageQuery(AlqcJobClass.class);
//		
//        Integer id = getParameterValue(Integer.class,"id", "查询编号错误，应该是整型");
//        if (isNotEmpty(id)) query.eq("id", id);
//        
//        String name = getParameterValue(String.class, "className", "");
//        if (isNotEmpty(name)) query.likeAnywhere("className", name);
//        
//        simpleEntityOrder(query, order);
//        return query;
//	}

}
