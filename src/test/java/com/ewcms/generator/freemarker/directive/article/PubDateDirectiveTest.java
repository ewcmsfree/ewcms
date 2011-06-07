/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.article;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.content.document.model.Article;
import com.ewcms.generator.freemarker.directive.AbstractDirectiveTest;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 *
 * @author wangwei
 */
public class PubDateDirectiveTest extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(PubDateDirectiveTest.class);

    @Override
    protected void setDirective(Configuration cfg) {
        cfg.setSharedVariable("article_date", new PubDateDirective());
    }

    @Test
    public void testExecute() throws Exception {
        Template template = cfg.getTemplate("www/article/pub_date.html");
        cfg.setSharedVariable("arti", getArticle());

        String value = this.process(template, null);
        log.info(value);
        
        Assert.assertTrue(value.indexOf("2010-07-28") != -1);
        Assert.assertTrue(value.indexOf("2010年07月28日 01时00分00秒") != -1);
    }

    private Article getArticle() {
        Article arti = new Article();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 6, 28,1,0,0);
        arti.setPublished(new Date(calendar.getTimeInMillis()));
        return arti;
    }
}
