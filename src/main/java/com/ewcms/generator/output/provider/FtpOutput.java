/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output.provider;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.SiteServer;

/**
 * 发布指定的资源到ftp服务器上
 * 
 * @author wangwei
 */
public class FtpOutput extends OutputBase{

    private static final Logger logger = LoggerFactory.getLogger(FtpOutput.class);
    
    @Override
    protected FileObject getTargetRoot(FileSystemOptions opts,SiteServer server,FileSystemManager manager) throws FileSystemException {
        
        StringBuilder builder = new StringBuilder();
        builder.append("ftp://");
        builder.append(server.getHostName()).append(":").append(server.getPort());
        builder.append(server.getPath());
        
        String address = builder.toString();
        logger.debug("uri is  {}",address);
        
        return manager.resolveFile(address, opts);
    }
}
