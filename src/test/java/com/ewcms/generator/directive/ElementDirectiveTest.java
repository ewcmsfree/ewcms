/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive;

import com.ewcms.content.document.model.Article;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author wangwei
 */
public class ElementDirectiveTest extends AbstractDirectiveTest {

    private static final String DEFAULT_VARIABLE_NAME = "article";
    private static final Log log = LogFactory.getLog(ElementDirectiveTest.class);

    @Override
    protected void setDirective(Configuration cfg) {
        cfg.setSharedVariable("article_test", new ElementDirectiveImpl());
    }

    @Test
    public void testExecute() throws Exception {
        Template template = cfg.getTemplate("www/element/element.html");
        cfg.setSharedVariable("arti", getArticle("test title"));

        String value = this.process(template, null);
        log.info(value);
        
        Assert.assertTrue(value.indexOf("test title") != -1);
    }

    @Test
    public void testExecuteParameters() throws Exception {
        Template template = cfg.getTemplate("www/element/element.html");

        Map map = new HashMap();
        map.put("arti", getArticle("test title1"));
        String value = this.process(template, map);
        log.info(value);
        
        Assert.assertTrue(value.indexOf("test title1") != -1);
    }

    @Test
    public void testExecuteDefault()throws Exception{
        Template template = cfg.getTemplate("www/element/element_default.html");
        cfg.setSharedVariable("article", getArticle("test title default"));

        String value = this.process(template, null);
        log.info(value);

        Assert.assertTrue(value.indexOf("test title default") != -1);
    }

    @Test
    public void testExecuteList() throws Exception {

        List<Article> list = new ArrayList<Article>();
        for (int i = 0; i < 10; ++i) {
            list.add(getArticle("test title" + String.valueOf(i)));
        }

        Template template = cfg.getTemplate("www/element/element_value.html");
        Map map = new HashMap();
        map.put("artis", list);
        String value = this.process(template, map);
        System.out.println(value);
        Assert.assertTrue(value.indexOf("test title0") != -1);
        Assert.assertTrue(value.indexOf("test title9") != -1);
    }

    @Test
    public void testExecuteDebug()throws Exception{
        Template template = cfg.getTemplate("www/element/element.html");
        cfg.setSharedVariable(DirectiveVariable.Debug.toString(), cfg.getObjectWrapper().wrap(true));

        String value = this.process(template, null);
        log.info(value);

        Assert.assertTrue(value.indexOf("arti的值不存在") != -1);
    }

     @Test
    public void testExecuteError()throws Exception{
        try{
            Template template = cfg.getTemplate("www/element/element_error.html");
            cfg.setSharedVariable(DirectiveVariable.Debug.toString(), cfg.getObjectWrapper().wrap(true));
            String value = this.process(template, null);
            log.info(value);
        }catch(TemplateException e){
            System.out.println(e.getMessage());
            log.error(e.getMessage());
        }

//        Assert.assertTrue(value.indexOf("arti的值不存在") != -1);
    }


    private Article getArticle(String title) {
        Article arti = new Article();
        arti.setTitle(title);

        return arti;
    }

    class ElementDirectiveImpl extends ElementDirective<Article> {

        @Override
        protected String defaultVariable() {
            return DEFAULT_VARIABLE_NAME;
        }

        @Override
        protected String constructOutValue(Article article) {
            return article.getTitle();
        }
    }
}
