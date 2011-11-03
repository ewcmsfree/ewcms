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

import com.ewcms.publication.PublishException;
import com.ewcms.publication.preview.PreviewServiceable;

/**
 * 返回指定预览页面
 * 
 * @author wangwei
 */
public class PreviewRender implements Renderable{

    private static final Logger logger = LoggerFactory.getLogger(PreviewRender.class);
    
    private static final String CHANNEL_ID_PARAM = "channelId";
    private static final String TEMPLATE_ID_PARAM = "templateId";
    private static final String ARTICLE_ID_PARAM = "articleId";
    private static final String PAGE_NUMBER_PARAM = "page";
    private static final String MOCK_PARAM = "mock";
    
    private PreviewServiceable preview;
    
    public PreviewRender(PreviewServiceable preview){
        this.preview = preview;
    }
    
    private Integer getChannelId(HttpServletRequest request){
        String value = request.getParameter(CHANNEL_ID_PARAM);
        logger.debug("ChannelId is {}",value);
        return value == null ? null : Integer.valueOf(value);
    }
    
    private Integer getTemplateId(HttpServletRequest request){
        String value = request.getParameter(TEMPLATE_ID_PARAM);
        logger.debug("TemplateId is {}",value);
        return value == null ? null : Integer.valueOf(value);
    }
    
    private Long getArticleId(HttpServletRequest request){
        String value = request.getParameter(ARTICLE_ID_PARAM);
        logger.debug("ArticleId is {}",value);
        return value == null ? null : Long.valueOf(value);
    }
    
    private Integer getPageNumber(HttpServletRequest request){
        String value = request.getParameter(PAGE_NUMBER_PARAM);
        logger.debug("Page number is {}",value);
        return value == null ? new Integer(0) : Integer.valueOf(value);
    }
    
    private boolean isMock(HttpServletRequest request){
        String value = request.getParameter(MOCK_PARAM);
        logger.debug("TemplateId is {}",value);
        if(value == null){
            return true;
        }else{
            return Boolean.valueOf(value);
        }
    }
    
    @Override
    public boolean render(HttpServletRequest request,HttpServletResponse response) throws IOException {
       
        Integer templateId = getTemplateId(request);
        try{
            if(templateId != null){
                Boolean mock = isMock(request);
                preview.viewTemplate(response.getOutputStream(), templateId, mock);
            }else{
                Integer channelId = getChannelId(request);
                Long articleId = getArticleId(request);
                Integer pageNumber = getPageNumber(request);
                preview.viewArticle(response.getOutputStream(), channelId, articleId, pageNumber);
            }
            return true;
        }catch(PublishException e){
            logger.error("Preview is error:{}",e.toString());
            return false;
        }
    }
}
