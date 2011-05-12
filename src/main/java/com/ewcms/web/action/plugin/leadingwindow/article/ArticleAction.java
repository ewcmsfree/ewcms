/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.plugin.leadingwindow.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.plugin.leadingwindow.LeadingWindowFacable;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author 吴智俊
 */
@Controller("plugin.leadingwindow.article.index")
public class ArticleAction extends ActionSupport {

	private static final long serialVersionUID = 7275967705688396524L;

	@Autowired
	private LeadingWindowFacable leadingWindowFac;


	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	private Integer leaderChannelId;
	private Integer channelId;
	private Integer[] selArticleRmcIds;
	
	public Integer getLeaderChannelId() {
		return leaderChannelId;
	}

	public void setLeaderChannelId(Integer leaderChannelId) {
		this.leaderChannelId = leaderChannelId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer[] getSelArticleRmcIds() {
		return selArticleRmcIds;
	}

	public void setSelArticleRmcIds(Integer[] selArticleRmcIds) {
		this.selArticleRmcIds = selArticleRmcIds;
	}

	public void saveQuote(){
		try{
			leadingWindowFac.addLeaderChannelArticleRmc(getSelArticleRmcIds(), getLeaderChannelId(), getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void delQuote(){
		try{
			leadingWindowFac.delLeaderChannelArticleRmc(getSelArticleRmcIds(), getLeaderChannelId(), getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
