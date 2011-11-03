/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.html;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
import com.ewcms.publication.uri.DefaultDetailUriRule;
import com.ewcms.publication.uri.NullUriRule;
import com.ewcms.publication.uri.PreviewDetailUriRule;
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
    private static final UriRuleable DEFAULT_URI_RULE = new DefaultDetailUriRule();
    private static final UriRuleable PREVIEW_URI_RULE = new PreviewDetailUriRule();
    
    private Configuration cfg;
    private ArticlePublishServiceable service;
    private Integer maxArticles;
    
    public DetailGenerator(Configuration cfg,ArticlePublishServiceable service){
        this(cfg,service,MAX_ARTICLES);
    }
    
    public DetailGenerator(Configuration cfg,ArticlePublishServiceable service,Integer maxArticles){
        this.cfg = cfg;
        this.service = service;
        this.maxArticles = maxArticles;
    }
    
    /**
     * 构造生成页面参数集合
     * 
     * @param site
     *          站点对象
     * @param channel
     *          频道对象
     * @param article
     *          文章对象
     * @param pageNumber
     *          页数
     * @param pageCount
     *          页数总合
     * @param debug
     *          是否调试
     * @return
     */
    private Map<String,Object> constructParameters(Site site,Channel channel,
            Article article,Integer pageNumber,Integer pageCount,Boolean debug) {
        
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.SITE.toString(), site);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        params.put(GlobalVariable.DOCUMENT.toString(), article);
        params.put(GlobalVariable.PAGE_NUMBER.toString(), pageNumber);
        params.put(GlobalVariable.PAGE_COUNT.toString(), pageCount);
        params.put(GlobalVariable.DEBUG.toString(), debug);
        
        return params;
    }
    
    @Override
    public List<OutputResource> process(Site site,Channel channel,Template template)throws PublishException {
        List<OutputResource> resources = new ArrayList<OutputResource>();
        freemarker.template.Template t = getFreemarkerTemplate(cfg,template.getUniquePath());
        List<Article> articles = service.findPreReleaseArticles(channel.getId(), maxArticles);
        logger.debug("aritcle count is {}",articles.size());
        for(Article article : articles){
            OutputResource resource = new OutputResource();
            Integer count = article.getContentTotal();
            
            if(count == 0){
                logger.debug("Aritcle content is null");
                continue;
            }
            
            for(int i = 0 ; i < count; ++i){
                Map<String,Object> parameters = constructParameters(site,channel,article,i,count,Boolean.FALSE);
                resource.addChild(generator(t,parameters,DEFAULT_URI_RULE));
            }
            
            String url = resource.getChildren().get(0).getUri();
            logger.debug("Aritcle uri is {}",url);
            resource.registerEvent(new ArticleOutputEvent(article.getId(),url,service));
            resources.add(resource);
        }
        
        return resources;
    }
    
    @Override
    public void previewProcess(OutputStream stream, Site site, Channel channel,Template template) throws PublishException {
        int pageCount = 5;
        int pageNumber = 1;
        int max = 1;
        
        List<Article> articles = service.findPreReleaseArticles(channel.getId(), max);
        Map<String,Object> parameters = constructParameters(site,channel,articles.get(0),pageNumber,pageCount,Boolean.TRUE);
        
        freemarker.template.Template t = getFreemarkerTemplate(cfg,template.getUniquePath());
        UriRuleable rule =new NullUriRule();
        Writer writer = new OutputStreamWriter(stream);
        write(t , parameters, rule, writer);
    }
    
    public void previewProcess(OutputStream stream,Site site,Channel channel,Template template,long id,int pageNumber)throws PublishException{
        Article article = service.getArticle(id);
         if(article == null){
             throw new PublishException("Aritcle is not exist,id = " + id );
         }
         
         int pageCount = article.getContentTotal();
         Map<String,Object> parameters = constructParameters(site,channel,article,pageNumber,pageCount,Boolean.TRUE);
         
         freemarker.template.Template t = getFreemarkerTemplate(cfg,template.getUniquePath());
         Writer writer = new OutputStreamWriter(stream);
         write(t , parameters, PREVIEW_URI_RULE, writer);
    }
}
