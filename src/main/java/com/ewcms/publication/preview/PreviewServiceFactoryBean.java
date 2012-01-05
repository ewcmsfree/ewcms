/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.preview;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.common.message.EwcmsMessageSource;
import com.ewcms.publication.freemarker.preview.PreviewService;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;

import freemarker.template.Configuration;

/**
 * 预览服务Factory
 * 
 * @author wangwei
 */
@Service
public class PreviewServiceFactoryBean implements FactoryBean<PreviewServiceable>,InitializingBean,MessageSourceAware{

    @Autowired
    private Configuration configuration;
    @Autowired
    private ArticlePublishServiceable articleService ;
    @Autowired
    private ChannelPublishServiceable channelService;
    @Autowired
    private TemplatePublishServiceable templateService;
    
    private MessageSource messageSource =new EwcmsMessageSource();
    
    @Override
    public void afterPropertiesSet() throws Exception {
        
        Assert.notNull(configuration,"template's configuration must setting");
        Assert.notNull(articleService,"articleService must setting");
        Assert.notNull(channelService,"channelService configuration must setting");
        Assert.notNull(templateService,"templateService must setting");
    }

    @Override
    public PreviewServiceable getObject() throws Exception {
        PreviewService service = new PreviewService(configuration,articleService,channelService,templateService);
        service.setMessageSource(messageSource);
        return service;
    }

    @Override
    public Class<?> getObjectType() {
        return PreviewServiceable.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
    
    public void setConfiguration(Configuration cfg){
        this.configuration = cfg;
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

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
