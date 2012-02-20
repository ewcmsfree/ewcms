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
 * 上传或指定资源插入内容编辑器Action
 * 
 * @author wangwei
 */
@Controller("resource.insert.action")
public class InsertAction extends ActionSupport {

	private static final long serialVersionUID = -669504897835815241L;
	
	private String type;
    private boolean multi = true;
    private String context;
    
    @Override
    public String execute(){
        context = ServletActionContext.getRequest().getContextPath();
        context = StringUtils.removeEnd(context, "/");
        
        return SUCCESS;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public boolean isMulti() {
        return multi;
    }
    public void setMulti(boolean multi) {
        this.multi = multi;
    }
    
    public String getContext() {
        return context;
    }
}
