package com.ewcms.plugin.visit.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.visit.manager.VisitFacable;
import com.ewcms.plugin.visit.manager.vo.ClickRateVo;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

/**
 * 点击量来源
 * 
 * @author wu_zhijun
 *
 */
public class ClickRateAction extends VisitBaseAction {

	private static final long serialVersionUID = -6211421724543192547L;

	@Autowired
	private VisitFacable visitFac;
	
	private String domain;
	private String webSite;
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	/*========================== 来源组成 =================================*/
	public void sourceTable(){
		List<ClickRateVo> list = visitFac.findSourceTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void sourceReport(){
		Struts2Util.renderHtml(visitFac.findSourceReport(getStartDate(), getEndDate(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}

	/*========================== 搜索引擎 =================================*/
	public void searchTable(){
		List<ClickRateVo> list = visitFac.findSearchTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void searchReport(){
		Struts2Util.renderHtml(visitFac.findSearchReport(getStartDate(), getEndDate(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 搜索引擎 时间趋势 =================================*/
	public String searchTrend(){
		return SUCCESS;
	}
	
	public void searchTrendReport(){
		Struts2Util.renderHtml(visitFac.findSearchTrendReport(getStartDate(), getEndDate(), getDomain(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 搜索引擎 =================================*/
	public void webSiteTable(){
		List<ClickRateVo> list = visitFac.findWebSiteTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void webSiteReport(){
		Struts2Util.renderHtml(visitFac.findWebSiteReport(getStartDate(), getEndDate(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 搜索引擎 时间趋势 =================================*/
	public String webSiteTrend(){
		return SUCCESS;
	}
	
	public void webSiteTrendReport(){
		Struts2Util.renderHtml(visitFac.findWebSiteTrendReport(getStartDate(), getEndDate(), getWebSite(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
}
