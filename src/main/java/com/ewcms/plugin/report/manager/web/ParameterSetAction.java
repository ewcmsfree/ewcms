/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.report.generate.factory.ChartFactoryable;
import com.ewcms.plugin.report.generate.factory.TextFactoryable;
import com.ewcms.plugin.report.generate.util.ParamConversionPage;
import com.ewcms.plugin.report.generate.vo.PageShowParam;
import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.TextReport;
import com.ewcms.plugin.report.model.TextType;
import com.opensymphony.xwork2.ActionSupport;

public class ParameterSetAction extends ActionSupport {

	private static final long serialVersionUID = 3622306004223474063L;
	
	@Autowired
	private ReportFacable reportFac;
	@Autowired
	private TextFactoryable textFactory;
	@Autowired
	private ChartFactoryable chartFactory;

	private TextType fileFormat;
	private Long reportId;
	private String reportType;
	private Map<String, String> paraMap;
	private List<PageShowParam> parameters;
	
	public void setFileFormat(TextType fileFormat) {
		this.fileFormat = fileFormat;
	}

	public List<TextType> getFileFormatList() {
		return Arrays.asList(TextType.values());
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String eventOP) {
		this.reportType = eventOP;
	}

	public Map<String, String> getParaMap() {
		return paraMap;
	}

	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}

	public List<PageShowParam> getParameters() {
		return parameters;
	}

	public void setParameters(List<PageShowParam> parameters) {
		this.parameters = parameters;
	}

	public Boolean isTextable() {
		if (reportType.indexOf("text") != -1)
			return true;
		return false;
	}

	@Override
	public String execute() throws Exception {
		if (reportType.equals("text")) {
			parameters = ParamConversionPage.conversion(reportFac.findByText(reportId).getParameters());
			return SUCCESS;
		}

		if (reportType.equals("chart")) {
			parameters = ParamConversionPage.conversion(reportFac.findByChart(reportId).getParameters());
			return SUCCESS;
		}
		return null;
	}
	
	public void build(){
		if (reportType.equals("text")){
			buildText();
		}else if (reportType.equals("chart")){
			buildChart();
		}
	}

	private void buildText() {
		PrintWriter pw = null;
		InputStream in = null;
		try {
			TextReport report = reportFac.findByText(reportId);
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();

			pw = response.getWriter();
			response.reset();// 清空输出
			response.setContentLength(0);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			byte[] bytes = textFactory.export(paraMap, report, fileFormat, response, request);
			in = new ByteArrayInputStream(bytes);
			int len = 0;
			while ((len = in.read()) > -1) {
				pw.write(len);
			}
			pw.flush();
		} catch (Exception e) {
			// log.error(e.toString());
		} finally {
			if (pw != null) {
				try {
					pw.close();
					pw = null;
				} catch (Exception e) {
				}
			}
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (Exception e) {
				}
			}
		}
	}

	private void buildChart() {
		PrintWriter pw = null;
		InputStream in = null;
		try {
			ChartReport chart = reportFac.findByChart(reportId);

			HttpServletResponse response = ServletActionContext.getResponse();
			pw = response.getWriter();

			response.reset();// 清空输出
			response.setContentLength(0);
			byte[] bytes = chartFactory.export(chart, paraMap);
			response.setContentLength(bytes.length);
			response.setHeader("Content-Type", "image/png");
			in = new ByteArrayInputStream(bytes);
			int len = 0;
			while ((len = in.read()) > -1) {
				pw.write(len);
			}
			pw.flush();
		} catch (Exception e) {
			// log.error(e.toString());
		} finally {
			if (pw != null) {
				try {
					pw.close();
					pw = null;
				} catch (Exception e) {
				}
			}
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (Exception e) {
				}
			}
		}
	}

}
