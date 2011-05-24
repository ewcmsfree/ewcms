/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.content.resource.web;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.ResourceType;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author wangwei
 */
public abstract class ReceiveAction {
    private static final Log log = LogFactory.getLog(ReceiveAction.class);
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
            log.error(e.toString());
        }
    }
}
