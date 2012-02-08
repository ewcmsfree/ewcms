/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import java.util.List;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

import freemarker.template.Configuration;

/**
 * 发布模版生成的页面任务
 * 
 * @author wangwei
 */
public class TemplateTask extends TaskBase{
    
    public static class Builder{
        private final Configuration cfg;
        private final TemplatePublishServiceable templateService;
        private final TemplateSourcePublishServiceable templateSourceService;
        private final ResourcePublishServiceable resourceService;
        private final ArticlePublishServiceable articleService;
        private final Site site;
        private final Channel channel;
        private final Template template;
        private String username = DEFAULT_USERNAME;
        private boolean again = false;
        private boolean independence = true;
        private Taskable task;
        
        public Builder(Configuration cfg,
                TemplateSourcePublishServiceable templateSourceService,
                ResourcePublishServiceable resourceService,
                ArticlePublishServiceable articleService,
                TemplatePublishServiceable templateService,
                Site site,
                Channel channel,
                Template template){
            
            this.cfg = cfg;
            this.templateSourceService = templateSourceService;
            this.resourceService = resourceService;
            this.articleService = articleService;
            this.templateService = templateService;
            this.site = site;
            this.channel = channel;
            this.template = template;
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
        
        private Taskable newHomeTask(){
            return new HomeTask.Builder(
                    cfg,templateSourceService,site,channel,template).
                    setUsername(username).
                    setIndependence(independence).
                    build();
        }
        
        /**
         * 判断是否需要创建栏目首页
         * 
         * @return
         */
        private boolean isCreateHome(){
            List<Template> templates = templateService.getTemplatesInChannel(channel.getId());
            boolean home = true;
            for(Template template : templates){
                if(template.getType() == TemplateType.HOME){
                    home = false;
                    break;
                }
            }
            return home;
        }
        
        private Taskable newListTask(){
            return new ListTask.Builder(
                    cfg,templateSourceService,articleService,
                    site,channel,template).
                    setUsername(username).
                    setIndependence(independence).
                    setCreateHome(isCreateHome()).
                    build();    
        }
        
        private Taskable newDetailTask(){
            return new DetailTask.Builder(
                    cfg,templateSourceService,resourceService,
                    articleService,site,channel,template).
                    setUsername(username).
                    setIndependence(independence).
                    setAgain(again).
                    build();
        }
        
        private Taskable getTemplateTaskBy(TemplateType type,String patter) {
            Taskable task;
            switch (template.getType()) {
            case HOME:
                task = newHomeTask();
                break;
            case LIST:
                task = newListTask();
                break;
            case DETAIL:
                task = newDetailTask();
                break;
            default:
                task = new NoneTask();
            }
            return task;
        }
        
        public TemplateTask build(){
            task = getTemplateTaskBy(template.getType(),template.getUniquePath());
            return new TemplateTask(this);
        }
    }
    
    private final Builder builder;

    public TemplateTask(Builder builder){
        super(builder.task.getId().toString());
        this.builder = builder;
    }
    
    @Override
    public String getDescription() {
        return builder.task.getDescription();
    }

    @Override
    public String getUsername() {
        return builder.task.getUsername();
    }

    @Override
    public List<Taskable> getDependences() {
        return builder.task.getDependences();
    }

    @Override
    protected List<TaskProcessable> getTaskProcesses() throws TaskException {
        return ((TaskBase)builder.task).getTaskProcesses();
    }
}
