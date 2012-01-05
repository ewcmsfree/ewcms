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
    
    public static final String MANAGER_USERNAME = "ewcms_system_manager";
    
    /**
     * 注册新的任务
     * 
     * @param task 任务
     */
    void registerNewTask(Taskable task);
    
    /**
     * 移除任务
     * <br/>
     * 
     * @param siteId 站点编号
     * @param id 任务编号
     * @param username  用户名
     */
    void removeTask(Integer siteId,String id,String username)throws TaskException;
    
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
