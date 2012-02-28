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
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

import freemarker.template.Configuration;

/**
 * 发布整个站点任务
 * 
 * @author wangwei
 */
public class SiteTask extends TaskBase {

    public static class Builder extends BaseBuilder<Builder> {
        
        private final Configuration cfg;
        private final ChannelPublishServiceable channelService;
        private final TemplatePublishServiceable templateService;
        private final TemplateSourcePublishServiceable templateSourceService;
        private final ResourcePublishServiceable resourceService;
        private final ArticlePublishServiceable articleService;
        
        public Builder(Configuration cfg,
                TemplatePublishServiceable templateService,
                TemplateSourcePublishServiceable templateSourceService,
                ResourcePublishServiceable resourceService,
                ArticlePublishServiceable articleService,
                ChannelPublishServiceable channelService,
                Site site){
            
            super(site);
            
            this.cfg = cfg;
            this.templateService = templateService;
            this.templateSourceService = templateSourceService;
            this.resourceService = resourceService;
            this.articleService = articleService;
            this.channelService = channelService;
        }
        
        @Override
        protected String getDescription() {
            return String.format("%s站点发布", site.getSiteName()) ;
        }
        
        private void dependenceResourceAndTemplateSource(List<Taskable> dependences){
            dependences.add(new  TemplateSourceTask.Builder(templateSourceService,site).
                    setAgain(again).
                    build());
            dependences.add(new ResourceTask.Builder(resourceService,site).
                    setAgain(again).
                    build());
        }

        private void dependenceRootChannel(List<Taskable> dependences){
            Channel root = channelService.getChannelRoot(site.getId());
            dependences.add(new ChannelTask.Builder(cfg, templateService, 
                    templateSourceService, resourceService,
                    articleService, channelService,site,root).
                    setAgain(again).
                    setDependence(true).
                    setPublishChildren(true).
                    build());
        }
        
        @Override
        protected List<Taskable> getDependenceTasks() {
            List<Taskable> dependences = new ArrayList<Taskable>();
            dependenceResourceAndTemplateSource(dependences);
            dependenceRootChannel(dependences);
            return dependences;
        }

        @Override
        protected List<TaskProcessable> getTaskProcesses() {
            return new ArrayList<TaskProcessable>(0);
        }
    }
    
    public SiteTask(String id,Builder builder){
        super(id,builder);
    }
}
