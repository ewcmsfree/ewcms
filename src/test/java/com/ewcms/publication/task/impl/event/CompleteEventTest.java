/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl.event;

import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.junit.Test;

import com.ewcms.publication.task.TaskException;

/**
 * CompleteEvent单元测试
 * 
 * @author wangwei
 */
public class CompleteEventTest {
    
    @Test
    public void testSuccess(){
        AtomicInteger completeNumber = new AtomicInteger(0);
        CompleteEvent event = new CompleteEvent(completeNumber);
        event.success("");
        Assert.assertEquals(1, completeNumber.get());
    }
    
    @Test
    public void error(){
        AtomicInteger completeNumber = new AtomicInteger(0);
        CompleteEvent event = new CompleteEvent(completeNumber);
        event.error(new TaskException("test exception"));
        Assert.assertEquals(1, completeNumber.get());
    }
}
