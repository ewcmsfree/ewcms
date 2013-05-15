/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.interaction.web;

import com.ewcms.plugin.interaction.InteractionFacable;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author wangwei
 */
@Controller
public class SpeakAction {

    private int[] selections;
    private boolean esc = false;

    @Autowired
    private InteractionFacable interactionFac;

    public int[] getSelections() {
        return selections;
    }

    public void setSelections(int[] selections) {
        this.selections = selections;
    }

    public void setEsc(boolean esc) {
        this.esc = esc;
    }

    public void checked(){
        for(int id : selections){
            if(esc){
            	interactionFac.speakChecked(id,false);
            }else{
            	interactionFac.speakChecked(id,true);
            }
        }
        Struts2Util.renderJson("{\"message\":\"success\"}");
    }
    
    public void delete(){
    	try{
    		interactionFac.deleteSpeak(getSelections());
    		Struts2Util.renderJson(JSONUtil.toJSON("true"));
    	}catch(Exception e){
    		Struts2Util.renderJson(JSONUtil.toJSON("false"));
    	}
    }
}
