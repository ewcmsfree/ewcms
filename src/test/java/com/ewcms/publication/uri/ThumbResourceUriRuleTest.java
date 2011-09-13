/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.uri;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * ThumbResourceUriRule单元测试
 * 
 * @author wangwei
 */
public class ThumbResourceUriRuleTest {

    @Test
    public void testGetUri()throws Exception{
        ThumbResourceUriRule rule = new ThumbResourceUriRule("resource");
        
        String uri = rule.getUri();
        Assert.assertTrue(uri.length()>12);
        Assert.assertTrue(StringUtils.contains(uri, "_thumb"));
    }
}
