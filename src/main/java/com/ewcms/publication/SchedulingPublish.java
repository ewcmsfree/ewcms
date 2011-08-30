/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.SiteServer;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.publication.freemarker.html.DetailGenerator;
import com.ewcms.publication.freemarker.html.Generatorable;
import com.ewcms.publication.freemarker.html.HomeGenerator;
import com.ewcms.publication.freemarker.html.ListGenerator;
import com.ewcms.publication.output.OutputFactory;
import com.ewcms.publication.output.OutputResource;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.service.SitePublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.ChannelPublishTask;
import com.ewcms.publication.task.MemoryTaskRegistry;
import com.ewcms.publication.task.TaskRegistryable;
import com.ewcms.publication.task.Taskable;

import freemarker.template.Configuration;

/**
 * 实现发布服务
 * 
 * @author wangwei
 */
public class SchedulingPublish implements SchedulingPublishable,InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SchedulingPublish.class);
    private static final TaskRegistryable DEFAULT_TASK_REGISTRY = new MemoryTaskRegistry();
    
    protected ChannelPublishServiceable channelService;
    protected ArticlePublishServiceable articleService;
    protected TemplatePublishServiceable templateService;
    
    protected SitePublishServiceable siteService;
    protected ResourcePublishServiceable resourceService;
    protected TemplateSourcePublishServiceable templateSourceService;
    
    protected ResourcePublishable resourcePublish;
    protected Configuration cfg;

    protected TaskRegistryable taskRegistry = DEFAULT_TASK_REGISTRY;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(channelService,"channelService must setting");
        Assert.notNull(articleService,"articleService must setting");
        Assert.notNull(templateService,"templateService must setting");
        Assert.notNull(siteService,"siteService must setting");
        Assert.notNull(resourceService,"resourceService must setting");
        Assert.notNull(templateSourceService,"templateSourceService must setting");
        
        resourcePublish = new ResourcePublish(siteService,resourceService,templateSourceService);
    }
    
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
            publishChannelAll(channel,false);
        }else{
            publishChannel(channel,false);
        }
    }
    
    /**
     * 判断频道是否正在发布
     * 
     * @param channel 频道
     */
    protected synchronized boolean isPublishingNow(Channel channel){
        
        Integer id = channel.getId();
        if(taskRegistry.alreadyExistTask(id)){
            return true;
        }
        
        Site site = channel.getSite();
        Taskable task = new ChannelPublishTask(site,channel);
        taskRegistry.registerNewTask(id, task);
        
        return false;
    }
    
    
    
    /**
     * 发布生成页面到指定位置 
     * 
     * @param site        站点
     * @param resources   发布的页面资源
     * @throws PublishException
     */
    private void outputHtml(Site site,List<OutputResource> resources)throws PublishException{
        SiteServer server = site.getSiteServer();
        OutputFactory.factory(server.getOutputType()).out(server, resources);
    }
    
    /**
     * 发布频道文章
     * 
     * @param site      站点
     * @param channel   频道
     * @param templates 频道模版集合 
     * @throws PublishException
     */
	private void publishDetail(Site site, Channel channel, List<Template> templates)throws PublishException {
		for(Template template  :  templates){
			if (template.getType() == TemplateType.DETAIL) {
				Generatorable generator = new DetailGenerator(cfg, articleService);
				List<OutputResource> resources = generator.process(site, channel,template);
				outputHtml(site, resources);
			}
		}
	}
	
	/**
	 * 判断是否有首页模版
	 * 
	 * @param templates 频道模版集合 
	 * @return
	 */
	private boolean hasHome(List<Template> templates){
		for(Template template : templates){
			if(template.getType() == TemplateType.HOME){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 发布频道列表
	 * 
     * @param site      站点
     * @param channel   频道
     * @param templates 频道模版集合 
	 * @throws PublishException
	 */
	private void publishList(Site site,Channel channel,List<Template> templates)throws PublishException{
		for(Template template  :  templates){
			if(template.getType() == TemplateType.LIST){
				boolean createHome = !hasHome(templates);
				Generatorable generator = new ListGenerator(cfg, articleService,createHome);
				List<OutputResource> resources = generator.process(site, channel,template);
				outputHtml(site, resources);
			}
		}
	}
    
	/**
	 * 发布频道首页 
	 * 
     * @param site      站点
     * @param channel   频道
     * @param templates 频道模版集合 
	 * @throws PublishException
	 */
	private void publishHome(Site  site,Channel channel,List<Template> templates)throws PublishException{
		for(Template template  :  templates){
			if(template.getType() == TemplateType.HOME){
				Generatorable generator = new HomeGenerator(cfg);
				List<OutputResource> resources = generator.process(site, channel,template);
				outputHtml(site, resources);
			}
		}
	}
    
	/**
	 * 发布完成
	 * <br>
	 * 删除频道发布任务
	 */
	protected void publishFinish(Channel channel){
	    Integer id = channel.getId();
	    taskRegistry.removeTask(id);
	}
	
    /**
     * 发布频道
     * 
     * @param channel 频道
     * @param again 是否再次发布
     * @throws PublishException
     */
    protected void publishChannel(Channel channel,boolean again)throws PublishException{
        
        if(isPublishingNow(channel)){
            return ;
        }
        
        if(again){
            articleService.updatePreRelease(channel.getId());
        }
        
        List<Template> templates = templateService.getTemplatesInChannel(channel.getId());
        Site site = channel.getSite();
        
        publishDetail(site,channel,templates);
        publishList(site,channel,templates);
        publishHome(site,channel,templates);
        
        publishFinish(channel);
    }
    
    /**
     * 发布频道及所有子频道
     * 
     * @param channel 频道
     * @param again 是否再次发布
     * @throws PublishException
     */
    protected void publishChannelAll(Channel channel,boolean again)throws PublishException{
        List<Channel> children = channelService.getChannelChildren(channel.getId());
        for(Channel c : children){
            publishChannelAll(c,again);
            publishChannel(channel,again);
        }
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
    
}
