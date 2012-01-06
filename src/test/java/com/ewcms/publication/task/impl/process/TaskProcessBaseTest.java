/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl.process;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ewcms.publication.PublishException;
import com.ewcms.publication.deploy.DeployOperatorable;
import com.ewcms.publication.task.impl.event.TaskEventable;

/**
 * TaskProcess单元测试
 * 
 * @author wangwei
 */
public class TaskProcessBaseTest {

    private String path = "/root/test.txt";
    private String uri = "/resource/test.txt";
    
    @Test
    public void testExecuteIsSuccess()throws PublishException{
        TaskEventable event = mock(TaskEventable.class);
        DeployOperatorable operator = mock(DeployOperatorable.class);
        TaskProcessBaseImpl process = new TaskProcessBaseImpl();
        process.registerEvent(event);
        
        process.execute(operator);
        
        verify(event,times(1)).success(uri);
        verify(event,never()).error(any(PublishException.class));
        verify(operator,times(1)).copy(path, uri);
    }
    
    @Test
    public void testExecuteIsError()throws PublishException{
        TaskEventable event = mock(TaskEventable.class);
        DeployOperatorable operator = mock(DeployOperatorable.class);
        TaskProcessBaseImpl process = new TaskProcessBaseImpl();
        process.registerEvent(event);
        operator.copy(any(String.class), any(String.class));
        doThrow(new PublishException()).when(operator).copy(path, uri);
        process.execute(operator);
        
        verify(event,times(1)).error(any(PublishException.class));
        verify(event,never()).success(uri);
        verify(operator,times(1)).copy(path, uri);
    }
    
    private class TaskProcessBaseImpl extends TaskProcessBase{
        @Override
        protected String process(DeployOperatorable operator) throws PublishException {
            operator.copy(path, uri);
            return uri;
        }
    }
}
