/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.CategoryReport;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.TextReport;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author wuzhijun
 * 
 */
public class CategoryReportDetailAction extends ActionSupport {

	private static final long serialVersionUID = 2270713965369157617L;

	@Autowired
	private ReportFacable reportFac;

	private Long categoryId;
	private Long[] textReportIds;
	private Long[] chartReportIds;
	private CategoryReport categoryReportVo;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long[] getTextReportIds() {
		return textReportIds;
	}

	public void setTextReportIds(Long[] textReportIds) {
		this.textReportIds = textReportIds;
	}

	public Long[] getChartReportIds() {
		return chartReportIds;
	}

	public void setChartReportIds(Long[] chartReportIds) {
		this.chartReportIds = chartReportIds;
	}

	public CategoryReport getCategoryReportVo() {
		return categoryReportVo;
	}

	public void setCategoryReportVo(CategoryReport categoryReportVo) {
		this.categoryReportVo = categoryReportVo;
	}

	@Override
	public String execute() {
		return SUCCESS;
	}

	public String input() {
		CategoryReport vo = reportFac.findCategoryReportById(getCategoryId());

		Set<ChartReport> chartReports = vo.getCharts();
		List<Long> chartIds = new ArrayList<Long>();
		for (ChartReport chartReport : chartReports) {
			chartIds.add(chartReport.getId());
		}
		setChartReportIds(chartIds.toArray(new Long[0]));

		Set<TextReport> textReports = vo.getTexts();
		List<Long> textIds = new ArrayList<Long>();
		for (TextReport textReport : textReports) {
			textIds.add(textReport.getId());
		}
		setTextReportIds(textIds.toArray(new Long[0]));

		setCategoryReportVo(vo);
		return INPUT;
	}

	public String save() {
		reportFac.addChartToCategories(getCategoryId(), getChartReportIds());
		reportFac.addTextToCategories(getCategoryId(), getTextReportIds());
		return SUCCESS;
	}

	public Map<Long, String> getTextReport() {
		Map<Long, String> map = new HashMap<Long, String>();
		List<TextReport> texts = reportFac.findAllTextReport();
		for (TextReport text : texts) {
			map.put(text.getId(), text.getName());
		}
		return map;
	}

	public Map<Long, String> getChartReport() {
		Map<Long, String> map = new HashMap<Long, String>();
		List<ChartReport> charts = reportFac.findAllChartReport();
		for (ChartReport chart : charts) {
			map.put(chart.getId(), chart.getName());
		}
		return map;
	}
}
