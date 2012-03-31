/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.core.site.model.Channel;

/**
 * @author 周冬初
 * @author wuzhijun
 */
@Repository
public class ChannelDAO extends JpaDAO<Integer, Channel> {
	/**
	 * 获取站点子专栏
	 * 
	 */
	public List<Channel> getChannelChildren(final Integer parentId) {
		if (parentId == null){
			return new ArrayList<Channel>();
		}
	    String hql = "From Channel o Where o.parent.id=:parentId Order By o.id";

	    TypedQuery<Channel> query = this.getEntityManager().createQuery(hql, Channel.class);
	    query.setParameter("parentId", parentId);

	    return query.getResultList();
	}
	/**
	 * 获取站点首页专栏
	 * 
	 */	
	public Channel getChannelRoot(final Integer siteId){
		String hql = "From Channel o Where o.parent is null and o.site.id=:siteId Order By o.id";

		TypedQuery<Channel> query = this.getEntityManager().createQuery(hql, Channel.class);
		query.setParameter("siteId", siteId);

		Channel channel = null;
		try{
			channel = (Channel)query.getSingleResult();
		}catch (NoResultException e){
		}
		
		return channel;		
	}
	
	/**
	 * 获取站点首页专栏
	 * 
	 */	
	public Channel getChannelByURL(final Integer siteId,final String path){
		String hql = "From Channel o Where o.site.id=:siteId and o.pubPath=:path Order By o.id";

		TypedQuery<Channel> query = this.getEntityManager().createQuery(hql, Channel.class);
		query.setParameter("siteId", siteId);
		query.setParameter("path", path);

		Channel channel = null;
		try{
			channel = (Channel) query.getSingleResult();
		}catch (NoResultException e){
			
		}
		return channel;
	}		
}
