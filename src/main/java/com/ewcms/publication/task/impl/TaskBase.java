/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.Assert;

import com.ewcms.core.site.model.Site;
import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

/**
 * 实现发布任务
 * 
 * @author wangwei
 */
public class TaskBase implements Taskable{
    
    public abstract static class BaseBuilder<T extends BaseBuilder<?>>{
        protected static final Random random = new Random();
        
        protected final AtomicInteger complete = new AtomicInteger(0);
        protected final AtomicInteger count = new AtomicInteger(-1);
        
        protected final Site site;
        protected String username = DEFAULT_USERNAME;
        protected boolean again = false;
        protected boolean dependence = false;
        protected String description;
        protected List<Taskable> dependenceTasks;
        protected List<TaskProcessable> processes;
        
        public BaseBuilder(Site site){
            Assert.notNull(site,"Site is null");
            this.site = site;
        }
        
        @SuppressWarnings("unchecked")
        public T setUsername(String username){
            this.username = username;
            return (T)this;
        }
        
        @SuppressWarnings("unchecked")
        public T setAgain(boolean again){
            this.again = again;
            return (T)this;
        }
        
        @SuppressWarnings("unchecked")
        public T setDependence(boolean dependence){
            this.dependence = dependence;
            return (T)this;
        }
        
        private String newTaskId(){
            return String.valueOf(Math.abs(random.nextLong()));
        }
        
        public Taskable build(){
            description = getDescription();
            dependenceTasks = getDependenceTasks();
            processes = getTaskProcesses();
            count.set(processes.size());
            return new TaskBase(newTaskId(),this);
        }
        
        protected abstract String getDescription();
        
        protected abstract List<Taskable> getDependenceTasks();
        
        protected abstract List<TaskProcessable> getTaskProcesses();
    }
    
    protected final String id;
    protected final BaseBuilder<?> builder;
    
    public TaskBase(String id,BaseBuilder<?> builder){
        this.id = id;
        this.builder = builder;
    }
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getProgress() {
        List<Taskable> tasks = builder.dependenceTasks;
        int totalProgress = 0;
        int taskCount = 0;
        for(Taskable task : tasks){
            int progress = task.getProgress();
            if(progress != -1){
                totalProgress = totalProgress + progress;
                ++taskCount;    
            }
        }
        if(builder.count.get() != 0){
            int progress =  ((builder.complete.get() * 100) / builder.count.get());
            totalProgress = totalProgress + progress;
            ++taskCount; 
        }
          
        return taskCount == 0 ? -1 : (totalProgress/taskCount);
    }

    @Override
    public boolean isCompleted() {
        return builder.count.get() == builder.complete.get();
    }
    
    @Override
    public String getDescription() {
        return builder.description;
    }

    @Override
    public String getUsername() {
        return builder.username;
    }

    @Override
    public List<Taskable> getDependenceTasks() {
        return Collections.unmodifiableList(builder.dependenceTasks);
    }
    
    @Override
    public List<TaskProcessable> toTaskProcess() throws TaskException {
       return Collections.unmodifiableList(builder.processes);
    }
}
