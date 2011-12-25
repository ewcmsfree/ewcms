/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl.event;

/**
 * 空事件
 * 
 * @author wangwei
 */
public class NoneEvent implements TaskEventable{

    @Override
    public void success(String uri) {
        //do not instance
    }

    @Override
    public void error(Throwable e){
        //do not instance
    }
}
