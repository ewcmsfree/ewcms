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
	    String hql = "From Channel o Where o.parent.id=:parentId Order By o.sort, o.id";

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
	
	public Long findMaxSiblingChannel(final Integer parentId){
		String hql = "Select Max(c.sort) From Channel As c Where c.parent.id=:parentId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("parentId", parentId);
		Long maxSort = 0L;
		try{
			maxSort = (Long) query.getSingleResult();
		}catch(NoResultException e){
		}
		if (maxSort == null){
			maxSort = 0L;
		}
		return maxSort;
	}

	public Channel findChannelByParentIdAndSort(final Integer parentId, final Long sort){
    	String hql = "From Channel As o Where o.parent.id=:parentId And o.sort=:sort";
        
        TypedQuery<Channel> query = this.getEntityManager().createQuery(hql, Channel.class);
        query.setParameter("parentId", parentId);
        query.setParameter("sort", sort);

        Channel channel = null;
        try{
        	channel = (Channel) query.getSingleResult();
        }catch(NoResultException e){
        }
        return channel;
	}
	
	public List<Integer> findChannelParent(){
		String hql = "Select o.parent.id from Channel As o group by o.parent.id order by o.parent.id";
		TypedQuery<Integer> query = this.getEntityManager().createQuery(hql, Integer.class);
		return query.getResultList();
	}
	
	public List<Channel> findChannelByGreaterThanSort(final Integer parentId, final Long sort){
		String hql = "From Channel As o Where o.parent.id=:parentId And o.sort>:sort Order By o.sort Asc";
		TypedQuery<Channel> query = this.getEntityManager().createQuery(hql, Channel.class);
		query.setParameter("parentId", parentId);
		query.setParameter("sort", sort);
		return query.getResultList();
	}
}
