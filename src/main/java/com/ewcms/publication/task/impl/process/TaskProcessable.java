/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl.process;

import com.ewcms.publication.output.DeployOperatorable;
import com.ewcms.publication.task.impl.event.TaskEventable;

/**
 * 单个任务处理过程
 * 
 * @author wangwei
 */
public interface TaskProcessable {
    
    /**
     * 执行任务
     * 
     * @param operator 发布操作对象
     */
    public Boolean execute(DeployOperatorable operator);
    
    /**
     * 设置任务事件
     * 
     * @param event 事件对象
     */
    public void registerEvent(TaskEventable event);
}
    