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

import java.util.Date;

import com.ewcms.plugin.interaction.InteractionFacable;
import com.ewcms.plugin.interaction.model.Interaction;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author wangwei
 */
@Controller
public class InteractionAction extends ActionSupport {

	private static final long serialVersionUID = 1207449565812176793L;

	private String replay;
	private Interaction interaction;
	private Integer id;
	private Integer organId;
	private String organName;
	private Boolean checked;
	private Boolean update = false;
	private Date date;
	private Date replayDate;
	
	@Autowired
	private InteractionFacable interactionFac;

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

	public Integer getOrganId() {
		return organId;
	}

	public void setOrganId(Integer organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
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

	@Override
	public String execute() {
		if (update) {
			interactionFac.interactionOrgan(id, organId, organName);
			interactionFac.interactionReplay(id, replay, date, replayDate);
			if (checked != null) {
				interactionFac.interactionChecked(id, checked);
			}
			interactionFac.interactionBackRatio(organId);
			this.addActionMessage("保存成功");
		}
		interaction = interactionFac.getInteraction(id);
		organId = interaction.getOrganId();
		organName = interaction.getOrganName();
		replay = interaction.getReplay();
		checked = interaction.isChecked();
		date = interaction.getDate();
		replayDate = interaction.getReplayDate();
		return SUCCESS;
	}
}
