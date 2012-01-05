/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication;

import com.ewcms.publication.task.TaskRegistryable;

/**
 * 实现定时发布服务
 * 
 * @author wangwei
 */
public class SchedulePublishFac implements SchedulePublishFacable {

    private final String username = TaskRegistryable.MANAGER_USERNAME;
    private PublishServiceable publishService;
    
    @Override
    public void publishSite(int siteId) throws PublishException {
        publishService.publishSite(siteId, false, username);
    }

    @Override
    public void publishChannel(int channelId, boolean children)throws PublishException {
        publishService.publishChannel(channelId, children, false, username);
    }

    public void setPublishService(PublishServiceable publishService){
        this.publishService = publishService;
    }
}
