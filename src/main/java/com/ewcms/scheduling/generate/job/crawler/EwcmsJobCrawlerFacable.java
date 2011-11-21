/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.crawler;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.job.crawler.model.EwcmsJobCrawler;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;

/**
 * 采集器定时任务Fac接口
 * 
 * @author 吴智俊
 */
public interface EwcmsJobCrawlerFacable {
	/**
	 * 通过任务编号查询采集器定时任务对象
	 * 
	 * @param jobId 任务编号
	 * @return 采集器定时任务对象
	 */
	public EwcmsJobCrawler getScheduledJobCrawler(Integer jobId);
	
	/**
	 * 新增或修改采集器定时任务
	 * 
	 * @param gatherId 采集器编号
	 * @param vo 页面设置的对象值
	 * @return 采集器定时任务编号
	 * @throws BaseException
	 */
	public Integer saveOrUpdateJobCrawler(Long gatherId, PageDisplayVO vo) throws BaseException;
	
	/**
	 * 通过采集器编号查询定时任务对象
	 * 
	 * @param gatherId 采集器编号
	 * @return 采集器定时任务对象
	 */
	public EwcmsJobCrawler findJobCrawlerByGatherId(Long gatherId);
}
