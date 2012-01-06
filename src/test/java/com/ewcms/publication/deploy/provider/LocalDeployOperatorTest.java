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
import com.ewcms.publication.deploy.provider.LocalDeployOperator;

/**
 * LocalDeployOperator单元测试
 * 
 * @author wangwei
 */
public class LocalDeployOperatorTest {
    
    @Test
    public void testGetTargtRoot()throws Exception{
        
        DeployOperatorable operator = new LocalDeployOperator.Builder("/tmp").build();
        LocalDeployOperator localOperator = (LocalDeployOperator)operator;
        
        FileObject target = localOperator.getRootFileObject();
        Assert.notNull(target);
        target.close();
    }
}
