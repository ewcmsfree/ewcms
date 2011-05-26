/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.quartz;

import java.util.List;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.common.ValidationErrorsable;
import com.ewcms.scheduling.model.AlqcJob;

/**
 * 
 * @author 吴智俊
 */
public interface AlqcJobsQuartzSchedulerable {

    /**
     * 新建调度任务
     *
     * @param job 调度器任务
     * @throws BaseException
     */
    public void scheduleJob(AlqcJob job) throws BaseException;

    /**
     * 修改调度任务
     *
     * @param job 调度器任务
     * @throws BaseException
     */
    public void rescheduleJob(AlqcJob job) throws BaseException;

    /**
     * 删除调度任务
     *
     * @param jobId 调度任务编号
     * @throws BaseException
     */
    public void removeScheduledJob(Integer jobId) throws BaseException;
    
    /**
     * 暂停调度任务
     * 
     * @param jobId 调度任务编号
     * @throws BaseException
     */
    public void pauseJob(Integer jobId) throws BaseException;
    
    /**
     * 从暂停恢复调度任务
     * @param jobId 调度任务编号
     * @throws BaseExcepiton
     */
    public void resumedJob(Integer jobId) throws BaseException;

    /**
     * 新增调度器监听接口
     *
     * @param listener 监听接口
     * @throws BaseException
     */
    public void addAlqcSchedulerListener(AlqcSchedulerListenerable listener) throws BaseException;

    /**
     * 删除调度器监听接口
     *
     * @param listener 监听接口
     * @throws BaseException
     */
    public void removeAlqcSchedulerListener(AlqcSchedulerListenerable listener) throws BaseException;

    /**
     * 查询调度器任务状态
     *
     * @param alqcJobs 调度器任务集合
     * @return List
     * @throws BaseException
     */
    public List<AlqcJob> getJobsRuntimeInformation(List<AlqcJob> alqcJobs) throws BaseException;

    /**
     * 校验调度器任务
     *
     * @param job 调度器任务
     * @param errors 错误信息
     * @throws BaseException
     */
    public void validate(AlqcJob job, ValidationErrorsable errors) throws BaseException;
}
