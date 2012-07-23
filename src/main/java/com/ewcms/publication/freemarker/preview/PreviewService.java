/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.preview;

import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

import com.ewcms.common.message.EwcmsMessageSource;
import com.ewcms.content.document.model.Article;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.Template.TemplateType;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.EwcmsConfigurationFactory;
import com.ewcms.publication.freemarker.generator.DetailGenerator;
import com.ewcms.publication.freemarker.generator.HomeGenerator;
import com.ewcms.publication.freemarker.generator.ListGenerator;
import com.ewcms.publication.generator.Generatorable;
import com.ewcms.publication.preview.PreviewServiceable;
import com.ewcms.publication.preview.service.ArticlePublishServiceWrapper;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;
import com.ewcms.publication.uri.UriRules;

import freemarker.template.Configuration;

/**
 * 实现Freemarker模板预览
 * 
 * @author wangwei
 */
public class PreviewService implements PreviewServiceable ,MessageSourceAware{

    private static final Logger logger = LoggerFactory.getLogger(PreviewService.class);
    
    private final Configuration configuration;
    private final ArticlePublishServiceable articleService ;
    private final ChannelPublishServiceable channelService;
    private final TemplatePublishServiceable templateService;
    private MessageSourceAccessor messages = EwcmsMessageSource.getAccessor();
    
    public PreviewService(Configuration configuration,
            ArticlePublishServiceable articleService,
            ChannelPublishServiceable channelService,
            TemplatePublishServiceable templateService){
        
        Assert.notNull(configuration,"template's configuration must setting");
        Assert.notNull(articleService,"articleService must setting");
        Assert.notNull(channelService,"channelService configuration must setting");
        Assert.notNull(templateService,"templateService must setting");
        
        this.configuration = configuration;
        this.articleService = articleService;
        this.channelService = channelService;
        this.templateService = templateService;
    }
    
    /**
     * 根据参数得到Freemarker configuration
     * 
     * @param articleService 文章发布
     * @param mock 是否模拟
     * @return
     */
    Configuration getConfiguration(ArticlePublishServiceable articleService,boolean mock)throws PublishException{
        
        if(!mock){
            return this.configuration;
        }
        
        try{
            EwcmsConfigurationFactory factory = new  EwcmsConfigurationFactory();
            factory.setArticleService(articleService);
            factory.setChannelService(channelService);
            factory.setTemplateService(templateService);
            return factory.createConfiguration();
        }catch(Exception e){
            logger.warn("Freemarker configure created error:{}",e);
            throw new PublishException(messages.getMessage("error.publication.createConfigure","Freemarker's configure created fail."));
        }
    }
    
    /**
     * 预览home模版
     * 
     * @param out 输出
     * @param site 站点
     * @param channel 频道
     * @param template 模版
     * @param mock 是否模拟数据（mock:true 模拟)
     * @throws PublishException
     */
    protected void viewHomeTemplate(
            OutputStream out,Site site,Channel channel,Template template,boolean mock)throws PublishException{
        
        ArticlePublishServiceable service = new ArticlePublishServiceWrapper(articleService,mock);
        Configuration cfg =  getConfiguration(service,mock);
        Generatorable generator = new HomeGenerator(cfg,channel.getSite(),channel);
        generator.debugEnable();
        generator.process(out,template.getUniquePath());
    }
    
    /**
     * 预览list模版
     * 
     * @param out 输出
     * @param site 站点
     * @param channel 频道
     * @param template 模版
     * @param mock 是否模拟数据（mock:true 模拟)
     * @throws PublishException
     */
    protected void viewListTemplate( 
            OutputStream out,Site site,Channel channel,Template template,boolean mock)throws PublishException{
        
        ArticlePublishServiceable service = new ArticlePublishServiceWrapper(articleService,mock);
        Configuration cfg =  getConfiguration(service,mock);
        Generatorable  generator = new ListGenerator(cfg,channel.getSite(),channel,0,100);
        generator.debugEnable();
        generator.process(out,template.getUniquePath());
    }
    
    /**
     * 预览Detail模版
     *</p>
     *预览detail模版只使用模拟数据
     * 
     * @param out 输出
     * @param site 站点
     * @param channel 频道
     * @param template 模版
     * @throws PublishException
     */
    protected void viewDetailTemplate(
            OutputStream out,Site site,Channel channel,Template template)throws PublishException{
        
        boolean mock = true;
        ArticlePublishServiceable service = new ArticlePublishServiceWrapper(articleService,mock);
        Configuration cfg =  getConfiguration(service,mock);
        Article  article = service.getArticle(null);
        Generatorable  generator = new DetailGenerator(cfg,channel.getSite(),channel,article,0);
        generator.debugEnable();
        generator.process(out,template.getUniquePath());
    }
    
    @Override
    public void viewTemplate(OutputStream out,Integer id, Boolean mock) throws PublishException {
        
        Template template = templateService.getTemplate(id);
        if(template == null){
            logger.warn("Template is not exist,Id is {}." , id);
            throw new PublishException(messages.getMessage("error.publication.templateNotExist","Template is not exist."));
        }
        Channel channel = channelService.getChannel(template.getChannelId());
        
        switch (template.getType()){
        case HOME:
            viewHomeTemplate(out,channel.getSite(),channel,template,mock);
            break;
        case LIST:
            viewListTemplate(out,channel.getSite(),channel,template,mock);
            break;
        case DETAIL:
            viewDetailTemplate(out,channel.getSite(),channel,template);
            break;
        default:
            logger.warn("Template type is not genrator page,Type is {}." , template.getType());
            throw new PublishException(messages.getMessage("error.publication.templateType","Template type is not genrator page."));
        }
    }
    
    @Override
    public void viewArticle(OutputStream out,Integer channelId,Long id, Integer pageNumber) throws PublishException {
        
        Channel channel = channelService.getChannel(channelId);
        if(channel == null){
            logger.warn("Channel  not exist,Id is {}." , channelId);
            throw new PublishException(messages.getMessage("error.publication.channelNotExist","Channel is not exist"));
        }
        
        Article article = articleService.getArticle(id);
        if(article == null){
            logger.warn("Article  not exist,Id is {}." , id);
            throw new PublishException(messages.getMessage("error.publication.articleNotExist","Article is not exist"));
        }
     
        Template template = null;
        for(Template t : templateService.getTemplatesInChannel(channelId)){
            if(t.getType() == TemplateType.DETAIL){
                template  = t;
                break;
            }
        }
        
        if (template == null){
            logger.warn("Channel's detail template not exist,channel id is {}",channelId);
            throw new PublishException(messages.getMessage("error.publication.detailTemplateNotExist", "Channel's detail template not exist."));
        }
        Generatorable generator = new DetailGenerator(
                configuration,channel.getSite(),channel,
                UriRules.newArticlePreview(),article,pageNumber);
        generator.process(out,template.getUniquePath());
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
