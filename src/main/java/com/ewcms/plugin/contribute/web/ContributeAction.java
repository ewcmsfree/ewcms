/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.contribute.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ewcms.plugin.contribute.ContributeFacable;
import com.ewcms.plugin.contribute.model.Contribute;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author 吴智俊
 */
@Controller
public class ContributeAction extends ActionSupport {

	private static final long serialVersionUID = 3103054905250581309L;

	private Contribute contribute;
	private Long id;
	private Boolean checked;
	private String content;
	private Date date;
	private Boolean update = false;
	private List<Long> selections = new ArrayList<Long>();

	@Autowired
	private ContributeFacable contributeFac;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Contribute getContribute() {
		return contribute;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public List<Long> getSelections() {
		return selections;
	}

	public void setSelections(List<Long> selections) {
		this.selections = selections;
	}

	@Override
	public String execute() {
		if (update) {
			if (checked != null) {
				contributeFac.contributeChecked(id, checked);
			}
			this.addActionMessage("保存成功");
		}
		contribute = contributeFac.getContribute(id);
		content = contribute.getContent();
		checked = contribute.getChecked();
		date = contribute.getDate();
		return SUCCESS;
	}

	public void delete() {
		try {
			contributeFac.deleteContribute(getSelections());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
