/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output.provider;

import java.util.List;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.output.OutputResource;

/**
 * 发布指定的资源到window共享目录和Samba服务
 * 
 * @author wangwei
 */
public class SmbOutput extends OutputBase {

    private static final Logger logger = LoggerFactory.getLogger(SmbOutput.class);
    
    @Override
    public void out(SiteServer server, List<OutputResource> resources)throws PublishException {
        //TODO VFS1.0没有实现
        throw new PublishException("vfs 1.0 do not provider implement");
    }
    @Override
    protected FileObject getTargetRoot(FileSystemOptions opts,SiteServer server,FileSystemManager manager) throws FileSystemException {
        //TODO VFS1.0没有实现
        return null;
    }
}
