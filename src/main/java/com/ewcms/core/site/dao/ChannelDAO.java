/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

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
	public List<Channel> getChannelChildren(final Integer parentId,
			final Integer siteId) {
		Object res = this.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				if (parentId == null)
					return null;

				String hql = "From Channel o Where o.parent.id=? and o.site.id=? Order By o.id";
				Query query = em.createQuery(hql);
				query.setParameter(1, parentId);
				query.setParameter(2, siteId);
				return query.getResultList();

			}
		});
		return (List<Channel>) res;
	}
	/**
	 * 获取站点首页专栏
	 * 
	 */	
	public Channel getChannelRoot(final Integer siteId){
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Object res = this.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String hql = "From Channel o Where o.parent is null and o.site.id=? Order By o.id";
				Query query = em.createQuery(hql);
				query.setParameter(1, siteId);
				return query.getResultList();

			}
		});	
		List<Channel> channelList = (List<Channel>) res;
		if(channelList==null||channelList.size()==0)return null;
		return channelList.get(0);		
	}	
}
