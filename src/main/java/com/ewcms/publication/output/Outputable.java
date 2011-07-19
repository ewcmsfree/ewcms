/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output;

import java.util.List;

import org.apache.commons.vfs.FileSystemException;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.PublishException;

/**
 * 发布资源接口
 * 
 * @author wangwei
 */
public interface Outputable {

    /**
     * 输出资源
     * 
     * @param server 发布服务
     * @param resources 发布的资源集合 
     * @throws FileSystemException
     */
    public void out(SiteServer server,List<OutputResource> resources)throws PublishException;
    
}
