/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.deploy.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.ftps.FtpsFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.publication.deploy.DeployOperatorable;

/**
 * 发布指定的资源到ftps服务器上
 * 
 * @author wangwei
 */
public class FtpsDeployOperator extends FtpDeployOperator{

    private static final Logger logger = LoggerFactory.getLogger(FtpsDeployOperator.class);
    
    public static class Builder extends FtpDeployOperator.Builder{

        public Builder(String hostname, String path) {
            super(hostname, path);
        }
        
        public DeployOperatorable build(){
            return new FtpsDeployOperator(this);
        }
    }
    
    public FtpsDeployOperator(Builder builder){
        super(builder);
    }
    
    @Override
    protected FileObject getRootFileObject(FileSystemOptions opts,BuilderBase builder, FileSystemManager manager)throws FileSystemException {
        
        FtpsFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false);
        
        String port = StringUtils.isBlank(builder.getPort()) ? DEFAULT_PORT :builder.getPort();
        
        StringBuilder connBuilder = new StringBuilder();
        connBuilder.append("ftps://");
        connBuilder.append(builder.getHostname()).append(":").append(port);
        connBuilder.append(builder.getPath());
        
        String conn = connBuilder.toString();
        logger.debug("uri is  {}",conn);
        
        return manager.resolveFile(conn, opts);
    }
}
