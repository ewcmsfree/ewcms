/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.filter;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.service.ResourceServiceable;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.core.site.model.TemplatesrcEntity;
import com.ewcms.core.site.service.TemplateSourceServiceable;

/**
 *  通过该过滤器，读取EWCMS系统中管理的资源。
 *  
 * @author wangwei
 */
@Controller("error404Filter")
public class Error404Filter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(Error404Filter.class);
    @Autowired
    private ResourceServiceable resourceService;
    
    @Autowired
    private TemplateSourceServiceable templateSourceService;
    
    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
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
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse rep,FilterChain chain) throws IOException, ServletException {
       
        HttpServletResponse response = (HttpServletResponse) rep;
        HttpServletRequest request = (HttpServletRequest) req;
        Error404ResponseWrapper responseWrapper = new Error404ResponseWrapper(response);
        
        chain.doFilter(request, responseWrapper);
        if(responseWrapper.isFound()){
            return ;
        }
        
        String uri = getPath(request);
        logger.debug("Resource path is {}",uri);
        
        if(outputResource(response,uri) 
                ||outputTemplateSource(response,uri)){
            
            return ;
        }
        
        
        logger.warn("This is not path = {}",uri);
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
    
    public ResourceServiceable getResourceService() {
        return resourceService;
    }

    public void setResourceService(ResourceServiceable resourceService) {
        this.resourceService = resourceService;
    }

    public TemplateSourceServiceable getTemplateSourceService() {
        return templateSourceService;
    }

    public void setTemplateSourceService(
            TemplateSourceServiceable templateSourceService) {
        this.templateSourceService = templateSourceService;
    }

    private class Error404ResponseWrapper extends HttpServletResponseWrapper {
        
        private int status = SC_OK;
        
        public Error404ResponseWrapper(HttpServletResponse response){
            super(response);
        }
        
        @Override
        public void sendError(int sc) throws IOException {
            this.status = sc;
            if(isFound()){
                super.sendError(sc);                
            }else{
                super.setStatus(SC_OK);
            }
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            this.status = sc;
            if(isFound()){
                super.sendError(sc,msg);  
            }else{
                super.setStatus(SC_OK);                 
            }
        }
        
        public void setStatus(int status){
           this.status = status;
           super.setStatus(status);
        }
        
        @Override
        public void reset() {
            this.status = SC_OK;
            super.reset();
        }

        
        public boolean isFound(){
            return status != SC_NOT_FOUND;
        }
    }
}
