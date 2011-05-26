/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.article;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Related;
import com.ewcms.generator.freemarker.directive.AbstractDirectiveTest;
import com.ewcms.generator.freemarker.directive.article.RelatedDirective;
import com.ewcms.generator.freemarker.directive.article.TitleDirective;
import com.ewcms.generator.freemarker.directive.article.UrlDirective;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wangwei
 */
public class RelatedDirectiveTest extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(RelatedDirectiveTest.class);

    @Override
    protected void setDirective(Configuration cfg) {
        cfg.setSharedVariable("article_relation", new RelatedDirective());
        cfg.setSharedVariable("article_url", new UrlDirective());
        cfg.setSharedVariable("article_title", new TitleDirective());
    }

    @Test
    public void testExecute() throws Exception {
        Template template = cfg.getTemplate("www/article/relation.html");
        cfg.setSharedVariable("arti", getArticle());

        String value = this.process(template, null);
        log.info(value);

        Assert.assertTrue(value.indexOf("test1") != -1);
        Assert.assertTrue(value.indexOf("/new/test1") != -1);
        Assert.assertTrue(value.indexOf("test2") != -1);
        Assert.assertTrue(value.indexOf("/new/test2") != -1);

    }

    @Test
    public void testExecuteLoop()throws Exception{
        Template template = cfg.getTemplate("www/article/relation_loop.html");
        cfg.setSharedVariable("arti", getArticle());

        String value = this.process(template, null);
        log.info(value);

        Assert.assertTrue(value.indexOf("/new/test1--test1") != -1);
        Assert.assertTrue(value.indexOf("/new/test2--test2") != -1);
    }

    private Article getArticle() {
        Article article = new Article();

//        List<Related> relateds = new ArrayList<Related>();
//        Related related = new Related();
//        Article relatedArticle = new Article();
//        relatedArticle.setTitle("test1");
//        relatedArticle.setUrl("/new/test1");
//        related.setArticle(relatedArticle);
//        relateds.add(related);
//
//        related = new Related();
//        relatedArticle = new Article();
//        relatedArticle.setTitle("test2");
//        relatedArticle.setUrl("/new/test2");
//        related.setArticle(relatedArticle);
//        relateds.add(related);
//
//        article.setRelateds(relateds);

        return article;
    }
}
