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
import java.util.List;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.vote.model.Questionnaire;
import com.ewcms.plugin.vote.model.Questionnaire.Status;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author 吴智俊
 *
 */
public class QuestionnaireQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 4389831251347203862L;
	
	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Integer channelId;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
    	String hql = "Select q From Questionnaire As q Where q.channelId=:channelId ";
    	String countHql = "Select Count(q.id) From Questionnaire As q Where q.channelId=:channelId ";
    	
    	Long id = getParameterValue(Long.class,"id", "查询编号错误，应该是整型");
    	if (isNotNull(id)) {
    		hql += " And q.id=:id";
    		countHql += " And q.id=:id";
    	}
    	        
    	String title = getParameterValue(String.class, "title");
    	if (isStringNotEmpty(title)) {
    		hql += " And q.title Like :title";
    		countHql += " And q.title Like :title";
    	}
    	
    	String startTime = getParameterValue(String.class, "startTime");
    	if (isNotNull(startTime)) {
    		hql += " And q.startTime>=:startTime";
    		countHql += " And q.startTime>=:startTime";
    	}
    	
    	String endTime = getParameterValue(String.class, "endTime");
    	if (isNotNull(endTime)) {
    		hql += " And q.endTime<:endTime";
    		countHql += " And q.endTime<:endTime";
    	}
    	
    	String status = getParameterValue(String.class, "status");
		if (isStringNotEmpty(status) && !status.equals("-1")){
			hql += " And q.status=:status";
			countHql += " And q.status=:status";
		}
		
		Long numberBegin = getParameterValue(Long.class, "numberBegin");
		if (isNotNull(numberBegin)) {
			hql += " And q.number>=:numberBegin";
			countHql +=" And q.number>=:numberBegin";
		}
		
		Long numberEnd = getParameterValue(Long.class, "numberEnd");
		if (isNotNull(numberEnd)) {
			hql += " And q.number<=:numberEnd";
			countHql +=" And q.number<=:numberEnd";
		}
		
		String voteEnd = getParameterValue(String.class, "voteEnd", "");
		if (isStringNotEmpty(voteEnd) && !voteEnd.equals("-1")){
			hql += " And q.voteEnd=:voteEnd";
			countHql += " And q.voteEnd=:voteEnd";
		}
		
		String verifiCode = getParameterValue(String.class, "verifiCode");
		if (isStringNotEmpty(verifiCode) && !verifiCode.equals("-1")) {
			hql += " And q.verifiCode=:verifiCode";
			countHql += " And q.verifiCode=:verifiCode";
		}
		
		hql += " Order By q.id Desc ";
		hql += " Limit " + rows + " OffSet " + (rows * (page + 1));
		
    	HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
    	
		if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
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
		if (isStringNotEmpty(status) && !status.equals("-1")){
			query.setParameter("status", Status.valueOf(status));
		}
		if (isNotNull(numberBegin)) {
			query.setParameter("numberBegin", numberBegin);
		}
		if (isNotNull(numberEnd)) {
			query.setParameter("numberEnd", numberEnd);
		}
		if (isStringNotEmpty(voteEnd) && !voteEnd.equals("-1")){
			query.setParameter("voteEnd", Boolean.parseBoolean(voteEnd));
		}
		if (isStringNotEmpty(verifiCode) && !verifiCode.equals("-1")){
			query.setParameter("verifiCode", Boolean.parseBoolean(verifiCode));
		}
    	query.setParameter("channelId", getChannelId());
    	
    	setDateFormat(DATE_FORMAT);
    	
    	return query.setRow(rows).setPage(page).queryResult();
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
    	EntityQueryable query = queryFactory.createEntityQuery(Questionnaire.class).setPage(page).setRow(rows).orderAsc("id");
    	
    	query.eq("channelId", getChannelId());
    	
        List<Long> ids = getIds(Long.class);
        query.in("id", ids);
        
        setDateFormat(DATE_FORMAT);
        
        return query.queryResult();    
	}

}
