/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.contribute.web;

import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ewcms.web.QueryBaseAction;
import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.contribute.model.Contribute;

import org.springframework.stereotype.Controller;


/**
 *
 * @author 吴智俊
 */
@Controller
public class ContributeQueryAction extends QueryBaseAction {
    
	private static final long serialVersionUID = -2689913260990597980L;

	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
		EntityQueryable query = queryFactory.createEntityQuery(Contribute.class).setPage(page).setRow(rows).orderDesc("date");
		Integer checked = getParameterValue(Integer.class, "checked");
        if (checked != null && checked != 0) {
            if (checked == 1) {
                query.eq("checked", true);
            } else {
                query.eq("checked", false);
            }
        }

        String content = getParameterValue(String.class, "content");
        if (isStringNotEmpty(content)) query.likeAnywhere("content", content);
        
        String username = getParameterValue(String.class, "username");
        if (isStringNotEmpty(username)) query.likeAnywhere("username", username);
        
        String email = getParameterValue(String.class, "email");
        if (isStringNotEmpty(email)) query.likeAnywhere("email", email);
        
        String phone = getParameterValue(String.class, "phone");
        if (isStringNotEmpty(email)) query.likeAnywhere("phone", phone);
        
        String title = getParameterValue(String.class, "title");
        if (isStringNotEmpty(title)) query.likeAnywhere("title", title);
        
        try{
        	Integer ageStart = getParameterValue(Integer.class, "ageStart");
        	Integer ageEnd = getParameterValue(Integer.class, "endStart");
        	if (ageStart == null) ageStart = 1;
        	if (ageEnd == null) ageEnd = 130;
        	query.between("age", ageStart, ageEnd);
        }catch(Exception e){
        }
        
        try {
	        Date dateStart = getParameterValue(Date.class, "dateStart");
	        Date dataEnd = getParameterValue(Date.class, "dataEnd");
	        if (dateStart == null)
	        	dateStart = DATE_FORMAT.parse("1900-01-01 00:00:00");
	        if (dataEnd == null)
	        	dataEnd = DATE_FORMAT.parse("2099-12-31 23:59:59");
	        query.between("lastUsed", dateStart, dataEnd);
		} catch (Exception e) {
		}
        
		setDateFormat(DATE_FORMAT);
        
		return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		return null;
	}
}
