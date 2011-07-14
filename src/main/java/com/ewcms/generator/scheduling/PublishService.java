/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.OutputType;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.SiteServer;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.generator.PublishException;
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
import com.ewcms.generator.service.SitePublishServiceable;
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
    private SitePublishServiceable siteService;
    @Autowired
    private ChannelPublishServiceable channelService;
    @Autowired
    private ArticlePublishServiceable articleService;
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
    public void publishSite(Integer id) throws PublishException {
        publishSiteResource(id);
        Channel root = channelService.getChannelRoot(id);
        publishChannelHtml(root,true);
    }
    
    @Override
    public void publishChannel(Integer id,boolean publishChildren) throws PublishException {
        Channel channel = channelService.getChannel(id);
        if(channel == null){
            logger.warn("Channel is not exist. Id = {}.",id);
            return;
        }
        publishSiteResource(channel.getSite().getId());
        publishChannelHtml(channel,publishChildren);
    }
    
    private void publishChannelHtml(Channel channel,boolean publishChildren)throws PublishException{
        if(publishChildren){
            List<Channel> children = channelService.getChannelChildren(channel.getId());
            if(children.isEmpty()){
                return ;
            }
            for(Channel child : children){
                publishChannelHtml(child,publishChildren);
            }
        }
        List<Template> templates = templateService.getTemplatesInChannel(channel.getId());
        for(Template template : templates){
            publishChannelTemplate(channel.getSite(),channel,template);
        }
    }
    
    private void publishChannelTemplate(Site site,Channel channel,Template template)throws PublishException{
        Generatorable generator = generators.get(template.getType());
        List<OutputResource> resources = generator.process(template, site, channel);
        outputResources(site.getSiteServer(),resources);
        //文章生成有最大限制，所以需要循环调用，生成全部文章
        if(template.getType() == TemplateType.DETAIL && !resources.isEmpty()){
            publishChannelTemplate(site,channel,template);
        }
    }
    
    private void outputResources(SiteServer server,List<OutputResource> resources)throws PublishException{
        Outputable output = outputs.get(server.getOutputType());
        output.out(server, resources);
    }

    private void publishSiteResource(Integer id) throws PublishException {
        Site site = siteService.getSite(id);
        if(site == null){
            logger.debug("Site id = {} is not exist",id);
            throw new PublishException("Site is not exits");
        }
        SiteServer server = site.getSiteServer();
        publishSiteLocalResource(id,server);
        publishSiteTemplateSource(id,server);
    }

    private void publishSiteLocalResource(Integer id,SiteServer server)throws PublishException{
        List<Resource> resources = resourceService.findPublishResource(id);
        List<OutputResource> outputResources = new ArrayList<OutputResource>();
        for(Resource resource  : resources){
            outputResources.add(createOutputResource(resource));
        }
        outputResources(server,outputResources);
    }
    
    private OutputResource createOutputResource(Resource resource){
        OutputResource outputResource ;
        if(StringUtils.isBlank(resource.getPathZip()) || StringUtils.isBlank(resource.getReleasePathZip())){
            outputResource = new OutputResource();
            //TODO register event
            outputResource.addChild(new OutputResource(resource.getPath(),resource.getReleasePath()));
            outputResource.addChild(new OutputResource(resource.getPath(),resource.getReleasePath()));
            
        }else{
            outputResource = new OutputResource(resource.getPath(),resource.getReleasePath());
        }
        return outputResource;
    }
    
    private void publishSiteTemplateSource(Integer id,SiteServer server)throws PublishException{
        List<TemplateSource> sources = templateSourceService.findReleaseTemplateSources(id);
        List<OutputResource> outputResources = new ArrayList<OutputResource>();
        for(TemplateSource source  : sources){
            OutputResource outputResource = 
                new OutputResource(source.getSourceEntity().getSrcEntity(),source.getUniquePath());
            //TODO register event
            outputResources.add(outputResource);
        }
        outputResources(server,outputResources);
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
