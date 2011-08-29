/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.preview.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleCategory;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.content.document.model.ArticleType;
import com.ewcms.content.document.model.Content;
import com.ewcms.publication.service.ArticlePublishServiceable;

/**
 * 模拟ArticlePublishSerivceable实现，为模板预览提供文章模拟数据。
 * 
 * @author wangwei
 */
public class MockArticlePublishService implements ArticlePublishServiceable{

    private static final Random random = new Random();
    
    private static final String DEFAULT_TITLE = "测试文章-测试文章-测试文章-测试文章-测试文章-测试文章-";
    
    @Override
    public Article getArticle(Long id) {
        return ARTICLE_DEFAULT;
    }

    @Override
    public void publishArticle(Long id, String url) {
        // it's not mock
    }

    @Override
    public List<Article> findPreReleaseArticles(Integer channelId, Integer limit) {
        // it's not mock
        return new ArrayList<Article>();
    }

    @Override
    public List<Article> findReleaseArticlePage(Integer channelId, Integer page, Integer row, Boolean top) {
        List<Article> articles = new ArrayList<Article>();
        for(int i = 0 ; i < row ; i++){
            articles.add(createArticle(i,top));
        }
        return articles;
    }

    @Override
    public int getArticleCount(Integer channelId) {
        return 1000;
    }

    @Override
    public void updatePreRelease(Integer channelId) {
        // it's not mock
    }
    
    private static final Article ARTICLE_DEFAULT ;
    
    static{
        Article article = new Article();
        article.setAudit("audit test");
        article.setAuditReal("编辑测试员");
        article.setAuthor("作者测试员");
        article.setCommentFlag(true);
        article.setContentTotal(5);
        article.setCreateTime(new Date());
        article.setDeleteFlag(false);
        article.setId(Long.MIN_VALUE);
        article.setInside(false);
        article.setKeyword("模板 预览");
        article.setModified(new Date());
        article.setOrigin("ewcms group");
        article.setOwner("test");
        article.setPublished(new Date());
        article.setShortTitle("模板预览");
        article.setStatus(ArticleStatus.RELEASE);
        article.setSubTitle("模板预览子标题");
        article.setSummary("模板预览,显示用户设置的模板样式。");
        article.setTag("preview");
        article.setTitle("文章模板预览");
        article.setTopFlag(true);
        article.setType(ArticleType.GENERAL);
        article.setUrl("#");
        
        List<ArticleCategory> categories = new ArrayList<ArticleCategory>();
        ArticleCategory category = new ArticleCategory();
        category.setCategoryName("文章");
        categories.add(category);
        category = new ArticleCategory();
        category.setCategoryName("图文");
        categories.add(category);
        article.setCategories(categories);
        
        StringBuilder builder = new StringBuilder();
        builder.append("CMS具有许多基于模板的优秀设计，可以加快网站开发的速度和减少开发的成本。CMS的功能并不只限于文本处理，它也可以处理图片、Flash动画、声像流、图像甚至电子邮件档案。<br>");
        builder.append("CMS还分各个平台脚本种类的。<br>");
        builder.append("内容管理系统是企业信息化建设和电子政务的新宠，也是一个相对较新的市场，对于内容管理，业界还没有一个统一的定义，不同的机构有不同的理解：<br>");
        builder.append("Gartner Group 认为内容管理从内涵上应该包括企业内部内容管理、Web内容管理、电子商务交易内容管理和企业外部网(Extranet)信息共享内容管理（如CRM和SCM等），Web内容管理是当前的重点，e-business和XML是推动内容管理发展的源动力。<br>");
        builder.append("Merrill Lynch的分析师认为内容管理侧重于企业员工、企业用户、合作伙伴和供应商方便获得非结构化信息的处理过程。内容管理的目的是把非结构化信息出版到intranets, extranets和ITE(Internet Trading Exchanges), 从而使用户可以检索、使用、分析和共享。商业智能系统(BI)侧重于结构化数据的价值提取，而内容管理则侧重于企业内部和外部非结构化资源的战略价值提取。<br>");
        builder.append("Giga Group 认为作为电子商务引擎，内容管理解决方案必须和电子商务服务器紧密集成，从而形成内容生产(Production)、传递(Delivery)以及电子商务端到端系统。<br>");
        List<Content> contents = new ArrayList<Content>();
        Content content = new Content();
        content.setDetail(builder.toString());
        content.setPage(1);
        article.setContents(contents);
        
        ARTICLE_DEFAULT = article;
    }
    
    
    private Article createArticle(int index,boolean top){
        Article article = new Article();
        article.setAudit("audit test");
        article.setAuditReal("编辑测试员");
        article.setAuthor("作者测试员");
        article.setCommentFlag(true);
        article.setContentTotal(5);
        article.setCreateTime(new Date());
        article.setDeleteFlag(false);
        article.setId(new Long(index));
        article.setInside(false);
        article.setKeyword("模板 预览");
        article.setModified(new Date());
        article.setOrigin("ewcms group");
        article.setOwner("test");
        article.setPublished(new Date());
        article.setShortTitle("模板预览");
        article.setStatus(ArticleStatus.RELEASE);
        article.setSubTitle("模板预览子标题");
        article.setSummary("模板预览,显示用户设置的模板样式。");
        article.setTag("preview");
        int len = Math.abs(random.nextInt()) % 5;
        len = (len == 0 ? 1 : len);
        article.setTitle(DEFAULT_TITLE.substring(len * 5));
        article.setTopFlag(top);
        article.setType(ArticleType.GENERAL);
        article.setUrl("#");
        
        return article;
        
    }
}
