/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output.event;

import com.ewcms.publication.service.ResourcePublishServiceable;

/**
 * 发布资源事件
 * 
 * @author wangwei
 */
public class ResourceOutputEvent extends DefaultOutputEvent {
    
    private Integer id;
    private ResourcePublishServiceable service;
    
    public ResourceOutputEvent(Integer id,ResourcePublishServiceable service){
        this.id = id;
        this.service = service;
    }
    
    @Override
    public void success(){
        service.publishResource(id);
    }
}
