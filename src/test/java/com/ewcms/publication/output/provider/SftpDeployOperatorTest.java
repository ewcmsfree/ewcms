/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output.provider;

import org.apache.commons.vfs2.FileObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * SftpDeployOperator单元测试
 * 
 * @author wangwei
 *
 */
public class SftpDeployOperatorTest {

    @Test
    public void testGetTargtRoot()throws Exception{
        String hostname = "127.0.0.1";
        String username = "wangwei";
        String password = "hhywangwei";
        String path = "/tmp";
        SftpDeployOperator operator = (SftpDeployOperator)new SftpDeployOperator
                .Builder(hostname, path)
                .setUsername(username)
                .setPassword(password)
                .build();
        
        FileObject target = operator.getRootFileObject();
        Assert.assertNotNull(target);
        target.close();
    }
}
