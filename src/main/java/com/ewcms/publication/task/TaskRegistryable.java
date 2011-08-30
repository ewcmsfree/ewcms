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
     * @param id   任务编号
     * @param task 任务
     */
    void registerNewTask(Object id,Taskable task);
    
    /**
     * 判断任务是否存在
     * 
     * @param id 任务编号
     * @return true 任务已经存在
     */
    boolean alreadyExistTask(Object id);
    
    /**
     * 得到任务
     * 
     * @param id 任务编号
     * @return
     */
    Taskable getTask(Object id);
    
    /**
     * 移除任务
     * 
     * @param id 任务编号
     */
    void removeTask(Object id);
    
    /**
     * 得到所有任务列表
     * 
     * @return
     */
    List<Taskable> getAllTasks();
    
    /**
     * 得到所属站点任务列表
     * 
     * @param siteId 站点编号
     * @return
     */
    List<Taskable> getOwnerSiteTasks(Integer siteId);
}
