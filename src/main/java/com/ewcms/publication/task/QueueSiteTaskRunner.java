/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.publication.task.impl.NoneTask;
import com.ewcms.publication.task.impl.process.TaskProcessable;
import com.ewcms.publication.task.publish.SitePublishable;
/**
 * 实现任务运行，任务按照先进先出顺序运行。
 * 
 * @author wangwei
 */
public class QueueSiteTaskRunner  implements SiteTaskRunnerable{
    private static final Logger logger = LoggerFactory.getLogger(QueueSiteTaskRunner.class);
    
    protected final LinkedBlockingQueue<Taskable> queue = new LinkedBlockingQueue<Taskable>();
    private final SitePublishable sitePublish;
    private final Semaphore limit;
    private volatile Taskable taskRunning;
    private volatile boolean closed = false;
    
    public QueueSiteTaskRunner(SitePublishable sitePublish,Semaphore limit){
        this.limit = limit;
        this.sitePublish = sitePublish;
    }
    
    @Override
    public List<Taskable> getTasks() {
        List<Taskable> tasks = new ArrayList<Taskable>();
        if(taskRunning != null){
            tasks.add(new TaskInfoClone(taskRunning));
        }
        for(Taskable task : queue){
            tasks.add(new TaskInfoClone(task));
        }
        return tasks;
    }
    
    private void publish(final Taskable task){
        try {
            sitePublish.publish(task);
        } catch (TaskException e) {
            logger.debug("Task published fail:{}",e);
        }
    }
    
    @Override
    public void run() {
        logger.info("Site's runner start");
        while(true){
            try {
                taskRunning = queue.take();
                if(closed){
                    taskRunning = null;
                    break;
                }
                limit.acquire();
                publish(taskRunning);
                taskRunning = null;
            } catch (InterruptedException e) {
                logger.debug("Task runned  fail:{}",e);
            } finally{
                limit.release();
            }
        }
    }

    @Override
    public void add(Taskable task) {
        queue.add(task);
    }

    @Override
    public boolean remov(Taskable task) {
        if(task.equals(taskRunning)){
            logger.info("Task is running.");
            sitePublish.cancelPublish();
            taskRunning = null;
            return true;
        }
        return queue.remove(task);
    }

    @Override
    public Taskable get(String id){
        logger.info("it will be getting task by {}",id);
        for(Taskable task : queue){
            if(task.getId() != null && task.getId().equals(id)){
                return task;
            }
        }
        if(taskRunning != null ){
            logger.info("Task's {} is running ",taskRunning.getId());
            if(taskRunning.getId().equals(id)){
                return taskRunning;
            }
        }
        return null;
    }
    
    public boolean contains(Taskable task){
        return queue.contains(task)  ||
                (taskRunning != null && taskRunning.equals(task));
    }
    
    @Override
    public void close() {
        closed = true;
        if(this.taskRunning != null){
            sitePublish.cancelPublish();
        }
        //skip break
        if(queue.isEmpty()){
           queue.add(new NoneTask()); 
        }
    }
    
    class TaskInfoClone implements Taskable{
        private final String id;
        private final String description;
        private final boolean completed;
        private final String username;
        private final int progress;
        private final List<Taskable> dependences = new ArrayList<Taskable>();
        
        public TaskInfoClone(Taskable task){
            this.id = task.getId();
            this.description = task.getDescription();
            this.completed = task.isCompleted();
            this.username = task.getUsername();
            this.progress = task.getProgress();
            if(task.getDependenceTasks() != null && !task.getDependenceTasks().isEmpty()){
                for(Taskable d : task.getDependenceTasks()){
                    TaskInfoClone dc = new TaskInfoClone(d);
                    dependences.add(dc);
                }
            }
        }
        
        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String getUsername() {
            return username;
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
        public List<Taskable> getDependenceTasks() {
            return dependences;
        }

        @Override
        public List<TaskProcessable> toTaskProcess() throws TaskException {
            throw new RuntimeException("it's not instance.");
        }
    }
}
