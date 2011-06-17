/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manage.service;

import java.util.List;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.model.JobInfo;

/**
 * @author 吴智俊
 */
public interface JobInfoServiceable {
	/**
	 * 新增调度工作
	 * 
	 * @param jobInfo 
	 * @throws BaseException
	 */
	public JobInfo saveJob(JobInfo jobInfo) ;
	
	/**
	 * 修改调度工作
	 * 
	 * @param jobInfo
	 * @throws BaseException
	 */
	public JobInfo updateJob(JobInfo jobInfo) ;
	
	/**
	 * 查询调度工作
	 * 
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public JobInfo findByJob(Integer id) ;
	
	/**
	 * 查询所有的调度
	 * 
	 * @param context
	 * @return
	 */
	public List<JobInfo> findByAllJob();
	
	/**
	 * 删除调度工作
	 * 
	 * @param id
	 * @throws BaseException
	 */
	public void deletedJob(Integer id);
}
