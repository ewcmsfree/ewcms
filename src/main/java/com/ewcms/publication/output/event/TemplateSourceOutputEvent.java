/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output.event;

import com.ewcms.publication.service.TemplateSourcePublishServiceable;

/**
 * 发布模板资源事件
 * 
 * @author wangwei
 */
public class TemplateSourceOutputEvent extends DefaultOutputEvent {
    private Integer id;
    private TemplateSourcePublishServiceable service;
    
    public TemplateSourceOutputEvent(Integer id,TemplateSourcePublishServiceable service){
        this.id = id;
        this.service = service;
    }
    
    @Override
    public void success(){
        service.publishTemplateSource(id);
    }
}
