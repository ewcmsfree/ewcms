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
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.freemarker.generator.HomeGenerator;
import com.ewcms.publication.generator.Generatorable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
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

    public static class Builder  extends BaseBuilder<Builder>{
        
        private final Configuration cfg;
        private final TemplateSourcePublishServiceable templateSourceService;
        private final Channel channel;
        private final Template template;
        private UriRuleable uriRule= UriRules.newHome() ;
        
        public Builder(Configuration cfg,
                TemplateSourcePublishServiceable templateSourceService,
                Site site,Channel channel,Template template){
            super(site);
            
            Assert.notNull(cfg,"Freemark Configuration is null");
            Assert.notNull(templateSourceService,"Template source is null");
            Assert.notNull(channel,"Channel is null");
            Assert.notNull(template,"Template is null");
            
            this.cfg = cfg;
            this.templateSourceService= templateSourceService;
            this.channel = channel;
            this.template = template;
            if(StringUtils.isNotBlank(template.getUriPattern())){
                uriRule =  UriRules.newUriRuleBy(template.getUriPattern());
            }
        }
        
        @Override
        protected String getDescription() {
            return String.format("%s页面发布",  template.getName()) ;
        }
        
        @Override
        protected List<Taskable> getDependenceTasks(){
            List<Taskable> dependences = new ArrayList<Taskable>();
            if(!dependence){
                dependences.add(
                        new  TemplateSourceTask
                        .Builder(templateSourceService, site)
                        .setUsername(username)
                        .build());
            }
            return dependences;
        }

        @Override
        public List<TaskProcessable> getTaskProcesses() {
            List<TaskProcessable> processes = new ArrayList<TaskProcessable>();
            Generatorable generator = new HomeGenerator(cfg,site,channel,uriRule);   
            TaskProcessable process = new GeneratorProcess(generator, template.getUniquePath());
            process.registerEvent(new CompleteEvent(complete));
            processes.add(process);
            return processes;
        }
    }
    
    public HomeTask(String id,Builder builder){
        super(id,builder);
    }
}
