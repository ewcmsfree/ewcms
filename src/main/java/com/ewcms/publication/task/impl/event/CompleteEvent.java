/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl.event;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.Assert;

/**
 * 实现完成事件
 * 
 * @author wangwei
 */
public class CompleteEvent implements TaskEventable{

    protected final AtomicInteger completeNumber;
    
    public CompleteEvent(AtomicInteger completeNumber){
        Assert.notNull(completeNumber,"Complete number is null");
        this.completeNumber = completeNumber;
    }
    
    @Override
    public void success(String uri) {
        completeNumber.incrementAndGet();
        successAfter(uri);
    }

    /**
     * 成功后处理过程
     */
    protected void successAfter(String uri){
        
    }
    
    @Override
    public void error(Throwable e) {
        completeNumber.incrementAndGet();
        errorAfter(e);
    }
    
    /**
     * 错误后处理过程
     * 
     * @param e 错误对象
     */
    protected void errorAfter(Throwable e){
        
    }
}
