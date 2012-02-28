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
    
    public static class Builder extends BaseBuilder<Builder>{
        private final TemplateSourcePublishServiceable templateSourceService;
        private int[] publishIds;
        
        public Builder(TemplateSourcePublishServiceable templateSourceService,Site site){
            super(site);
            Assert.notNull(templateSourceService,"Template source service is null");
            this.templateSourceService = templateSourceService;
        }

        public Builder setPublishIds(int[] publishIds) {
            this.publishIds = publishIds;
            return this;
        }

        @Override
        protected String getDescription() {
            return String.format("%s模版资源发布",site.getSiteName()) ;
        }
        
        @Override
        protected List<Taskable> getDependenceTasks() {
            return new ArrayList<Taskable>(0);
        }

        private boolean hasPublishIds(){
            return publishIds != null && publishIds.length > 0;
        }
        
        private  List<TemplateSource> getTemplateSourceOfSite(){
            return templateSourceService.findPublishTemplateSources(site.getId(), again);
        }
        
        /**
         * 判断是否是模版资源
         * 
         * @param source 模版资源对象
         * @return true 模版资源
         */
        private boolean isTemplateSource(TemplateSource source){
            return source.getSourceEntity() != null;
        }
        
        /**
         * 递归得到要发布的子模版资源
         * 
         * @param sources 发布的模版资源集合
         * @param parent 父资源对象
         */
        private void getTemplateSourceChildren(List<TemplateSource> sources,TemplateSource parent){
            if(isTemplateSource(parent)){
                sources.add(parent);    
            }
            List<TemplateSource> children =
                templateSourceService.getTemplateSourceChildren(parent.getId());
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
        private List<TemplateSource> getTemplateSourceOfPublishIds(){
            
            List<TemplateSource> sources = new ArrayList<TemplateSource>();
            for(Integer id : publishIds){
                TemplateSource source = templateSourceService.getTemplateSource(id);
                if(source != null){
                    getTemplateSourceChildren(sources,source);
                }else{
                    logger.warn("TemplateSource id = {} is not exist",id);
                }
            }    
            return sources;
        }
        
        @Override
        protected List<TaskProcessable> getTaskProcesses() {
            List<TemplateSource> sources = hasPublishIds() ? getTemplateSourceOfPublishIds() : getTemplateSourceOfSite();
            List<TaskProcessable> taskProcesses = new ArrayList<TaskProcessable>();
            for(TemplateSource source : sources){
                TemplateSourceProcess process = 
                    new TemplateSourceProcess(source.getPath(),source.getSourceEntity().getSrcEntity());
                process.registerEvent(new TemplateSourceEvent(complete,source,templateSourceService));
                taskProcesses.add(process);
            }
            return taskProcesses;
        }
    }
    
    public TemplateSourceTask(String id,Builder builder){
        super(id,builder);
    }

    protected boolean isAgain(){
        return builder.again;
    }
    
    protected int[] getPublishIds(){
        return ((Builder)builder).publishIds;
    }
}
