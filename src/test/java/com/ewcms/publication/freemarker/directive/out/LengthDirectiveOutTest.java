/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.out;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;

/**
 * LengthDirectiveOut单元测试
 * 
 * @author wangwei
 */
public class LengthDirectiveOutTest {

    @Test
    public void testNotLimitLengthConstructOut()throws Exception{
        LengthDirectiveOut out = new LengthDirectiveOut();
        String outValue = out.constructOut("test", null, null);
        Assert.assertEquals("test", outValue);
    }
    
    @Test
    public void testLengthConstructOut()throws Exception{
        LengthDirectiveOut out = new LengthDirectiveOut();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("length", new SimpleNumber(2));
        String outValue = out.constructOut("test", null, params);
        Assert.assertEquals("te...", outValue);
    }
    
    @Test
    public void testMarkConstructOut()throws Exception{
        LengthDirectiveOut out = new LengthDirectiveOut();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("length", new SimpleNumber(2));
        params.put("mark", new SimpleScalar("---"));
        String outValue = out.constructOut("test", null, params);
        Assert.assertEquals("te---", outValue);
    }
}
