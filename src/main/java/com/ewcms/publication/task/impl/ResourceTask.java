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

import com.ewcms.content.resource.model.Resource;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.event.ResourceEvent;
import com.ewcms.publication.task.impl.process.ResourceProcess;
import com.ewcms.publication.task.impl.process.TaskProcessable;

/**
 * 发布内容资源任务
 * 
 * @author wangwei
 */
public class ResourceTask extends TaskBase{
    private final static Logger logger = LoggerFactory.getLogger(ResourceTask.class);
    
    public static class Builder extends BaseBuilder<Builder>{
        private final ResourcePublishServiceable resourceService;
        private int[] publishIds;
        
        public Builder(ResourcePublishServiceable resourceService,Site site){
            super(site);
            this.resourceService = resourceService;
        }
        
        public Builder setPublishIds(int[] publishIds){
            this.publishIds = publishIds;
            return this;
        }
        
        @Override
        protected String getDescription() {
            return String.format("%s资源发布", site.getSiteName()) ;
        }

        
        @Override
        protected List<Taskable> getDependenceTasks() {
            return new ArrayList<Taskable>(0);
        }
        
        private boolean hasPublishIds(){
            return publishIds != null && publishIds.length > 0;
        }
        
        private List<Resource> getResourceOfSite(){
            return resourceService.findPublishResources(site.getId(),again);
        }

        /**
         * 是否需要发布
         * 
         * @param state 资源状态
         * @return true 需要发布
         */
        private boolean isPublish(Resource.Status state){
            return (state == Resource.Status.NORMAL) 
                       ||(state==Resource.Status.RELEASED);
        }
        
        private List<Resource> getResourceOfPublishIds(){
            List<Resource> resources = new ArrayList<Resource>();
            for(Integer id : publishIds){
                Resource resource = resourceService.getResource(id);
                if(resource != null){
                    if(isPublish(resource.getStatus())){
                        resources.add(resource);
                    }
                }else{
                    logger.warn("Resource is null,Id is {}.",id);
                }
            }
            return resources;
        }
        
        /**
         * 判断是否有引导图
         * 
         * @param resource 资源对象
         * @return true 有引导图
         */
        private boolean isThumb(Resource resource){
            return StringUtils.isNotBlank(resource.getThumbPath()) 
                    && StringUtils.isNotBlank(resource.getThumbUri());
        }
        
        @Override
        protected List<TaskProcessable> getTaskProcesses(){
            List<Resource> resources = (hasPublishIds() ? getResourceOfPublishIds() : getResourceOfSite());
            List<TaskProcessable> processes = new ArrayList<TaskProcessable>();
            for(Resource resource : resources){
                String[] paths;
                String[] uris;
                if(isThumb(resource)){
                    paths = new String[]{resource.getThumbPath(),resource.getPath()};
                    uris = new String[]{resource.getThumbUri(),resource.getUri()};
                }else{
                    paths = new String[]{resource.getPath()};
                    uris = new String[]{resource.getUri()};
                }
                TaskProcessable process = new ResourceProcess(paths,uris);
                process.registerEvent(new ResourceEvent(complete,resource,resourceService));
                processes.add(process);
            }
            
            return processes;
        }
    }
    
    public ResourceTask(String id,Builder builder){
        super(id,builder);
    }

    protected boolean isAgain(){
        return builder.again;
    }
    
    protected int[] getResourceIds(){
        return ((Builder)builder).publishIds;
    }
}
