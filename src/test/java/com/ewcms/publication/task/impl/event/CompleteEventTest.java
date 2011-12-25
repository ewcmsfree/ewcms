/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl.event;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;

/**
 * CompleteEvent单元测试
 * 
 * @author wangwei
 */
public class CompleteEventTest {
    
    @Test
    public void testSuccess(){
        Taskable task = mock(Taskable.class);
        CompleteEvent event = new CompleteEvent(task);
        event.success("");
        verify(task,times(1)).completeProcess();
    }
    
    @Test
    public void error(){
        Taskable task = mock(Taskable.class);
        CompleteEvent event = new CompleteEvent(task);
        event.error(new TaskException("test exception"));
        verify(task,times(1)).completeProcess();
    }
}
