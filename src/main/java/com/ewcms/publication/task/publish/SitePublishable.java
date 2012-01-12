/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.publish;

import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;

/**
 * 站点任务发布接口
 * 
 * @author wangwei
 */
public interface SitePublishable {

    /**
     * 发布指定的任务（如：模版资源、资源、频道等）
     * 
     * @param task 任务对象
     * @throws TaskException
     */
    public void publish(Taskable task) throws TaskException;
    
    /**
     * 关闭当前的发布过程
     */
    public void cancelPublish();
}
