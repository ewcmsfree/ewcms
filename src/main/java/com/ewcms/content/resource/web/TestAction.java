/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.content.resource.web;

import com.ewcms.context.EwcmsContextHolder;
import com.ewcms.core.site.model.Site;
import com.opensymphony.xwork2.ActionSupport;

/**
 *测试资源文件
 *
 * @author wangwei
 */
public class TestAction extends ActionSupport {

    @Override
    public String execute(){
        Site site = new Site();
        site.setId(0);
        site.setResourceDir("/home/wangwei/html");
        EwcmsContextHolder.getContext().setSite(site);

        return SUCCESS;
    }
}
