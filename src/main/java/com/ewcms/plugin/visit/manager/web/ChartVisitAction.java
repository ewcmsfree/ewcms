package com.ewcms.plugin.visit.manager.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.visit.manager.VisitFacable;
import com.ewcms.plugin.visit.manager.vo.ArticleVisitVo;
import com.ewcms.plugin.visit.manager.vo.InAndExitVo;
import com.ewcms.plugin.visit.manager.vo.LastVisitVo;
import com.ewcms.plugin.visit.manager.vo.OnlineVo;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
import com.ewcms.web.EwcmsBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

/**
 * 
 * @author wu_zhijun
 *
 */
public class ChartVisitAction extends EwcmsBaseAction {

	private static final long serialVersionUID = -19535212533945033L;

	@Autowired
	private VisitFacable visitFac;
	
	private String startDate;
	private String endDate;
	private Integer labelCount = 8;
	private Integer rows = 20;
	private String url;
	private String host;
	private Boolean enabled;
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getLabelCount() {
		return labelCount;
	}

	public void setLabelCount(Integer labelCount) {
		this.labelCount = labelCount;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void summaryReport(){
		Struts2Util.renderHtml(visitFac.findVisitSummaryReport(getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public void summaryTable(){
		List<SummaryVo> list = visitFac.findVisitSummaryTable(getCurrentSite().getId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void siteReport(){
		Struts2Util.renderHtml(visitFac.findVisitSummaryReport(getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public void hourReport(){
		Struts2Util.renderHtml(visitFac.findHourVisitReport(getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public String entranceTrend(){
		return SUCCESS;
	}
	
	public void entranceReport(){
		Struts2Util.renderHtml(visitFac.findEmtranceVisitCountPv(getUrl(), getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public String exitTrend(){
		return SUCCESS;
	}
	
	public void exitReport(){
		Struts2Util.renderHtml(visitFac.findExitVisitCountPv(getUrl(), getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public String hostTrend(){
		return SUCCESS;
	}
	
	public void hostReport(){
		Struts2Util.renderHtml(visitFac.findHostVisitReport(getStartDate(), getEndDate(), getRows(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public void hostTrendReport(){
		Struts2Util.renderHtml(visitFac.findHostVisitCountPv(getHost(), getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public void siteTable(){
		List<SummaryVo> list = visitFac.findVisitSiteTable(getStartDate(), getEndDate(), getCurrentSite().getId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	private static DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	
	public void lastVisitTable(){
		List<LastVisitVo> list = visitFac.findLastVisit(getStartDate(), getEndDate(), getRows(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data, TIME_FORMAT));
	}
	
	public void hourTable(){
		List<SummaryVo> list = visitFac.findHourVisitTable(getStartDate(), getEndDate(), getCurrentSite().getId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void entranceTable(){
		List<InAndExitVo> list = visitFac.findEntranceVisit(getStartDate(), getEndDate(), getRows(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void exitTable(){
		List<InAndExitVo> list = visitFac.findExitVisit(getStartDate(), getEndDate(), getRows(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void hostTable(){
		List<SummaryVo> list = visitFac.findHostVisit(getStartDate(), getEndDate(), getRows(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void countryTable(){
		List<SummaryVo> list = visitFac.findCountryVisitTable(getStartDate(), getEndDate(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void countryReport(){
		Struts2Util.renderHtml(visitFac.findCountryVisitReport(getStartDate(), getEndDate(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	private String country;
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String districtTrend(){
		return SUCCESS;
	}
	
	public void countryTrendReport(){
		Struts2Util.renderHtml(visitFac.findCountryTrendVisitReport(getStartDate(), getEndDate(), getCountry(), getLabelCount(), getCurrentSite().getId()));
	}
	
	private String fieldValue;
	
	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public void osTable(){
		List<SummaryVo> list = visitFac.findClientVisitTable(getStartDate(), getEndDate(), "os", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void osReport(){
		Struts2Util.renderHtml(visitFac.findClientVisitReport(getStartDate(), getEndDate(),  "os", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public String osTrend(){
		return SUCCESS;
	}
	
	public void osTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendVisitReport(getStartDate(), getEndDate(), "os", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	public void browserTable(){
		List<SummaryVo> list = visitFac.findClientVisitTable(getStartDate(), getEndDate(), "browser", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void browserReport(){
		Struts2Util.renderHtml(visitFac.findClientVisitReport(getStartDate(), getEndDate(),  "browser", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public String browserTrend(){
		return SUCCESS;
	}
	
	public void browserTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendVisitReport(getStartDate(), getEndDate(), "browser", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	public void languageTable(){
		List<SummaryVo> list = visitFac.findClientVisitTable(getStartDate(), getEndDate(), "language", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void languageReport(){
		Struts2Util.renderHtml(visitFac.findClientVisitReport(getStartDate(), getEndDate(),  "language", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public String languageTrend(){
		return SUCCESS;
	}
	
	public void languageTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendVisitReport(getStartDate(), getEndDate(), "language", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	public void screenTable(){
		List<SummaryVo> list = visitFac.findClientVisitTable(getStartDate(), getEndDate(), "screen",getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void screenReport(){
		Struts2Util.renderHtml(visitFac.findClientVisitReport(getStartDate(), getEndDate(),  "screen", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public String screenTrend(){
		return SUCCESS;
	}
	
	public void screenTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendVisitReport(getStartDate(), getEndDate(), "screen", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	public void colorDepthTable(){
		List<SummaryVo> list = visitFac.findClientVisitTable(getStartDate(), getEndDate(), "colorDepth", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void colorDepthReport(){
		Struts2Util.renderHtml(visitFac.findClientVisitReport(getStartDate(), getEndDate(),  "colorDepth", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}

	public String colorDepthTrend(){
		return SUCCESS;
	}
	
	public void colorDepthTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendVisitReport(getStartDate(), getEndDate(), "colorDepth", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	public void javaEnabledTable(){
		List<SummaryVo> list = visitFac.findClientBooleanVisitTable(getStartDate(), getEndDate(), "javaEnabled", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void javaEnabledReport(){
		Struts2Util.renderHtml(visitFac.findClientBooleanVisitReport(getStartDate(), getEndDate(),  "javaEnabled", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public String javaEnabledTrend(){
		return SUCCESS;
	}
	
	public void javaEnabledTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientBooleanTrendVisitReport(getStartDate(), getEndDate(), "javaEnabled", getEnabled(), getLabelCount(), getCurrentSite().getId()));
	}
	
	public void flashVersionReport(){
		Struts2Util.renderHtml(visitFac.findClientVisitReport(getStartDate(), getEndDate(),  "flashVersion", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public void flashVersionTable(){
		List<SummaryVo> list = visitFac.findClientVisitTable(getStartDate(), getEndDate(), "flashVersion", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public String flashVersionTrend(){
		return SUCCESS;
	}
	
	public void flashVersionTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientTrendVisitReport(getStartDate(), getEndDate(), "flashVersion", getFieldValue(), getLabelCount(), getCurrentSite().getId()));
	}
	
	public void cookieEnabledTable(){
		List<SummaryVo> list = visitFac.findClientBooleanVisitTable(getStartDate(), getEndDate(), "cookieEnabled", getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void cookieEnabledReport(){
		Struts2Util.renderHtml(visitFac.findClientBooleanVisitReport(getStartDate(), getEndDate(),  "cookieEnabled", getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public String cookieEnabledTrend(){
		return SUCCESS;
	}
	
	public void cookieEnabledTrendReport(){
		Struts2Util.renderHtml(visitFac.findClientBooleanTrendVisitReport(getStartDate(), getEndDate(), "cookieEnabled", getEnabled(), getLabelCount(), getCurrentSite().getId()));
	}
	
	public void onlineReport(){
		Struts2Util.renderHtml(visitFac.findOnlineVisitReport(getStartDate(), getEndDate(), getLabelCount(), getCurrentSite().getId()), "encoding:UTF-8","no-cache:false");
	}
	
	public void onlineTable(){
		List<OnlineVo> list = visitFac.findOnlineVisitTable(getStartDate(), getEndDate(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void articleTable(){
		List<ArticleVisitVo> list = visitFac.findArticleVisit(getRows(), getCurrentSite().getId());
		DataGrid data = new DataGrid(getRows(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	private String firstAddDate;
	private Integer visitDay;
	
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

	@Override
	public String execute() {
		setFirstAddDate(visitFac.findVisitFirstAddDate(getCurrentSite().getId()));
		setVisitDay(visitFac.findVisitDay(getCurrentSite().getId()));
		return SUCCESS;
	}
}
