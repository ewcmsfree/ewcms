/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.html;

import java.io.OutputStream;
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
import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.output.OutputResource;
import com.ewcms.publication.output.event.ArticleOutputEvent;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.uri.DefaultArticleUriRule;
import com.ewcms.publication.uri.UriRuleable;

import freemarker.template.Configuration;

/**
 * 生成文章页
 *
 * @author wangwei
 */
public class DetailGenerator extends GeneratorBase {

    private static final Logger logger = LoggerFactory.getLogger(DetailGenerator.class);
    
    private static final Integer MAX_ARTICLES = 1000;
    
    private Configuration cfg;
    private ArticlePublishServiceable service;
    private Integer maxArticles;
    
    UriRuleable uriRule = new DefaultArticleUriRule();
    
    public DetailGenerator(Configuration cfg,ArticlePublishServiceable service){
        this(cfg,service,MAX_ARTICLES);
    }
    
    public DetailGenerator(Configuration cfg,ArticlePublishServiceable service,Integer maxArticles){
        this.cfg = cfg;
        this.service = service;
        this.maxArticles = maxArticles;
    }
    
    @Override
    public List<OutputResource> process(Site site,Channel channel,Template template)throws PublishException {
        List<OutputResource> resources = new ArrayList<OutputResource>();
        freemarker.template.Template t = getFreemarkerTemplate(cfg,template.getUniquePath());
        List<Article> articles = findReleaseArticles(channel.getId());
        logger.debug("aritcle count is {}",articles.size());
        for(Article article : articles){
            OutputResource resource = new OutputResource();
            Integer count = article.getContentTotal();
            
            if(count == 0){
                logger.debug("Aritcle content is null");
                continue;
            }
            
            for(int i = 0 ; i < count; ++i){
                Map<String,Object> parameters = constructParameters(site,channel,article,i,count);
                resource.addChild(generator(t,parameters,uriRule));
            }
            
            String url = resource.getChildren().get(0).getUri();
            logger.debug("Aritcle uri is {}",url);
            resource.registerEvent(new ArticleOutputEvent(article.getId(),url,service));
            resources.add(resource);
        }
        
        return resources;
    }
    
    private List<Article> findReleaseArticles(Integer channelId){
        return service.findPreReleaseArticles(channelId, maxArticles);
    }
    
    private Map<String,Object> constructParameters(Site site,Channel channel,
            Article article,Integer pageNumber,Integer pageCount) {
        
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.SITE.toString(), site);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        params.put(GlobalVariable.DOCUMENT.toString(), article);
        params.put(GlobalVariable.PAGE_NUMBER.toString(), pageNumber);
        params.put(GlobalVariable.PAGE_COUNT.toString(), pageCount);
        return params;
    }

    @Override
    public void previewProcess(OutputStream stream, Site site, Channel channel,
            Template template) throws PublishException {
        // TODO Auto-generated method stub
        
    }
    
}
