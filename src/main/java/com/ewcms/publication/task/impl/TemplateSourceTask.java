/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

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
    private final static String DEFAULT_DESCRIPTION="publish template source";
    
    public static class Builder{
        private final TemplateSourcePublishServiceable templateSourceService;
        private final Integer siteId;
        private String username = DEFAULT_USERNAME;
        private Integer sourceId;
        private String description = DEFAULT_DESCRIPTION;
        private boolean again = false;
        
        public Builder(TemplateSourcePublishServiceable templateSourceService,Integer siteId){
            Assert.notNull(templateSourceService,"Template source service is null");
            Assert.notNull(siteId,"Site id is null");
            
            this.templateSourceService = templateSourceService;
            this.siteId = siteId;
        }

        public Builder setSourceId(Integer sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        public Builder forceAgain() {
            this.again = true;
            return this;
        }
        
        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        
        public Builder setDescription(String description) {
            this.description = description;
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
        return builder.description;
    }

    @Override
    public Integer getSiteId() {
        return builder.siteId;
    }

    @Override
    public String getUsername() {
        return builder.username;
    }

    protected boolean isAgain(){
        return builder.again;
    }
    
    protected Integer getSourceId(){
        return builder.sourceId;
    }
    
    @Override
    public List<Taskable> getDependences() {
        return new ArrayList<Taskable>(0);
    }

    @Override
    public void close() throws TaskException {
        //not instance
    }
    
    /**
     * 递归得到子模版资源
     * 
     * @param sources 资源集合
     * @param parent 父资源对象
     */
    private void getTemplateSourceChildren(List<TemplateSource> sources,TemplateSource parent){
        
        if(builder.again || !parent.getRelease()){
            sources.add(parent);    
        }
        
        List<TemplateSource> children = 
            builder.templateSourceService.getTemplateSourceChildren(parent.getId());
        if(children == null || children.isEmpty()){
            return ;
        }
        for(TemplateSource child : children){
            getTemplateSourceChildren(sources,child);
        }
    }
    
    /**
     * 判断是否是模版资源目录
     * 
     * @param source 模版资源对象
     * @return
     */
    private boolean isDirectory(TemplateSource source){
        return source.getSourceEntity() == null;
    }
    
    /**
     * 移除模版资源中是目录的对象
     * 
     * @param sources
     * @return
     */
    private List<TemplateSource> removeDirectory(List<TemplateSource> sources){
        List<TemplateSource> entities = new ArrayList<TemplateSource>();
        for(TemplateSource source : sources){
            if(isDirectory(source)){
                logger.debug("{} is directory",source.getUniquePath());
                continue;
            }
            entities.add(source);
        }
        return entities;
    }
    
    /**
     * 发布的模版资源集合
     * 
     * @return 模版资源集合
     * @throws TaskException
     */
    private List<TemplateSource> publishTemplateSources()throws TaskException{
        
        TemplateSourcePublishServiceable templateSourceService = builder.templateSourceService;
        if(builder.sourceId == null){
            List<TemplateSource> sources =
                templateSourceService.findNotReleaseTemplateSources(builder.siteId);
            return removeDirectory(sources);
        }
        
        List<TemplateSource> sources = new ArrayList<TemplateSource>();
        TemplateSource source = templateSourceService.getTemplateSource(builder.sourceId);
        if(source == null){
            logger.debug("TemplateSource id = {} is not exist",id);
            throw new TaskException("TemplateSource is not exits");
        }
        getTemplateSourceChildren(sources,source);
        
        return removeDirectory(sources);
    }
    
    @Override
    protected List<TaskProcessable> getTaskProcesses()throws TaskException {
        List<TemplateSource> sources= publishTemplateSources();
        List<TaskProcessable> taskProcesses = new ArrayList<TaskProcessable>();
        for(TemplateSource source : sources){
            TemplateSourceProcess process = new TemplateSourceProcess(
                        source.getPath(),source.getSourceEntity().getSrcEntity());
            process.registerEvent(
                    new TemplateSourceEvent(
                            this,source,builder.templateSourceService));
            taskProcesses.add(process);
        }
        return taskProcesses;
    }   
}
