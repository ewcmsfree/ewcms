/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.content.document.model.Article;
import com.ewcms.generator.freemarker.FreemarkerTest;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 *
 * @author wangwei
 */
public class CountDirectiveTest extends FreemarkerTest {

    private static final Log log = LogFactory.getLog(CountDirectiveTest.class);

    @Override
    protected void currentConfiguration(Configuration cfg) {
//        cfg.setSharedVariable("component_count", new CountDirective());
    }

    @Test
    public void testExecute() throws Exception {
        Template template = cfg.getTemplate("www/component/count.html");
        cfg.setSharedVariable("arti", getArticle());

        String value = this.process(template, null);
        log.info(value);

        Assert.assertTrue(value.indexOf("browse_count_element_id") != -1);
        Assert.assertTrue(value.indexOf("article_id=20") != -1);
        Assert.assertTrue(value.indexOf("channel_id=10") != -1);
        Assert.assertTrue(value.indexOf("callback=browse_count_callback") != -1);
    }

    @Test
    public void testExecuteElementId() throws Exception {
        Template template = cfg.getTemplate("www/component/count_element.html");
        cfg.setSharedVariable("arti", getArticle());

        String value = this.process(template, null);
        log.info(value);

        Assert.assertTrue(value.indexOf("test_browse_element_id") != -1);
        Assert.assertTrue(value.indexOf("article_id=20") != -1);
        Assert.assertTrue(value.indexOf("channel_id=10") != -1);
        Assert.assertTrue(value.indexOf("callback=browse_count_callback") != -1);
    }

    @Test
    public void testExecuteCallBack() throws Exception {
        Template template = cfg.getTemplate("www/component/count_callback.html");
        cfg.setSharedVariable("arti", getArticle());

        String value = this.process(template, null);
        log.info(value);

        Assert.assertTrue(value.indexOf("test_browse_element_id") == -1);
        Assert.assertTrue(value.indexOf("article_id=20") != -1);
        Assert.assertTrue(value.indexOf("channel_id=10") != -1);
        Assert.assertTrue(value.indexOf("callback=test_browse_callback") != -1);
    }

    private Article getArticle() {
        Article arti = new Article();

//        arti.setId(20);
//        Channel channel = new Channel();
//        channel.setId(10);
//        arti.setChannel(channel);

        return arti;
    }
}
