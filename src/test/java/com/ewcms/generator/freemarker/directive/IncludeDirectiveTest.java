/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.generator.freemarker.FreemarkerTest;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.service.ChannelPublishServiceable;
import com.ewcms.generator.service.TemplatePublishServiceable;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * IncludeDirective单元测试
 *
 * @author wangwei
 */
public class IncludeDirectiveTest extends FreemarkerTest {

    @Test
    public void testGetUniqueTemplatePath(){
       IncludeDirective directive = new IncludeDirective();
       String path = "/home/test.html";
       Integer siteId=new Integer(2);
       
       String uPath = directive.getUniqueTemplatePath(siteId, path);
       Assert.assertEquals("2/home/test.html", uPath);
       path = "home/test.html";
       uPath = directive.getUniqueTemplatePath(siteId, path);
       Assert.assertEquals("2/home/test.html", uPath);
    }
    
    @Override
    protected void currentConfiguration(Configuration cfg) {
        IncludeDirective directive = new IncludeDirective();
        ChannelPublishServiceable channelLoaderService = mock(ChannelPublishServiceable.class);
        Channel channel = new Channel();
        channel.setId(1);
        when(channelLoaderService.getChannelByUrlOrPath(any(Integer.class), any(String.class))).thenReturn(channel);
        directive.setChannelService(channelLoaderService);
        TemplatePublishServiceable templateLoaderService = mock(TemplatePublishServiceable.class);
        when(templateLoaderService.getUniquePathOfChannelTemplate(any(Integer.class), any(Integer.class), any(String.class))).thenReturn("2/1/include.html");
        directive.setTemplateService(templateLoaderService);
        cfg.setSharedVariable("include", directive);
    }
    
    /**
     * 得到模板路径
     * 
     * @param name
     *            模板名
     * @return
     */
    private String getTemplatePath(String name) {
        return String.format("directive/include/%s", name);
    }
    
    @Test
    public void testPathTemplate() throws Exception {
        Template template = cfg.getTemplate(getTemplatePath("path.html"));
        Map<String,Object> params = new HashMap<String,Object>();
        Site site = new Site();
        site.setId(2);
        params.put(GlobalVariable.SITE.toString(), site);
        String value = process(template,params);
        value = StringUtils.deleteWhitespace(value);
        Assert.assertEquals("test-path-include", value);
    }
    
    @Test
    public void testChannelTemplate()throws Exception{
        Template template = cfg.getTemplate(getTemplatePath("channel.html"));
        
        Map<String,Object> params = new HashMap<String,Object>();
        Site site = new Site();
        site.setId(2);
        params.put(GlobalVariable.SITE.toString(), site);
        Channel channel = new Channel();
        channel.setId(1);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        String value = process(template,params);
        value = StringUtils.deleteWhitespace(value);
        Assert.assertEquals("test-channel-includetest-channel-includetest-channel-include", value);
    }
}
