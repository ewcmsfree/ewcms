/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.crawler.service;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.job.crawler.model.EwcmsJobCrawler;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;

/**
 * 采集器定时任务Service接口
 * 
 * @author 吴智俊
 */
public interface EwcmsJobCrawlerServiceable {
	
	/**
	 * 新增和修改采集器定时工作任务
	 * 
	 * @param gatherId 采集器编号
	 * @param vo 定时设置对象
	 * @return 定时工作任务编号
	 */
	public Integer saveOrUpdateJobCrawler(Long gatherId, PageDisplayVO vo) throws BaseException;
	
	/**
	 * 通过任务编号查询采集器定时任务对象
	 * 
	 * @param jobId 任务编号
	 * @return 采集器定时任务对象
	 */
	public EwcmsJobCrawler getScheduledJobCrawler(Integer jobId);
	
	/**
	 * 通过采集器编号查询定时任务对象
	 * 
	 * @param gatherId 采集器编号
	 * @return 采集器定时任务对象
	 */
	public EwcmsJobCrawler findJobCrawlerByGatherId(Long gatherId);
}
