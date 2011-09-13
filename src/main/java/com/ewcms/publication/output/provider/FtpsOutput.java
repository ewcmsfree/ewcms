/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output.provider;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.ftps.FtpsFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.SiteServer;

/**
 * 发布指定的资源到ftps服务器上
 * 
 * @author wangwei
 */
public class FtpsOutput extends OutputBase{

    private static final Logger logger = LoggerFactory.getLogger(FtpsOutput.class);
    
    @Override
    protected FileObject getTargetRoot(FileSystemOptions opts,SiteServer server, FileSystemManager manager)throws FileSystemException {
        
        FtpsFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false);
        
        StringBuilder builder = new StringBuilder();
        builder.append("ftps://");
        builder.append(server.getHostName()).append(":").append(server.getPort());
        builder.append(server.getPath());
        
        String address = builder.toString();
        logger.debug("uri is  {}",address);
        
        return manager.resolveFile(address, opts);
    }

}
