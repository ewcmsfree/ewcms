/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.manager.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.crawler.model.Domain;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class DomainDAO extends JpaDAO<Long, Domain> {
	
	public Domain findDomainById(final Long domainId){
		String hql = "From Domain As d Where d.id=:domainId";
		
		TypedQuery<Domain> query = this.getEntityManager().createQuery(hql, Domain.class);
		query.setParameter("domainId", domainId);
		
		Domain domain = null;
		try{
			domain = (Domain)query.getSingleResult();
		}catch(NoResultException e){
		}
		return domain;
	}
	
	public Long findMaxDomainByGatherId(final Long gatherId){
		String hql = "Select Max(u.level) From Gather As g Left Join g.domains As u Where g.id=:gatherId";
		
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("gatherId", gatherId);
		
		Long maxLevel = 0L;
		try{
			maxLevel = (Long) query.getSingleResult();
		}catch(NoResultException e){
		}
		return maxLevel;
	}
	
	public Boolean findDomainUniqueUrlByGatherId(final Long gatherId, final String url){
		String hql = "Select u From Gather As g Right Join g.domains As u Where g.id=:gatherId And u.url=:url";
		
		TypedQuery<Domain> query = this.getEntityManager().createQuery(hql, Domain.class);
		query.setParameter("gatherId", gatherId);
		query.setParameter("url", url);
		
		List<Domain> list = query.getResultList();
		return list.isEmpty() ? false : true;
	}
}
