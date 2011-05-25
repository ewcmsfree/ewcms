/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.content.document.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.document.model.ArticleRmcStatus;
import com.ewcms.core.site.service.ChannelService;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 
 * @author 吴智俊
 */
@Controller("article")
public class ArticleQueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = 5355642552995277216L;
	
	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
	
	@Autowired
	private ChannelService channelService;
	    
	private Integer channelId;
		
	private Boolean isRelease = false;
	
	public Integer getChannelId() {
		return channelId;
	}
	
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
	public Boolean getIsRelease() {
		return isRelease;
	}
	
	public void setIsRelease(Boolean isRelease) {
		this.isRelease = isRelease;
	}
	
    @Override
    protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
		String hql = "Select r From ArticleRmc AS r RIGHT JOIN r.article AS a Where r.deleteFlag=false And r.channel.id=:channelId ";
		String countHql = "Select count(r.id) From ArticleRmc AS r RIGHT JOIN r.article AS a Where r.deleteFlag=false And r.channel.id=:channelId";
		
		Integer id = getParameterValue(Integer.class, "id", "查询编号错误，应该是整型");
		if (isNotNull(id)){
			hql += " And r.id=:id ";
			countHql += " And r.id=:id";
		}
		String title = getParameterValue(String.class, "title", "");
		if (isStringNotEmpty(title)){
			hql += " And a.title Like :title";
			countHql += " And a.title Like :title";
		}
		if (isRelease){
			hql += " And r.status=:status ";
			countHql += " And r.status=:status ";
		}
		if (!getPermissionIsChannel()){
			hql += " And a.author=:author ";
			countHql += " And a.author=:author ";
		}
		
		hql += " Order By a.topFlag Desc, r.published Desc Nulls Last, r.modified Desc Nulls Last, r.id";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		if (isRelease){
			query.setParameter("status", ArticleRmcStatus.RELEASE);
		}
		if (!getPermissionIsChannel()){
			query.setParameter("author", EwcmsContextUtil.getUserName());
		}
		query.setParameter("channelId", getChannelId());
		
		setDateFormat(DATE_FORMAT);
		
		return query.queryCacheResult(cacheKey);
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String hql = "Select r From ArticleRmc AS r RIGHT JOIN r.article AS a Where r.deleteFlag=false And r.id In (:id) And r.channel.id=:channelId ";
		String countHql = "Select count(r.id) From ArticleRmc AS r RIGHT JOIN r.article a Where r.deleteFlag=false And r.id In (:id) And r.channel.id=:channelId";
		
		if (isRelease){
			hql += " And r.status=:status ";
			countHql += " And r.status=:status ";
		}
		if (!getPermissionIsChannel()){
			hql += " And a.author=:author ";
			countHql += " And a.author=:author ";
		}
		hql = hql + " Order By a.topFlag Desc, r.published Desc Nulls Last, r.modified Desc Nulls Last, r.id";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		query.setParameter("id", getIds(Integer.class));
		query.setParameter("channelId", getChannelId());
		if (isRelease){
			query.setParameter("status", ArticleRmcStatus.RELEASE);
		}
		if (!getPermissionIsChannel()){
			query.setParameter("author", EwcmsContextUtil.getUserName());
		}
		
		setDateFormat(DATE_FORMAT);
		
		return query.queryResult();
    }

	private Boolean getPermissionIsChannel(){
		if (getChannelId() == null) return false;
		Set<Permission>  permissions = channelService.getPermissionsOfChannelById(getChannelId());
		for (Permission permission : permissions){
			if (permission.getMask() > 2){
				return true;
			}
		}
		return false;
	}
}
