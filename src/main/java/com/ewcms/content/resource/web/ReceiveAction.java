/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.content.resource.web;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.ResourceType;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author wangwei
 */
public abstract class ReceiveAction {
    private static final Logger logger = LoggerFactory.getLogger(ReceiveAction.class);
    @Autowired
    private ResourceFacable resourceFac;

    public void setResourceFac(ResourceFacable fac) {
        this.resourceFac = fac;
    }
    private File myUpload;
    private String myUploadFileName;

    public void setMyUpload(File upload) {
        this.myUpload = upload;
    }

    public void setMyUploadFileName(String uploadFileName) {
        this.myUploadFileName = uploadFileName;
    }

    protected abstract ResourceType resourceType();

    public void receive() {
        try {
            ResourceType type = resourceType();
            Resource resource =
                    resourceFac.addResource(myUpload, myUploadFileName, type);
            Struts2Util.renderJson(JSONUtil.toJSON(resource));
        } catch (IOException e) {
            //TODO 错误处理
            logger.error(e.toString());
        }
    }
}
