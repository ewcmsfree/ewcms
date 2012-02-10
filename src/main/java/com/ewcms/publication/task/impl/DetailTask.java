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
import com.ewcms.publication.task.TaskException;
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
    
    public static class Builder{
        private final Configuration cfg;
        private final TemplateSourcePublishServiceable templateSourceService;
        private final ResourcePublishServiceable resourceService;
        private final ArticlePublishServiceable articleService;
        private final Site site;
        private final Channel channel;
        private final String path;
        private final String name;
        private String username = DEFAULT_USERNAME;
        private boolean again = false;
        private long[] articleIds;
        private UriRuleable uriRule= UriRules.newDetail() ;
        private boolean independence = true;
        private List<Taskable> dependences;
        
        public Builder(Configuration cfg,
                TemplateSourcePublishServiceable templateSourceService,
                ResourcePublishServiceable resourceService,
                ArticlePublishServiceable articleService,
                Site site,Channel channel,Template template){
            
            this.cfg = cfg;
            this.templateSourceService = templateSourceService;
            this.resourceService = resourceService;
            this.articleService = articleService;
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
        
        public Builder setArticleIds(long[] ids){
            this.articleIds = ids;
            return this;
        }
        
        public Builder forceAgain(){
            this.again = true;
            return this;
        }
        
        Builder setAgain(boolean again){
            this.again = again;
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
                dependences.add(new  TemplateSourceTask.Builder(templateSourceService,site).builder());
                dependences.add(new ResourceTask.Builder(resourceService,site).builder());
            }
            return dependences;
        }
        
        public DetailTask build(){
            dependences = getDependenceTasks();
            return new DetailTask(this);
        }
        
    }
    
    private final Builder builder;
    
    public DetailTask(Builder builder){
        super(newTaskId());
        this.builder = builder;
    }
    
    @Override
    public String getDescription() {
        String description = String.format("%s-页面发布%s",
                builder.name,getAgainMessage(builder.again)) ;
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

    /**
     * 是否需要发布
     * 
     * @param status 文章状态
     * @return true 需要发布
     */
    private boolean isPublish(Status status){
        return (status == Status.PRERELEASE) || 
                (status==Status.RELEASE && builder.again);
    }
    
    private List<Article> getPublishArticles(){
        
        if(builder.articleIds == null){
            return builder.articleService.findPreReleaseArticles(
                    builder.channel.getId(), MAX_PUBLISH_SIZE);
        }
        
        List<Article> articles = new ArrayList<Article>();
        for(Long id : builder.articleIds){
            Article article = builder.articleService.getArticle(id);
            if(article == null){
                logger.warn("Article get by {} is null", id);
                continue;
            }
            if(isPublish(article.getStatus())){
                articles.add(article);                
            }
        }
        return articles;
    }
    
    @Override
    protected List<TaskProcessable> getTaskProcesses() throws TaskException {
        
        List<TaskProcessable> processes = new ArrayList<TaskProcessable>();
        List<Article> articles = getPublishArticles();
        for(Article article : articles){
            final int total = article.getContentTotal();
            if(total ==  0){
                continue;
            }
            Generatorable[] generators = new Generatorable[total];
            for(int page = 0 ; page < total ; page++ ){
                generators[page] = new DetailGenerator(
                        builder.cfg,builder.site,builder.channel,builder.uriRule,article,page);
            }
            TaskProcessable process = new GeneratorProcess(generators,builder.path);
            process.registerEvent(new DetailEvent(complete,builder.articleService,article));
            processes.add(process);
        }
        return processes;
    }
    
    public int getProgress() {
        if(count.get()== 0){
            return 100;
        }
        if((builder.channel.getId() == 223 || builder.channel.getId() == 377)){
            System.out.println(count);
        }
        return (complete.get() * 100 / count.get());
    }
}
