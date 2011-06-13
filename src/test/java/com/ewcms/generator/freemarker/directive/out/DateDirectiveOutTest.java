/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.out;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateScalarModel;

/**
 * DateDirectiveOut单元测试
 * 
 * @author wangwei
 */
public class DateDirectiveOutTest {

    @Test
    public void testDefaultFormat()throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 0, 01);
        Date date = new Date(calendar.getTimeInMillis());
        
        DateDirectiveOut out = new DateDirectiveOut();
        String format = out.constructOut(date, null, null);
        
        Assert.assertEquals("2010-01-01", format);
    }
    
    @Test
    public void testCustomizeFormat()throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 0, 01);
        Date date = new Date(calendar.getTimeInMillis());
        
        Map<String,TemplateScalarModel> params = new HashMap<String,TemplateScalarModel>();
        params.put("format", new SimpleScalar("yyyy年MM月dd日"));
        
        DateDirectiveOut out = new DateDirectiveOut();
        String format = out.constructOut(date, null, params);
        Assert.assertEquals("2010年01月01日", format);
    }
}
