/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manage.fac;

import java.util.List;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.common.ValidationErrorsable;
import com.ewcms.scheduling.model.JobInfo;
import com.ewcms.scheduling.model.JobClass;

/**
 * 定时任务管理Fac
 * 
 * @author 吴智俊
 */
public interface SchedulingFacable {

	/**
	 * 新增任务信息
	 * 
	 * @param jobInfo 任务信息
	 * @return Integer
	 * @throws BaseException
	 */
	public Integer saveScheduleJob(JobInfo jobInfo) throws BaseException;
	
	/**
	 * 修改任务信息
	 * 
	 * @param jobInfo 任务信息
	 * @throws BaseException
	 */
	public Integer updateScheduledJob(JobInfo jobInfo) throws BaseException;
	
	/**
	 * 查询所有任务信息
	 * 
	 * @return List
	 * @throws BaseException
	 */
	public List<JobInfo> getScheduledJobs() throws BaseException;

	/**
	 * 删除任务信息
	 * 
	 * @param jobInfoId 任务信息编号
	 * @throws BaseException
	 */
	public void deletedScheduledJob(Integer jobInfoId) throws BaseException;

	/**
	 * 查询任务信息
	 * 
	 * @param jobInfoId 任务信息编号
	 * @return JobInfo
	 * @throws BaseException
	 */
	public JobInfo getScheduledJob(Integer jobInfoId) throws BaseException;

	/**
	 * 校验任务信息
	 * 
	 * @param jobInfo 任务信息
	 * @return ValidationErrorsable
	 * @throws BaseException
	 */
	public ValidationErrorsable validateJob(JobInfo jobInfo) throws BaseException;
	
	/**
	 * 暂停任务信息
	 * 
	 * @param jobInfoId 任务信息编号
	 * @throws BaseException
	 */
	public void pauseJob(Integer jobInfoId) throws BaseException;
	
    /**
     * 从暂停恢复任务
     * @param jobInfoId 任务信息编号
     * @throws BaseExcepiton
     */
    public void resumedJob(Integer jobInfoId) throws BaseException;

    /**
	 * 新增执行类
	 * 
	 * @param jobClass 执行类
	 * @return Integer
	 * @throws BaseException
	 */
    public Integer saveJobClass(JobClass jobClass) throws BaseException;

    /**
     * 修改执行类
     * 
     * @param jobClass 执行类
     * @return Integer
     * @throws BaseException
     */
    public Integer updateJobClass(JobClass jobClass) throws BaseException;

    /**
     * 根据jobClassId条件查询执行类
     * 
     * @param jobClassId 执行类编号
     * @return JobClass
     * @throws BaseException
     */
    public JobClass findJobClassById(Integer jobClassId) throws BaseException;

    /**
     * 查询所有执行类
     * 
     * @return List
     * @throws BaseException
     */
    public List<JobClass> findJobClassAll() throws BaseException;

    /**
     * 删除执行类
     * 
     * @param jobClassId 执行类编号
     * @throws BaseException
     */
    public void deletedJobClass(Integer jobClassId) throws BaseException;

    /**
     * 根据classEntity条件查询执行类
     * 
     * @param classEntity 执行类实体名
     * @return JobClass
     * @throws BaseException
     */
    public JobClass findJobClassByClassEntity(String classEntity) throws BaseException;
}
