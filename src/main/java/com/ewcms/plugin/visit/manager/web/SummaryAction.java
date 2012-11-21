/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.visit.manager.VisitFacable;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

/**
 * 总体情况Action
 * 
 * @author wu_zhijun
 *
 */
public class SummaryAction extends VisitBaseAction {

	private static final long serialVersionUID = -5090943771911901099L;
	
	private static DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	
	@Autowired
	private VisitFacable visitFac;
	
	private String firstAddDate;
	private Integer visitDay;
	private String url;
	private String host;
	private String country;
	private String province;
	private String city;

	public String getFirstAddDate() {
		return firstAddDate;
	}

	public void setFirstAddDate(String firstAddDate) {
		this.firstAddDate = firstAddDate;
	}

	public Integer getVisitDay() {
		return visitDay;
	}

	public void setVisitDay(Integer visitDay) {
		this.visitDay = visitDay;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/*========================== 综合报告 =================================*/
	public String summary(){
		setFirstAddDate(visitFac.findFirstDate(getSiteId()));
		setVisitDay(visitFac.findDays(getSiteId()));
		return SUCCESS;
	}
	
	public void summaryTable(){
		List<SummaryVo> list = visitFac.findSummaryTable(getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void summaryReport(){
		Struts2Util.renderHtml(visitFac.findSummaryReport(getStartDate(), getEndDate(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 全站点击率 =================================*/
	public void siteTable(){
		List<SummaryVo> list = visitFac.findSiteTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void siteReport(){
		Struts2Util.renderHtml(visitFac.findSummaryReport(getStartDate(), getEndDate(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 访问记录 =================================*/
	public void lastVisitTable(){
		List<SummaryVo> list = visitFac.findLastTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data, TIME_FORMAT));
	}
	
	/*========================== 时段分布 =================================*/
	public void hourTable(){
		List<SummaryVo> list = visitFac.findHourTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void hourReport(){
		Struts2Util.renderHtml(visitFac.findHourReport(getStartDate(), getEndDate(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 入口分析 =================================*/
	public void entranceTable(){
		List<SummaryVo> list = visitFac.findEntranceTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	/*========================== 入口分析 时间趋势 =================================*/
	public String entranceTrend(){
		return SUCCESS;
	}
	
	public void entranceTrendReport(){
		Struts2Util.renderHtml(visitFac.findEmtranceTrendReport(getUrl(), getStartDate(), getEndDate(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 出口分析 =================================*/
	public void exitTable(){
		List<SummaryVo> list = visitFac.findExitTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	/*========================== 出口分析 时间趋势 =================================*/
	public String exitTrend(){
		return SUCCESS;
	}
	
	public void exitTrendReport(){
		Struts2Util.renderHtml(visitFac.findExitTrendReport(getUrl(), getStartDate(), getEndDate(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 被访问主机分析 =================================*/
	public void hostTable(){
		List<SummaryVo> list = visitFac.findHostTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void hostReport(){
		Struts2Util.renderHtml(visitFac.findHostReport(getStartDate(), getEndDate(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 被访问主机分析 时间趋势 =================================*/
	public String hostTrend(){
		return SUCCESS;
	}
	
	public void hostTrendReport(){
		Struts2Util.renderHtml(visitFac.findHostTrendReport(getHost(), getStartDate(), getEndDate(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 区域分布 =================================*/
	public void countryTable(){
		List<SummaryVo> list = visitFac.findCountryTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void countryReport(){
		Struts2Util.renderHtml(visitFac.findCountryReport(getStartDate(), getEndDate(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	public String province(){
		return SUCCESS;
	}
	
	public void provinceTable(){
		List<SummaryVo> list = visitFac.findProvinceTable(getStartDate(), getEndDate(), getCountry(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void provinceReport(){
		Struts2Util.renderHtml(visitFac.findProvinceReport(getStartDate(), getEndDate(),getCountry(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	public String city(){
		return SUCCESS;
	}
	
	public void cityTable(){
		List<SummaryVo> list = visitFac.findCityTable(getStartDate(), getEndDate(), getCountry(), getProvince(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void cityReport(){
		Struts2Util.renderHtml(visitFac.findCityReport(getStartDate(), getEndDate(),getCountry(), getProvince(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 区域分布 时间趋势=================================*/
	public String countryTrend(){
		return SUCCESS;
	}
	
	public void countryTrendReport(){
		Struts2Util.renderHtml(visitFac.findCountryTrendReport(getStartDate(), getEndDate(), getCountry(), getLabelCount(), getSiteId()));
	}

	public String provinceTrend(){
		return SUCCESS;
	}
	
	public void provinceTrendReport(){
		Struts2Util.renderHtml(visitFac.findProvinceTrendReport(getStartDate(), getEndDate(), getCountry(), getProvince(), getLabelCount(), getSiteId()));
	}
	
	public String cityTrend(){
		return SUCCESS;
	}
	
	public void cityTrendReport(){
		Struts2Util.renderHtml(visitFac.findCityTrendReport(getStartDate(), getEndDate(), getCountry(), getProvince(), getCity(), getLabelCount(), getSiteId()));
	}
	
	/*========================== 在线情况 =================================*/
	public void onlineTable(){
		List<SummaryVo> list = visitFac.findOnlineTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void onlineReport(){
		Struts2Util.renderHtml(visitFac.findOnlineReport(getStartDate(), getEndDate(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
}
