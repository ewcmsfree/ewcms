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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.service.ChannelServiceable;
import com.ewcms.core.site.service.TemplateServiceable;

@Controller("previewFilter")
public class PreviewFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(PreviewFilter.class);
    
    private static final String PREVIEW_PATH = "/template/preview";
    private static final String CHANNEL_ID = "channelId";
    private static final String TEMPLATE_ID = "templateId";
    
    @Autowired
    private ChannelServiceable channelService;
    
    @Autowired
    private TemplateServiceable templateService;
    
    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
    
    private Integer getChannelId(HttpServletRequest request){
        String value = request.getParameter(CHANNEL_ID);
        logger.debug("ChannelId is {}",value);
        return value == null ? null : Integer.valueOf(value);
    }
    
    private Integer getTemplateId(HttpServletRequest request){
        String value = request.getParameter(TEMPLATE_ID);
        logger.debug("TemplateId is {}",value);
        return value == null ? null : Integer.valueOf(value);
    }
    
    private String getUri(Integer channelId,Integer templateId){
        Channel channel = channelService.getChannel(channelId);
        Template template = templateService.getTemplate(templateId);
        
        if(channel == null || template == null){
            return null;
        }
        
        return channel.getAbsUrl() + "/" + template.getName()+"?view=true";
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse rep,FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        if(!StringUtils.startsWith(uri, PREVIEW_PATH)){
            logger.debug("Uri is not preview address");
            chain.doFilter(req, rep);
            return ;
        }
        
        HttpServletResponse response = (HttpServletResponse) rep;
        try{
            Integer channelId = getChannelId(request);
            Integer templateId = getTemplateId(request);
            if(channelId == null || templateId == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return ;
            }

            String redirectUri = getUri(channelId,templateId);
            if(redirectUri == null){
                logger.debug("Redirect's address is null");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }else{
                logger.debug("Redirect's address is {}",redirectUri);
                response.sendRedirect(redirectUri);
            }
        }catch(Exception e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.toString());
        }
    }
}
