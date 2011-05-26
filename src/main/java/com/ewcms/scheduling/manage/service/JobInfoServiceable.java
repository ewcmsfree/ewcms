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
 * 定时器任务信息Service
 * 
 * @author 吴智俊
 */
public interface JobInfoServiceable {
	/**
	 * 新增任务信息
	 * 
	 * @param jobInfo 任务信息
	 * @return JobInfo
	 * @throws BaseException
	 */
	public JobInfo saveJobInfo(JobInfo jobInfo) ;
	
	/**
	 * 修改任务信息
	 * 
	 * @param jobInfo 任务信息
	 * @return JobInfo
	 * @throws BaseException
	 */
	public JobInfo updateJobInfo(JobInfo jobInfo) ;
	
	/**
	 * 查询任务信息
	 * 
	 * @param jobInfoId 任务信息编号
	 * @return JobInfo
	 * @throws BaseException
	 */
	public JobInfo findJobInfoById(Integer jobInfoId) ;
	
	/**
	 * 查询所有任务信息
	 * 
	 * @return List
	 */
	public List<JobInfo> findJobInfoAll();
	
	/**
	 * 删除任务信息
	 * 
	 * @param jobInfoId 任务信息编号
	 * @throws BaseException
	 */
	public void deletedJobInfo(Integer jobInfoId);
}
