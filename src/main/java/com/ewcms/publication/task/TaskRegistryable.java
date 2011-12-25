/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task;

import java.util.List;

/**
 * 注册任务
 * 
 * @author wangwei
 */
public interface TaskRegistryable {
    
    /**
     * 注册新的任务
     * 
     * @param task 任务
     */
    void registerNewTask(Taskable task);
    
    /**
     * 移除任务
     * 
     * @param task 移除任务
     */
    void removeTask(Taskable task);
    
    /**
     * 得到等待处理的站点
     * </p>
     * 站点所属的任务没有在运行
     * 
     * @return
     */
    Integer getWaitSite();
    
    /**
     * 得到站点任务
     * 
     * @param siteId 站点编号
     * @return 任务
     */
    Taskable getTaskOfSite(Integer siteId);
    
    /**
     * 获得站点所以任务
     * 
     * @param siteId 站点编号
     * @return
     */
    List<Taskable> getSiteTasks(Integer siteId);
}
