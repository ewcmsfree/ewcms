/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.lang.StringUtils;

import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.freemarker.FreemarkerTest;
import com.ewcms.generator.freemarker.GlobalVariable;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * PositionDirective单元测试
 * 
 * @author wangwei
 */
public class PositionDirectiveTest extends FreemarkerTest {

    @Override
    protected void currentConfiguration(Configuration cfg) {
        cfg.setSharedVariable("position", new PositionDirective());
        cfg.setSharedVariable("channel", new ChannelDirective());
    }

    /**
     * 得到模板路径
     * 
     * @param name
     *            模板名
     * @return
     */
    private String getTemplatePath(String name) {
        return String.format("directive/position/%s", name);
    }

    @Test
    public void testOutTemplate() throws Exception {
        Template template = cfg.getTemplate(getTemplatePath("position_out.html"));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(GlobalVariable.CHANNEL.toString(), initChannel());

        String value = this.process(template, params);

        String expected = "<a href=\"http://www.jict.org/grand\">grand</a>&gt;"
                + "<a href=\"http://www.jict.org/parent\">parent</a>&gt;" 
                + "<a href=\"http://www.jict.org/channel\">channel</a>";
        
        Assert.assertEquals(expected, value);
    }

    @Test
    public void testBodyTemplate() throws Exception {
        Template template = cfg.getTemplate(getTemplatePath("position_body.html"));
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(GlobalVariable.CHANNEL.toString(), initChannel());

        String value = this.process(template, params);
        String expected = "<a href='http://www.jict.org/grand'>grand</a>&gt;"
            + "<a href='http://www.jict.org/parent'>parent</a>&gt;" 
            + "<a href='http://www.jict.org/channel'>channel</a>";
        
        value = StringUtils.replace(value, "\n", "");
        Assert.assertEquals(expected, value);
    }
    
    @Test
    public void testLoopTemplate() throws Exception {
        Template template = cfg.getTemplate(getTemplatePath("position_loop.html"));
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(GlobalVariable.CHANNEL.toString(), initChannel());

        String value = this.process(template, params);
        String expected = "<a href='http://www.jict.org/grand'>grand</a>&gt;"
            + "<a href='http://www.jict.org/parent'>parent</a>&gt;" 
            + "<a href='http://www.jict.org/channel'>channel</a>";
        
        value = StringUtils.replace(value, "\n", "");
        Assert.assertEquals(expected, value);
    }

    private Channel initChannel() {
        Channel channel = new Channel();

        channel.setName("channel");
        channel.setAbsUrl("http://www.jict.org/channel");

        Channel parent = new Channel();
        parent.setName("parent");
        parent.setAbsUrl("http://www.jict.org/parent");
        channel.setParent(parent);

        Channel grand = new Channel();
        grand.setName("grand");
        grand.setAbsUrl("http://www.jict.org/grand");
        parent.setParent(grand);

        return channel;
    }
}
