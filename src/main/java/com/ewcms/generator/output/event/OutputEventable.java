/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output.event;

/**
 * 发布事件接口
 * 
 * @author wangwei
 */
public interface OutputEventable {

    /**
     * 发布事件
     */
    public void success();
    
    /**
     * 错误事件
     * 
     * @param message 错误信息
     * @param e 异常
     */
    public void error(String message,Throwable e);
}
