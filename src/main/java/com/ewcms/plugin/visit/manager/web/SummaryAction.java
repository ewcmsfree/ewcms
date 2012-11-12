package com.ewcms.plugin.visit.manager.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.visit.manager.VisitFacable;
import com.ewcms.plugin.visit.manager.vo.InAndExitVo;
import com.ewcms.plugin.visit.manager.vo.LastVisitVo;
import com.ewcms.plugin.visit.manager.vo.OnlineVo;
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
	
	/*========================== 综合报告 =================================*/
	public String summary(){
		setFirstAddDate(visitFac.findFirstDate(getCurrentSite().getId()));
		setVisitDay(visitFac.findDays(getCurrentSite().getId()));
		return SUCCESS;
	}
	
	public void summaryTable(){
		List<SummaryVo> list = visitFac.findSummaryTable(getCurrentSite().getId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void summaryReport(){
		Struts2Util.renderHtml(visitFac.findSummaryReport(getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 全站点击率 =================================*/
	public void siteTable(){
		List<SummaryVo> list = visitFac.findSiteTable(getStartDate(), getEndDate(), getCurrentSite().getId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void siteReport(){
		Struts2Util.renderHtml(visitFac.findSummaryReport(getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 访问记录 =================================*/
	public void lastVisitTable(){
		List<LastVisitVo> list = visitFac.findLastTable(getStartDate(), getEndDate(), getRows(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data, TIME_FORMAT));
	}
	
	/*========================== 时段分布 =================================*/
	public void hourTable(){
		List<SummaryVo> list = visitFac.findHourTable(getStartDate(), getEndDate(), getCurrentSite().getId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void hourReport(){
		Struts2Util.renderHtml(visitFac.findHourReport(getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 入口分析 =================================*/
	public void entranceTable(){
		List<InAndExitVo> list = visitFac.findEntranceTable(getStartDate(), getEndDate(), getRows(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	/*========================== 入口分析 时间趋势 =================================*/
	public String entranceTrend(){
		return SUCCESS;
	}
	
	public void entranceTrendReport(){
		Struts2Util.renderHtml(visitFac.findEmtranceTrendReport(getUrl(), getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 出口分析 =================================*/
	public void exitTable(){
		List<InAndExitVo> list = visitFac.findExitTable(getStartDate(), getEndDate(), getRows(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	/*========================== 出口分析 时间趋势 =================================*/
	public String exitTrend(){
		return SUCCESS;
	}
	
	public void exitTrendReport(){
		Struts2Util.renderHtml(visitFac.findExitTrendReport(getUrl(), getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 被访问主机分析 =================================*/
	public void hostTable(){
		List<SummaryVo> list = visitFac.findHostTable(getStartDate(), getEndDate(), getRows(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void hostReport(){
		Struts2Util.renderHtml(visitFac.findHostReport(getStartDate(), getEndDate(), getRows(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 被访问主机分析 时间趋势 =================================*/
	public String hostTrend(){
		return SUCCESS;
	}
	
	public void hostTrendReport(){
		Struts2Util.renderHtml(visitFac.findHostTrendReport(getHost(), getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 区域分布 =================================*/
	public void countryTable(){
		List<SummaryVo> list = visitFac.findCountryTable(getStartDate(), getEndDate(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void countryReport(){
		Struts2Util.renderHtml(visitFac.findCountryReport(getStartDate(), getEndDate(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 区域分布 时间趋势=================================*/
	public String districtTrend(){
		return SUCCESS;
	}
	
	public void countryTrendReport(){
		Struts2Util.renderHtml(visitFac.findCountryTrendReport(getStartDate(), getEndDate(), getCountry(), getLabelCount(), getCurrentSite().getId()));
	}

	/*========================== 在线情况 =================================*/
	public void onlineTable(){
		List<OnlineVo> list = visitFac.findOnlineTable(getStartDate(), getEndDate(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void onlineReport(){
		Struts2Util.renderHtml(visitFac.findOnlineReport(getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
}
