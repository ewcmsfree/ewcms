/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
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
        private final String name;
        private String username = DEFAULT_USERNAME;
        private UriRuleable uriRule= UriRules.newHome() ;
        private boolean independence = true;
        private List<Taskable> dependences;
        
        public Builder(Configuration cfg,
                TemplateSourcePublishServiceable templateSourceService,
                Site site,Channel channel,Template template){
            
            Assert.notNull(cfg,"Freemark Configuration is null");
            Assert.notNull(templateSourceService,"Template source is null");
            Assert.notNull(site,"Site is null");
            Assert.notNull(channel,"Channel is null");
            Assert.notNull(template,"Template is null");
            
            this.cfg = cfg;
            this.templateSourceService= templateSourceService;
            this.site = site;
            this.channel = channel;
            this.path = template.getUniquePath();
            this.name = template.getName();
            if(StringUtils.isNotBlank(template.getUriPattern())){
                uriRule =  UriRules.newUriRuleBy(template.getUriPattern());
            }
        }
        
        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        
        public Builder dependence(){
            this.independence = false;
            return this;
        }
        
        Builder setIndependence(boolean independence){
            this.independence = independence;
            return this;
        }
        
        private List<Taskable> getDependenceTasks(){
            List<Taskable> dependences = new ArrayList<Taskable>();
            if(independence){
                dependences.add(new  TemplateSourceTask.Builder(templateSourceService, site).builder());
            }
            return dependences;
        }
        
        public HomeTask build(){
            dependences = getDependenceTasks();
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
        String description = String.format("%s-页面发布",builder.name) ;
        return description;
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
    
    UriRuleable getUriRule(){
        return builder.uriRule;
    }
    
    Channel getChannel(){
        return builder.channel;
    }
    
    Site getSite(){
        return builder.site;
    }
    
    boolean isIndependence(){
        return builder.independence;
    }
    
    @Override
    public List<Taskable> getDependences() {
        return Collections.unmodifiableList(builder.dependences);
    }

    @Override
    public void close() throws TaskException {
        //do not instance
    }

    @Override
    protected List<TaskProcessable> getTaskProcesses() throws TaskException {
            List<TaskProcessable> processes = new ArrayList<TaskProcessable>();
            Generatorable generator = 
                new HomeGenerator(builder.cfg,builder.site,builder.channel,builder.uriRule);   
            TaskProcessable process = new GeneratorProcess(generator,builder.path);
            process.registerEvent(new CompleteEvent(this));
            processes.add(process);
            return processes;
    }
}
