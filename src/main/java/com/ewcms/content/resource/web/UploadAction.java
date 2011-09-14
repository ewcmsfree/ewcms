/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.web;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.web.JsonBaseAction;
import com.opensymphony.xwork2.Action;

/**
 * 上传资源
 * 
 * @author wangwei
 */
@Controller("resource.upload.action")
public class UploadAction extends JsonBaseAction {
    
    private static final Logger logger = LoggerFactory.getLogger(UploadAction.class);
    
    private boolean multi = true;
    private String context;
    private String fileDesc;
    private String fileExt;
    
    private File myUpload;
    private String myUploadFileName;
    private String type;

    @Autowired
    private ResourceFacable resourceFac;
    
    public String input(){
        context = ServletActionContext.getRequest().getContextPath();
        StringUtils.removeEnd(context, "/");
        Resource.Type resType = Resource.Type.valueOf(StringUtils.upperCase(type));
        fileDesc =resType.getFileDesc();
        fileExt = resType.getFileExt();
        return Action.SUCCESS;
    }
    
    public void receive() {
        
        logger.debug("Resource name is {} and type is {}",myUploadFileName,type);
        
        try {
            Resource.Type resType = Resource.Type.valueOf(StringUtils.upperCase(type));
            Resource resource = resourceFac.uploadResource(myUpload, myUploadFileName, resType);            
            renderSuccess(resource);
        } catch (IOException e) {
            logger.error("Upload resource is error:{}",e);
            renderError(e.toString());
        }
    }
    
    public boolean getMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    public String getContext() {
        return this.context;
    }
    
    public String getFileDesc() {
        return fileDesc;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setMyUpload(File upload) {
        this.myUpload = upload;
    }

    public void setMyUploadFileName(String uploadFileName) {
        this.myUploadFileName = uploadFileName;
    }

    public void setType(String type){
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public void setResourceFac(ResourceFacable fac) {
        resourceFac = fac;
    }
}
