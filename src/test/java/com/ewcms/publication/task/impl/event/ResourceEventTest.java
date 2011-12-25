/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl.event;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.task.Taskable;

public class ResourceEventTest {

    @Test
    public void testSuccessAfter(){
        Taskable task = mock(Taskable.class);
        ResourcePublishServiceable service = mock(ResourcePublishServiceable.class);
        Resource resource = new Resource();
        resource.setId(Integer.MAX_VALUE);
        
        ResourceEvent event = new ResourceEvent(task,resource,service);
        event.successAfter("");
        
        verify(service,times(1)).publishResource(Integer.MAX_VALUE);
    }
}
