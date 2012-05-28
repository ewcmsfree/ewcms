/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.online.web;

import com.ewcms.plugin.online.OnlineFacable;
import com.ewcms.plugin.online.model.Advisor;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangwei
 */
public class AdvisorEditAction extends ActionSupport {

	private static final long serialVersionUID = 4309218974087800394L;

	private String replay;
    private Advisor advisor;
    private Integer id;
    private Boolean checked;
    private Boolean success = false;
    @Autowired
    private OnlineFacable fac;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public Boolean getSuccess() {
        return success;
    }

    @Override
    public String execute() {
        if(checked != null){
            fac.releaseAdvisor(id, checked);
            success = true;
        }
        if (replay != null && replay.trim().length() > 0) {
            fac.advisorReplay(id, replay);
            advisor = new Advisor();
            replay = "";
            id = null;
            success = true;
        }
        advisor = fac.getAdvisor(id);
        replay = advisor.getReplay();
        checked = advisor.isChecked();

        return SUCCESS;
    }
}
