/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.history.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 *
 * @author 吴智俊
 */
@Controller("history.query")
public class HistoryQueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = -6958534645862618323L;
	
	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    @Override
    protected Resultable queryResult(QueryFactory queryFactory,String cacheKey, int rows, int page, Order order) {
		String hql = "Select h From HistoryModel h Where h.userName=:userName ";
		String countHql = "Select count(h.id) From HistoryModel h Where h.userName=:userName ";
		
		Long id = getParameterValue(Long.class, "id", "查询编号错误，应该是整型");
		if (isNotNull(id)){
			hql += " And h.id=:id ";
			countHql += " And h.id=:id";
		}
		
		String startDate = getParameterValue(String.class, "startDate","开始时间错误");
        if (isNotNull(startDate)){
        	hql += " And h.createDate>=:startDate ";
        	countHql += " And h.createDate>=:startDate ";
        }
        
        String endDate = getParameterValue(String.class, "endDate","结束时间错误");
        if (isNotNull(endDate)){
        	hql += " And h.createDate<=:endDate";
        	countHql += " And h.createDate<=:endDate";
        }

        hql += " Order By h.id Desc";
        
        HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
        
        query.setParameter("userName", EwcmsContextUtil.getUserName());
        
		if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(startDate)){
			try {
				query.setParameter("startDate", DATE_FORMAT.parse(startDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}		
		if (isStringNotEmpty(endDate)){
			try {
				query.setParameter("endDate", DATE_FORMAT.parse(startDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}		
        setDateFormat(DATE_FORMAT);
        
        return query.setRow(rows).setPage(page).queryCacheResult(cacheKey);
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String hql = "Select h From HistoryModel h Where h.id In (:id) And h.userName=:userName Order By h.id DESC";
		String countHql = "Select count(h.id) From HistoryModel h Where h.id In (:id) And h.userName=:userName ";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		query.setParameter("id", getIds(Integer.class));
		query.setParameter("userName", EwcmsContextUtil.getUserName());
		
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryResult();	
    }
}
