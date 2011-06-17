/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.out.channel;

import org.junit.Assert;
import org.junit.Test;

/**
 * ImageDirectiveOut单元测试
 * 
 * @author wangwei
 */
public class ImageDirectiveOutTest {

    @Test
    public void testConstructOut()throws Exception{
        ImageDirectiveOut out = new ImageDirectiveOut();
        String value = out.constructOut("/test", null, null);
        Assert.assertEquals("/test/icon.png", value);
        
        value = out.constructOut("/test/", null, null);
    }
}
