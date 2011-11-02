/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.filter;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.ewcms.content.resource.service.ResourceServiceable;
import com.ewcms.core.site.service.TemplateSourceServiceable;
import com.ewcms.publication.preview.PreviewServiceable;
import com.ewcms.web.filter.render.PreviewRender;
import com.ewcms.web.filter.render.Renderable;
import com.ewcms.web.filter.render.ResourceRender;

/**
 *  当找不到资源时，该过滤器会重新在系统查询匹配资源，如有匹配资源返回该资源。
 *  
 * @author wangwei
 */
@Controller("error404Filter")
public class Error404Filter implements Filter, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(Error404Filter.class);
    
    @Autowired
    private ResourceServiceable resourceService;
    @Autowired
    private TemplateSourceServiceable templateSourceService;
    @Autowired
    private PreviewServiceable preview;
    
    private Renderable resourceRender;
    private Renderable previewRender;
    
    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(resourceService,"resourceService must setting");
        Assert.notNull(templateSourceService,"templateSourceService must setting");
        Assert.notNull(preview,"preview must setting");
        resourceRender = new ResourceRender(resourceService,templateSourceService);
        previewRender = new PreviewRender(preview);
    }
    
    private boolean isView(HttpServletRequest request){
        String value = request.getParameter("view");
        return value != null;
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse rep,FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;
        Error404ResponseWrapper responseWrapper = new Error404ResponseWrapper(this, response);
        
        chain.doFilter(request, responseWrapper);
        if(responseWrapper.isFound()){
            return ;
        }
        
        Renderable render ;
        if(isView(request)){
            logger.debug("now is preview render");
            render = previewRender;
        }else{
            logger.debug("now is resource render");
            render = resourceRender;
        }
        
        if(render.render(request, response)){
            return ;
        }
        
        String uri = request.getRequestURI();
        logger.warn("This is not path = {}",uri);
        response.sendError(HttpServletResponse.SC_NOT_FOUND,uri);
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
        
        public Error404ResponseWrapper(Error404Filter error404Filter, HttpServletResponse response){
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
