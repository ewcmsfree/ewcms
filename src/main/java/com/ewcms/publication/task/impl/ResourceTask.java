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

import com.ewcms.content.resource.model.Resource;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.task.TaskException;
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
    
    public static class Builder{
        private final ResourcePublishServiceable resourceService;
        private final Integer siteId;
        private final String name;
        private String username = DEFAULT_USERNAME;
        private Integer[] resourceIds;
        private boolean again = false;
        
        public Builder(ResourcePublishServiceable resourceService,Site site){
            this.resourceService = resourceService;
            this.siteId = site.getId();
            this.name = site.getSiteName();
        }
        
        public Builder setUsername(String username){
            this.username = username;
            return this;
        }
        
        public Builder setResourceIds(Integer[] ids){
            this.resourceIds = ids;
            return this;
        }
        
        public Builder forceAgain(){
            this.again = true;
            return this;
        }
        
        public Builder setAgain(boolean again){
            this.again = again;
            return this;
        }
        
        public ResourceTask builder(){
            return new ResourceTask(this);
        }
    }
    
    private final Builder builder;
    
    public ResourceTask(Builder builder){
        super(newTaskId());
        this.builder = builder;
    }
    
    @Override
    public String getDescription() {
        String description = String.format("%s-资源发布%s",
                builder.name,getAgainMessage(builder.again)) ;
        return description;
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
    
    protected Integer[] getResourceIds(){
        return builder.resourceIds;
    }
    
    @Override
    public List<Taskable> getDependences() {
        return Collections.unmodifiableList(new ArrayList<Taskable>(0));
    }

    @Override
    public void close() throws TaskException {
        // do not instance
    }

    /**
     * 是否需要发布
     * 
     * @param state 资源状态
     * @return true 需要发布
     */
    private boolean isPublish(Resource.State state){
        return (state == Resource.State.NORMAL) || 
                (state==Resource.State.RELEASED && builder.again);
    }
    
    private List<Resource> publishResources(){
        
        List<Resource> resources;
        ResourcePublishServiceable service = builder.resourceService;
        if(builder.resourceIds == null){
            resources = service.findNotReleaseResources(builder.siteId);
        }else{
            resources = new ArrayList<Resource>();
            for(Integer id : builder.resourceIds){
                Resource resource = service.getResource(id);
                if(resource == null){
                    logger.warn("Resource is null,Id is {}.",id);
                    continue;
                }
                if(isPublish(resource.getState())){
                    resources.add(resource);
                }
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
    protected List<TaskProcessable> getTaskProcesses() throws TaskException {
        List<Resource> resources = publishResources();
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
            process.registerEvent(new ResourceEvent(this,resource,builder.resourceService));
            processes.add(process);
        }
        
        return processes;
    }
}
