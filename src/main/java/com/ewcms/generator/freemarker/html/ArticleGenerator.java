/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.content.document.model.Article;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.generator.ReleaseException;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.output.OutputResource;
import com.ewcms.generator.output.event.ArticleOutputEvent;
import com.ewcms.generator.service.ArticleLoaderServiceable;
import com.ewcms.generator.uri.DefaultArticleUriRule;
import com.ewcms.generator.uri.UriRuleable;

import freemarker.template.Configuration;

/**
 * 生成文章页
 *
 * @author wangwei
 */
public class ArticleGenerator extends GeneratorHtmlBase {

    private static final Logger logger = LoggerFactory.getLogger(ArticleGenerator.class);
    
    private static final Integer MAX_PAGES = 1000;
    
    private Configuration cfg;
    private Site site;
    private Channel channel;
    private ArticleLoaderServiceable service;
    private Integer maxPages;
    
    UriRuleable uriRule = new DefaultArticleUriRule();
    
    public ArticleGenerator(Configuration cfg,Site site,
            Channel channel,ArticleLoaderServiceable service){
        
        this(cfg,site,channel,service,MAX_PAGES);
    }
    
    public ArticleGenerator(Configuration cfg,Site site,
            Channel channel,ArticleLoaderServiceable service,Integer maxPages){
        
        this.cfg = cfg;
        this.site = site;
        this.channel = channel;
        this.service = service;
        this.maxPages = maxPages;
    }
    
    @Override
    public List<OutputResource> process(Template template)throws ReleaseException {
        List<OutputResource> resources = new ArrayList<OutputResource>();
        freemarker.template.Template t = getFreemarkerTemplate(cfg,template.getUniquePath());
        List<Article> articles = findReleaseArticles();
        logger.debug("aritcle count is {}",articles.size());
        for(Article article : articles){
            OutputResource resource = new OutputResource();
            Integer count = article.getContentTotal();
            
            if(count == 0){
                continue;
            }
            
            for(int i = 0 ; i < count; ++i){
                Map<String,Object> parameters = constructParameters(article,i,count);
                resource.addChild(generator(t,parameters,uriRule));
            }
            String url = resource.getChildren().get(0).getReleasePath();
            resource.registerEvent(new ArticleOutputEvent(article.getId(),url,service));
            resources.add(resource);
        }
        
        return resources;
    }
    
    private List<Article> findReleaseArticles(){
        return service.findReleaseArticles(channel.getId(), maxPages);
    }
    
    private Map<String,Object> constructParameters(Article article,Integer pageNumber,Integer pageCount) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.SITE.toString(), site);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        params.put(GlobalVariable.DOCUMENT.toString(), article);
        params.put(GlobalVariable.PAGE_NUMBER.toString(), pageNumber);
        params.put(GlobalVariable.PAGE_COUNT.toString(), pageCount);
        return params;
    }
    
}
