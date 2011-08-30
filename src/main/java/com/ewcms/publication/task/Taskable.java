/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task;

import java.util.List;

/**
 * 任务接口
 * 
 * @author wangwei
 */
public interface Taskable {

    /**
     * 任务编号
     * 
     * @return
     */
    Object getId();
    
    /**
     * 判断是否属于站点
     * 
     * @param siteId 站点编号
     * @return true 属于站点
     */
    Boolean isOwnerSite(Integer siteId);
    
    /**
     * 任务名称
     * 
     * @return
     */
    String getTaskName();
    
    /**
     * 完成进度
     * 
     * @return
     */
    Integer complete();
    
    /**
     * 所属子任务
     * 
     * @return
     */
    List<Taskable> getChildren();
    
    /**
     * 添加子任务
     * 
     * @param task
     */
    void addChild(Taskable task);
}
