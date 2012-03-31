/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.crawler.dao;


import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.scheduling.generate.job.crawler.model.EwcmsJobCrawler;

/**
 * 采集器定时任务DAO
 * 
 * @author 吴智俊
 */
@Repository()
public class EwcmsJobCrawlerDAO extends JpaDAO<Long, EwcmsJobCrawler> {
	
	public EwcmsJobCrawler findJobCrawlerByGatherId(Long gatherId) {
		String hql = "Select o From EwcmsJobCrawler o Inner Join o.gather c Where c.id=:gatherId";
		
		TypedQuery<EwcmsJobCrawler> query = this.getEntityManager().createQuery(hql, EwcmsJobCrawler.class);
    	query.setParameter("gatherId", gatherId);

    	EwcmsJobCrawler ewcmsJobCrawler = null;
    	try{
    		ewcmsJobCrawler = (EwcmsJobCrawler) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	return ewcmsJobCrawler;
	}
}
