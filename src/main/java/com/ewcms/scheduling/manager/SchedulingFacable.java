/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manager;

import java.util.List;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.common.ValidationErrorsable;
import com.ewcms.scheduling.model.JobClass;
import com.ewcms.scheduling.model.JobInfo;

/**
 * @author 吴智俊
 */
public interface SchedulingFacable {

	/**
	 * 新增调度工作
	 * 
	 * @param jobInfo
	 * @return
	 * @throws BaseException
	 */
	public Integer saveScheduleJob(JobInfo jobInfo) throws BaseException;
	
	/**
	 * 修改调度工作
	 * 
	 * @param jobInfo
	 * @throws BaseException
	 */
	public Integer updateScheduledJob(JobInfo jobInfo) throws BaseException;
	
	/**
	 * 查询所有调度工作
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<JobInfo> getScheduledJobs() throws BaseException;

	/**
	 * 删除调度工作
	 * 
	 * @param jobId
	 * @throws BaseException
	 */
	public void deletedScheduledJob(Integer jobId) throws BaseException;

	/**
	 * 查询调度工作
	 * 
	 * @param jobId
	 * @return
	 * @throws BaseException
	 */
	public JobInfo getScheduledJob(Integer jobId) throws BaseException;

	/**
	 * 校验调度工作
	 * 
	 * @param jobInfo
	 * @return
	 * @throws BaseException
	 */
	public ValidationErrorsable validateJob(JobInfo jobInfo) throws BaseException;
	
    public Integer saveJobClass(JobClass jobClass) throws BaseException;

    public Integer updateJobClass(JobClass jobClass) throws BaseException;

    public JobClass findByJobClass(Integer id) throws BaseException;

    public List<JobClass> findByAllJobClass() throws BaseException;
    
    public void deletedJobClass(Integer id) throws BaseException;

	public JobClass findByJobClassByClassEntity(String classEntity) throws BaseException;
	
	/**
	 * 暂停调度工作
	 * 
	 * @param id 调度任务编号
	 * @throws BaseException
	 */
	public void pauseJob(Integer id) throws BaseException;
	
    /**
     * 从暂停恢复调度任务
     * @param id 调度任务编号
     * @throws BaseExcepiton
     */
    public void resumedJob(Integer id) throws BaseException;
}
