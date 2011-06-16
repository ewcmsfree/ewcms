/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.article;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Content;
import com.ewcms.content.document.model.Relation;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.freemarker.FreemarkerTest;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 文档标签单元测试
 * 
 * @author wangwei
 */
public class ArticlePropertyDirectiveTest extends FreemarkerTest {

    private static final Logger logger = LoggerFactory.getLogger(ArticlePropertyDirectiveTest.class);
    
    @Override
    @SuppressWarnings("deprecation")
    protected void currentConfiguration(Configuration cfg) {
        cfg.setSharedVariable("article_author", new AuthorDirective());
        cfg.setSharedVariable("article_content", new ContentDirective());
        cfg.setSharedVariable("article_eauthor", new EauthorDirective());
        cfg.setSharedVariable("article_image", new ImageDirective());
        cfg.setSharedVariable("article_origin", new OriginDirective());
        cfg.setSharedVariable("article_pubDate", new PubDateDirective());
        cfg.setSharedVariable("article_relation", new RelatedDirective());
        cfg.setSharedVariable("article_shortTitle", new ShortTitleDirective());
        cfg.setSharedVariable("article_subTitle", new SubTitleDirective());
        cfg.setSharedVariable("article_summary", new SummaryDirective());
        cfg.setSharedVariable("article_title", new TitleDirective());
        cfg.setSharedVariable("article_url", new UrlDirective());
    }

    /**
     * 得到模板路径
     * 
     * @param name 模板名
     * @return
     */
    private String getTemplatePath(String name){
        return String.format("directive/article/%s", name);
    }
    
    @Test
    public void testArticleHtml() throws Exception {
        Template template = cfg.getTemplate(getTemplatePath("article_property.html"));

        Map<Object,Object> params= new HashMap<Object,Object>();
        params.put(GlobalVariable.DOCUMENT.toString(), initArticle());
        String value = this.process(template, params);
        
        logger.info(value);
        value = StringUtils.deleteWhitespace(value);
        Assert.assertTrue(value.indexOf("content0") != -1);
        Assert.assertTrue(value.indexOf("</html>") != -1);
    }

    private Article initArticle() {
        Article value = new Article();
        value.setId(Long.valueOf(0));
        value.setAudit("editor");
        value.setAuditReal("编辑");
        value.setAuthor("authro");
        
        List<Content> contents= new ArrayList<Content>();
        for (int i = 0; i < 3; ++i) {
            Content content = new Content();
            content.setDetail("content" + String.valueOf(i));
            content.setPage(i + 1);
            contents.add(content);
        }
        value.setContents(contents);
        
        value.setImage("/image/1.jpg");
        value.setOrigin("origin");
        value.setPublished(new Date());
        
        List<Relation> relations = new ArrayList<Relation>();
        for(int i = 0 ; i < 3; i++){
            Relation relation = new Relation();
            Article article = new Article();
            article.setTitle("relation title" + String.valueOf(i));
            article.setUrl("/article/"+String.valueOf(i)+".html");
            relation.setArticle(article);
            relations.add(relation);
        }
        value.setRelations(relations);
        
        value.setShortTitle("shortTitle");
        value.setSubTitle("subTitle");
        value.setSummary("summary");
        value.setTitle("title");
        value.setUrl("/url");
        
        return value;
    }
}
