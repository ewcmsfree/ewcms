package com.ewcms.plugin.visit.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.visit.manager.VisitFacable;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
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
		List<SummaryVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "os", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void osReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "os", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 操作系统 时间趋势 =================================*/
	public String osTrend(){
		return SUCCESS;
	}
	
	public void osTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "os", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	/*========================== 浏览器 =================================*/
	public void browserTable(){
		List<SummaryVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "browser", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void browserReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "browser", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 浏览器 时间趋势 =================================*/
	public String browserTrend(){
		return SUCCESS;
	}
	
	public void browserTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "browser", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	/*========================== 语言 =================================*/
	public void languageTable(){
		List<SummaryVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "language", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void languageReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "language", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 语言 时间趋势 =================================*/
	public String languageTrend(){
		return SUCCESS;
	}
	
	public void languageTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "language", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	/*========================== 屏幕分析辨率 =================================*/
	public void screenTable(){
		List<SummaryVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "screen",getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void screenReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "screen", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 屏幕分析辨率 时间趋势 =================================*/
	public String screenTrend(){
		return SUCCESS;
	}
	
	public void screenTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "screen", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	/*========================== 屏幕色度 =================================*/
	public void colorDepthTable(){
		List<SummaryVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "colorDepth", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void colorDepthReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "colorDepth", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}

	/*========================== 屏幕色度 时间趋势 =================================*/
	public String colorDepthTrend(){
		return SUCCESS;
	}
	
	public void colorDepthTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "colorDepth", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	/*========================== 是否支持Applet =================================*/
	public void javaEnabledTable(){
		List<SummaryVo> list = visitFac.findClientBooleanTable(getStartDate(), getEndDate(), "javaEnabled", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void javaEnabledReport(){
		Struts2Util.renderHtml(visitFac.findClientBooleanReport(getStartDate(), getEndDate(),  "javaEnabled", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 是否支持Applet 时间趋势 =================================*/
	public String javaEnabledTrend(){
		return SUCCESS;
	}
	
	public void javaEnabledTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendBooleanReport(getStartDate(), getEndDate(), "javaEnabled", getEnabled(), getLabelCount(), getCurrentSite().getId()));
	}
	
	/*========================== Flash版本 =================================*/
	public void flashVersionReport(){
		Struts2Util.renderHtml(visitFac.findClientReport(getStartDate(), getEndDate(),  "flashVersion", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public void flashVersionTable(){
		List<SummaryVo> list = visitFac.findClientTable(getStartDate(), getEndDate(), "flashVersion", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	/*========================== Flash版本 时间趋势 =================================*/
	public String flashVersionTrend(){
		return SUCCESS;
	}
	
	public void flashVersionTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendReport(getStartDate(), getEndDate(), "flashVersion", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	/*========================== 是否允许Cookies =================================*/
	public void cookieEnabledTable(){
		List<SummaryVo> list = visitFac.findClientBooleanTable(getStartDate(), getEndDate(), "cookieEnabled", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void cookieEnabledReport(){
		Struts2Util.renderHtml(visitFac.findClientBooleanReport(getStartDate(), getEndDate(),  "cookieEnabled", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 是否允许Cookies 时间趋势 =================================*/
	public String cookieEnabledTrend(){
		return SUCCESS;
	}
	
	public void cookieEnabledTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendBooleanReport(getStartDate(), getEndDate(), "cookieEnabled", getEnabled(), getLabelCount(), getCurrentSite().getId()));
	}
}
