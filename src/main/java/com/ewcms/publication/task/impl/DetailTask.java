/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Article.Status;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.freemarker.generator.DetailGenerator;
import com.ewcms.publication.generator.Generatorable;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.event.DetailEvent;
import com.ewcms.publication.task.impl.process.GeneratorProcess;
import com.ewcms.publication.task.impl.process.TaskProcessable;
import com.ewcms.publication.uri.UriRuleable;
import com.ewcms.publication.uri.UriRules;

import freemarker.template.Configuration;

/**
 * 发布Detail（文章）页面任务
 * 
 * @author wangwei
 */
public class DetailTask extends TaskBase{
    private final static Logger logger = LoggerFactory.getLogger(DetailTask.class);
    
    private final static int MAX_PUBLISH_SIZE = 50000;
    
    public static class Builder extends BaseBuilder<Builder>{
        private final Configuration cfg;
        private final TemplateSourcePublishServiceable templateSourceService;
        private final ResourcePublishServiceable resourceService;
        private final ArticlePublishServiceable articleService;
        private final Channel channel;
        private final Template template;
        private long[] publishIds;
        private UriRuleable uriRule= UriRules.newDetail() ;
        
        public Builder(Configuration cfg,
                TemplateSourcePublishServiceable templateSourceService,
                ResourcePublishServiceable resourceService,
                ArticlePublishServiceable articleService,
                Site site,Channel channel,Template template){
            
            super(site);
            
            this.cfg = cfg;
            this.templateSourceService = templateSourceService;
            this.resourceService = resourceService;
            this.articleService = articleService;
            this.channel = channel;
            this.template = template;
            if(StringUtils.isNotBlank(template.getUriPattern())){
                uriRule =  UriRules.newUriRuleBy(template.getUriPattern());
            }
        }
        
        public Builder setPublishIds(long[] ids){
            this.publishIds = ids;
            return this;
        }
        
        @Override
        protected String getDescription() {
            return String.format("%s文章发布", template.getName()) ;
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
        
        protected List<Taskable> getDependenceTasks(){
            List<Taskable> dependences = new ArrayList<Taskable>();
            if(!dependence){
                dependenceResourceAndTemplateSource(dependences);
            }
            return dependences;
        }

        /**
         * 判断是否有指定的文章
         * 
         * @return
         */
        private boolean hasPublishIds(){
            return publishIds != null && publishIds.length > 0;
        }
        
        /**
         * 得到频道发布文章
         * 
         * @return 文章列表
         */
        private List<Article> getArticleOfChannel(){
            return articleService.findPublishArticles(channel.getId(), again, MAX_PUBLISH_SIZE);
        }

        /**
         * 是否需要发布
         * 
         * @param status 文章状态
         * @return true 需要发布
         */
        private boolean isPublish(Status status){
            return (status == Status.PRERELEASE) || (status==Status.RELEASE );
        }

        /**
         * 得到指定编号的文章列表
         * 
         * @return
         */
        private List<Article> getArticleOfPublishIds(){
            List<Article> articles = new ArrayList<Article>();
            for(Long id : publishIds){
                Article article = articleService.getArticle(id);
                if(article != null){
                    if(isPublish(article.getStatus())){
                        articles.add(article);                
                    }
                }else{
                    logger.warn("Article get by {} is null", id);
                }
            }
            return articles;
        }
        
        @Override
        protected List<TaskProcessable> getTaskProcesses() {
            
            List<TaskProcessable> processes = new ArrayList<TaskProcessable>();
            List<Article> articles = (hasPublishIds() ? getArticleOfPublishIds() : getArticleOfChannel());
            for(Article article : articles){
                final int total = article.getContentTotal();
                if(total ==  0){
                    continue;
                }
                Generatorable[] generators = new Generatorable[total];
                for(int page = 0 ; page < total ; page++ ){
                    generators[page] = new DetailGenerator(cfg,site,channel,uriRule,article,page);
                }
                TaskProcessable process = new GeneratorProcess(generators,template.getUniquePath());
                process.registerEvent(new DetailEvent(complete,articleService,article));
                processes.add(process);
            }
            return processes;
        }
    }
    
    public DetailTask(String id,Builder builder){
        super(id,builder);
    }
}
