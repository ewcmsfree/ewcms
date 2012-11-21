/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.visit.manager.VisitFacable;
import com.ewcms.plugin.visit.manager.vo.TrafficVo;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

/**
 * 访问量排行Action
 * 
 * @author wu_zhijun
 *
 */
public class TrafficAction extends VisitBaseAction {

	private static final long serialVersionUID = -4250722388466542110L;

	@Autowired
	private VisitFacable visitFac;
	
	private String url;
	private Integer channelParentId;
	private Integer channelId;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getChannelParentId() {
		return channelParentId;
	}

	public void setChannelParentId(Integer channelParentId) {
		this.channelParentId = channelParentId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
	/*========================== 栏目点击排行 =================================*/
	public void channelTable(){
		List<TrafficVo> list = visitFac.findChannelTable(getStartDate(), getEndDate(), getChannelParentId(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void channelReport(){
		Struts2Util.renderHtml(visitFac.findChannelReport(getStartDate(), getEndDate(),getChannelParentId(), getLabelCount(), getSiteId()));
	}
	
	/*========================== 栏目点击排行 时间趋势 =================================*/
	public String channelTrend(){
		return SUCCESS;
	}
	
	public void channelTrendReport(){
		Struts2Util.renderHtml(visitFac.findChannelTrendReport(getStartDate(), getEndDate(), getChannelId(), getLabelCount(), getSiteId()));
	}

	
	/*========================== 文章点击排行 =================================*/
	public void articleTable(){
		List<TrafficVo> list = new ArrayList<TrafficVo>();
		if (getChannelId() == null || getChannelId() == 0){
			list = visitFac.findArticleTable(0, getSiteId());
		}else{
			list = visitFac.findArticleTable(getChannelId(), getSiteId());
		}
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	/*========================== URL点击排行 =================================*/
	public void urlTable(){
		List<TrafficVo> list = visitFac.findUrlTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	/*========================== URL点击排行 时间趋势=================================*/
	public String urlTrend(){
		return SUCCESS;
	}
	
	public void urlTrendReport(){
		Struts2Util.renderHtml(visitFac.findUrlTrendReport(getStartDate(), getEndDate(), getUrl(), getLabelCount(), getSiteId()));
	}
}
