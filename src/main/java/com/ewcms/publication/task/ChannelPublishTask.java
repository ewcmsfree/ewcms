/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task;

import java.util.ArrayList;
import java.util.List;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;

/**
 * 频道任务对象
 * 
 * @author wangwei
 */
public class ChannelPublishTask implements Taskable{

    private List<Taskable> children = new ArrayList<Taskable>();
    private final Integer siteId;
    private final Integer channelId;
    private final String channelName;
    
    public ChannelPublishTask(Site site,Channel channel){
        siteId = site.getId();
        channelId = channel.getId();
        channelName = channel.getName();
    }
    
    @Override
    public Object getId() {
        return channelId;
    }

    @Override
    public Boolean isOwnerSite(Integer siteId) {
        return this.siteId == siteId;
    }

    @Override
    public String getTaskName() {
        return channelName + "频道正在发布...";
    }

    @Override
    public Integer complete() {
        // TODO 还未想好如何计算处理进度
        return -1;
    }

    @Override
    public List<Taskable> getChildren() {
        return children;
    }

    @Override
    public void addChild(Taskable task) {
        children.add(task);
    }
}
