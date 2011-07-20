/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.SiteServer;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.publication.freemarker.html.DetailGenerator;
import com.ewcms.publication.freemarker.html.Generatorable;
import com.ewcms.publication.freemarker.html.HomeGenerator;
import com.ewcms.publication.freemarker.html.ListGenerator;
import com.ewcms.publication.freemarker.html.OtherGenerator;
import com.ewcms.publication.output.OutputFactory;
import com.ewcms.publication.output.OutputResource;
import com.ewcms.publication.resource.ResourcePublishable;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;

import freemarker.template.Configuration;

/**
 * 实现发布服务
 * 
 * @author wangwei
 */
@Service
public class SchedulingPublish implements SchedulingPublishable {

    private static final Logger logger = LoggerFactory.getLogger(SchedulingPublish.class);
    
    @Autowired
    protected ChannelPublishServiceable channelService;
    @Autowired
    protected ArticlePublishServiceable articleService;
    @Autowired
    protected TemplatePublishServiceable templateService;
    @Autowired
    protected ResourcePublishable resourcePublish;
    @Autowired
    protected Configuration cfg;
    
    protected Map<TemplateType,Generatorable> generators = initGenerators();

    @Override
    public void publishSite(Integer id) throws PublishException {
        resourcePublish.publishSite(id);
        Channel root = channelService.getChannelRoot(id);
        publishChannel(root.getId(),true);
    }
    
    @Override
    public void publishChannel(Integer id,boolean all) throws PublishException {
        Channel channel = channelService.getChannel(id);
        if(channel == null){
            logger.warn("Channel id={} is not exist.",id);
            return;
        }
        
        resourcePublish.publishSite(id);
        
        if(all){
            publishChannelAll(channel);
        }else{
            publishChannel(channel);
        }
    }
    
    
    private void publishChannel(Channel channel)throws PublishException{
        if(isPublishing(channel)){
            return ;
        }
        
        List<Template> templates = templateService.getTemplatesInChannel(channel.getId());
        for(Template template : templates){
            publishChannelTemplate(channel.getSite(),channel,template);
        }
    }
    
    /**
     * 判断频道是否在发布
     * 
     * @param siteId
     *            站点编号
     * @param channelId
     *            频道编号
     * @throws PublishException
     */
    protected synchronized boolean isPublishing(Channel channel){
        //判断频道是否在发布
        return false;
    }
    
    private void publishChannelAll(Channel channel)throws PublishException{
        List<Channel> children = channelService.getChannelChildren(channel.getId());
        for(Channel c : children){
            publishChannelAll(c);
            publishChannel(channel);
        }
    }
    
    protected void publishChannelTemplate(Site site,Channel channel,Template template)throws PublishException{
        Generatorable generator = generators.get(template.getType());
        List<OutputResource> resources = generator.process(template, site, channel);
        SiteServer server = site.getSiteServer();
        OutputFactory.factory(server.getOutputType()).out(server, resources);
        
        //文章生成有最大限制，所以需要循环调用，生成全部文章
        if(template.getType() == TemplateType.DETAIL && !resources.isEmpty()){
            publishChannelTemplate(site,channel,template);
        }
    }
    
    private Map<TemplateType,Generatorable> initGenerators(){
        Map<TemplateType,Generatorable> map = new HashMap<TemplateType,Generatorable>();
        
        map.put(TemplateType.HOME, new HomeGenerator(cfg));
        map.put(TemplateType.LIST, new ListGenerator(cfg,articleService));
        map.put(TemplateType.DETAIL, new DetailGenerator(cfg,articleService));
        map.put(TemplateType.OTHER, new OtherGenerator(cfg));
        
        return map;
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
