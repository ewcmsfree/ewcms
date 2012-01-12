/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl.event;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;

/**
 * TemplateSourceEvent单元测试
 * 
 * @author wangwei
 */
public class TemplateSourceEventTest {

    @Test
    public void testSuccessAfter(){
        TemplateSourcePublishServiceable  service = mock(TemplateSourcePublishServiceable.class);
        TemplateSource source = new TemplateSource();
        source.setId(Integer.MAX_VALUE);
        
        AtomicInteger completeNumber = new AtomicInteger(0);
        TemplateSourceEvent event = new TemplateSourceEvent(completeNumber,source,service);
        event.successAfter("");
        
        verify(service,times(1)).publishTemplateSource(Integer.MAX_VALUE);
    }
}
