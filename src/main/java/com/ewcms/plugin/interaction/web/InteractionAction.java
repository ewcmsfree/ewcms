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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ewcms.plugin.interaction.InteractionFacable;
import com.ewcms.plugin.interaction.model.Interaction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
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
	private String content;
	private String title;
	private Integer type;
	private List<Integer> selections = new ArrayList<Integer>();
	
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Integer> getSelections() {
		return selections;
	}

	public void setSelections(List<Integer> selections) {
		this.selections = selections;
	}

	@Override
	public String execute() {
		if (update) {
			interactionFac.interactionOrgan(id, organId, organName);
			interactionFac.interactionReplay(id, replay, date, replayDate, content, title, type);
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
		content = interaction.getContent();
		title = interaction.getTitle();
		type = interaction.getType();
		return SUCCESS;
	}
	
	public void delete(){
		try{
			interactionFac.deleteInteraction(getSelections());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
