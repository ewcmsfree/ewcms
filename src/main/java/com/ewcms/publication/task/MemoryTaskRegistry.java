/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.task.publish.SitePublish;
import com.ewcms.publication.task.publish.SitePublishable;

/**
 * 内存任务注册
 * <br>
 * 任务以Map形式保存在内存中。
 * 
 * @author wangwei
 */
public class MemoryTaskRegistry implements TaskRegistryable{

    private static final Logger logger = LoggerFactory.getLogger(MemoryTaskRegistry.class);
    private static final int DEFAULT_MAX_RUNNNING = 5;
    
    private final Map<Integer, SiteTaskRunnerable> taskPool =
        Collections.synchronizedMap(new LinkedHashMap<Integer,SiteTaskRunnerable>());
    private final  Semaphore limit;
    
    public MemoryTaskRegistry(){
        this(DEFAULT_MAX_RUNNNING);
    }
    
    public MemoryTaskRegistry(int maxRunning){
        limit = new Semaphore(maxRunning);
    }
    
    @Override
    public void registerNewTask(Site site,Taskable task) {
        Assert.notNull(task,"task is null");
        synchronized(taskPool){
            final Integer siteId = site.getId();
            SiteTaskRunnerable running = taskPool.get(siteId);
            if(running == null){
                logger.debug("create site running,site id is {}",siteId);
                SiteServer server = site.getSiteServer();
                //TODO server add multi property
                SitePublishable publish = new SitePublish(server);
                running = new QueueSiteTaskRunner(publish,limit);
                taskPool.put(siteId, running);
                //start publish
                Thread t = new Thread(running);
                t.start();
            }
            running.add(task);
        }
    }

    @Override
    public void removeTask(Integer siteId,String id,String username)throws TaskException {
        synchronized(taskPool){
            SiteTaskRunnerable runner = taskPool.get(siteId);
            if(runner == null){
                logger.debug("Site's runner is not exist,site id is {}",siteId);
                return ;
            }
            Taskable task = runner.get(id);
            if(task == null){
                logger.debug("Task is not exist,task id is {}.",id);
                return ;
            }
            if(!task.getUsername().equals(username) && 
                    !task.getUsername().equals(MANAGER_USERNAME)){
                logger.debug("{} is owner of task,{} does not operator",task.getUsername(),username);
                throw new TaskException("The task is not owner of it");
            }
            runner.remov(task);
        }
    }

    @Override
    public List<Taskable> getSiteTasks(Integer siteId) {
        
        SiteTaskRunnerable runner;
        synchronized(taskPool){
            runner = taskPool.get(siteId);
        }
        if(runner == null){
            logger.debug("Site's runner is not exist,site id is {}",siteId);
            return new ArrayList<Taskable>();
        }
        return runner.getTasks();
    }
    
    /**
     * 判断任务是否存在
     * 
     * @param task 任务
     * @return
     */
    boolean containsTask(Taskable task){
        synchronized(taskPool){
            boolean contains = false;
            for(SiteTaskRunnerable runner :taskPool.values()){
                contains = contains || runner.contains(task);
            }
            return contains;
        }
    }

    @Override
    public void closeSite(Integer siteId) {
        synchronized(taskPool){
            SiteTaskRunnerable runner = taskPool.get(siteId);
            if(runner == null){
                logger.debug("Site runner is not exist,site id is {}.",siteId);
                return ;
            }
            runner.close();
            taskPool.remove(siteId);
        }
    }
}
