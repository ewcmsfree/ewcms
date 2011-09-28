/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.WebPublishable;
import com.ewcms.web.JsonBaseAction;
import com.opensymphony.xwork2.Action;

/**
 * 上传资源
 * 
 * @author wangwei
 */
@Controller("resource.resource.action")
public class ResourceAction extends JsonBaseAction {
    
    private static final Logger logger = LoggerFactory.getLogger(ResourceAction.class);
    
    private Integer id = Integer.MIN_VALUE;
    private boolean multi = true;
    private String context;
    private String fileDesc;
    private String fileExt;
    private File myUpload;
    private String myUploadFileName;
    private String type;

    private int[] selections;
    private Map<Integer,String> descriptions = new HashMap<Integer,String>();
    
    @Autowired
    private ResourceFacable resourceFac;
    
    @Autowired
    private WebPublishable webpublish;
    
    public String input(){
        context = ServletActionContext.getRequest().getContextPath();
        context = StringUtils.removeEnd(context, "/");
        Resource.Type resType = Resource.Type.valueOf(StringUtils.upperCase(type));
        fileDesc =resType.getFileDesc();
        fileExt = resType.getFileExt();
        return Action.SUCCESS;
    }
    
    private boolean isNewAdd(){
        return id == Integer.MIN_VALUE;
    }

    /**
     * 接收上传资源
     */
    public void receive() {
        try {
            logger.debug("Resource name is {} and type is {}",myUploadFileName,type);
            Resource.Type resType = Resource.Type.valueOf(StringUtils.upperCase(type));
            Resource resource;
            if(isNewAdd()){
                resource = resourceFac.uploadResource(myUpload, myUploadFileName, resType);            
            }else{
                resource = resourceFac.updateResource(id,myUpload, myUploadFileName, resType);            
            }
            renderSuccess(resource);
        } catch (IOException e) {
            logger.error("Upload resource is error:{}",e);
            renderError(e.toString());
        }
    }
    
    /**
     * 删除资源，只是标识资源为删除状态
     */
    public void delete() {
        resourceFac.softDeleteResource(selections);
        renderSuccess();
    }

    /**
     * 发布资源
     */
    public void publish() {
        
        List<Integer> errors = new ArrayList<Integer>();
        
        for(int id : selections){
            try{
                webpublish.publishResourceAgain(id, false);
            }catch(PublishException e){
                logger.error("resource publish fail {}",e);
                errors.add(id);
            }
        }
        
        if(errors.isEmpty()){
            renderSuccess();
        }else{
            String message =String.format("资源编号[%s]发布失败!", StringUtils.join(errors, ","));
            renderError(message);
        }
    }

    /**
     * 保存已经上传的资源
     */
    public void save() {
        List<Resource> resources = resourceFac.saveResource(descriptions);
        renderSuccess(resources);
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
    public void setSelections(int[] selects) {
        this.selections = selects;
    }
    
    public void setDescriptions(Map<Integer,String> infos) {
        this.descriptions = infos;
    }

    public Map<Integer,String> getDescriptions() {
        return descriptions;
    }
    
    public void setResourceFac(ResourceFacable fac) {
        resourceFac = fac;
    }
    
    public void setWebpublish(WebPublishable webpublish) {
        this.webpublish = webpublish;
    }
}
