/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manage.fac;

import java.util.List;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.common.ValidationErrorsable;
import com.ewcms.scheduling.model.AlqcJob;
import com.ewcms.scheduling.model.AlqcJobClass;

/**
 * @author 吴智俊
 */
public interface AlqcSchedulingFacable {

	/**
	 * 新增调度工作
	 * 
	 * @param job
	 * @return
	 * @throws BaseException
	 */
	public Integer saveScheduleJob(AlqcJob alqcJob) throws BaseException;
	
	/**
	 * 修改调度工作
	 * 
	 * @param alqcJobReport
	 * @throws BaseException
	 */
	public Integer updateScheduledJob(AlqcJob alqcJob) throws BaseException;
	
	/**
	 * 查询所有调度工作
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<AlqcJob> getScheduledJobs() throws BaseException;

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
	public AlqcJob getScheduledJob(Integer jobId) throws BaseException;

	/**
	 * 校验调度工作
	 * 
	 * @param alqcJobReport
	 * @return
	 * @throws BaseException
	 */
	public ValidationErrorsable validateJob(AlqcJob alqcJob) throws BaseException;
	
    public Integer saveJobClass(AlqcJobClass alqcJobClass) throws BaseException;

    public Integer updateJobClass(AlqcJobClass alqcJobClass) throws BaseException;

    public AlqcJobClass findByJobClass(Integer id) throws BaseException;

    public List<AlqcJobClass> findByAllJobClass() throws BaseException;
    
    public void deletedJobClass(Integer id) throws BaseException;

	public AlqcJobClass findByAlqcJobClassByClassEntity(String classEntity) throws BaseException;
	
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
