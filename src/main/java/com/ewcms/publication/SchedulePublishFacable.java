/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication;

/**
 * 定时发布服务接口
 * 
 * @author wangwei
 */
public interface SchedulePublishFacable {
    
    /**
     * 发布站点
     * <br>
     * 发布站点需要发布的资源和内容。
     * 
     * @param siteId 站点编号
     * @throws PublishException
     */
    void publishSite(int siteId)throws PublishException;
    
    /**
     * 发布指定频道内容
     * <br>
     * 发布频道需要发布的资源和内容。可以发布关联子频道。
     * 
     * @param channelId 频道编号
     * @param children true:发布子频道
     * 
     * @throws PublishException
     */
    void publishChannel(int channelId,boolean children) throws PublishException;
}
