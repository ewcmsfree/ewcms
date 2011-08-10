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
import com.ewcms.publication.PublishException;
import com.ewcms.publication.WebPublishable;
import com.ewcms.web.util.Struts2Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author wangwei
 */
@Controller
public class ResourceManageAction {

    private int[] selections;

    @Autowired
    private WebPublishable webpublish;
    
    @Autowired
    private ResourceFacable resourceFac;

    public void setResourceFac(ResourceFacable fac) {
        this.resourceFac = fac;
    }

    public void setSelections(int[] selects) {
        this.selections = selects;
    }

    public void delete() {
        for (int id : selections) {
            resourceFac.delResource(id);
        }
        Struts2Util.renderJson("{\"info\":\"删除成功\"}");
    }

    public void release() {
        for(int id : selections){
            try{
            	webpublish.publishResourceAgain(id, true);
            }catch(PublishException e){
               //TODO 错误处理
            }
        }
        Struts2Util.renderJson("{\"info\":\"重新发布成功\"}");
    }
}
