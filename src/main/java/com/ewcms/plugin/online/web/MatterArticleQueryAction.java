/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.common.lang.EmptyUtil;
/**
 * 
 * @author 吴智俊
 */
public class MatterArticleQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 5355642552995277216L;

	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Integer workingBodyId;
	
	private Integer channelId;

	public Integer getWorkingBodyId() {
		return workingBodyId;
	}

	public void setWorkingBodyId(Integer workingBodyId) {
		this.workingBodyId = workingBodyId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
	@Override
	protected Resultable queryResult(QueryFactory queryFactory,
			String cacheKey, int rows, int page, Order order) {
		String hql = "Select r From WorkingBody As c Right Join c.articleRmcs AS r RIGHT JOIN r.article AS a Where r.deleteFlag=false And c.id=:workingBodyId And c.channelId=:channelId ";
		String countHql = "Select count(r.id) From WorkingBody As c Right Join c.articleRmcs AS r RIGHT JOIN r.article AS a Where r.deleteFlag=false And c.id=:workingBodyId And c.channelId=:channelId ";
		
		Integer id = getParameterValue(Integer.class, "id", "查询编号错误，应该是整型");
		if (EmptyUtil.isNotNull(id)){
			hql += " And r.id=:id ";
			countHql += " And r.id=:id";
		}
		String title = getParameterValue(String.class, "title", "");
		if (EmptyUtil.isNotNull(title)){
			hql += " And a.title Like :title";
			countHql += " And a.title Like :title";
		}
		hql += " Order By a.topFlag Desc, r.published Desc Nulls Last, r.modified Desc Nulls Last, r.id";
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		if (EmptyUtil.isNotNull(id)){
			query.setParameter("id", id);
		}
		if (EmptyUtil.isNotNull(title)){
			query.setParameter("title", "%" + title + "%");
		}
		query.setParameter("workingBodyId", getWorkingBodyId());
		query.setParameter("channelId", getChannelId());
		
		setDateFormat(DATE_FORMAT);		
		return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory,
			int rows, int page, String[] selections, Order order) {
		String hql = "Select r From WorkingBody As c Right Join c.articleRmcs AS r RIGHT JOIN r.article AS a Where r.deleteFlag=false And r.id In (:id) And c.id=:workingBodyId And c.channelId=:channelId Order By a.topFlag Desc, r.published Desc Nulls Last, r.modified Desc Nulls Last, r.id";
		String countHql = "Select count(r.id) From WorkingBody As c Right Join c.articleRmcs AS r RIGHT JOIN r.article a Where r.deleteFlag=false And r.id In (:id) And c.id=:workingBodyId And c.channelId=:channelId";
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		query.setParameter("id", getIds(Integer.class));
		query.setParameter("workingBodyId", getWorkingBodyId());
		query.setParameter("channelId", getChannelId());
		setDateFormat(DATE_FORMAT);
		return query.queryResult();
	}
}
