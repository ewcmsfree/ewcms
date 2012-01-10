/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task;

import java.util.List;


/**
 * 站点任务运行接口
 * 
 * @author wangwei
 */
public interface SiteTaskRunnerable extends Runnable {

    /**
     * 得到任务列表
     * <br>
     * 只返回任务信息列表
     * 
     * @return
     */
    List<Taskable> getTasks();
    
    /**
     * 添加任务
     * 
     * @param task
     */
    void add(Taskable task);
    
    /**
     * 移除任务
     * 
     * @param task
     */
    boolean remov(Taskable task);
    
    /**
     * 得到任务
     * 
     * @param id 任务编号
     * @return
     */
    Taskable get(String id);
    
    /**
     * 任务是否存在
     * 
     * @param task 任务
     * @return true:存在
     */
    boolean contains(Taskable task);
    
    /**
     * 关闭任务
     */
    void close();
}
