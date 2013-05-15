/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.security.manage.web.loginlogs;

import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.security.manage.model.LoginLogs;
import com.ewcms.security.manage.service.LoginLogsServiceable;
import com.ewcms.web.QueryBaseAction;

@Controller("security.manage.web.loginlogs.query")
public class QueryAction extends QueryBaseAction{

	private static final long serialVersionUID = 2584716134140615173L;

	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private LoginLogsServiceable persistentLoginsService;
	
	@Override
	protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
		EntityQueryable query = 
	            queryFactory.createEntityQuery(LoginLogs.class)
	            .setPage(page)
	            .setRow(rows).orderDesc("lastUsed");
		
		String userName = getParameterValue(String.class, "userName");
        if (isStringNotEmpty(userName)) query.likeAnywhere("userName", userName);
        
		try {
	        Date startDate = getParameterValue(Date.class, "startDate");
	        Date endDate = getParameterValue(Date.class, "endDate");
	        if (startDate == null)
	        	startDate = DATE_FORMAT.parse("1900-01-01 00:00:00");
	        if (endDate == null)
	        	endDate = DATE_FORMAT.parse("2099-12-31 23:59:59");
	        query.between("lastUsed", startDate, endDate);
		} catch (ParseException e) {
		}
        
		setDateFormat(DATE_FORMAT);
		
		return query.setPage(page).setRow(rows).queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		return null;
	}

}
