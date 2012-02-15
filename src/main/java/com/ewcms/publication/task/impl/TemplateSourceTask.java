/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;
import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.event.TemplateSourceEvent;
import com.ewcms.publication.task.impl.process.TaskProcessable;
import com.ewcms.publication.task.impl.process.TemplateSourceProcess;

/**
 * 发布模版资源任务
 * 
 * @author wangwei
 */
public class TemplateSourceTask extends TaskBase {
    private final static Logger logger = LoggerFactory.getLogger(TemplateSourceTask.class);
    
    public static class Builder{
        private final TemplateSourcePublishServiceable templateSourceService;
        private final Integer siteId;
        private final String name;
        private String username = DEFAULT_USERNAME;
        private int[] sourceIds;
        private boolean again = false;
        
        public Builder(TemplateSourcePublishServiceable templateSourceService,Site site){
            Assert.notNull(templateSourceService,"Template source service is null");
            Assert.notNull(site,"Site is null");
            
            this.templateSourceService = templateSourceService;
            this.siteId = site.getId();
            this.name = site.getSiteName();
        }

        public Builder setSourceIds(int[] sourceIds) {
            this.sourceIds = sourceIds;
            return this;
        }

        public Builder forceAgain() {
            this.again = true;
            return this;
        }
        
        public Builder setAgain(boolean again){
            this.again = again;
            return this;
        }
        
        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        
        public TemplateSourceTask builder(){
            return new  TemplateSourceTask(this);
        }
    }
    
    private final Builder builder; 
    
    public TemplateSourceTask(Builder builder){
        super(newTaskId());
        this.builder = builder;
    }

    @Override
    public String getDescription() {
        String description = String.format("%s-模版资源发布%s",
                builder.name,getAgainMessage(builder.again)) ;
        return description;
    }

    @Override
    public String getUsername() {
        return builder.username;
    }

    protected boolean isAgain(){
        return builder.again;
    }
    
    protected int[] getSourceIds(){
        return builder.sourceIds;
    }
    
    @Override
    public List<Taskable> getDependences() {
        return Collections.unmodifiableList(new ArrayList<Taskable>(0));
    }

    /**
     * 判断模版资源是否是目录
     * 
     * @param source 模版资源对象
     * @return true 是目录
     */
    private boolean isDirectory(TemplateSource source){
        return source.getSourceEntity() == null;
    }
    
    /**
     * 递归得到子模版资源
     * 
     * @param sources 发布的模版资源集合
     * @param parent 父资源对象
     */
    private void getTemplateSourceChildren(List<TemplateSource> sources,TemplateSource parent){
        
        if(isDirectory(parent)){
            sources.add(parent);    
        }
        
        List<TemplateSource> children = 
            builder.templateSourceService.getTemplateSourceChildren(parent.getId());
        
        for(TemplateSource child : children){
            getTemplateSourceChildren(sources,child);
        }
    }
    
    /**
     * 发布的模版资源集合
     * 
     * @return 模版资源集合
     * @throws TaskException
     */
    private List<TemplateSource> publishTemplateSources()throws TaskException{
        
        TemplateSourcePublishServiceable templateSourceService = builder.templateSourceService;
        List<TemplateSource> sources = null;
        if(builder.sourceIds == null){
            sources =templateSourceService.findPublishTemplateSources(builder.siteId, builder.again);
        }else{
            sources = new ArrayList<TemplateSource>();
            for(Integer id : builder.sourceIds){
                TemplateSource source = templateSourceService.getTemplateSource(id);
                if(source != null){
                    getTemplateSourceChildren(sources,source);
                }else{
                    logger.warn("TemplateSource id = {} is not exist",id);
                }
            }    
        }
        
        return sources;
    }
    
    @Override
    protected List<TaskProcessable> getTaskProcesses()throws TaskException {
        List<TemplateSource> sources= publishTemplateSources();
        List<TaskProcessable> taskProcesses = new ArrayList<TaskProcessable>();
        for(TemplateSource source : sources){
            TemplateSourceProcess process = new TemplateSourceProcess(
                        source.getPath(),source.getSourceEntity().getSrcEntity());
            process.registerEvent(
                    new TemplateSourceEvent(complete,source,builder.templateSourceService));
            taskProcesses.add(process);
        }
        return taskProcesses;
    }   
}
