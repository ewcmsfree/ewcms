/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.deploy.provider;

import org.apache.commons.vfs2.FileObject;
import org.junit.Test;
import org.springframework.util.Assert;

import com.ewcms.publication.deploy.DeployOperatorable;
import com.ewcms.publication.deploy.provider.FtpDeployOperator;

/**
 * FtpDeployOperator单元测试
 * 
 * @author wangwei
 */
public class FtpDeployOperatorTest {
    
    @Test
    public void testGetTargtRoot()throws Exception{
        String hostname = "14.136.146.36";
        String username = "itkaizen.com";
        String password = "kaizenidc";
        String path = "/wwwroot";
        
        DeployOperatorable operator = new FtpDeployOperator
                .Builder(hostname, path)
                .setUsername(username)
                .setPassword(password)
                .build();
        
        FtpDeployOperator ftpOperator = (FtpDeployOperator)operator;
        
        FileObject target = ftpOperator.getRootFileObject();
        Assert.notNull(target);
        target.close();
    }
}
