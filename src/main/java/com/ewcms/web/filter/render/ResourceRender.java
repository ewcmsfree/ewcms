/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.filter.render;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.service.ResourceServiceable;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.core.site.model.TemplatesrcEntity;
import com.ewcms.core.site.service.TemplateSourceServiceable;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 返回指定的资源
 * 
 * @author wangwei
 */
public class ResourceRender implements Renderable {

    private static final Logger logger = LoggerFactory.getLogger(ResourceRender.class);
    
    private ResourceServiceable resourceService;
    private TemplateSourceServiceable templateSourceService;
    
    public ResourceRender(ResourceServiceable resourceService,TemplateSourceServiceable templateSourceService){
        this.resourceService = resourceService;
        this.templateSourceService = templateSourceService;
    }
    
    private String getPath(HttpServletRequest request){
        
        String uri = request.getRequestURI();
        logger.debug("404's uri is {}",uri);
        String contextPath = request.getContextPath();
        logger.debug("ContextPath is {}",contextPath);
        
        return uri.replace(contextPath, "");
    }
    
    /**
     * 输出站点资源
     * 
     * @param response
     * @param uri   资源路基
     * @return
     * @throws IOException
     */
    private boolean outputResource(HttpServletResponse response,String uri)throws IOException{
        Resource resource = resourceService.getResourceByUri(uri);
        if(resource == null){
            return false;
        }
        String realPath = resource.getPath();
        if(StringUtils.endsWith(resource.getThumbUri(), uri)){
            realPath = resource.getThumbPath();
        }
        
        IOUtils.copy(new FileInputStream(realPath), response.getOutputStream());
        response.flushBuffer();
        
        return true;
    }
    
    /**
     * 输出模版资源
     * 
     * @param response
     * @param uri   资源路基
     * @return
     * @throws IOException
     */
    private boolean outputTemplateSource(HttpServletResponse response,String uri)throws IOException{
        TemplateSource source = templateSourceService.getTemplateSourceByUniquePath(uri);
        if(source == null){
            return false;
        }
        TemplatesrcEntity entity = source.getSourceEntity();
       if(entity == null || entity.getSrcEntity() == null){
           return false;
       }
       
       IOUtils.write(entity.getSrcEntity(), response.getOutputStream());
       response.flushBuffer();
       
       return true;
    }
    
    private boolean skip(HttpServletRequest request,HttpServletResponse response){
        Site site = EwcmsContextUtil.getCurrentSite();
        return site == null;
    }
    
    @Override
    public boolean render(HttpServletRequest request,HttpServletResponse response)throws IOException {
        
        if(skip(request,response)){
            return false;
        }
        
        String uri = getPath(request);
        logger.debug("Resource path is {}",uri);
        
        return outputResource(response,uri)||outputTemplateSource(response,uri);
        
    }

}
