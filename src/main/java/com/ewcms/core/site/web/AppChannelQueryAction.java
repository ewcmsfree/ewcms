/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.util.List;

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
public class AppChannelQueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = 8951492555901921467L;

	@Autowired
	private SiteFacable siteFac;

	private Integer channelId;
	
    public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@Override
    protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
        String hql = "Select o From Channel As o Where";
        String countHql = "Select count(o.id) From Channel As o Where";
        
        List<Integer> appChannelIds = siteFac.findAssociatedChannel(getChannelId()); 
        if (!appChannelIds.isEmpty()){
        	hql += " o.id in :appChannelIds";
        	countHql += " o.id in :appChannelIds";
        }else{
        	hql += " 1<>1";
        	countHql += " 1<>1";
        }
        
        Integer id = getParameterValue(Integer.class, "id", "查询编号错误，应该是整型");
		if (isNotNull(id)){
			hql += " And o.id=:id ";
			countHql += " And o.id=:id";
		}
        
        String name = getParameterValue(String.class, "name", "");
		if (isStringNotEmpty(name)){
			hql += " And o.name Like :name";
			countHql += " And o.name Like :name";
		}
		
		String absUrl = getParameterValue(String.class, "absUrl", "");
		if (isStringNotEmpty(absUrl)){
			hql += " And o.absUrl Like :absUrl";
			countHql += " And o.absUrl Like :absUrl";
		}
        
		hql += " Order By o.id Asc Limit " + rows + " OffSet " + (rows * (page + 1));
		
        HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
        
        if (!appChannelIds.isEmpty()){
        	query.setParameter("appChannelIds", appChannelIds);
        }
        if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(name)){
			query.setParameter("name", "%" + name + "%");
		}
		if (isStringNotEmpty(absUrl)){
			query.setParameter("absUrl", "%" + absUrl + "%");
		}
        return query.setRow(rows).setPage(page).queryResult();
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
        return null;
    }

}
