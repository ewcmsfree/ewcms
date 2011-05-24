/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive.article;

import java.util.Date;
import java.util.Calendar;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.generator.directive.AbstractDirectiveTest;
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

    private ArticleRmc getArticle() {
        ArticleRmc arti = new ArticleRmc();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 6, 28,1,0,0);
        arti.setPublished(new Date(calendar.getTimeInMillis()));
        return arti;
    }
}
