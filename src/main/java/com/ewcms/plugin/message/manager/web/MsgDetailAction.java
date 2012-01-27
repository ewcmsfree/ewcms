/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.manager.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.message.manager.MessageFacable;
import com.ewcms.plugin.message.model.MsgContent;
import com.ewcms.plugin.message.model.MsgReceive;
import com.ewcms.plugin.message.model.MsgSend;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.opensymphony.xwork2.ActionSupport;

public class MsgDetailAction extends ActionSupport {
	
	private static final long serialVersionUID = 7582607158887547434L;
	
	@Autowired
	private MessageFacable messageFac;
	
	private String type;
	
	private Long id;
	
	private String title;
	
	private String detail;
	
	private List<String> results;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}
	
	@Override
	public String execute() throws Exception {
		if (type.equals("notice")){
			MsgSend msgSend = messageFac.findMsgSend(getId());
			setTitle(msgSend.getTitle());
			setDetail(msgSend.getMsgContents().get(0).getDetail());
			setResults(null);
		}else if (type.equals("subscription")){
			MsgSend msgSend = messageFac.findMsgSend(getId());
			setTitle(msgSend.getTitle());
			List<MsgContent> msgContents = msgSend.getMsgContents();
			List<String> details = new ArrayList<String>();
			for (MsgContent msgContent : msgContents){
				details.add(msgContent.getDetail());
			}
			setResults(details);
		}else if (type.equals("message")){
			MsgReceive msgReceive = messageFac.findMsgReceive(getId());
			setTitle(msgReceive.getMsgContent().getTitle());
			setDetail(msgReceive.getMsgContent().getDetail());
			setResults(null);
			messageFac.readMsgReceive(getId());
		}
		return SUCCESS;
	}
	
	public void subscribe(){
		Struts2Util.renderJson(JSONUtil.toJSON(messageFac.subscribeMsg(getId())));
	}
}
