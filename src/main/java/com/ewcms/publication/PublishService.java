/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.service.SitePublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.TaskRegistryable;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.ChannelTask;
import com.ewcms.publication.task.impl.DetailTask;
import com.ewcms.publication.task.impl.ResourceTask;
import com.ewcms.publication.task.impl.SiteTask;
import com.ewcms.publication.task.impl.TemplateSourceTask;
import com.ewcms.publication.task.impl.TemplateTask;

import freemarker.template.Configuration;

/**
 * 实现发布服务
 * 
 * @author wangwei
 */
public class PublishService implements PublishServiceable {

    private static final Logger logger = LoggerFactory.getLogger(PublishService.class);
    
    private ChannelPublishServiceable channelService;
    private ArticlePublishServiceable articleService;
    private TemplatePublishServiceable templateService;
    private SitePublishServiceable siteService;
    private ResourcePublishServiceable resourceService;
    private TemplateSourcePublishServiceable templateSourceService;
    private Configuration cfg;
    private TaskRegistryable taskRegistry;
    
    /**
     * 得到站点对象
     * </br>
     * 站点不存在抛出异常
     * 
     * @param siteId 站点编号
     * @return
     * @throws PublishException
     */
    private Site getSite(Integer siteId)throws PublishException{
        Site site = siteService.getSite(siteId);
        if(site == null){
            logger.error("Site id is {},but site is null",siteId);
            throw new PublishException("Sit is null");
        }
        return site;
    }
    
    @Override
    public void publishTemplateSource(int siteId,int[] publishIds,String username) throws PublishException {
        Site site = getSite(siteId);
        Taskable task = 
            new TemplateSourceTask.
            Builder(templateSourceService, site).
            setPublishIds(publishIds).
            setUsername(username).
            setAgain(true).
            build();
        taskRegistry.registerNewTask(site,task);
    }

    @Override
    public void publishTemplateSourceBySite(int siteId, boolean again,String username)throws PublishException {
        Site site = getSite(siteId);
        Taskable task = 
            new TemplateSourceTask.
            Builder(templateSourceService, site).
            setAgain(again).
            setUsername(username).
            build();
        taskRegistry.registerNewTask(site,task);
    }

    @Override
    public void publishResource(int siteId,int[] publishIds,String username) throws PublishException {
        Site site = getSite(siteId);
        Taskable task = 
            new ResourceTask.Builder(resourceService, site).
            setAgain(true).
            setPublishIds(publishIds).
            setUsername(username).
            build();
        taskRegistry.registerNewTask(site,task);
    }

    @Override
    public void publishResourceBySite(int siteId, boolean again,String username)throws PublishException {
        Site site = getSite(siteId);
        Taskable task = 
            new ResourceTask.Builder(resourceService, site).
            setAgain(again).
            setUsername(username).
            build();
        taskRegistry.registerNewTask(site,task);
    }

    @Override
    public void publishTemplate(int templateId, boolean again,String username)throws PublishException {
        Template template = templateService.getTemplate(templateId);
        if(template == null){
            logger.error("Template id is {},but templet is null",templateId);
            throw new PublishException("Template is null");
        }
        Site site = template.getSite();
        Channel channel = channelService.getChannel(template.getChannelId());
        Taskable task = 
            new TemplateTask.Builder(
                    cfg,templateSourceService,resourceService,
                    articleService,templateService,site,channel,template).
                    setAgain(again).
                    setUsername(username).
                    build();
        taskRegistry.registerNewTask(site,task);
    }
    
    private Channel getChannel(Integer channelId)throws PublishException{
        Channel channel = channelService.getChannel(channelId);
        if(channel == null){
            logger.error("Channel id is {},Channel is null",channelId);
            throw new PublishException("Channel is null");
        }
        return channel;
    }
    
    @Override
    public void publishChannel(int channelId, boolean chidren,
            boolean again, String username) throws PublishException {
        Channel channel = getChannel(channelId);
        Site site = channel.getSite();
        Taskable task = new ChannelTask.Builder(
                cfg, templateService, templateSourceService,
                resourceService, articleService, channelService,
                site, channel).
                setPublishChildren(chidren).
                setAgain(again).
                setUsername(username).
                build();
       taskRegistry.registerNewTask(site,task);
    }

    @Override
    public void publishSite(int siteId, boolean again, String username)
            throws PublishException {
        Site  site = getSite(siteId);
        Taskable task = new SiteTask.Builder(
                cfg, templateService, templateSourceService, 
                resourceService, articleService, 
                channelService, site).
                setUsername(username).
                setAgain(again).
                build();
        taskRegistry.registerNewTask(site,task);
    }

    @Override
    public void publishArticle(int channelId, long[] publishIds, String username)
            throws PublishException {
        Channel channel = getChannel(channelId);
        Site site = channel.getSite();
        List<Template> templates = templateService.getTemplatesInChannel(channelId);
        for(Template template : templates){
            if(template.getType() != TemplateType.DETAIL){
                continue;
            }
            Taskable task = new DetailTask.Builder(
                    cfg, templateSourceService, resourceService,
                    articleService, site, channel, template).
                    setUsername(username).
                    setAgain(true).
                    setPublishIds(publishIds).
                    build();
            taskRegistry.registerNewTask(site,task);
        }
    }

    @Override
    public void removePublish(Integer siteId,String id, String username)throws PublishException {
        try{
            taskRegistry.removeTask(siteId, id, username);
        }catch(TaskException e){
            throw new PublishException(e);
        }
    }
    
    @Override
    public void closeSitePublish(Integer siteId) {
        taskRegistry.closeSite(siteId);
    }
    
    @Override
    public List<Taskable> getSitePublishTasks(Integer siteId) {
        return taskRegistry.getSiteTasks(siteId);
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
}
