/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ewcms.publication.task.impl.process.TaskProcessable;


/**
 * 内存任务注册
 * <br>
 * 任务以Map形式保存在内存中。
 * 
 * @author wangwei
 */
public class MemoryTaskRegistry implements TaskRegistryable{

    private static final Logger logger = LoggerFactory.getLogger(MemoryTaskRegistry.class);
    
    private final Map<Integer, SiteTasks> taskPool =Collections.synchronizedMap(new LinkedHashMap<Integer,SiteTasks>());
    
    @Override
    public void registerNewTask(Taskable task) {
        Assert.notNull(task,"task is null");
        synchronized(taskPool){
            final Integer siteId = task.getSiteId();
            SiteTasks siteTasks = taskPool.get(siteId);
            if(siteTasks == null){
                logger.debug("create site tasks,site id is {}",siteId);
                siteTasks = new SiteTasks(siteId);
                taskPool.put(siteId, siteTasks);
            }
            siteTasks.addTask(task);
        }
    }

    @Override
    public void removeTask(Taskable task) {
        Assert.notNull(task,"task is null");
        synchronized(taskPool){
            final Integer siteId = task.getSiteId();
            SiteTasks siteTasks = taskPool.get(siteId);
            if(siteTasks == null){
                logger.debug("Site's task object is not exist,site id is {}",siteId);
                return ;
            }
            siteTasks.removeTask(task);
        }
    }


    @Override
    public Integer getWaitSite() {
        synchronized(taskPool){
            Iterator<Integer> iterator = taskPool.keySet().iterator();
            for(;iterator.hasNext();){
                Integer siteId = iterator.next();
                SiteTasks siteTasks = taskPool.get(siteId);
                logger.debug("Site's id is {},task running state is {}",siteId,siteTasks.isRunning());
                if(!siteTasks.isRunning()){
                    return siteId;
                }
            }
        }
        return null;
    }


    @Override
    public Taskable getTaskOfSite(Integer siteId) {
        Assert.notNull(siteId,"site id is null");
        synchronized(taskPool){
            SiteTasks siteTask = taskPool.get(siteId);
            if(siteTask == null){
                return null;
            }
            return siteTask.getWaitTask();
        }
    }

    @Override
    public List<Taskable> getSiteTasks(Integer siteId) {
        
        List<Taskable> tasks = new ArrayList<Taskable>();
        
        SiteTasks siteTasks;
        synchronized(taskPool){
            siteTasks = taskPool.get(siteId);
        }
        if(siteTasks == null){
            logger.debug("Site tasks is not exist,site id is {}",siteId);
            return tasks;
        }
        for(Taskable task : siteTasks.tasks){
            tasks.add(new TaskInfoClone(task));
        }
        return tasks;
    }
    
    protected boolean containsTask(Taskable task){
        synchronized(taskPool){
            Integer siteId = task.getSiteId();
            SiteTasks siteTasks = taskPool.get(siteId);
            if(siteTasks == null){
                return false;
            }
            return siteTasks.containsTask(task);
        }
    }
    
    class SiteTasks{
        private final int id;
        private final List<Taskable> tasks = Collections.synchronizedList(new ArrayList<Taskable>());
        private final AtomicInteger runningNum = new AtomicInteger(0);
        
        SiteTasks(int id){
            this.id = id;
        }
        
        public Integer getId(){
            return this.id;
        }
        
        public void addTask(Taskable task){
            synchronized(tasks){
                tasks.add(task);
            }
        }
        
        public void removeTask(Taskable task){
            synchronized(task){
                tasks.remove(task);
            }
        }
        
        public boolean containsTask(Taskable task){
            synchronized(task){
                return tasks.contains(task);
            }
        }
        
        public Taskable getWaitTask(){
            synchronized(tasks){
                if (tasks.isEmpty()) {
                    return null;
                }
                for (final Taskable task : tasks) {
                    if (!task.isRunning()) {
                        return new TaskWrapper(tasks,task,runningNum);
                    }
                }
                return null;
            }
        }
        
        public boolean isRunning(){
            return runningNum.get() != 0;
        }
    }
    
    class TaskWrapper implements Taskable{
        private final List<Taskable> tasks;
        private final Taskable task;
        private final AtomicInteger runningNum;
        private final List<Taskable> dependences= new ArrayList<Taskable>();
        
        TaskWrapper(List<Taskable> tasks,Taskable task,AtomicInteger runningNum){
            this.tasks = tasks;
            this.task = task;
            this.runningNum = runningNum;
            if(task.getDependences() != null && !task.getDependences().isEmpty()){
                for(Taskable d : task.getDependences()){
                    dependences.add(new TaskWrapper(new ArrayList<Taskable>(),d,runningNum));
                }
            }
        }
        
        @Override
        public Object getId() {
            return task.getId();
        }

        @Override
        public String getDescription() {
            return task.getDescription();
        }

        @Override
        public Integer getSiteId() {
            return task.getSiteId();
        }

        @Override
        public String getUsername() {
            return task.getUsername();
        }

        @Override
        public boolean isRunning() {
            return task.isRunning();
        }

        @Override
        public int getProgress() {
            return task.getProgress();
        }

        @Override
        public boolean isCompleted() {
            return task.isCompleted();
        }

        @Override
        public List<Taskable> getDependences() {
            return dependences;
        }

        @Override
        public List<TaskProcessable> execute() throws TaskException {
            runningNum.getAndIncrement();
            return task.execute();
        }

        @Override
        public void close() throws TaskException {
            runningNum.getAndDecrement();
            synchronized(this.tasks){
                if(tasks.contains(task)){
                    tasks.remove(task);
                }
            }
            task.close();
        }

        @Override
        public void completeProcess() {
           task.completeProcess();
        }
    }
  
    class TaskInfoClone implements Taskable{
        private final Object id;
        private final String description;
        private final Integer siteId;
        private final boolean running;
        private final boolean completed;
        private final String username;
        private final int progress;
        private final List<Taskable> dependences = new ArrayList<Taskable>();
        
        public TaskInfoClone(Taskable task){
            this.id = task.getId();
            this.description = task.getDescription();
            this.siteId = task.getSiteId();
            this.running = task.isRunning();
            this.completed = task.isCompleted();
            this.username = task.getUsername();
            this.progress = task.getProgress();
            if(task.getDependences() != null && !task.getDependences().isEmpty()){
                for(Taskable d : task.getDependences()){
                    TaskInfoClone dc = new TaskInfoClone(d);
                    dependences.add(dc);
                }
            }
        }
        
        @Override
        public Object getId() {
            return id;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public Integer getSiteId() {
            return siteId;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isRunning() {
            return running;
        }

        @Override
        public int getProgress() {
            return progress;
        }

        @Override
        public boolean isCompleted() {
            return completed;
        }

        @Override
        public List<Taskable> getDependences() {
            return dependences;
        }

        @Override
        public void close() throws TaskException {
            throw new RuntimeException("it's not instance.");
        }

        @Override
        public List<TaskProcessable> execute() throws TaskException {
            throw new RuntimeException("it's not instance.");
        }

        @Override
        public void completeProcess() {
            throw new RuntimeException("it's not instance.");
        }
    }
}
