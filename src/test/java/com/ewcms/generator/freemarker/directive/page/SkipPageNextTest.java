/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import org.junit.Assert;
import org.junit.Test;

/**
 * SkipPageNext单元测试
 * 
 * @author wangwei
 */
public class SkipPageNextTest {

    @Test
    public void testNumberIsLast() {
        String url = "http://test.com/dddd_3.html";
        SkipPageNext skip = new SkipPageNext();
        PageOut page = skip.skip(10,9,"next",url);
        Assert.assertEquals("next",page.getLabel());
        Assert.assertEquals(page.getUrl(),null);
        Assert.assertEquals(Integer.valueOf(10), page.getNumber());
    }
    
    @Test
    public void testSkipNext(){
        String url = "http://test.com/dddd_3.html";
        SkipPageNext skip = new SkipPageNext();
        PageOut page = skip.skip(10,8,"next",url);
        Assert.assertEquals("next",page.getLabel());
        Assert.assertEquals(page.getUrl(),url);
        Assert.assertEquals(Integer.valueOf(10), page.getNumber());
        
        page = skip.skip(10,7,"next",url);
        Assert.assertEquals(Integer.valueOf(9), page.getNumber());
    }
    
   
}
