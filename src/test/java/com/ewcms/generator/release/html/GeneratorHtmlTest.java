/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.release.html;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.generator.freemarker.directive.AbstractDirectiveTest;
import com.ewcms.generator.freemarker.directive.page.PageParam;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wangwei
 */
public class GeneratorHtmlTest extends AbstractDirectiveTest {

    @Override
    protected void setDirective(Configuration cfg) {
        //不需要使用
    }

    @Test
    public void testProcess() throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Writer writer = createWriter(stream);
        GeneratorHtmlImpl generator = new GeneratorHtmlImpl();
        generator.process(cfg,writer, getCurrentChannel(),new PageParam());
        String html = stringValueOf(stream.toByteArray());

        Assert.assertEquals(html.trim(), "test generator html");
    }

    private Channel getCurrentChannel() {
        Channel channel = new Channel();
        channel.setId(1);
        Site site = new Site();
        site.setId(1);
        channel.setSite(site);
        channel.setPublicenable(true);

        return channel;
    }

    class GeneratorHtmlImpl extends GeneratorHtml {

        @Override
        protected Template getTemplate(Configuration cfg, Object object) throws IOException {
            return cfg.getTemplate("www/generator.html");
        }

        @Override
        protected Map constructParams(Object object, PageParam pageParam, boolean debug) {
            return new HashMap();
        }
    }
}
