/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.history.web;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.web.QueryBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("aspect.history.query")
public class HistoryQueryAction extends QueryBaseAction {

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
	
//	private static final long serialVersionUID = -6958534645862618323L;
//	
//	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	
//	@Autowired
//    private QueryFactory queryFactory;
//	
//	@SuppressWarnings("rawtypes")
//	@Override
//	protected PageQueryable constructNewQuery(Order order) {
//		String hql = "Select h From HistoryModel h Where h.id In (:id) And h.userName=:userName Order By h.id DESC";
//		String countHql = "Select count(h.id) From HistoryModel h Where h.id In (:id) And h.userName=:userName ";
//		
//		HqlPageQueryable<HistoryModel> query = queryFactory.createHqlPageQuery(hql, countHql);
//		query.setParameter("id", getIds(Integer.class));
//		query.setParameter("userName", EwcmsContextUtil.getUserName());
//		
//		setDateFormat(DATE_FORMAT);
//		
//		return query;	
//	}
//
//	@SuppressWarnings("rawtypes")
//	@Override
//	protected PageQueryable constructQuery(Order order) {
//		String hql = "Select h From HistoryModel h Where h.userName=:userName ";
//		String countHql = "Select count(h.id) From HistoryModel h Where h.userName=:userName ";
//		
//		Long id = getParameterValue(Long.class, "id", "查询编号错误，应该是整型");
//		if (isNotEmpty(id)){
//			hql += " And h.id=:id ";
//			countHql += " And h.id=:id";
//		}
//		
//        Date startDate = getParameterValue(Date.class, "startDate","开始时间错误");
//        if (isNotEmpty(startDate)){
//        	hql += " And h.createDate>=:startDate ";
//        	countHql += " And h.createDate>=:startDate ";
//        }
//        
//        Date endDate = getParameterValue(Date.class, "endDate","结束时间错误");
//        if (isNotEmpty(endDate)){
//        	hql += " And h.createDate<=:endDate";
//        	countHql += " And h.createDate<=:endDate";
//        }
//
//        hql += " Order By h.id Desc";
//        
//        HqlPageQueryable<HistoryModel> query = queryFactory.createHqlPageQuery(hql, countHql);
//        
//        query.setParameter("userName", EwcmsContextUtil.getUserName());
//        
//		if (isNotEmpty(id)){
//			query.setParameter("id", id);
//		}
//		if (isNotEmpty(startDate)){
//			query.setParameter("startDate", startDate);
//		}
//		if (isNotEmpty(endDate)){
//			query.setParameter("endDate", endDate);
//		}
//        setDateFormat(DATE_FORMAT);
//        
//        return query;
//	}

}
