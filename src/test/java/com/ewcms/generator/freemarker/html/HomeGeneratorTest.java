/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.generator.output.OutputResource;

import freemarker.template.Configuration;

/**
 * SingleCeneratorHtml单元测试
 * 
 * @author wangwei
 */
public class HomeGeneratorTest extends GeneratorHtmlTest {

    @Override
    protected void currentConfiguration(Configuration cfg) {
        //不需要
    }
    
    @Test
    public void testHomeTemplate()throws Exception{
        GeneratorHtmlable generator = new HomeGenerator(cfg,initSite(),initChannel());
        List<OutputResource> list = generator.process(initTemplate(getTemplatePath("index.html")));
        Assert.assertFalse(list.isEmpty());
        
        OutputResource info = list.get(0);
        String content = getContent(info.getPath());
        content = StringUtils.deleteWhitespace(content);
        Assert.assertEquals("homepage", content);
        Assert.assertEquals("news/cn/index.html", info.getUri());
    }
}
