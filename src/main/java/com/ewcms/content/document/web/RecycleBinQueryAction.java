/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.content.document.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Controller;

import com.ewcms.common.jpa.query.HqlPageQueryable;
import com.ewcms.common.jpa.query.PageQueryable;
import com.ewcms.common.jpa.query.QueryFactory;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.core.site.service.ChannelService;
import com.ewcms.web.action.QueryBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 *
 * @author 吴智俊
 */
@Controller("recyclebin")
public class RecycleBinQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = -5014571744056723878L;

	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
	
    @Autowired
    private QueryFactory queryFactory;
    @Autowired
    private ChannelService channelService;

	private Integer channelId;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructNewQuery(Order order) {
		String hql = "Select r From ArticleRmc AS r RIGHT JOIN r.article a Where r.deleteFlag=true And r.id In (:id) And r.channel.id=:channelId ";
		String countHql = "Select count(r.id) From ArticleRmc AS r RIGHT JOIN r.article a Where r.deleteFlag=true And r.id In (:id) And r.channel.id=:channelId";
		
		if (!getPermissionIsChannel()){
			hql += " And a.author=:author ";
			countHql += " And a.author=:author ";
		}
		
		hql = hql + " Order By r.id";
		
		HqlPageQueryable<ArticleRmc> query = queryFactory.createHqlPageQuery(hql, countHql);
		query.setParameter("id", getNewIdAll(Integer.class));
		query.setParameter("channelId", getChannelId());
		if (!getPermissionIsChannel()){
			query.setParameter("author", EwcmsContextUtil.getUserName());
		}

		setDateFormat(DATE_FORMAT);
		
		return query;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructQuery(Order order) {
		String hql = "Select r From ArticleRmc AS r RIGHT JOIN r.article a Where r.deleteFlag=true And r.channel.id=:channelId ";
		String countHql = "Select count(r.id) From ArticleRmc AS r RIGHT JOIN r.article a Where r.deleteFlag=true And r.channel.id=:channelId";
		
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
		if (!getPermissionIsChannel()){
			hql += " And a.author=:author ";
			countHql += " And a.author=:author ";
		}
		hql += " Order By r.id";
		
		HqlPageQueryable<ArticleRmc> query = queryFactory.createHqlPageQuery(hql, countHql);
		if (isNotEmpty(id)){
			query.setParameter("id", id);
		}
		if (isNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		if (!getPermissionIsChannel()){
			query.setParameter("author", EwcmsContextUtil.getUserName());
		}
		query.setParameter("channelId", getChannelId());
		
		setDateFormat(DATE_FORMAT);

		return query;
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
