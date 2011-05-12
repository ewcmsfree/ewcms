/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.web.action.resource;

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
