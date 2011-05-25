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

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author wangwei
 */
public class ResourceAction extends ActionSupport {

    private boolean multi = true;
    private String context;

    public boolean getMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    public String getContext() {
        return this.context;
    }

    @Override
    public String execute() {
        context = ServletActionContext.getRequest().getContextPath();
        if (context.endsWith("/")) {
            context = context.substring(0, context.length() - 1);
        }
        return SUCCESS;
    }
}
