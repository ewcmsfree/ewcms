/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.uri;

import org.junit.Assert;
import org.junit.Test;

/**
 * ResourceUriRule单元测试
 * 
 * @author wangwei
 */
public class ResourceUriRuleTest {

    @Test
    public void testGetUri()throws Exception{
        ResourceUriRule rule = new ResourceUriRule("resource");
        
        String uri = rule.getUri();
        Assert.assertTrue(uri.length()>12);
    }
}
