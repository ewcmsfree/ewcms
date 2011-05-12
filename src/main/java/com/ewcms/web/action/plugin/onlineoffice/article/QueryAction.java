/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.plugin.onlineoffice.article;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.comm.jpa.query.HqlPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.comm.jpa.query.QueryFactory;
import com.ewcms.core.document.model.ArticleRmc;
import com.ewcms.web.action.QueryBaseAction;

/**
 * 
 * @author 吴智俊
 */
@Controller("plugin.onlineoffice.article.query")
public class QueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 5355642552995277216L;

	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");

    @Autowired
    private QueryFactory queryFactory;
    
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

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructNewQuery(Order order) {
		String hql = "Select r From WorkingBody As c Right Join c.articleRmcs AS r RIGHT JOIN r.article AS a Where r.deleteFlag=false And r.id In (:id) And c.id=:workingBodyId And c.channelId=:channelId Order By a.topFlag Desc, r.published Desc Nulls Last, r.modified Desc Nulls Last, r.id";
		String countHql = "Select count(r.id) From WorkingBody As c Right Join c.articleRmcs AS r RIGHT JOIN r.article a Where r.deleteFlag=false And r.id In (:id) And c.id=:workingBodyId And c.channelId=:channelId";
		
		HqlPageQueryable<ArticleRmc> query = queryFactory.createHqlPageQuery(hql, countHql);
		query.setParameter("id", getNewIdAll(Integer.class));
		query.setParameter("workingBodyId", getWorkingBodyId());
		query.setParameter("channelId", getChannelId());
		
		setDateFormat(DATE_FORMAT);
		
		return query;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructQuery(Order order) {
		String hql = "Select r From WorkingBody As c Right Join c.articleRmcs AS r RIGHT JOIN r.article AS a Where r.deleteFlag=false And c.id=:workingBodyId And c.channelId=:channelId ";
		String countHql = "Select count(r.id) From WorkingBody As c Right Join c.articleRmcs AS r RIGHT JOIN r.article AS a Where r.deleteFlag=false And c.id=:workingBodyId And c.channelId=:channelId ";
		
		Integer id = getParameterValue(Integer.class, "id", "查询编号错误，应该是整型");
		if (isNotEmpty(id)){
			hql += " And r.id=:id ";
			countHql += " And r.id=:id";
		}
		String title = getParameterValue(String.class, "title", "");
		if (isNotEmpty(title)){
			hql += " And a.title Like :title";
			countHql += " And a.title Like :title";
		}
		hql += " Order By a.topFlag Desc, r.published Desc Nulls Last, r.modified Desc Nulls Last, r.id";
		
		HqlPageQueryable<ArticleRmc> query = queryFactory.createHqlPageQuery(hql, countHql);
		if (isNotEmpty(id)){
			query.setParameter("id", id);
		}
		if (isNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		query.setParameter("workingBodyId", getWorkingBodyId());
		query.setParameter("channelId", getChannelId());
		
		setDateFormat(DATE_FORMAT);
		
		return query;
	}
}
