/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.filter.render;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.core.site.model.TemplatesrcEntity;
import com.ewcms.core.site.service.TemplateSourceServiceable;

/**
 * 返回指定的模版资源
 * 
 * @author wangwei
 */
public class TemplateSourceRender  extends AbstractResourceRender{
    
 private static final Logger logger = LoggerFactory.getLogger(TemplateSourceRender.class);
    
    private TemplateSourceServiceable templateSourceService;
    
    public TemplateSourceRender(TemplateSourceServiceable templateSourceService){
        this.templateSourceService = templateSourceService;
    }
    
   
    
    /**
     * 输出模版资源
     * 
     * @param response
     * @param uri   资源路基
     * @return
     * @throws IOException
     */
    protected boolean output(HttpServletResponse response,String uri)throws IOException{
        TemplateSource source = templateSourceService.getTemplateSourceByUniquePath(uri);
        if(source == null){
            logger.debug("TemplateSource is not exist,uri is {}",uri);
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
}
