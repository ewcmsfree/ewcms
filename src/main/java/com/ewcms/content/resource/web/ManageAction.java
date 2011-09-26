/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 资源管理页面Action
 * 
 * @author wangwei
 */
@Controller("resource.manage.action")
public class ManageAction extends ActionSupport {

    private String type;
    private String context;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String execute(){
        context = ServletActionContext.getRequest().getContextPath();
        context = StringUtils.removeEnd(context, "/");
        
        return SUCCESS;
    }
    
    public String getContext(){
        return context;
    }
}
