/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.filter.render;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.Site;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 返回资源
 * 
 * @author wangwei
 */
public abstract class AbstractResourceRender implements Renderable {

    private static final Logger logger = LoggerFactory
            .getLogger(AbstractResourceRender.class);

    protected String getPath(HttpServletRequest request) {

        String uri = request.getRequestURI();
        logger.debug("404's uri is {}", uri);
        String contextPath = request.getContextPath();
        logger.debug("ContextPath is {}", contextPath);

        return uri.replace(contextPath, "");
    }
    
    protected boolean skip(HttpServletRequest request,HttpServletResponse response){
        Site site = EwcmsContextUtil.getCurrentSite();
        return site == null;
    }
    
    /**
     * 输出指定的资源
     * 
     * @param response 
     * @param uri  资源地址
     * @return true 返回资源 
     * @throws IOException
     */
    protected abstract boolean output(HttpServletResponse response,String uri)throws IOException;
        
    @Override
    public boolean render(HttpServletRequest request,HttpServletResponse response)throws IOException {
        
        if(skip(request,response)){
            return false;
        }
        
        String uri = getPath(request);
        logger.debug("Resource path is {}",uri);
        
        return output(response,uri);
    }
}
