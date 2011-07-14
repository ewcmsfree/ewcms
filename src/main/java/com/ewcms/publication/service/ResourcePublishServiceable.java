/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.service;

import java.util.List;

import com.ewcms.content.resource.model.Resource;

/**
 * 资源加载和操作接口
 * <br>
 * 提供发布资源所需要的数据，并更改以发布发状态。
 * 
 * @author wangwei
 */
public interface ResourcePublishServiceable {

    /**
     * 得到资源
     * 
     * @param id  资源编号
     * @return
     */
    Resource getResource(Integer id);
    
    /**
     * 查询需要发布的资源
     * 
     * @param siteId 站点编号
     * @return
     */
    List<Resource> findPublishResource(Integer siteId);

    /**
     * 发布资源
     * <br>
     * 更新资源状态到发布状态
     * 
     * @param id 资源编号
     */
    void publishResource(Integer id);
    
    /**
     * 把已经发布的资源变成未发布资源。
     *  
     * @param siteId 站点编号
     */
    void againPublishResource(Integer siteId);
}
