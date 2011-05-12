/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.web.action.plugin.interaction;

import com.ewcms.plugin.interaction.InteractionFacable;
import com.ewcms.util.Struts2Util;
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
    private InteractionFacable fac;

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
                fac.speakChecked(id,false);
            }else{
                fac.speakChecked(id,true);
            }
        }
        Struts2Util.renderJson("{\"message\":\"success\"}");
    }
}
