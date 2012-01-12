/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl.event;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.Assert;

import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;

/**
 * 发布模板资源事件
 * 
 * @author wangwei
 */
public class TemplateSourceEvent extends CompleteEvent {
    private final TemplateSource source;
    private final TemplateSourcePublishServiceable service;
    
    public TemplateSourceEvent(AtomicInteger completeNumber,
            TemplateSource source,TemplateSourcePublishServiceable service){
        super(completeNumber);
        Assert.notNull(source,"Template source is null");
        Assert.notNull(service,"Template source service is null");
        this.source = source;
        this.service = service;
    }
    
    @Override
    public void successAfter(String uri){
        service.publishTemplateSource(source.getId());
    }
}
