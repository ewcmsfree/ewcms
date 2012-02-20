/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author 吴智俊
 *
 */
public class PersonQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 9052235352505644394L;
	
	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Long questionnaireId;
	
	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
    	String hql = "Select p From Person As p Where p.questionnaireId=:questionnaireId ";
    	String countHql = "Select Count(p.id) From Person As p Where p.questionnaireId=:questionnaireId ";
    	
    	Long id = getParameterValue(Long.class,"id", "查询编号错误，应该是整型");
    	if (isNotNull(id)) {
    		hql += " And p.id=:id";
    		countHql += " And p.id=:id";
    	}
    	        
    	String ip = getParameterValue(String.class, "ip");
    	if (isStringNotEmpty(ip)) {
    		hql += " And p.ip Like :ip";
    		countHql += " And p.ip Like :ip";
    	}
    	
    	String startTime = getParameterValue(String.class, "startTime");
    	if (isNotNull(startTime)) {
    		hql += " And p.recordTime>=:startTime";
    		countHql += " And p.recordTime>=:startTime";
    	}
    	
    	String endTime = getParameterValue(String.class, "endTime");
    	if (isNotNull(endTime)) {
    		hql += " And p.recordTime<:endTime";
    		countHql += " And p.recordTime<:endTime";
    	}
    	
		hql += " Order By p.id";
		
    	HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
    	
		if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(ip)){
			query.setParameter("ip", "%" + ip + "%");
		}
    	
		if (isStringNotEmpty(startTime)){
			try {
				query.setParameter("startTime", DATE_FORMAT.parse(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(endTime)){
			try {
				query.setParameter("endTime", DATE_FORMAT.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
   	
		query.setParameter("questionnaireId", getQuestionnaireId());
		
    	setDateFormat(DATE_FORMAT);
    	
    	return query.setRow(rows).setPage(page).queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		return null;
	}

}
