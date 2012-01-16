/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.core.site.model.OutputType;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.SiteServer;

/**
 * MemoryTaskRegistry单元测试
 * 
 * @author wangwei
 */
public class MemoryTaskRegistryTest {
     
    @Test
    public void testRegisterNewTask(){
        Taskable task = mock(Taskable.class);
        MemoryTaskRegistry register = new MemoryTaskRegistry();
        Site site = new Site();
        site.setId(1);
        SiteServer server = new SiteServer();
        server.setOutputType(OutputType.LOCAL);
        site.setSiteServer(server);
        register.registerNewTask(site,task);
        Assert.assertTrue(register.containsTask(task));
    }
     
    @Test
    public void testRemoveTaskNotExist() throws TaskException{
        Integer siteId = 1;
        String taskId = "0";
        String username="test";
        Taskable task = mock(Taskable.class);
        when(task.getId()).thenReturn(taskId);
        when(task.getUsername()).thenReturn(username);
        MemoryTaskRegistry register = new MemoryTaskRegistry();
        register.removeTask(siteId,taskId,username);
        Assert.assertFalse(register.containsTask(task));
    }
    
    @Test
    public void testRemoveTask() throws TaskException{
        Integer siteId = 1;
        String taskId = "0";
        String username="test";
        Taskable task = mock(Taskable.class);
        when(task.getId()).thenReturn(taskId);
        when(task.getUsername()).thenReturn(username);
        MemoryTaskRegistry register = new MemoryTaskRegistry();
        Site site = new Site();
        site.setId(siteId);
        SiteServer server = new SiteServer();
        server.setOutputType(OutputType.LOCAL);
        site.setSiteServer(server);
        register.registerNewTask(site,task);
        
        register.removeTask(siteId,taskId,username);
        Assert.assertFalse( register.containsTask(task));
    }
    
    @Test
    public void testDifferentUserRemoveTask(){
        Integer siteId = 1;
        String taskId = "0";
        String username="test";
        Taskable task = mock(Taskable.class);
        when(task.getId()).thenReturn(taskId);
        when(task.getUsername()).thenReturn(username);
        MemoryTaskRegistry register = new MemoryTaskRegistry(0);
        Site site = new Site();
        site.setId(siteId);
        SiteServer server = new SiteServer();
        server.setOutputType(OutputType.LOCAL);
        site.setSiteServer(server);
        register.registerNewTask(site,task);
        
        try{
            register.removeTask(siteId,taskId,"test1");
            Assert.assertFalse( register.containsTask(task));
            Assert.fail();
        }catch(TaskException e){
            Assert.assertTrue(register.containsTask(task));
        }
    }
    
    @Test
    public void testManagerUserRemove(){
        Integer siteId = 1;
        String taskId = "0";
        String username="test";
        Taskable task = mock(Taskable.class);
        when(task.getId()).thenReturn(taskId);
        when(task.getUsername()).thenReturn(username);
        MemoryTaskRegistry register = new MemoryTaskRegistry(0);
        Site site = new Site();
        site.setId(siteId);
        SiteServer server = new SiteServer();
        server.setOutputType(OutputType.LOCAL);
        site.setSiteServer(server);
        register.registerNewTask(site,task);
        
        try{
            register.removeTask(siteId,taskId,TaskRegistryable.MANAGER_USERNAME);
            Assert.assertFalse( register.containsTask(task));
        }catch(TaskException e){
            Assert.fail();
        }
    }
    
    @Test
    public void testGetSiteTasks(){
        Integer siteId = 1;
        MemoryTaskRegistry register = new MemoryTaskRegistry(0);
        Site site = new Site();
        site.setId(siteId);
        SiteServer server = new SiteServer();
        server.setOutputType(OutputType.LOCAL);
        site.setSiteServer(server);
        register.registerNewTask(site, mock(Taskable.class));
        register.registerNewTask(site, mock(Taskable.class));
        List<Taskable> tasks = register.getSiteTasks(siteId);
        Assert.assertEquals(2, tasks.size());
    }
    
    @Test
    public void testCloseSite(){
        Integer siteId = 1;
        MemoryTaskRegistry register = new MemoryTaskRegistry(0);
        Site site = new Site();
        site.setId(siteId);
        SiteServer server = new SiteServer();
        server.setOutputType(OutputType.LOCAL);
        site.setSiteServer(server);
        register.registerNewTask(site, mock(Taskable.class));
        register.registerNewTask(site, mock(Taskable.class));
        
        register.closeSite(siteId);
        Assert.assertFalse(register.containsSite(siteId));
    }
}
