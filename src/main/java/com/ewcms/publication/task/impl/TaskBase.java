/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

/**
 * 实现发布任务
 * 
 * @author wangwei
 */
public abstract class TaskBase implements Taskable{
    
    protected static final String DEFAULT_USERNAME = "ewcms_system_manager";
    protected static final Random random = initRandom();
    
    protected final String id;
    protected final AtomicInteger runningNum = new AtomicInteger(0);
    protected final AtomicInteger countProcess = new AtomicInteger(0);
    protected final AtomicInteger completeProcess = new AtomicInteger(0);
    protected volatile boolean running = false;

    /**
     * 创建随机数对象
     * <br>
     * 用于任务编号生成
     * 
     * @return
     */
    private synchronized static Random initRandom(){
        return new Random();
    }
    
    public TaskBase(String id){
        this.id = id;
    }
    
    @Override
    public Object getId() {
        return id;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getProgress() {
        if(countProcess.get() == 0){
            return 100;
        }
        return (completeProcess.get() * 100 / countProcess.get());
    }

    @Override
    public boolean isCompleted() {
        return countProcess.get()== completeProcess.get();
    }
    
    @Override
    public List<TaskProcessable> execute() throws TaskException {
        //running only one 
        if(runningNum.incrementAndGet() != 1){
           throw new TaskException("Task is already run");
       }
        
       List<TaskProcessable> taskProcesses = getTaskProcesses();
       running =true;
       countProcess.set(taskProcesses.size());
       
       return taskProcesses;
    }
    
    /**
     * 得到发布的任务过程集合
     * 
     * @return 任务过程集合
     * @throws
     */
    protected abstract List<TaskProcessable> getTaskProcesses()throws TaskException;

    @Override
    public void completeProcess() {
        completeProcess.incrementAndGet();
    }
    
    /**
     * 创建任务编号
     * 
     * @return
     */
    protected static String newTaskId(){
        return String.valueOf(random.nextLong());
    }
}
