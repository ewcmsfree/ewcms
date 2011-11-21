/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.crawler.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.scheduling.generate.job.crawler.model.EwcmsJobCrawler;

/**
 * 采集器定时任务DAO
 * 
 * @author 吴智俊
 */
@Repository()
public class EwcmsJobCrawlerDAO extends JpaDAO<Integer, EwcmsJobCrawler> {
	@SuppressWarnings("unchecked")
	public EwcmsJobCrawler findJobCrawlerByGatherId(Long gatherId) {
		String hql = "Select o From EwcmsJobCrawler o Inner Join o.gather c Where c.id=?";
		List<EwcmsJobCrawler> list = this.getJpaTemplate().find(hql, gatherId);
		if (list.isEmpty())	return null;
		return list.get(0);
	}
}
