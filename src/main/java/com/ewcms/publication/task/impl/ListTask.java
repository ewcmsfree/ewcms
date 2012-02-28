/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.freemarker.generator.ListGenerator;
import com.ewcms.publication.generator.Generatorable;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.event.CompleteEvent;
import com.ewcms.publication.task.impl.process.GeneratorProcess;
import com.ewcms.publication.task.impl.process.TaskProcessable;
import com.ewcms.publication.uri.UriRuleable;
import com.ewcms.publication.uri.UriRules;

import freemarker.template.Configuration;

/**
 * 发布List页面任务
 * 
 * @author wangwei
 */
public class ListTask extends TaskBase{
    private static final Logger logger = LoggerFactory.getLogger(ListTask.class);
    
    public static class Builder extends BaseBuilder<Builder>{
        private final Configuration cfg;
        private final TemplateSourcePublishServiceable templateSourceService;
        private final ArticlePublishServiceable articleService;
        private final Channel channel;
        private final Template template;
        private UriRuleable uriRule= UriRules.newList() ;
        private boolean createHome = false;
        
        public Builder(Configuration cfg,
                TemplateSourcePublishServiceable templateSourceService,
                ArticlePublishServiceable articleService,
                Site site,Channel channel,Template template){
            super(site);
            
            this.cfg = cfg;
            this.templateSourceService = templateSourceService;
            this.articleService = articleService;
            this.channel = channel;
            this.template = template;
            if(StringUtils.isNotBlank(template.getUriPattern())){
                uriRule =UriRules.newUriRuleBy(template.getUriPattern());
            }
        }
        
        public Builder setCreateHome(boolean createHome){
            this.createHome = createHome;
            return this;
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
                        .build());
            }
            return dependences;
        }
        
        /**TaskProcessable
         * 得到频道需要发布的页数
         * 
         * @param channelId 频道编号
         * @param row 每页行数builder
         * @return 总页数
         */
        private int getPageCount(int channelId, int row){
            Integer count = articleService.getArticleReleaseCount(channelId);
            logger.debug("Article count is {}",count);
            return (count + row -1)/row;
        }
        
        @Override
        protected List<TaskProcessable> getTaskProcesses() {
            
            int pageCount = getPageCount(channel.getId(),channel.getListSize());
            logger.debug("Page count is {}",pageCount);
            List<TaskProcessable> processes = new ArrayList<TaskProcessable>();
            for(int page = 0 ; page < pageCount ; page++){
                Generatorable generator = 
                    new ListGenerator(cfg,site,channel,uriRule,page,pageCount,createHome);
                TaskProcessable process = new GeneratorProcess(generator, template.getUniquePath());
                process.registerEvent(new CompleteEvent(complete));
                processes.add(process);
            }
            return processes;
        }
    }
    
    public ListTask(String id,Builder builder){
        super(id,builder);
    }
}
