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

import com.ewcms.publication.task.impl.process.TaskProcessable;
import com.ewcms.publication.task.publish.SitePublishable;
/**
 * 实现任务运行，任务按照先进先出顺序运行。
 * 
 * @author wangwei
 */
public class QueueSiteTaskRunner  implements SiteTaskRunnerable{
    private static final Logger logger = LoggerFactory.getLogger(QueueSiteTaskRunner.class);
    
    private final LinkedBlockingQueue<Taskable> queue = new LinkedBlockingQueue<Taskable>();
    private final SitePublishable sitePublish;
    private final Semaphore limit;
    private Taskable taskRunning;
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
        while(true){
            try {
                if(closed) break;
                taskRunning = queue.take();
                limit.acquire();
                publish(taskRunning);
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
            sitePublish.cancelPublish();
            return true;
        }
        return queue.remove(task);
    }

    @Override
    public Taskable get(String id){
        for(Taskable task : this.queue){
            if(task.getId().equals(id)){
                return task;
            }
        }
        if(this.taskRunning.equals(id)){
            return taskRunning;
        }
        return null;
    }
    
    public boolean contains(Taskable task){
        return queue.contains(task) || taskRunning.equals(task);
    }
    
    @Override
    public void close() {
        closed = true;
        if(this.taskRunning != null){
            sitePublish.cancelPublish();
        }
    }
    
    class TaskInfoClone implements Taskable{
        private final String id;
        private final String description;
        private final boolean running;
        private final boolean completed;
        private final String username;
        private final int progress;
        private final List<Taskable> dependences = new ArrayList<Taskable>();
        
        public TaskInfoClone(Taskable task){
            this.id = task.getId();
            this.description = task.getDescription();
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
        public List<TaskProcessable> toTaskProcess() throws TaskException {
            throw new RuntimeException("it's not instance.");
        }
    }
}
