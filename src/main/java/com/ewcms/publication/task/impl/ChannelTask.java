/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl;

import java.util.ArrayList;
import java.util.List;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

import freemarker.template.Configuration;

/**
 * 发布频道任务
 * <br>
 * 发布频道下所有需要发布的资源和任务（如：首页，文章等）
 * 
 * @author wangwei
 */
public class ChannelTask extends TaskBase{
    
    public static class Builder extends BaseBuilder<Builder>{
        private final Configuration cfg;
        private final ChannelPublishServiceable channelService;
        private final TemplatePublishServiceable templateService;
        private final TemplateSourcePublishServiceable templateSourceService;
        private final ResourcePublishServiceable resourceService;
        private final ArticlePublishServiceable articleService;
        private final Channel channel;
        private boolean publishChildren = false;
        
        public Builder(Configuration cfg,
                TemplatePublishServiceable templateService,
                TemplateSourcePublishServiceable templateSourceService,
                ResourcePublishServiceable resourceService,
                ArticlePublishServiceable articleService,
                ChannelPublishServiceable channelService,
                Site site,Channel channel){
            
            super(site);
            
            this.cfg = cfg;
            this.templateService = templateService;
            this.templateSourceService = templateSourceService;
            this.resourceService = resourceService;
            this.articleService = articleService;
            this.channelService = channelService;
            this.channel = channel;
        }
        
        public Builder setPublishChildren(boolean publishChildren){
            this.publishChildren = publishChildren;
            return this;
        }
        
        @Override
        public String getDescription() {
            return String.format("%s频道发布",channel.getName());
        }
        
        private void dependenceResourceAndTemplateSource(List<Taskable> dependences){
            dependences.add(
                    new  TemplateSourceTask
                    .Builder(templateSourceService,site)
                    .setUsername(username)
                    .build());
            dependences.add(
                    new ResourceTask
                    .Builder(resourceService,site)
                    .setUsername(username)
                    .build());
        }
        
        private void dependenceTemplate(List<Taskable> dependences){
            List<Template> templates = templateService.getTemplatesInChannel(channel.getId());
            for(Template template : templates){
                Taskable task = new TemplateTask.Builder(
                        cfg, templateSourceService, resourceService,
                        articleService, templateService, site, channel, template)
                        .setDependence(true)
                        .setUsername(username)
                        .setAgain(again)
                        .build();
                 dependences.add(task);
            }
        }
        
        private void dependenceChildren(List<Taskable> dependences){
            List<Channel> children = channelService.getChannelChildren(channel.getId());
            if(children == null || children.isEmpty()){
                return ;
            }
            for(Channel child : children){
                dependences.add(
                        new ChannelTask.Builder(
                        cfg, templateService, templateSourceService, resourceService,
                        articleService, channelService,site,child)
                        .setAgain(again)
                        .setUsername(username)
                        .setDependence(true)
                        .setPublishChildren(true)
                        .build());
            }
        }
        
        public List<Taskable> getDependenceTasks(){
            List<Taskable> dependences = new ArrayList<Taskable>();
            if(!dependence){
                dependenceResourceAndTemplateSource(dependences);
            }
            if(channel.getPublicenable()){
                dependenceTemplate(dependences);    
            }
            if(publishChildren){
                dependenceChildren(dependences);
            }
            return dependences;
        }

        @Override
        public List<TaskProcessable> getTaskProcesses() {
            return new ArrayList<TaskProcessable>(0);
        }
    }
    
    public ChannelTask(String id,Builder builder){
        super(id,builder);
    }
}
