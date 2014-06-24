/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.core.site.SiteFacable;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author 吴智俊
 */
public class ReferQueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = 5355642552995277216L;
	
	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private SiteFacable siteFac;
	    
	private Integer channelId;
	private Integer referChannelId;
		
	public Integer getChannelId() {
		return channelId;
	}
	
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
    public Integer getReferChannelId() {
		return referChannelId;
	}

	public void setReferChannelId(Integer referChannelId) {
		this.referChannelId = referChannelId;
	}

	@Override
    protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
		String hql = "Select o From ArticleMain As o Left Join o.article AS r Where r.delete=false And o.channelId=:channelId and r.id not in (Select st.article.id From ArticleMain as st where st.reference=true and st.channelId=:referChannelId) ";
		String countHql = "Select count(o.id) From ArticleMain As o Left Join o.article AS r Where r.delete=false And o.channelId=:channelId and r.id not in (Select st.article.id From ArticleMain as st where st.reference=true and st.channelId=:referChannelId) ";
		
		Long id = getParameterValue(Long.class, "id", "查询编号错误，应该是整型");
		if (isNotNull(id)){
			hql += " And o.id=:id ";
			countHql += " And o.id=:id";
		}
		String title = getParameterValue(String.class, "title", "");
		if (isStringNotEmpty(title)){
			hql += " And r.title Like :title";
			countHql += " And r.title Like :title";
		}
				
		hql += " Order By Case When o.top Is Null Then 1 Else 0 End, o.top Desc, o.sort Asc, Case When r.published Is Null Then 0 Else 1 End, r.published Desc, Case When r.modified Is Null Then 0 Else 1 End, r.modified Desc, o.id Desc";
		hql += " Limit " + rows + " OffSet " + (rows * (page + 1));
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
				
		query.setParameter("channelId", getChannelId());
		query.setParameter("referChannelId", getReferChannelId());
		
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryResult();
    }

	@Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String hql = "Select o From ArticleMain As o Left Join o.article AS r Where r.delete=false And o.id In (:id) And o.channelId=:channelId and r.id not in (Select st.article.id From ArticleMain as st where st.reference=true and st.channelId=:referChannelId) ";
		String countHql = "Select count(o.id) From ArticleMain As o Left  Join o.article AS r Where r.delete=false And o.id In (:id) And o.channelId=:channelId and r.id not in (Select st.article.id From ArticleMain as st where st.reference=true and st.channelId=:referChannelId) ";
		
		hql += " Order By Case When o.top Is Null Then 1 Else 0 End, o.top Desc, o.sort Asc,  r.modified Desc, Case When r.published Is Null Then 0 Else 1 End, r.published Desc, Case When r.modified Is Null Then 0 Else 1 End, o.id Desc";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		query.setParameter("id", getIds(Long.class));
		query.setParameter("channelId", getChannelId());
		query.setParameter("referChannelId", getReferChannelId());
		
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryResult();
    }
}
