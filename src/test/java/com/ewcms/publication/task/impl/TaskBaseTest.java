/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.ewcms.core.site.model.Site;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

/**
 * TaskBase单元测试
 * 
 * @author wangwei
 */
public class TaskBaseTest {  

    @Test
    public void testGetProgressCountIsZero(){
        Taskable task = new TaskBaseImpl.Builder(new Site(),0,0,new ArrayList<Taskable>(0)).build();
        Assert.assertEquals(-1, task.getProgress());
    }
    
    @Test
   public void testGetProgress(){
       List<Taskable> children = new ArrayList<Taskable>();
       Taskable child =new TaskBaseImpl.Builder(new Site(),10,5,new ArrayList<Taskable>(0)).build();
       children.add(child);
       child =new TaskBaseImpl.Builder(new Site(),10,2,new ArrayList<Taskable>(0)).build();
       children.add(child);
       child =new TaskBaseImpl.Builder(new Site(),0,0,new ArrayList<Taskable>(0)).build();
       children.add(child);
       Taskable task = new TaskBaseImpl.Builder(new Site(),0,0,children).build();
       
       Assert.assertEquals(35, task.getProgress());
   }

    @Test
    public void testIsComplate(){
        Taskable task = new TaskBaseImpl.Builder(new Site(),0,0,new ArrayList<Taskable>(0)).build();
        Assert.assertTrue(task.isCompleted());
        
        task = new TaskBaseImpl.Builder(new Site(),2,0,new ArrayList<Taskable>(0)).build();
        Assert.assertFalse(task.isCompleted());
        
        task = new TaskBaseImpl.Builder(new Site(),2,2,new ArrayList<Taskable>(0)).build();
        Assert.assertTrue(task.isCompleted());
    }
    
    private static class TaskBaseImpl extends TaskBase{
        
        private static  class Builder extends BaseBuilder<Builder>{
            
            private final int initCount;
            private final int initComplate;
            private final List<Taskable>  initDependenceTasks;
            public Builder(Site site,int count,int complate,List<Taskable> dependenceTasks) {
                super(site);
                this.initCount = count;
                this.initComplate = complate;
                this.initDependenceTasks = dependenceTasks;
            }

            @Override
            protected String getDescription() {
                return "";
            }

            @Override
            protected List<Taskable> getDependenceTasks() {
                return dependenceTasks;
            }

            @Override
            protected List<TaskProcessable> getTaskProcesses() {
                return new ArrayList<TaskProcessable>(0);
            }
            
            public Taskable build(){
                this.count.set(initCount);
                this.complete.set(initComplate);
                this.dependenceTasks = initDependenceTasks;
                return new TaskBaseImpl("1234",this);
            }
        }
        
        public TaskBaseImpl(String id, Builder builder) {
            super(id, builder);
        }
    }
}
