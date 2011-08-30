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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 内存任务注册
 * <br>
 * 任务以Map形式保存在内存中。
 * 
 * @author wangwei
 */
public class MemoryTaskRegistry implements TaskRegistryable{

    private static final Logger logger = LoggerFactory.getLogger(MemoryTaskRegistry.class);
    
    private static final Map<Object,Taskable> TASKS_POOL =Collections.synchronizedMap(new LinkedHashMap<Object,Taskable>());
    
    private Map<Object,Taskable> tasksPool = TASKS_POOL;
    
    @Override
    public synchronized void registerNewTask(Object id, Taskable task) {
        Assert.notNull(id,"id is null");
        Assert.notNull(task,"task is null");
        
        if(alreadyExistTask(id)){
            logger.debug("Id is already exist");
            removeTask(id);
        }
        
        tasksPool.put(id, task);
    }

    @Override
    public boolean alreadyExistTask(Object id) {
        return getTask(id) != null;
    }

    @Override
    public Taskable getTask(Object id) {
        return tasksPool.get(id);
    }
    
    @Override
    public void removeTask(Object id) {
        Taskable task = tasksPool.get(id);
        
        if(task == null){
            return ;
        }
        
        synchronized(task){
            tasksPool.remove(id);
        }
    }

    @Override
    public List<Taskable> getAllTasks() {
        return new ArrayList<Taskable>(tasksPool.values());
    }

    @Override
    public synchronized List<Taskable> getOwnerSiteTasks(Integer siteId) {
        List<Taskable> list = new ArrayList<Taskable>();
        for(Taskable task : tasksPool.values()){
            if(task.isOwnerSite(siteId)){
                list.add(task);
            }
        }
        return list;
    }
}
