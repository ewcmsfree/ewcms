/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.content.resource.web.annex;

import com.ewcms.web.util.Struts2Util;
import com.ewcms.content.resource.ResourceFacable;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author wangwei
 */
@Controller
public class AnnexManageAction {

    private static final String RELEASE_EVENT = "rel";
    private static final String DELETE_EVENT = "del";
    
    private int[] selects;
    private String eventOP;
    
    @Autowired
    private ResourceFacable resourceFac;

    public void setEventOP(String eventOP) {
        this.eventOP = eventOP;
    }

    public void setResourceFac(ResourceFacable fac){
        this.resourceFac = fac;
    }

    public void setSelects(int[] selects) {
        this.selects = selects;
    }

    public void execute(){
        
        String json = "{\"info\":\"保存成功\"}";
        if(isDeleteEvent(eventOP)){
            delete(selects);
        }else if(isReleaseEvent(eventOP)){
            release();
        }else{
            json = "{\"info\":\"没有该类型操作\"}";
        }
        Struts2Util.renderJson(json);
    }

    private boolean isDeleteEvent(String event){
        return DELETE_EVENT.equals(event);
    }

    private boolean isReleaseEvent(String event){
        return RELEASE_EVENT.equals(event);
    }

    private void delete(int[] ids){
        for(int id : ids){
            resourceFac.delResource(id);
        }
    }

    private void release(){
        
    }

}
