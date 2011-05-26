/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.component;

import com.ewcms.content.document.model.Article;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.freemarker.directive.AbstractDirectiveTest;
import com.ewcms.generator.freemarker.directive.component.CommentDirective;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wangwei
 */
public class CommentDirectiveTest extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(CommentDirectiveTest.class);

    @Override
    protected void setDirective(Configuration cfg) {
        cfg.setSharedVariable("component_comment", new CommentDirective());
    }

    @Test
    public void testExecute() throws Exception {
        Template template = cfg.getTemplate("www/component/comment.html");
        cfg.setSharedVariable("arti", getArticle());

        String value = this.process(template, null);
        log.info(value);

        Assert.assertTrue(value.indexOf("comment-warpper") != -1);
        Assert.assertTrue(value.indexOf("js/jquery-1.4.2.min.js") != -1);
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
