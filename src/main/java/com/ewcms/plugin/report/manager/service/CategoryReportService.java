/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.report.manager.dao.CategoryReportDAO;
import com.ewcms.plugin.report.manager.dao.ChartReportDAO;
import com.ewcms.plugin.report.manager.dao.TextReportDAO;
import com.ewcms.plugin.report.model.CategoryReport;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.TextReport;

/**
 * 
 * @author wu_zhijun
 * 
 */
@Service
public class CategoryReportService implements CategoryReportServiceable {

	@Autowired
	private CategoryReportDAO categoryReportDAO;
	@Autowired
	private TextReportDAO textReportDAO;
	@Autowired
	private ChartReportDAO chartReportDAO;

	@Override
	public Long addOrUpdCategoryReport(CategoryReport categoryReport){
		categoryReportDAO.merge(categoryReport);
		return categoryReport.getId();
	}

	@Override
	public void delCategoryReport(Long categoryReportId){
		categoryReportDAO.removeByPK(categoryReportId);
	}

	@Override
	public CategoryReport findCategoryReportById(Long categoryReportId) {
		return categoryReportDAO.get(categoryReportId);
	}

	@Override
	public List<CategoryReport> findAllCategoryReport(){
		return categoryReportDAO.findAll();
	}

	@Override
	public void addTextToCategories(Long categoryReportId, Long[] textIds){
		CategoryReport categoryReport = findCategoryReportById(categoryReportId);

		Set<TextReport> texts = new HashSet<TextReport>();
		if (textIds != null && textIds.length > 0) {
			for (int i = 0; i < textIds.length; i++) {
				Long reportId = textIds[i];
				TextReport text = textReportDAO.get(reportId);
				texts.add(text);
			}
		}
		categoryReport.setTexts(texts);
		addOrUpdCategoryReport(categoryReport);
	}

	@Override
	public void addChartToCategories(Long categoryReportId, Long[] chartIds) {
		CategoryReport categories = findCategoryReportById(categoryReportId);

		Set<ChartReport> chartList = new HashSet<ChartReport>();
		if (chartIds != null && chartIds.length > 0) {
			for (int i = 0; i < chartIds.length; i++) {
				Long chartId = chartIds[i];
				ChartReport chart = chartReportDAO.get(chartId);
				chartList.add(chart);
			}
		}
		categories.setCharts(chartList);
		addOrUpdCategoryReport(categories);
	}

	@Override
	public Boolean findTextIsEntityByTextAndCategory(Long textId, Long categoryId) {
		return categoryReportDAO.findTextIsEntityByTextAndCategory(textId, categoryId);
	}

	@Override
	public Boolean findChartIsEntityByChartAndCategory(Long chartId, Long categoryId) {
		return categoryReportDAO.findChartIsEntityByChartAndCategory(chartId, categoryId);
	}
}
