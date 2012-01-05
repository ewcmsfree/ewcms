/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.service.SitePublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.MemoryTaskRegistry;
import com.ewcms.publication.task.TaskRegistryable;

import freemarker.template.Configuration;

/**
 * 发布服务FactoryBean
 * <br/>
 * 通过spring创建PublishService服务，确保创建服务为单例。 
 * 
 * @author wangwei
 */
public class PublishServiceFactoryBean implements InitializingBean,FactoryBean<PublishServiceable>{
    private final static int DEFAULT_MAX_SITE = 5;
    
    private ChannelPublishServiceable channelService;
    private ArticlePublishServiceable articleService;
    private TemplatePublishServiceable templateService;
    private SitePublishServiceable siteService;
    private ResourcePublishServiceable resourceService;
    private TemplateSourcePublishServiceable templateSourceService;
    private Configuration cfg;
    private TaskRegistryable taskRegistry;
    private  PublishService service ;
    private PublishRunner runner;
    private int maxSite = DEFAULT_MAX_SITE;
    private boolean multi = true;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(channelService,"channelService must setting");
        Assert.notNull(articleService,"articleService must setting");
        Assert.notNull(templateService,"templateService must setting");
        Assert.notNull(siteService,"siteService must setting");
        Assert.notNull(resourceService,"resourceService must setting");
        Assert.notNull(templateSourceService,"templateSourceService must setting");
        Assert.notNull(cfg,"Templcat configuration must setting");
        Assert.notNull(taskRegistry,"TaskRegistry must setting");
        
        service = new PublishService();
        service.setChannelService(channelService);
        service.setArticleService(articleService);
        service.setTemplateService(templateService);
        service.setSiteService(siteService);
        service.setResourceService(resourceService);
        service.setTemplateSourceService(templateSourceService);
        service.setConfiguration(cfg);
        taskRegistry = (taskRegistry == null ? new MemoryTaskRegistry() : taskRegistry);
        service.setTaskRegistry(taskRegistry);
        runner = (runner == null ? new PublishRunner(siteService,taskRegistry,maxSite,multi) : runner);
        runner.run();
    }
    
    @Override
    public PublishServiceable getObject() throws Exception {
        return service;
    }

    @Override
    public Class<?> getObjectType() {
        return PublishServiceable.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setArticleService(ArticlePublishServiceable articleService) {
        this.articleService = articleService;
    }

    public void setChannelService(ChannelPublishServiceable channelService) {
        this.channelService = channelService;
    }

    public void setTemplateService(TemplatePublishServiceable templateService) {
        this.templateService = templateService;
    }
    
    public void setSiteService(SitePublishServiceable siteService) {
        this.siteService = siteService;
    }

    public void setResourceService(ResourcePublishServiceable resourceService) {
        this.resourceService = resourceService;
    }

    public void setTemplateSourceService(
            TemplateSourcePublishServiceable templateSourceService) {
        this.templateSourceService = templateSourceService;
    }
    
    public void setTaskRegistry(TaskRegistryable taskRegistry){
        this.taskRegistry = taskRegistry;
    }
    
    public void setConfiguration(Configuration cfg){
        this.cfg = cfg;
    }
    
    public void setPublishRunner(PublishRunner runner){
        this.runner = runner;
    }
    
    public void setMaxSite(int maxSite){
        this.maxSite = maxSite;
    }
    
    public void setMulti(boolean multi){
        this.multi = multi;
    }
}
