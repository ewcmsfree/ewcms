/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl.event;

/**
 * 发布事件接口
 * 
 * @author wangwei
 */
public interface TaskEventable {

    /**
     * 发布事件
     * 
     * @param uri 发布地址
     */
    public void success(String uri);
    
    /**
     * 错误事件
     * 
     * @param e 异常对象
     */
    public void error(Throwable e);
}
