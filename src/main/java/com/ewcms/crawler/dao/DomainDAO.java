/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.crawler.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.crawler.model.Domain;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class DomainDAO extends JpaDAO<Long, Domain> {
	@SuppressWarnings("unchecked")
	public Domain findDomainById(Long domainId){
		String hql = "From Domain As d Where d.id=?";
		List<Domain> list = this.getJpaTemplate().find(hql, domainId);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Long findMaxDomainByGatherId(Long gatherId){
		String hql = "Select Max(u.level) From Gather As g Left Join g.domains As u Where g.id=?";
    	List<Long> list = this.getJpaTemplate().find(hql, gatherId);
    	if (list.isEmpty()) return 0L;
    	return list.get(0) == null ? 0L : list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Boolean findDomainUniqueUrlByGatherId(Long gatherId, String url){
		String hql = "Select u From Gather As g Right Join g.domains As u Where g.id=? And u.url=?";
		List<Domain> list = this.getJpaTemplate().find(hql, gatherId, url);
		if (list.isEmpty()) return true;
		return false;
	}
}
