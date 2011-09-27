/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import com.ewcms.publication.freemarker.cache.DatabaseTemplateLoader;
import com.ewcms.publication.freemarker.directive.ArticleDirective;
import com.ewcms.publication.freemarker.directive.ArticleListDirective;
import com.ewcms.publication.freemarker.directive.ChannelDirective;
import com.ewcms.publication.freemarker.directive.ChannelListDirective;
import com.ewcms.publication.freemarker.directive.IncludeDirective;
import com.ewcms.publication.freemarker.directive.IndexDirective;
import com.ewcms.publication.freemarker.directive.PositionDirective;
import com.ewcms.publication.freemarker.directive.page.PageOutDirective;
import com.ewcms.publication.freemarker.directive.page.SkipDirective;
import com.ewcms.publication.freemarker.directive.page.SkipNumberDirective;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;

import freemarker.cache.CacheStorage;
import freemarker.cache.MruCacheStorage;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * EWCMS中Freemarker配置定义，设置DatabaseTemplateLoader和自定义指令。
 *
 * @author wangwei
 */
public class EwcmsConfigurationFactory extends FreeMarkerConfigurationFactory implements ResourceLoaderAware  {

    private Map<String, Object> freemarkerVariables = new HashMap<String,Object>();
    
    private List<TemplateLoader> postTemplateLoaders = new ArrayList<TemplateLoader>();;
    
    @Autowired
    protected ChannelPublishServiceable channelService;
    @Autowired
    protected ArticlePublishServiceable articleService;
    @Autowired
    protected TemplatePublishServiceable templateService;
    
    private CacheStorage cacheStorage =new MruCacheStorage(30,500);
    private boolean localizedLookup = false;
    private int delay = 24 * 60 * 60 ;
    
    /**
     * 初始化模板加载
     */
    private void initTemplateLoader(){
        
        postTemplateLoaders.add(new DatabaseTemplateLoader(templateService));
        super.setPostTemplateLoaders(postTemplateLoaders.toArray(new TemplateLoader[postTemplateLoaders.size()]));
    }
    
    /**
     * 初始定制指令
     */
    private void initFreemarkerVariables(){
        
        freemarkerVariables.put("article", new ArticleDirective());
        freemarkerVariables.put("channel", new ChannelDirective());
        
        freemarkerVariables.put("page", new PageOutDirective());
        freemarkerVariables.put("page_skip", new SkipDirective());
        freemarkerVariables.put("page_number", new SkipNumberDirective());
        
        freemarkerVariables.put("article_list", new ArticleListDirective(channelService,articleService));
        freemarkerVariables.put("channel_list", new ChannelListDirective(channelService));
        
        freemarkerVariables.put("position", new PositionDirective());
        freemarkerVariables.put("index", new IndexDirective());
        
        freemarkerVariables.put("include", new IncludeDirective(channelService,templateService));
        
        super.setFreemarkerVariables(freemarkerVariables);
    }
    
    @Override
    protected void postProcessConfiguration(Configuration config) throws IOException, TemplateException {
        config.setTemplateUpdateDelay(delay);
        config.setLocalizedLookup(localizedLookup);
        config.setCacheStorage(cacheStorage);
    }
    
    @Override
    public Configuration createConfiguration()throws IOException,TemplateException{
        initFreemarkerVariables();
        initTemplateLoader();
        
        return super.createConfiguration();
    }

    @Override
    public void setPostTemplateLoaders(TemplateLoader[] postTemplateLoaders) {
        this.postTemplateLoaders = Arrays.asList(postTemplateLoaders);
    }
    
    @Override
    public void setFreemarkerVariables(Map<String, Object> variables) {
        this.freemarkerVariables = variables;
    }
    
    public void setTemplateService(TemplatePublishServiceable service){
        this.templateService = service;
    }

    public void setChannelService(ChannelPublishServiceable channelService) {
        this.channelService = channelService;
    }

    public void setArticleService(ArticlePublishServiceable articleService) {
        this.articleService = articleService;
    }
    
    public void setCacheStorage(CacheStorage cacheStorage){
        this.cacheStorage = cacheStorage;
    }
    
    public void setLocalizedLookup(boolean localizedLookup){
        this.localizedLookup = localizedLookup;
    }
    
    public void setDelay(int delay){
        this.delay = delay;
    }
}
