/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.web;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.web.JsonBaseAction;

/**
 * 上传引导图
 * 
 * @author wangwei
 */
@Controller("resource.thumbupload.action")
public class ThumbUploadAction extends JsonBaseAction{
    
    private static final Logger logger = LoggerFactory.getLogger(ThumbUploadAction.class);
    
    private Integer id;
    private File myUpload;
    private String myUploadFileName;

    @Autowired
    private ResourceFacable resourceFac;
    
    public void receive() {
        
        logger.debug("Thumb name is {}",myUploadFileName);
        
        try {
            Resource resource = resourceFac.updateThumbResource(id, myUpload, myUploadFileName);
            renderSuccess(resource);
        } catch (IOException e) {
            logger.error("Upload thumb is error:{}",e);
            renderError(e.toString());
        }
    }
  
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setMyUpload(File myUpload) {
        this.myUpload = myUpload;
    }
    
    public void setMyUploadFileName(String myUploadFileName) {
        this.myUploadFileName = myUploadFileName;
    }
    
    public void setResourceFac(ResourceFacable fac) {
        resourceFac = fac;
    }
}
