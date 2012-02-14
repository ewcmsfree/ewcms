/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.TaskException;
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
    
    public static class Builder{
        private final Configuration cfg;
        private final ChannelPublishServiceable channelService;
        private final TemplatePublishServiceable templateService;
        private final TemplateSourcePublishServiceable templateSourceService;
        private final ResourcePublishServiceable resourceService;
        private final ArticlePublishServiceable articleService;
        private final Site site;
        private final Channel channel;
        private String username = DEFAULT_USERNAME;
        private boolean again = false;
        private boolean independence = true;
        private boolean publishChildren = false;
        private List<Taskable> dependences;
        
        public Builder(Configuration cfg,
                TemplatePublishServiceable templateService,
                TemplateSourcePublishServiceable templateSourceService,
                ResourcePublishServiceable resourceService,
                ArticlePublishServiceable articleService,
                ChannelPublishServiceable channelService,
                Site site,Channel channel){
            
            this.cfg = cfg;
            this.templateService = templateService;
            this.templateSourceService = templateSourceService;
            this.resourceService = resourceService;
            this.articleService = articleService;
            this.channelService = channelService;
            this.site = site;
            this.channel = channel;
        }
        
        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        
        public Builder forceAgain(){
            again = true;
            return this;
        }
        
        public Builder setAgain(boolean again){
            this.again = again;
            return this;
        }
        
        public Builder dependence(){
            independence = false;
            return this;
        }
        
        Builder setDependence(boolean dependence){
            this.independence = dependence;
            return this;
        }
        
        public Builder publishChildren(){
            this.publishChildren = true;
            return this;
        }
        
        public Builder setPublishChildren(boolean publishChildren){
            this.publishChildren = publishChildren;
            return this;
        }
        
        private void addResourceAndTemplateSourceTask(List<Taskable> dependences){
            dependences.add(new  TemplateSourceTask.Builder(templateSourceService,site).builder());
            dependences.add(new ResourceTask.Builder(resourceService,site).builder());
        }
        
        private void addTemplateTask(List<Taskable> dependences){
            List<Template> templates = templateService.getTemplatesInChannel(channel.getId());
            for(Template template : templates){
                Taskable task = new TemplateTask.Builder(
                        cfg, templateSourceService, resourceService,
                        articleService, templateService, site, channel, template).
                        dependence().
                        setAgain(again).
                        build();
                    dependences.add(task);
            }
        }
        
        private void addChildrenTask(List<Taskable> dependences){
            List<Channel> children = channelService.getChannelChildren(channel.getId());
            if(children == null || children.isEmpty()){
                return ;
            }
            for(Channel child : children){
                dependences.add(new ChannelTask.Builder(
                        cfg, templateService, templateSourceService, resourceService,
                        articleService, channelService,site,child).
                        setAgain(again).
                        dependence().
                        setPublishChildren(publishChildren).
                        build());
            }
        }
        
        private List<Taskable> getDependenceTasks(){
            List<Taskable> dependences = new ArrayList<Taskable>();
            if(independence){
                addResourceAndTemplateSourceTask(dependences);
            }
            if(channel.getPublicenable()){
                addTemplateTask(dependences);    
            }
            if(publishChildren){
                addChildrenTask(dependences);
            }
            return dependences;
        }
        
        public ChannelTask build(){
           dependences = getDependenceTasks();
            return new ChannelTask(this);
        }
    }
    
    private final Builder builder;
    
    public ChannelTask(Builder builder){
        super(newTaskId());
        this.builder = builder;
    }

    @Override
    public String getDescription() {
        String description = String.format("%s-频道发布%s",
                builder.channel.getName(),getAgainMessage(builder.again)) ;
        return description;
    }

    @Override
    public String getUsername() {
        return builder.username;
    }
    
    @Override
    public List<Taskable> getDependences() {        
        return Collections.unmodifiableList(builder.dependences);
    }

    @Override
    protected boolean hasTaskProcess(){
        return false;
    }
    
    @Override
    protected List<TaskProcessable> getTaskProcesses() throws TaskException {
        return Collections.unmodifiableList(new ArrayList<TaskProcessable>(0));
    }
}
