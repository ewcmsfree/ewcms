/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.core.site.model.TemplatesrcEntity;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;
/**
 * TemplateSourceTask单元测试
 * 
 * @author wangwei
 */
public class TemplateSourceTaskTest {

    @Test
    public void testBuildTask(){
        TemplateSourcePublishServiceable service = mock(TemplateSourcePublishServiceable.class);
        Site site = new Site();
        site.setId(Integer.MAX_VALUE);
        site.setSiteName("主站");
        Taskable task = new TemplateSourceTask.Builder(service, site).
            setAgain(true).
            setPublishIds(new int[]{Integer.MIN_VALUE}).
            setUsername("user").
            build();
        
        Assert.assertNotNull(task);
        Assert.assertEquals("user", task.getUsername());
        Assert.assertEquals("主站模版资源发布",task.getDescription());
    }
    
    @Test
    public void testGetTaskProcessesBySiteId()throws TaskException{
        Integer siteId = Integer.MAX_VALUE;
        
        List<TemplateSource> sources = new ArrayList<TemplateSource>();
        TemplateSource source = new TemplateSource();
        source.setId(Integer.MIN_VALUE);
        source = new TemplateSource();
        source.setId(Integer.MIN_VALUE+1);
        source.setSourceEntity(new TemplatesrcEntity());
        sources.add(source);
        TemplateSourcePublishServiceable service = mock(TemplateSourcePublishServiceable.class);
        when(service.findPublishTemplateSources(siteId, Boolean.FALSE)).thenReturn(sources);
        Site site = new Site();
        site.setId(Integer.MAX_VALUE);
        site.setSiteName("主站");
        Taskable task =new TemplateSourceTask.
            Builder(service, site).
            build();
        
        
        List<TaskProcessable> processes = task.toTaskProcess();
        Assert.assertEquals(1,processes.size());
    }
    
    @Test
    public void testGetTaskProcessesBySourceId()throws TaskException{
        Integer sourceId = Integer.MIN_VALUE;
        
        TemplateSourcePublishServiceable service = mock(TemplateSourcePublishServiceable.class);
        TemplateSource root = new TemplateSource();
        root.setId(sourceId);
        when(service.getTemplateSource(sourceId)).thenReturn(root);
        
        List<TemplateSource> sources = new ArrayList<TemplateSource>();
        
        TemplateSource source = new TemplateSource();
        Integer sourceId2 = Integer.MIN_VALUE+1;
        source.setId(sourceId2);
        source.setRelease(true);
        source.setSourceEntity(new TemplatesrcEntity());
        sources.add(source);
        source = new TemplateSource();
        Integer sourceId3 = Integer.MIN_VALUE+2;
        source.setId(sourceId3);
        source.setRelease(false);
        source.setSourceEntity(new TemplatesrcEntity());
        sources.add(source);
        when(service.getTemplateSourceChildren(sourceId)).thenReturn(sources);
        when(service.getTemplateSourceChildren(sourceId2)).thenReturn(new ArrayList<TemplateSource>());
        when(service.getTemplateSourceChildren(sourceId3)).thenReturn(new ArrayList<TemplateSource>());
        
        Integer siteId = Integer.MAX_VALUE;
        Site site = new Site();
        site.setId(siteId);
        site.setSiteName("主站");
        Taskable task = new TemplateSourceTask.
            Builder(service, site).
            setPublishIds(new int[]{sourceId}).
            build();
        
        List<TaskProcessable> processes = task.toTaskProcess();
        Assert.assertEquals(2,processes.size());
    }
}
