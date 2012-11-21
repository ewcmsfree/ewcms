/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.visit.manager.VisitFacable;
import com.ewcms.plugin.visit.manager.vo.ClientVo;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

/**
 * 客户端情况Action
 * 
 * @author wu_zhijun
 *
 */
public class ClientAction extends VisitBaseAction {

	private static final long serialVersionUID = 8315345290542166128L;
	
	@Autowired
	private VisitFacable visitFac;
	
	private String fieldValue;
	private Boolean enabled;
	
	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/*========================== 操作系统 =================================*/
	public void osTable(){
		List<ClientVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "os", getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void osReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "os", getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 操作系统 时间趋势 =================================*/
	public String osTrend(){
		return SUCCESS;
	}
	
	public void osTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "os", getFieldValue(), getLabelCount(), getSiteId()));
	}
	
	/*========================== 浏览器 =================================*/
	public void browserTable(){
		List<ClientVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "browser", getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void browserReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "browser", getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 浏览器 时间趋势 =================================*/
	public String browserTrend(){
		return SUCCESS;
	}
	
	public void browserTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "browser", getFieldValue(), getLabelCount(), getSiteId()));
	}
	
	/*========================== 语言 =================================*/
	public void languageTable(){
		List<ClientVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "language", getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void languageReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "language", getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 语言 时间趋势 =================================*/
	public String languageTrend(){
		return SUCCESS;
	}
	
	public void languageTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "language", getFieldValue(), getLabelCount(), getSiteId()));
	}
	
	/*========================== 屏幕分析辨率 =================================*/
	public void screenTable(){
		List<ClientVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "screen",getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void screenReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "screen", getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 屏幕分析辨率 时间趋势 =================================*/
	public String screenTrend(){
		return SUCCESS;
	}
	
	public void screenTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "screen", getFieldValue(), getLabelCount(), getSiteId()));
	}
	
	/*========================== 屏幕色度 =================================*/
	public void colorDepthTable(){
		List<ClientVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "colorDepth", getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void colorDepthReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "colorDepth", getSiteId()), "encoding:UTF-8","no-cache:false");
	}

	/*========================== 屏幕色度 时间趋势 =================================*/
	public String colorDepthTrend(){
		return SUCCESS;
	}
	
	public void colorDepthTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "colorDepth", getFieldValue(), getLabelCount(), getSiteId()));
	}
	
	/*========================== 是否支持Applet =================================*/
	public void javaEnabledTable(){
		List<ClientVo> list = visitFac.findClientBooleanTable(getStartDate(), getEndDate(), "javaEnabled", getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void javaEnabledReport(){
		Struts2Util.renderHtml(visitFac.findClientBooleanReport(getStartDate(), getEndDate(),  "javaEnabled", getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 是否支持Applet 时间趋势 =================================*/
	public String javaEnabledTrend(){
		return SUCCESS;
	}
	
	public void javaEnabledTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendBooleanReport(getStartDate(), getEndDate(), "javaEnabled", getEnabled(), getLabelCount(), getSiteId()));
	}
	
	/*========================== Flash版本 =================================*/
	public void flashVersionReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "flashVersion", getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	public void flashVersionTable(){
		List<ClientVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "flashVersion", getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	/*========================== Flash版本 时间趋势 =================================*/
	public String flashVersionTrend(){
		return SUCCESS;
	}
	
	public void flashVersionTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "flashVersion", getFieldValue(), getLabelCount(), getSiteId()));
	}
	
	/*========================== 是否允许Cookies =================================*/
	public void cookieEnabledTable(){
		List<ClientVo> list = visitFac.findClientBooleanTable(getStartDate(), getEndDate(), "cookieEnabled", getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void cookieEnabledReport(){
		Struts2Util.renderHtml(visitFac.findClientBooleanReport(getStartDate(), getEndDate(),  "cookieEnabled", getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 是否允许Cookies 时间趋势 =================================*/
	public String cookieEnabledTrend(){
		return SUCCESS;
	}
	
	public void cookieEnabledTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendBooleanReport(getStartDate(), getEndDate(), "cookieEnabled", getEnabled(), getLabelCount(), getSiteId()));
	}
}
