/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.task.impl.process.TaskProcessable;

import freemarker.template.Configuration;
/**
 * HomeTask单元测试
 * 
 * @author wangwei
 */
public class HomeTaskTest {
//    
//    @Test
//    public void testBuildTask(){
////        String path = "/test/index.html";
////        HomeTask task = 
////            new HomeTask.Builder(new Configuration(), new Site(), new Channel(), path).
////            setDescription("home publish").
//            setUsername("test").
//            setUriRulePatter("${a}").
//            build();
//        
//        Assert.assertNotNull(task.getConfiguration());
//        Assert.assertNotNull(task.getSite());
//        Assert.assertNotNull(task.getChannel());
//        Assert.assertEquals("home publish", task.getDescription());
//        Assert.assertEquals("test", task.getUsername());
//        Assert.assertEquals("${a}", task.getUriRulePatter());
//    }
//    
//    @Test
//    public void testGetTaskProcesses()throws Exception{
//        String path = "/test/index.html";
//        HomeTask task = 
//            new HomeTask.Builder(new Configuration(), new Site(), new Channel(), path).
//            build();
//        
//        List<TaskProcessable> processes = task.getTaskProcesses();
//        Assert.assertEquals(1, processes.size());
//    }
//    
//    @Test
//    public void testGetTaskProcessesByUriRulePatter()throws Exception{
//        String path = "/test/index.html";
//        HomeTask task = 
//            new HomeTask.Builder(new Configuration(), new Site(), new Channel(), path).
//            setUriRulePatter("${a}").
//            build();
//        
//        List<TaskProcessable> processes = task.getTaskProcesses();
//        Assert.assertEquals(1, processes.size());
//    }
}
