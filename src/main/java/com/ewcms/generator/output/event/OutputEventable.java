/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output.event;

import com.ewcms.generator.ReleaseException;

/**
 * 发布事件接口
 * 
 * @author wangwei
 */
public interface OutputEventable {

    /**
     * 发布成功事件
     * 
     * @throws ReleaseException
     */
    public void success()throws ReleaseException;
    
    /**
     * 发布失败实际
     * 
     * @throws ReleaseException
     */
    public void error()throws ReleaseException;
}
