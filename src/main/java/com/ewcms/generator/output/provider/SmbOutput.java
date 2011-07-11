/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output.provider;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;

import com.ewcms.core.site.model.SiteServer;

/**
 * 发布指定的资源到window共享目录中
 * 
 * @author wangwei
 */
public class SmbOutput extends OutputBase {

    @Override
    protected FileObject getTargetRoot(SiteServer server,FileSystemManager manager) throws FileSystemException {
        // TODO Auto-generated method stub
        return null;
    }
}
