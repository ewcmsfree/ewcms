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
 * 发布整个站点任务
 * 
 * @author wangwei
 */
public class SiteTask extends TaskBase {

    public static class Builder {
        private final Configuration cfg;
        private final ChannelPublishServiceable channelService;
        private final TemplatePublishServiceable templateService;
        private final TemplateSourcePublishServiceable templateSourceService;
        private final ResourcePublishServiceable resourceService;
        private final ArticlePublishServiceable articleService;
        private final Site site;
        private String username = DEFAULT_USERNAME;
        private boolean again = false;
        private List<Taskable> dependences;
        
        public Builder(Configuration cfg,
                TemplatePublishServiceable templateService,
                TemplateSourcePublishServiceable templateSourceService,
                ResourcePublishServiceable resourceService,
                ArticlePublishServiceable articleService,
                ChannelPublishServiceable channelService,
                Site site){
            
            this.cfg = cfg;
            this.templateService = templateService;
            this.templateSourceService = templateSourceService;
            this.resourceService = resourceService;
            this.articleService = articleService;
            this.channelService = channelService;
            this.site = site;
        }
        
        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        
        public Builder forceAgain(){
            this.again = true;
            return this;
        }
        
        public Builder setAgain(boolean again){
            this.again = again;
            return this;
        }
        
        private List<Taskable> getDependences(){
            List<Taskable> dependences = new ArrayList<Taskable>();
            dependences.add(new  TemplateSourceTask.Builder(templateSourceService,site).
                    setAgain(again).
                    builder());
            dependences.add(new ResourceTask.Builder(resourceService,site).
                    setAgain(again).
                    builder());
            Channel root = channelService.getChannelRoot(site.getId());
            dependences.add(new ChannelTask.Builder(cfg, templateService, 
                    templateSourceService, resourceService,
                    articleService, channelService,site,root).
                    setAgain(again).
                    dependence().
                    publishChildren().
                    build());
            return dependences;
        }
        
        public SiteTask build(){
            this.dependences = getDependences();
            return new SiteTask(this);
        }
    }
    
    private final Builder builder;
    
    public SiteTask(Builder builder){
        super(newTaskId());
        this.builder = builder;
    }
    
    @Override
    public String getDescription() {
        String description = String.format("%s-站点发布%s",
                builder.site.getSiteName(),getAgainMessage(builder.again)) ;
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
    protected List<TaskProcessable> getTaskProcesses() throws TaskException {
        return Collections.unmodifiableList(new ArrayList<TaskProcessable>(0));
    }
}
