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
     * <br>
     * 再发布时会得到所有要发布的资源（包括：normal和released）。
     * 
     * @param siteId 站点编号
     * @param forceAgain 再发布 
     * @return 需要发布的资源
     */
    List<Resource> findPublishResources(Integer siteId, Boolean forceAgain);

    /**
     * 发布资源成功
     * <br>
     * 标示资源为发布状态。
     * 
     * @param id 资源编号
     */
    void publishResourceSuccess(Integer id);
}
