/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl.event;

import org.springframework.util.Assert;

import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.Taskable;

/**
 * 发布模板资源事件
 * 
 * @author wangwei
 */
public class TemplateSourceEvent extends CompleteEvent {
    private final TemplateSource source;
    private final TemplateSourcePublishServiceable service;
    
    public TemplateSourceEvent(Taskable task,TemplateSource source,
            TemplateSourcePublishServiceable service){
        super(task);
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
