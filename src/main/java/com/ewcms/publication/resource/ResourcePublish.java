/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.SiteServer;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.core.site.model.TemplatesrcEntity;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.output.OutputFactory;
import com.ewcms.publication.output.OutputResource;
import com.ewcms.publication.output.event.ResourceOutputEvent;
import com.ewcms.publication.output.event.TemplateSourceOutputEvent;
import com.ewcms.publication.service.ResourcePublishServiceable;
import com.ewcms.publication.service.SitePublishServiceable;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;

/**
 * 发布站点资源
 * 
 * @author wangwei
 */
public class ResourcePublish implements ResourcePublishable{
    
    private static final Logger logger = LoggerFactory.getLogger(ResourcePublish.class);
    
    private SitePublishServiceable siteService;
    private ResourcePublishServiceable resourceService;
    private TemplateSourcePublishServiceable templateSourceService;
    
    /**
     * 构造资源发布对象
     * 
     * @param siteService
     *              站点服务
     * @param resourceService
     *              资源服务
     * @param templateSourceService
     *              模板资源服务
     */
    public ResourcePublish(SitePublishServiceable siteService,
            ResourcePublishServiceable resourceService,
            TemplateSourcePublishServiceable templateSourceService){
        
        Assert.notNull(siteService,"siteService is null");
        Assert.notNull(resourceService,"resourceService is null");
        Assert.notNull(templateSourceService,"templateSourceService is null");
        
        this.siteService = siteService;
        this.resourceService = resourceService;
        this.templateSourceService = templateSourceService;
    }
    
    @Override
    public void publishSite(Integer id) throws PublishException {
        Site site = siteService.getSite(id);
        if(site == null){
            logger.debug("Site id = {} is not exist",id);
            throw new PublishException("Site is not exits");
        }
        SiteServer server = site.getSiteServer();
        publishSiteLocalResource(id,server);
        publishSiteTemplateSource(id,server);
    }

    /**
     * 创建输出资源对象
     * 
     * @param resource 资源对象 
     * @return
     */
    private OutputResource createOutputResource(Resource resource){
        OutputResource outputResource = new OutputResource();
        outputResource.addChild(new OutputResource(resource.getPath(),resource.getUri(),false));
        if(StringUtils.isNotBlank(resource.getThumbPath()) && StringUtils.isNotBlank(resource.getThumbUri())){
            outputResource.addChild(new OutputResource(resource.getThumbPath(),resource.getThumbUri(),false));
        }
        outputResource.registerEvent(new ResourceOutputEvent(resource.getId(), resourceService));
        return outputResource;
    }
    
    private void publishSiteLocalResource(Integer id,SiteServer server)throws PublishException{
        List<Resource> resources = resourceService.findNotReleaseResources(id);
        List<OutputResource> outputResources = new ArrayList<OutputResource>();
        for(Resource resource  : resources){
            outputResources.add(createOutputResource(resource));
        }
        OutputFactory.factory(server.getOutputType()).out(server, outputResources);
    }
   
    /**
     * 创建输出资源对象
     * 
     * @param source 模板资源
     * @return
     */
    private OutputResource createOutputResource(TemplateSource source){
        TemplatesrcEntity entity = source.getSourceEntity();
        byte[] content = entity.getSrcEntity();
        OutputResource outputResource = new OutputResource(content,source.getPath());
        outputResource.registerEvent(new TemplateSourceOutputEvent(source.getId(), templateSourceService));
        return outputResource;
    }
    
    /**
     * 添加模版资源到输出资源中
     * 
     * @param outputResources 输出资源集合
     * @param source 模版资源
     */
    private void addTemplateSourceToOutputResources(List<OutputResource> outputResources,TemplateSource source){
        if(source.getSourceEntity() != null){
            outputResources.add(createOutputResource(source)); 
         }
    }
        
    private void publishSiteTemplateSource(Integer id,SiteServer server)throws PublishException{
        List<TemplateSource> sources = templateSourceService.findNotReleaseTemplateSources(id);
        List<OutputResource> outputResources = new ArrayList<OutputResource>();
        for(TemplateSource source  : sources){
            addTemplateSourceToOutputResources(outputResources,source);
        }
        OutputFactory.factory(server.getOutputType()).out(server, outputResources);
    }
     
    @Override
    public void publishSiteAgain(Integer id) throws PublishException {
        resourceService.updateNotRelease(id);
        templateSourceService.updateNotRelease(id);
        publishSite(id);
    }


    @Override
    public void publishAgain(Integer id, Boolean templateSource)throws PublishException {
        if(templateSource){
            List<OutputResource> outputResources =  new ArrayList<OutputResource>();
            TemplateSource source = templateSourceService.getTemplateSource(id);
            
            if(source == null){
                logger.debug("TemplateSource id = {} is not exist",id);
                throw new PublishException("TemplateSource is not exits");
            }
            
            addTemplateSourceChildrenToOutputResources(outputResources,source);
            Site site = source.getSite();
            SiteServer server = site.getSiteServer();
            OutputFactory.factory(server.getOutputType()).out(server, outputResources);
        }else{
            Resource resource = resourceService.getResource(id);
            if(resource == null){
                logger.debug("Resource id = {} is not exist",id);
                throw new PublishException("Resource is not exits");
            }
            Site site = resource.getSite();
            SiteServer server = site.getSiteServer();
            OutputFactory.factory(server.getOutputType()).out(server, Arrays.asList(createOutputResource(resource)));
        }
    }
    
    private void addTemplateSourceChildrenToOutputResources(List<OutputResource> outputResources,TemplateSource parent)throws PublishException{
        addTemplateSourceToOutputResources(outputResources,parent);
        List<TemplateSource> children = templateSourceService.getTemplateSourceChildren(parent.getId());
        
        if(children == null || children.isEmpty()){
            return ;
        }
        
        for(TemplateSource child : children){
            addTemplateSourceChildrenToOutputResources(outputResources,child);
        }
    }
}
