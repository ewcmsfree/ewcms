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

import java.util.Date;

import com.ewcms.plugin.interaction.InteractionFacable;
import com.ewcms.plugin.interaction.model.Interaction;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangwei
 */
public class AdvisorAction extends ActionSupport {

	private static final long serialVersionUID = 62738217265754197L;

	private String replay;
    private Interaction interaction;
    private Integer id;
    private Boolean checked;
    private Date date;
    private Date replayDate;
    private String content;
    private String title;
    
    @Autowired
    private InteractionFacable fac;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getReplay() {
        return replay;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }
    
    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getReplayDate() {
		return replayDate;
	}

	public void setReplayDate(Date replayDate) {
		this.replayDate = replayDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
    public String execute(){
        if(replay != null && checked != null){
            if(replay != null){
                fac.interactionReplay(id, replay, date, replayDate, content, title, 0);
            }
            if(checked != null){
                fac.interactionChecked(id,checked);
            }
        }
        interaction = fac.getInteraction(id);
        replay = interaction.getReplay();
        checked = interaction.isChecked();
        date = interaction.getDate();
        replayDate = interaction.getReplayDate();
        content = interaction.getContent();
        title = interaction.getTitle();
        return SUCCESS;
    }
}
