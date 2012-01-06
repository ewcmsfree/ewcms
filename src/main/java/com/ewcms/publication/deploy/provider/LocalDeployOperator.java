/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.deploy.provider;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.publication.deploy.DeployOperatorable;

/**
 * 发布指定的资源到指定目录上
 * 
 * @author wangwei
 */
public class LocalDeployOperator extends DeployOperatorBase {
    
    private static Logger logger = LoggerFactory.getLogger(LocalDeployOperator.class);
    
    public static class Builder extends BuilderBase{
        public Builder(String path){
            this.path = path;
        }
        
        public DeployOperatorable build(){
            return new LocalDeployOperator(this);
        }
    }
    
    public LocalDeployOperator(Builder builder) {
        super(builder);
    }
    
    @Override
    protected FileObject getRootFileObject(FileSystemOptions opts,BuilderBase builder,FileSystemManager manager) throws FileSystemException {
        
        StringBuilder connBuilder = new StringBuilder();
        connBuilder.append("file://");
        connBuilder.append(builder.getPath());
        
        String conn = connBuilder.toString();
        logger.debug("uri is {}",conn);
        
        return manager.resolveFile(conn);
    }
}
