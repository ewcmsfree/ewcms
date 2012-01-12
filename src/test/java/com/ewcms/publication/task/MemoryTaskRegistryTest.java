/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.publication.task.impl.process.TaskProcessable;

/**
 * MemoryTaskRegistry单元测试
 * 
 * @author wangwei
 */
public class MemoryTaskRegistryTest {
 
//    @Test
//    public void testTaskExecuteSiteTaskIsRunning()throws TaskException{
//        MemoryTaskRegistry register = new MemoryTaskRegistry();
//        MemoryTaskRegistry.SiteTasks siteTasks = register.new SiteTasks(Integer.MAX_VALUE);
//        Taskable task1 = mock(Taskable.class);
//        when(task1.isRunning()).thenReturn(false);
//        siteTasks.addTask(task1);
//        Taskable task2 = mock(Taskable.class);
//        when(task2.isRunning()).thenReturn(false);
//        siteTasks.addTask(task2);
//        
//        Taskable t = siteTasks.getWaitTask();
//        t.toTaskProcess();
//        
//        Assert.assertTrue(siteTasks.isRunning());
//    }
//    
//    @Test
//    public void testDependencesExecuteSiteTaskIsRunning()throws TaskException{
//        Taskable task = mock(Taskable.class);
//        when(task.getSiteId()).thenReturn(Integer.MAX_VALUE);
//        when(task.isRunning()).thenReturn(false);
//        List<Taskable> dependences = new ArrayList<Taskable>();
//        Taskable depend = mock(Taskable.class);
//        dependences.add(depend);
//        when(task.getDependences()).thenReturn(dependences);
//        
//        MemoryTaskRegistry register = new MemoryTaskRegistry();
//        MemoryTaskRegistry.SiteTasks siteTasks = register.new SiteTasks(Integer.MAX_VALUE);
//        siteTasks.addTask(task);
//        Taskable t = siteTasks.getWaitTask();
//        t.getDependences().get(0).toTaskProcess();
//        
//        Assert.assertTrue(siteTasks.isRunning());
//    }
//    
//    @Test
//    public void testCloseTasksSiteTaskIsWait()throws TaskException{
//        MemoryTaskRegistry register = new MemoryTaskRegistry();
//        MemoryTaskRegistry.SiteTasks siteTasks = register.new SiteTasks(Integer.MAX_VALUE);
//        Taskable task1 = mock(Taskable.class);
//        when(task1.isRunning()).thenReturn(false);
//        List<Taskable> dependences = new ArrayList<Taskable>();
//        Taskable depend = mock(Taskable.class);
//        dependences.add(depend);
//        when(task1.getDependences()).thenReturn(dependences);
//        siteTasks.addTask(task1);
//        
//        Taskable task2 = mock(Taskable.class);
//        when(task2.isRunning()).thenReturn(false);
//        siteTasks.addTask(task2);
//        
//        Taskable t = null;
//        while((t = siteTasks.getWaitTask())!=null){
//            for(Taskable d : t.getDependences()){
//                d.toTaskProcess();
//                d.close();
//            }
//            t.toTaskProcess();
//            t.close();
//        }
//       
//        Assert.assertFalse(siteTasks.isRunning());
//    }
//    
//    @Test
//    public void testRegisterNewTask(){
//        Taskable task = mock(Taskable.class);
//        when(task.getSiteId()).thenReturn(Integer.MAX_VALUE);
//        
//        MemoryTaskRegistry register = new MemoryTaskRegistry();
//        register.registerNewTask(task);
//        Assert.assertTrue(register.containsTask(task));
//    }
//     
//    @Test
//    public void testRemoveTaskNotExistTask() throws TaskException{
//        Integer siteId = 0;
//        String taskId = "0";
//        String username="test";
//        Taskable task = mock(Taskable.class);
//        when(task.getSiteId()).thenReturn(siteId);
//        when(task.getId()).thenReturn(taskId);
//        when(task.getUsername()).thenReturn(username);
//        MemoryTaskRegistry register = new MemoryTaskRegistry();
//        register.removeTask(siteId,taskId,username);
//        Assert.assertFalse(register.containsTask(task));
//    }
//    
//    @Test
//    public void testRemoveTask() throws TaskException{
//        Integer siteId = 0;
//        String taskId = "0";
//        String username="test";
//        Taskable task = mock(Taskable.class);
//        when(task.getSiteId()).thenReturn(siteId);
//        when(task.getId()).thenReturn(taskId);
//        when(task.getUsername()).thenReturn(username);
//        MemoryTaskRegistry register = new MemoryTaskRegistry();
//        register.registerNewTask(task);
//        
//        register.removeTask(siteId,taskId,username);
//        Assert.assertFalse( register.containsTask(task));
//    }
//    
//    @Test
//    public void testGetWaitSite(){
//        Taskable task = mock(Taskable.class);
//        when(task.getSiteId()).thenReturn(Integer.MAX_VALUE);
//        MemoryTaskRegistry register = new MemoryTaskRegistry();
//        register.registerNewTask(task);
//        
//        Integer siteId = register.getWaitSite();
//        Assert.assertEquals(Integer.MAX_VALUE,siteId.intValue());
//    }
//    
//    @Test
//    public void testGetTaskOfSite(){
//        Taskable task = mock(Taskable.class);
//        when(task.getSiteId()).thenReturn(Integer.MAX_VALUE);
//        when(task.isRunning()).thenReturn(false);
//        MemoryTaskRegistry register = new MemoryTaskRegistry();
//        register.registerNewTask(task);
//        Integer siteId = register.getWaitSite();
//        Taskable t = register.getTaskOfSite(siteId);
//        Assert.assertNotNull(t);
//        Assert.assertTrue(t instanceof MemoryTaskRegistry.TaskWrapper);
//    }
//    
//    @Test
//    public void testGetTaskOfSiteQueue() throws TaskException{
//        MemoryTaskRegistry register = new MemoryTaskRegistry();
//        Integer siteId = 1;
//        Taskable task1 = mock(Taskable.class);
//        when(task1.getSiteId()).thenReturn(siteId);
//        when(task1.getId()).thenReturn("1");
//        when(task1.isRunning()).thenReturn(false);
//        register.registerNewTask(task1);
//        Taskable task2 = mock(Taskable.class);
//        when(task2.getSiteId()).thenReturn(siteId);
//        when(task2.getId()).thenReturn("2");
//        when(task2.isRunning()).thenReturn(false);
//        register.registerNewTask(task2);
//        
//        for(Integer id = register.getWaitSite(); true ; id = register.getWaitSite()){
//            Taskable task = register.getTaskOfSite(id);
//            task.toTaskProcess();
//            task.close();
//        }
//    }
//    
//    @Test
//    public void testGetSiteTasks(){
//        Taskable task = mock(Taskable.class);
//        when(task.getSiteId()).thenReturn(Integer.MAX_VALUE);
//        when(task.isRunning()).thenReturn(false);
//        List<Taskable> dependences = new ArrayList<Taskable>();
//        Taskable depend = mock(Taskable.class);
//        dependences.add(depend);
//        when(task.getDependences()).thenReturn(dependences);
//        
//        MemoryTaskRegistry register = new MemoryTaskRegistry();
//        register.registerNewTask(task);
//        Integer siteId = register.getWaitSite();
//        List<Taskable> ts = register.getSiteTasks(siteId);
//        Assert.assertNotNull(ts);
//        Assert.assertEquals(1, ts.size());
//        Assert.assertTrue(ts.get(0) instanceof MemoryTaskRegistry.TaskInfoClone);
//        Assert.assertEquals(1, ts.get(0).getDependences().size());
//    }
    //TODO 重构了需要从新写测试用力
    
}
