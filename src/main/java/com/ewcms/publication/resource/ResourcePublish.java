/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.SiteServer;
import com.ewcms.core.site.model.TemplateSource;
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
@Service
public class ResourcePublish implements ResourcePublishable{
    
    private static final Logger logger = LoggerFactory.getLogger(ResourcePublish.class);
    
    @Autowired
    private SitePublishServiceable siteService;
    @Autowired
    private ResourcePublishServiceable resourceService;
    @Autowired
    private TemplateSourcePublishServiceable templateSourceService;
    
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

    private void publishSiteLocalResource(Integer id,SiteServer server)throws PublishException{
        List<Resource> resources = resourceService.findPublishResource(id);
        List<OutputResource> outputResources = new ArrayList<OutputResource>();
        for(Resource resource  : resources){
            outputResources.add(createOutputResource(resource));
        }
        OutputFactory.factory(server.getOutputType()).out(server, outputResources);
    }
    
    /**
     * 创建输出资源对象
     * 
     * @param resource 资源对象 
     * @return
     */
    private OutputResource createOutputResource(Resource resource){
        OutputResource outputResource ;
        if(StringUtils.isBlank(resource.getPathZip()) || StringUtils.isBlank(resource.getReleasePathZip())){
            outputResource = new OutputResource();
            outputResource.addChild(new OutputResource(resource.getPath(),resource.getReleasePath()));
            outputResource.addChild(new OutputResource(resource.getPath(),resource.getReleasePath()));
            
        }else{
            outputResource = new OutputResource(resource.getPath(),resource.getReleasePath());
        }
        outputResource.registerEvent(new ResourceOutputEvent(resource.getId(), resourceService));
        return outputResource;
    }
    
   
    
    private void publishSiteTemplateSource(Integer id,SiteServer server)throws PublishException{
        List<TemplateSource> sources = templateSourceService.findReleaseTemplateSources(id);
        List<OutputResource> outputResources = new ArrayList<OutputResource>();
        for(TemplateSource source  : sources){
            outputResources.add(createOutputResource(source));
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
        byte[] entity = source.getSourceEntity().getSrcEntity();
        OutputResource outputResource = new OutputResource(entity,source.getUniquePath());
        outputResource.registerEvent(new TemplateSourceOutputEvent(source.getId(), templateSourceService));
        return outputResource;
    }
    
    @Override
    public void publishSiteAgain(Integer id) throws PublishException {
        resourceService.againPublishResource(id);
        templateSourceService.againPublishTemplateSource(id);
        publishSite(id);
    }


    @Override
    public void publishAgain(Integer id, Boolean templateSource)throws PublishException {
        if(templateSource){
            List<OutputResource> outputs =  new ArrayList<OutputResource>();
            TemplateSource source = templateSourceService.getTemplateSource(id);
            if(source == null){
                logger.debug("TemplateSource id = {} is not exist",id);
                throw new PublishException("TemplateSource is not exits");
            }
            outputs.add(createOutputResource(source));
            createOutputsByTemplateSourceChildren(outputs,id);
        }else{
            Resource resource = resourceService.getResource(id);
            if(resource == null){
                logger.debug("Resource id = {} is not exist",id);
                throw new PublishException("Resource is not exits");
            }
            Site site = siteService.getSite(resource.getSiteId());
            SiteServer server = site.getSiteServer();
            OutputFactory.factory(server.getOutputType()).out(server, Arrays.asList(createOutputResource(resource)));
        }
    }
    
    private void createOutputsByTemplateSourceChildren(List<OutputResource> outputResources,Integer id)throws PublishException{
        List<TemplateSource> children = templateSourceService.getTemplateSourceChildren(id);
        if(children == null || children.isEmpty()){
            return ;
        }
        for(TemplateSource child : children){
            outputResources.add(createOutputResource(child));
            createOutputsByTemplateSourceChildren(outputResources,child.getId());
        }
    }
}
