/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

import freemarker.template.Configuration;

/**
 * HomeTask单元测试
 * 
 * @author wangwei
 */
public class HomeTaskTest {
    
    private Template newTemplate(String path){
        Template template = new Template();
        template.setName("home");
        template.setUniquePath(path);
        return template;
    }
    
    @Test
    public void testBuildTask(){
        String path = "/test/index.html";
        TemplateSourcePublishServiceable templateSourceService = mock(TemplateSourcePublishServiceable.class);
        Template template = newTemplate(path);
        template.setUriPattern("${a}");
        HomeTask task = new HomeTask.Builder(
                new Configuration(),templateSourceService, new Site(), new Channel(), template).
                setUsername("test").
                dependence().
                build();
        
        Assert.assertNotNull(task.getConfiguration());
        Assert.assertNotNull(task.getSite());
        Assert.assertNotNull(task.getChannel());
        Assert.assertFalse(task.isIndependence());
        Assert.assertEquals("home-页面发布", task.getDescription());
        Assert.assertEquals("test", task.getUsername());
        Assert.assertEquals("${a}", task.getUriRule().getPatter());
    }

    @Test
    public void testGetTaskProcesses()throws Exception{
        String path = "/test/index.html";
        TemplateSourcePublishServiceable templateSourceService = mock(TemplateSourcePublishServiceable.class);
        Template template = newTemplate(path);
        HomeTask task = new HomeTask.Builder(
                new Configuration(),templateSourceService, new Site(), new Channel(), template).
                build();
        
        List<TaskProcessable> processes = task.getTaskProcesses();
        Assert.assertEquals(1, processes.size());
    }
    
    @Test
    public void testDependencesByInDependenceTask(){
        String path = "/test/index.html";
        TemplateSourcePublishServiceable templateSourceService = mock(TemplateSourcePublishServiceable.class);
        Template template = newTemplate(path);
        HomeTask task = new HomeTask.Builder(
                new Configuration(),templateSourceService, new Site(), new Channel(), template).
                build();
        
        List<Taskable> dependences = task.getDependences();
        Assert.assertEquals(1, dependences.size());
        Assert.assertTrue(dependences.get(0) instanceof TemplateSourceTask);
    }
    
    @Test
    public void testDependencesByDependenceTask(){
        String path = "/test/index.html";
        TemplateSourcePublishServiceable templateSourceService = mock(TemplateSourcePublishServiceable.class);
        Template template = newTemplate(path);
        HomeTask task = new HomeTask.Builder(
                new Configuration(),templateSourceService, new Site(), new Channel(), template).
                dependence().
                build();
        
        List<Taskable> dependences = task.getDependences();
        Assert.assertTrue(dependences.isEmpty());
    }
}
