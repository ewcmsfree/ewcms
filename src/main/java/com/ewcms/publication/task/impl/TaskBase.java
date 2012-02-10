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
    
    protected static final Random random = initRandom();
    
    protected final String id;
    protected final AtomicInteger complete = new AtomicInteger(0);
    protected final AtomicInteger count = new AtomicInteger(-1);

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
    public String getId() {
        return id;
    }

    @Override
    public boolean isRunning() {
        return count.get() != -1;
    }

    @Override
    public int getProgress() {
        if(count.get()== 0){
            return 100;
        }
        return (complete.get() * 100 / count.get());
    }

    @Override
    public boolean isCompleted() {
        return count.get() == complete.get();
    }
    
    @Override
    public List<TaskProcessable> toTaskProcess() throws TaskException {
       List<TaskProcessable> taskProcesses = getTaskProcesses();
       count.set(taskProcesses.size());
       
       return taskProcesses;
    }
    
    /**
     * 得到要发布任务过程集合
     * 
     * @return 任务过程集合
     * @throws
     */
    protected abstract List<TaskProcessable> getTaskProcesses()throws TaskException;

    /**
     * 得到再次发布提示信息
     * 
     * @param again
     * @return
     */
    protected String getAgainMessage(boolean again){
       return again ? "(重新)" : "";
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
