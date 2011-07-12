/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.OutputType;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.SiteServer;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.generator.freemarker.html.DetailGenerator;
import com.ewcms.generator.freemarker.html.Generatorable;
import com.ewcms.generator.freemarker.html.HomeGenerator;
import com.ewcms.generator.freemarker.html.ListGenerator;
import com.ewcms.generator.freemarker.html.OtherGenerator;
import com.ewcms.generator.output.OutputResource;
import com.ewcms.generator.output.Outputable;
import com.ewcms.generator.output.provider.FtpOutput;
import com.ewcms.generator.output.provider.LocalOutput;
import com.ewcms.generator.output.provider.SftpOutput;
import com.ewcms.generator.output.provider.SmbOutput;
import com.ewcms.generator.service.ArticlePublishServiceable;
import com.ewcms.generator.service.ChannelPublishServiceable;
import com.ewcms.generator.service.ResourcePublishServiceable;
import com.ewcms.generator.service.TemplatePublishServiceable;
import com.ewcms.generator.service.TemplateSourcePublishServiceable;

import freemarker.template.Configuration;

/**
 * 实现发布服务
 * 
 * @author wangwei
 */
@Service
public class PublishService implements PublishServiceable {

    private static final Logger logger = LoggerFactory.getLogger(PublishService.class);
    
    @Autowired
    private ArticlePublishServiceable articleService;
    @Autowired
    private ChannelPublishServiceable channelService;
    @Autowired
    private ResourcePublishServiceable resourceService;
    @Autowired
    private TemplatePublishServiceable templateService;
    @Autowired
    private TemplateSourcePublishServiceable templateSourceService;
    @Autowired
    private Configuration cfg;
    
    private Map<TemplateType,Generatorable> generators = initGenerators();
    
    private Map<OutputType,Outputable> outputs = initOutputs(); 
    
    @Override
    public void publishTemplate(Integer id) throws PublishException {
        Template template = templateService.getTemplate(id);
        if(template == null){
            logger.warn("Template is not exist. Id = {}.",id);
            return ;
        }
        Channel channel = channelService.getChannel(template.getChannelId());
        if(channel == null){
            logger.warn("Channel is not exist. Id = {}.",template.getChannelId());
            return ;
        }
        publishChannelTemplate(channel.getSite(),channel,template);
    }
    
    @Override
    public void publishChannel(Integer id) throws PublishException {
        Channel channel = channelService.getChannel(id);
        if(channel == null){
            logger.warn("Channel is not exist. Id = {}.",id);
            return;
        }
        List<Template> templates = templateService.getTemplatesInChannel(id);
        for(Template template : templates){
            publishChannelTemplate(channel.getSite(),channel,template);
        }
    }
    
    private void publishChannelTemplate(Site site,Channel channel,Template template)throws PublishException{
        Generatorable generator = generators.get(template.getType());
        List<OutputResource> resources = generator.process(template, site, channel);
        SiteServer server = site.getSiteServer();
        Outputable output = outputs.get(server.getOutputType());
        output.out(server, resources);
        //文章生成有最大限制，所以需要循环调用，生成全部文章
        if(template.getType() == TemplateType.DETAIL && !resources.isEmpty()){
            publishChannelTemplate(site,channel,template);
        }
    }

    @Override
    public void publishChannelAgain(Integer id) throws PublishException {
        articleService.againPublishArticle(id);
        publishChannel(id);
    }

    @Override
    public void publishSiteResource(Integer id) throws PublishException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void publishSiteResourceAgain(Integer id) throws PublishException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void publishResource(Integer id, Boolean templateSource)throws PublishException {
        // TODO Auto-generated method stub
        
    }
    
    private Map<TemplateType,Generatorable> initGenerators(){
        Map<TemplateType,Generatorable> map = new HashMap<TemplateType,Generatorable>();
        
        map.put(TemplateType.HOME, new HomeGenerator(cfg));
        map.put(TemplateType.LIST, new ListGenerator(cfg,articleService));
        map.put(TemplateType.DETAIL, new DetailGenerator(cfg,articleService));
        map.put(TemplateType.OTHER, new OtherGenerator(cfg));
        
        return map;
    }
    
    private Map<OutputType,Outputable> initOutputs(){
        Map<OutputType,Outputable> map = new HashMap<OutputType,Outputable>();
        
        map.put(OutputType.LOCAL, new LocalOutput());
        map.put(OutputType.FTP, new FtpOutput());
        map.put(OutputType.SFTP, new SftpOutput());
        map.put(OutputType.SMB, new SmbOutput());
        
        return map;
    }

    public void setArticleService(ArticlePublishServiceable articleService) {
        this.articleService = articleService;
    }

    public void setChannelService(ChannelPublishServiceable channelService) {
        this.channelService = channelService;
    }

    public void setResourceService(ResourcePublishServiceable resourceService) {
        this.resourceService = resourceService;
    }

    public void setTemplateService(TemplatePublishServiceable templateService) {
        this.templateService = templateService;
    }

    public void setTemplateSourceService(
            TemplateSourcePublishServiceable templateSourceService) {
        this.templateSourceService = templateSourceService;
    }
}
