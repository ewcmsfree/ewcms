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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.core.site.model.Channel;

/**
 * @author 周冬初
 * 
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
		return this.getJpaTemplate().execute(new JpaCallback<List<Channel>>() {
			@Override
			public List<Channel> doInJpa(EntityManager em) throws PersistenceException {
				String hql = "From Channel o Where o.parent.id=? Order By o.id";
				TypedQuery<Channel> query = em.createQuery(hql,Channel.class);
				query.setParameter(1, parentId);
				return query.getResultList();

			}
		});
	}
	/**
	 * 获取站点首页专栏
	 * 
	 */	
	public Channel getChannelRoot(final Integer siteId){
		List<Channel> channelList  = this.getJpaTemplate().execute(new JpaCallback<List<Channel>>() {
			@Override
			public List<Channel> doInJpa(EntityManager em) throws PersistenceException {
				String hql = "From Channel o Where o.parent is null and o.site.id=? Order By o.id";
				TypedQuery<Channel> query = em.createQuery(hql,Channel.class);
				query.setParameter(1, siteId);
				return query.getResultList();

			}
		});	
		return channelList.isEmpty() ? null :channelList.get(0);		
	}
	
	/**
	 * 获取站点首页专栏
	 * 
	 */	
	public Channel getChannelByURL(final Integer siteId,final String path){
	    List<Channel> channelList = this.getJpaTemplate().execute(new JpaCallback<List<Channel>>() {
			@Override
			public List<Channel> doInJpa(EntityManager em) throws PersistenceException {
				String hql = "From Channel o Where o.site.id=? and o.pubPath=? Order By o.id";
				TypedQuery<Channel> query = em.createQuery(hql,Channel.class);
				query.setParameter(1, siteId);
				query.setParameter(2, path);
				return query.getResultList();

			}
		});	
	    return channelList.isEmpty() ? null :channelList.get(0);      
	}		
}
