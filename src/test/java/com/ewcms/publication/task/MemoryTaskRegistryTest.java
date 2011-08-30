/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * MemoryTaskRegistry单元测试
 * 
 * @author wangwei
 */
public class MemoryTaskRegistryTest {

    private Integer id = 1;
    
    @Before
    public void defore(){
        MemoryTaskRegistry register = new MemoryTaskRegistry();
        
        Taskable task = mock(Taskable.class);
        when(task.isOwnerSite(any(Integer.class))).thenReturn(true);
        register.registerNewTask(id, task);
    }
    
    @After
    public void after(){
        MemoryTaskRegistry register = new MemoryTaskRegistry();
        register.removeTask(id);
    }
    
    @Test
    public void testRegisterNewTask(){
        MemoryTaskRegistry register = new MemoryTaskRegistry();
        Assert.assertTrue(register.alreadyExistTask(id));
    }
    
    @Test
    public void testGetTask(){
        MemoryTaskRegistry register = new MemoryTaskRegistry();
        Assert.assertNotNull(register.getTask(id));
    }
    
    @Test
    public void testRemoveTask(){
        MemoryTaskRegistry register = new MemoryTaskRegistry();
        
        register.removeTask(id);
        Assert.assertFalse(register.alreadyExistTask(id));
    }
    
    @Test
    public void testGetAllTask(){
        MemoryTaskRegistry register = new MemoryTaskRegistry();
        
        Taskable task = mock(Taskable.class);
        register.registerNewTask(2, task);
        Assert.assertEquals(2, register.getAllTasks().size());
        
        register.removeTask(2);
    }
    
    @Test
    public void testGetOwnerSiteTask(){
        MemoryTaskRegistry register = new MemoryTaskRegistry();
        
        Taskable task = mock(Taskable.class);
        when(task.isOwnerSite(any(Integer.class))).thenReturn(false);
        register.registerNewTask(2, task);
        Assert.assertEquals(1, register.getOwnerSiteTasks(1).size());
        
        register.removeTask(2);
    }
}
