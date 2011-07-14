/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.publication.freemarker.FreemarkerTest;
import com.ewcms.publication.freemarker.GlobalVariable;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.SimpleNumber;

/**
 * IndexDirective单元测试
 * 
 * @author wangwei
 */
public class IndexDirectiveTest extends FreemarkerTest{

    @Override
    protected void currentConfiguration(Configuration cfg) {
        IndexDirective directive = new IndexDirective();
        cfg.setSharedVariable("index", directive);
    }
    
    @Test
    public void testValueTemplate()throws Exception{
        Template template = cfg.getTemplate("directive/index/value.html");
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.INDEX.toString(),new SimpleNumber(2));
        
        String value = this.process(template, params);
        
        Assert.assertEquals("2", value);
    }
}
