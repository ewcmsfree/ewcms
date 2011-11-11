/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.filter.render;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.service.ResourceServiceable;

/**
 * 返回指定的资源
 * 
 * @author wangwei
 */
public class ResourceRender extends AbstractResourceRender{

    private static final Logger logger = LoggerFactory.getLogger(ResourceRender.class);
    
    private ResourceServiceable resourceService;
    
    public ResourceRender(ResourceServiceable resourceService){
        this.resourceService = resourceService;
    }
    
 
    
    /**
     * 输出站点资源
     * 
     * @param response
     * @param uri   资源路基
     * @return
     * @throws IOException
     */
    @Override
    protected boolean output(HttpServletResponse response,String uri)throws IOException{
        Resource resource = resourceService.getResourceByUri(uri);
        if(resource == null){
            logger.debug("Resource is not exist,uri is {}",uri);
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
}
