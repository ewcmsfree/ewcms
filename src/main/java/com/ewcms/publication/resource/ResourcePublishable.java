/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.resource;

import com.ewcms.publication.PublishException;


/**
 * 发布站点资源接口
 * 
 * @author wangwei
 */
public interface ResourcePublishable {
    /**
     * 发布站点资源
     * <br>
     * 发布站点下未发布所有资源。
     * 
     * @param id 站点编号
     * @throws PublishException
     */
    void publishSite(Integer id)throws PublishException;
    
    /**
     * 重新发布站点资源
     * <br>
     * 重新发布站点所有资源，不管是否已经发布。
     * 
     * @param id 站点编号
     * @throws PublishException
     */
    void publishSiteAgain(Integer id)throws PublishException;

    /**
     * 发布单个资源
     * <br>
     * 发布单个资源，不管它是否已经发布。
     * 
     * @param id 资源编号
     * @param templateSource 模板资源
     * @throws PublishException
     */
    void publishAgain(Integer id,Boolean templateSource)throws PublishException;
}
