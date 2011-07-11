/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output.event;

/**
 * 缺省事件
 * 
 * @author wangwei
 */
public class DefaultOutputEvent implements OutputEventable{

    @Override
    public void success() {
        
    }

    @Override
    public void error(String message,Throwable e){
        //TODO 写错误日志
    }
}
