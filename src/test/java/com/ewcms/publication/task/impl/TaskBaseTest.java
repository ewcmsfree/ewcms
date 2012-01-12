/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

/**
 * TaskBase单元测试
 * 
 * @author wangwei
 */
public class TaskBaseTest {

    @Test
    public void testNewTaskId(){
        String id = TaskBaseImpl.newTaskId();
        Assert.assertNotNull(id);
    }
    
    @Test
    public void testNewTask()throws TaskException{
        String id = TaskBaseImpl.newTaskId();
        List<TaskProcessable> processes= new ArrayList<TaskProcessable>();
        Taskable task = new TaskBaseImpl(id,processes);
        
        Assert.assertFalse(task.isRunning());
        Assert.assertFalse(task.isCompleted());
        Assert.assertEquals(0, task.getProgress());
    }
    
    @Test
    public void testZeroToTaskProcess()throws TaskException{
        String id = TaskBaseImpl.newTaskId();
        List<TaskProcessable> processes= new ArrayList<TaskProcessable>();
        Taskable task = new TaskBaseImpl(id,processes);
        
        task.toTaskProcess();
        
        Assert.assertTrue(task.isRunning());
        Assert.assertTrue(task.isCompleted());
        Assert.assertEquals(100, task.getProgress());
    }
    
    @Test
    public void testToTaskProcess()throws TaskException{
        
        String id = TaskBaseImpl.newTaskId();
        List<TaskProcessable> processes= new ArrayList<TaskProcessable>();
        processes.add(mock(TaskProcessable.class));
        processes.add(mock(TaskProcessable.class));
        Taskable task = new TaskBaseImpl(id,processes);
        task.toTaskProcess();
        
        Assert.assertTrue(task.isRunning());
        Assert.assertFalse(task.isCompleted());
        Assert.assertEquals(0, task.getProgress());
    }
    
    private class TaskBaseImpl extends TaskBase{
        private List<TaskProcessable> processes;
        
        public TaskBaseImpl(String id,List<TaskProcessable> processes) {
            super(id);
            this.processes = processes;
        }

        @Override
        public String getDescription() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getUsername() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<Taskable> getDependences() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected List<TaskProcessable> getTaskProcesses() throws TaskException {
            return processes;
        }
    }
}
