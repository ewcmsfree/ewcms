/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.preview;

import java.io.OutputStream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.html.DetailGenerator;
import com.ewcms.publication.freemarker.html.Generatorable;
import com.ewcms.publication.freemarker.html.HomeGenerator;
import com.ewcms.publication.freemarker.html.ListGenerator;
import com.ewcms.publication.preview.service.MockArticlePublishService;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;

import freemarker.template.Configuration;

/**
 * 实现模板预览
 * 
 * @author wangwei
 */
@Service
public class TemplatePreview implements TemplatePreviewable,InitializingBean {

    private static final ArticlePublishServiceable MOCK_ARTICLE_SERVICE = new MockArticlePublishService();
    
    @Autowired
    private Configuration configuration;
    
    private ArticlePublishServiceable articleService ;
    private ChannelPublishServiceable channelService;
    private TemplatePublishServiceable templateService;
    private Configuration mockConfiguration;
    
    @Override
    public void view(OutputStream out, Site site, Channel channel,
            Template template, Boolean mock) throws PublishException {
        
        ArticlePublishServiceable service = (mock ? MOCK_ARTICLE_SERVICE : articleService);
        Configuration cfg = (mock ? mockConfiguration : configuration);
        
        Generatorable generator ;
        if(template.getType() == TemplateType.HOME){
            generator = new HomeGenerator(cfg);
        }else if(template.getType() == TemplateType.LIST){
            generator = new ListGenerator(cfg,service,false);
        }else{
            generator = new DetailGenerator(cfg,service);
        }
        generator.previewProcess(out, site, channel, template);
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(configuration,"template's configuration must setting");
        Assert.notNull(articleService,"articleService must setting");
        Assert.notNull(channelService,"channelService configuration must setting");
        Assert.notNull(templateService,"templateService must setting");
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
}
