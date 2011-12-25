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

import com.ewcms.content.resource.model.Resource;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.impl.process.ResourceProcess;
import com.ewcms.publication.task.impl.process.TaskProcessable;

/**
 * ResourceTask单元测试
 * 
 * @author wangwei
 */
public class ResourceTaskTest {

    @Test
    public void testBuildTask(){
        Integer siteId = Integer.MAX_VALUE;
        ResourcePublishServiceable service = mock(ResourcePublishServiceable.class);
        ResourceTask task =new ResourceTask.Builder(service, siteId).
            forceAgain().
            setDescription("site's  resource publish").
            setResourceIds(new Integer[]{}).
            setUsername("test").
            builder();
        
        Assert.assertTrue(task.isAgain());
        Assert.assertEquals(siteId, task.getSiteId());
        Assert.assertNotNull(task.getResourceIds());
        Assert.assertEquals("site's  resource publish", task.getDescription());
        Assert.assertEquals("test", task.getUsername());
    }
    
    private Resource newResource(Integer id,boolean thumb,Resource.State state){
        Resource resource = new Resource();
        resource.setId(1);
        if(thumb){
            resource.setThumbPath("/root/test.jpg");
            resource.setThumbUri("/resource/test.jpg");
        }
        resource.setPath("/root/test.txt");
        resource.setUri("/resource/test.txt");
        resource.setState(state);
        return resource;
    }
    
    @Test
    public void testGetTaskProcessesBySiteId()throws TaskException{
        Integer siteId = Integer.MAX_VALUE;
        ResourcePublishServiceable service = mock(ResourcePublishServiceable.class);
        ResourceTask task =new ResourceTask.Builder(service, siteId).builder();
        List<Resource> resources = new ArrayList<Resource>();
        resources.add(newResource(0,false,Resource.State.NORMAL));
        resources.add(newResource(1,true,Resource.State.NORMAL));
        when(service.findNotReleaseResources(siteId)).thenReturn(resources);
        
        List<TaskProcessable> processes =task.getTaskProcesses();
        Assert.assertEquals(2, processes.size());
        
        ResourceProcess process = (ResourceProcess)processes.get(0);
        Assert.assertEquals(1, process.getPaths().length);
        Assert.assertEquals("/root/test.txt", process.getPaths()[0]);
        Assert.assertEquals(1, process.getUris().length);
        Assert.assertEquals("/resource/test.txt", process.getUris()[0]);
        
        process = (ResourceProcess)processes.get(1);
        Assert.assertEquals(2, process.getPaths().length);
        Assert.assertEquals("/root/test.jpg", process.getPaths()[0]);
        Assert.assertEquals("/root/test.txt", process.getPaths()[1]);
        Assert.assertEquals(2, process.getUris().length);
        Assert.assertEquals("/resource/test.jpg", process.getUris()[0]);
        Assert.assertEquals("/resource/test.txt", process.getUris()[1]);
    }
    
    @Test
    public void testTaskProcessByResourceIds()throws TaskException{
        Integer siteId = Integer.MAX_VALUE;
        ResourcePublishServiceable service = mock(ResourcePublishServiceable.class);
        Integer[] resourceIds = new Integer[]{0,1,2,3};
        ResourceTask task =new ResourceTask.Builder(service, siteId).setResourceIds(resourceIds).builder();
        when(service.getResource(0)).thenReturn(newResource(0,true,Resource.State.NORMAL));
        when(service.getResource(1)).thenReturn(newResource(1,false,Resource.State.INIT));
        when(service.getResource(2)).thenReturn(newResource(2,false,Resource.State.DELETE));
        when(service.getResource(3)).thenReturn(newResource(3,false,Resource.State.RELEASED));
        
        List<TaskProcessable> processes =task.getTaskProcesses();
        Assert.assertEquals(1, processes.size());
        ResourceProcess process = (ResourceProcess)processes.get(0);
        Assert.assertEquals(2, process.getPaths().length);
    }
    
    @Test
    public void testTaskProcessAgainByResourceIds()throws TaskException{
        Integer siteId = Integer.MAX_VALUE;
        ResourcePublishServiceable service = mock(ResourcePublishServiceable.class);
        Integer[] resourceIds = new Integer[]{0,1,2,3};
        ResourceTask task =
            new ResourceTask.Builder(service, siteId).
            setResourceIds(resourceIds).
            forceAgain().
            builder();
        
        when(service.getResource(0)).thenReturn(newResource(0,true,Resource.State.NORMAL));
        when(service.getResource(1)).thenReturn(newResource(1,false,Resource.State.INIT));
        when(service.getResource(2)).thenReturn(newResource(2,false,Resource.State.DELETE));
        when(service.getResource(3)).thenReturn(newResource(3,false,Resource.State.RELEASED));
        
        List<TaskProcessable> processes =task.getTaskProcesses();
        Assert.assertEquals(2, processes.size());
    }
}
