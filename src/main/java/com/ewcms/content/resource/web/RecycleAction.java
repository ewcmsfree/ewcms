/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.web.JsonBaseAction;
import com.opensymphony.xwork2.Action;

/**
 * 资源回收站管理页面Action
 * 
 * @author wangwei
 */
@Controller("resource.recycle.action")
public class RecycleAction extends JsonBaseAction{

    private String context;
    private int[] selections;

    @Autowired
    private ResourceFacable resourceFac;
    
    public String input(){
        context = ServletActionContext.getRequest().getContextPath();
        context = StringUtils.removeEnd(context, "/");
        
        return Action.SUCCESS;
    }
    
    public void revert(){
        resourceFac.revertResource(selections);
        renderSuccess();
    }
    
    public void delete(){
        resourceFac.deleteResource(selections);
        renderSuccess();
    }
    
    public void clear(){
        resourceFac.clearSoftDeleteResource();
        renderSuccess();
    }
    
    public String getContext(){
        return this.context;
    }
    
    public void setSelections(int[] selections){
        this.selections = selections;
    }
}
