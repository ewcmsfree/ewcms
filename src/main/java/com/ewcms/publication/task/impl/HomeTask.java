/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.freemarker.generator.HomeGenerator;
import com.ewcms.publication.generator.Generatorable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.event.CompleteEvent;
import com.ewcms.publication.task.impl.process.GeneratorProcess;
import com.ewcms.publication.task.impl.process.TaskProcessable;
import com.ewcms.publication.uri.UriRuleable;
import com.ewcms.publication.uri.UriRules;

import freemarker.template.Configuration;

/**
 * 发布home页面任务
 * 
 * @author wangwei
 */
public class HomeTask extends TaskBase{

    public static class Builder {
        
        private final Configuration cfg;
        private final TemplateSourcePublishServiceable templateSourceService;
        private final Site site;
        private final Channel channel;
        private final String path;
        private String description;
        private String username = DEFAULT_USERNAME;
        private String uriRulePatter ;
        private boolean independence = true;
        private List<Taskable> depencences;
        
        public Builder(Configuration cfg,
                TemplateSourcePublishServiceable templateSourceService,
                Site site,Channel channel,String path){
            
            Assert.notNull(cfg,"Freemark Configuration is null");
            Assert.notNull(templateSourceService,"Template source is null");
            Assert.notNull(site,"Site is null");
            Assert.notNull(channel,"Channel is null");
            Assert.notNull(path,"Template path is null");
            
            this.cfg = cfg;
            this.templateSourceService= templateSourceService;
            this.site = site;
            this.channel = channel;
            this.path = path;
        }
        
        public Builder setDescription(String description){
            this.description = description;
            return this;
        }
        
        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        
        public Builder setUriRulePatter(String uriRulePatter){
            this.uriRulePatter = uriRulePatter;
            return this;
        }
        
        public Builder dependence(){
            this.independence = false;
            return this;
        }
        
        private List<Taskable> getDependenceTasks(){
            List<Taskable> dependences = new ArrayList<Taskable>();
            if(independence){
                dependences.add(new  TemplateSourceTask.Builder(
                        templateSourceService, site.getId()).
                        builder());
            }
            return dependences;
        }
        
        public HomeTask build(){
            depencences = getDependenceTasks();
            return new HomeTask(this);
        }
    }
    
    private final Builder builder;
    
    public HomeTask(Builder builder){
        super(newTaskId());
        this.builder = builder;
    }

    @Override
    public String getDescription() {
        return builder.description;
    }

    @Override
    public Integer getSiteId() {
        return builder.site.getId();
    }

    @Override
    public String getUsername() {
        return builder.username;
    }

    Configuration getConfiguration(){
        return builder.cfg;
    }
    
    String getPath(){
        return builder.path;
    }
    
    String getUriRulePatter(){
        return builder.uriRulePatter;
    }
    
    Channel getChannel(){
        return builder.channel;
    }
    
    Site getSite(){
        return builder.site;
    }
    
    @Override
    public List<Taskable> getDependences() {
        return builder.depencences;
    }

    @Override
    public void close() throws TaskException {
        //do not instance
    }

    @Override
    protected List<TaskProcessable> getTaskProcesses() throws TaskException {
            List<TaskProcessable> processes = new ArrayList<TaskProcessable>();
            UriRuleable uriRule = (StringUtils.isBlank(builder.uriRulePatter) ? 
                    UriRules.newHome() : UriRules.newUriRuleBy(builder.uriRulePatter));
            Generatorable generator =  new HomeGenerator(builder.cfg,builder.site,builder.channel,uriRule);   
            TaskProcessable process = new GeneratorProcess(generator,builder.path);
            process.registerEvent(new CompleteEvent(this));
            processes.add(process);
            return processes;
    }
}
