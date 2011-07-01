/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output;

import java.util.List;

import org.apache.commons.vfs.FileSystemException;

import com.ewcms.core.site.model.Site;
import com.ewcms.generator.ResourceInfo;

/**
 * 输出资源文件到指定发布路径或服务器上
 * 
 * @author wangwei
 */
public interface Outputable {

    /**
     * 输出资源
     * 
     * @param site 站点对象
     * @param infos 发布的资源集合 
     * @throws FileSystemException
     */
    public void out(Site site,List<ResourceInfo> infos)throws FileSystemException;
    
}
