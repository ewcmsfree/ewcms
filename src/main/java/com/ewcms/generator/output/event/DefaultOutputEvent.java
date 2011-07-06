/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output.event;

import com.ewcms.generator.ReleaseException;

/**
 * 空事件
 * 
 * @author wangwei
 */
public class DefaultOutputEvent implements OutputEventable{

    @Override
    public void success() throws ReleaseException {
        
    }

    @Override
    public void error() throws ReleaseException {
        //TODO 写错误日志
    }
}
