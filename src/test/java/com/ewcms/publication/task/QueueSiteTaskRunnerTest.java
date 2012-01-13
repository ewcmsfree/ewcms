/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.publication.task.QueueSiteTaskRunner.TaskInfoClone;
import com.ewcms.publication.task.impl.NoneTask;
import com.ewcms.publication.task.publish.SitePublishable;

/**
 * QueueSiteTaskRunner 单元测试
 * 
 * @author wangwei
 */
public class QueueSiteTaskRunnerTest {

    @Test
    public void testAdd(){
        SitePublishable sitePublish = mock(SitePublishable.class);
        Semaphore limit = new Semaphore(5);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        runner.add(mock(Taskable.class));
        runner.add(mock(Taskable.class));
        
        List<Taskable> tasks = runner.getTasks();
        Assert.assertEquals(2, tasks.size());
    }
    
    @Test
    public void testGetTasksQueue(){
        SitePublishable sitePublish = mock(SitePublishable.class);
        Semaphore limit = new Semaphore(5);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        runner.add(mock(Taskable.class));
        runner.add(mock(Taskable.class));
        
        List<Taskable> tasks = runner.getTasks();
        Assert.assertEquals(2, tasks.size());
        Taskable clone = tasks.get(0);
        Assert.assertEquals(TaskInfoClone.class, clone.getClass());
    }
    
    @Test
    public void testGetTasksHasRunning() throws InterruptedException{
        WaitSitePublish sitePublish =new WaitSitePublish();
        Semaphore limit = new Semaphore(5);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        runner.add(mock(Taskable.class));
        runner.add(mock(Taskable.class));
        ExecutorService executorSerivce = Executors.newSingleThreadExecutor();
        executorSerivce.submit(runner);
        Thread.sleep(1000);
        
        List<Taskable> tasks = runner.getTasks();
        Assert.assertEquals(2, tasks.size());
        Taskable clone = tasks.get(0);
        Assert.assertEquals(TaskInfoClone.class, clone.getClass());
        
        runner.close();
    }
    
    @Test
    public void testRemoveQueue(){
        SitePublishable sitePublish = mock(SitePublishable.class);
        Semaphore limit = new Semaphore(5);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        Taskable task = mock(Taskable.class);
        runner.add(task);
        runner.add(mock(Taskable.class));
        
        runner.remov(task);
        
        List<Taskable> tasks = runner.getTasks();
        Assert.assertEquals(1, tasks.size());
    }
    
    @Test
    public void testRemoveRunning() throws InterruptedException{
        WaitSitePublish sitePublish =new WaitSitePublish();
        Semaphore limit = new Semaphore(5);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        Taskable task = mock(Taskable.class);
        runner.add(task);
        runner.add(mock(Taskable.class));
        ExecutorService executorSerivce = Executors.newSingleThreadExecutor();
        executorSerivce.submit(runner);
        Thread.sleep(1000);
        
        runner.remov(task);
        Assert.assertTrue(sitePublish.isCanceled());
        List<Taskable> tasks = runner.getTasks();
        Assert.assertEquals(1, tasks.size());
        
        runner.close();
    }
    
    @Test
    public void testGetQueue(){
        SitePublishable sitePublish = mock(SitePublishable.class);
        Semaphore limit = new Semaphore(5);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        Taskable task = mock(Taskable.class);
        when(task.getId()).thenReturn("1");
        runner.add(task);
        runner.add(mock(Taskable.class));
        
        Taskable t = runner.get("1");
        Assert.assertNotNull(t);
        Assert.assertEquals("1", t.getId());
    }
    
    @Test
    public void testGetRunning() throws InterruptedException{
        WaitSitePublish sitePublish =new WaitSitePublish();
        Semaphore limit = new Semaphore(5);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        Taskable task = mock(Taskable.class);
        when(task.getId()).thenReturn("1");
        runner.add(task);
        runner.add(mock(Taskable.class));
        ExecutorService executorSerivce = Executors.newSingleThreadExecutor();
        executorSerivce.submit(runner);
        Thread.sleep(1000);
        
        Taskable t = runner.get("1");
        Assert.assertNotNull(t);
        Assert.assertEquals("1", t.getId());
        
        runner.close();
    }
    
    @Test
    public void testContainsQueue(){
        SitePublishable sitePublish = mock(SitePublishable.class);
        Semaphore limit = new Semaphore(5);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        Taskable task = mock(Taskable.class);
        runner.add(task);
        runner.add(mock(Taskable.class));
        
        Assert.assertTrue(runner.contains(task));
    }
    
    @Test
    public void testContainsRunning() throws InterruptedException{
        WaitSitePublish sitePublish =new WaitSitePublish();
        Semaphore limit = new Semaphore(5);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        Taskable task = mock(Taskable.class);
        when(task.getId()).thenReturn("1");
        runner.add(task);
        runner.add(mock(Taskable.class));
        ExecutorService executorSerivce = Executors.newSingleThreadExecutor();
        executorSerivce.submit(runner);
        Thread.sleep(1000);
        
        Assert.assertTrue(runner.contains(task));
        
        runner.close();
    }
    
    @Test
    public void testRunning()throws InterruptedException{
        WaitSitePublish sitePublish =new WaitSitePublish();
        Semaphore limit = new Semaphore(5);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        Taskable task = mock(Taskable.class);
        runner.add(task);
        ExecutorService executorSerivce = Executors.newSingleThreadExecutor();
        executorSerivce.submit(runner);
        Thread.sleep(1000);
        Assert.assertTrue(sitePublish.isRunning());
        runner.close();
    }
    
    @Test
    public void testRunningBreak()throws InterruptedException{
        WaitSitePublish sitePublish =new WaitSitePublish();
        Semaphore limit = new Semaphore(1);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        Taskable task = mock(Taskable.class);
        runner.add(task);

        limit.acquire();
        
        ExecutorService executorSerivce = Executors.newSingleThreadExecutor();
        executorSerivce.submit(runner);
        Thread.sleep(1000);
        Assert.assertFalse(sitePublish.isRunning());
        limit.release();
        Thread.sleep(1000);
        Assert.assertTrue(sitePublish.isRunning());
        runner.close();
    }
    
    @Test
    public void testClose()throws InterruptedException{
        WaitSitePublish sitePublish =new WaitSitePublish();
        Semaphore limit = new Semaphore(5);
        QueueSiteTaskRunner runner = new QueueSiteTaskRunner(sitePublish,limit);
        Taskable task = mock(Taskable.class);
        runner.add(task);
        ExecutorService executorSerivce = Executors.newSingleThreadExecutor();
        executorSerivce.submit(runner);
        Thread.sleep(1000);
        Assert.assertTrue(sitePublish.isRunning());
        
        runner.close();
        Assert.assertTrue(sitePublish.isCanceled());
        Assert.assertEquals(1, runner.queue.size());
        Assert.assertEquals(NoneTask.class, runner.queue.poll().getClass() );
    }
    
    class WaitSitePublish implements SitePublishable{

        private volatile boolean canceled = false;
        private volatile boolean running = false;
        
        @Override
        public void publish(Taskable task) throws TaskException {
            try {
                running = true;
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void cancelPublish() {
            canceled = true;
        }
        
        public boolean isCanceled(){
            return this.canceled;
        }
        
        public boolean isRunning(){
            return this.running;
        }
    }
}
