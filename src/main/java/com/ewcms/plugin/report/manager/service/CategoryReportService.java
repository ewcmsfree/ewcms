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
	public Long saveOrUpdateReportCategory(CategoryReport reportCategory){
		categoryReportDAO.merge(reportCategory);
		return reportCategory.getId();
	}

	@Override
	public void deletedReportCategory(Long reportCategoryId){
		categoryReportDAO.removeByPK(reportCategoryId);
	}

	@Override
	public CategoryReport findByCategory(Long reportCategoryId) {
		return categoryReportDAO.get(reportCategoryId);
	}

	@Override
	public List<CategoryReport> findAllReportCategory(){
		return categoryReportDAO.findAll();
	}

	@Override
	public void saveTextToCategories(Long reportCategoryId, Long[] textIds){
		CategoryReport reportCategory = findByCategory(reportCategoryId);

		Set<TextReport> texts = new HashSet<TextReport>();
		if (textIds != null && textIds.length > 0) {
			for (int i = 0; i < textIds.length; i++) {
				Long reportId = textIds[i];
				TextReport text = textReportDAO.get(reportId);
				texts.add(text);
			}
		}
		reportCategory.setTexts(texts);
		saveOrUpdateReportCategory(reportCategory);
	}

	@Override
	public void saveChartToCategories(Long reportCategoryId, Long[] chartIds) {
		CategoryReport categories = findByCategory(reportCategoryId);

		Set<ChartReport> chartList = new HashSet<ChartReport>();
		if (chartIds != null && chartIds.length > 0) {
			for (int i = 0; i < chartIds.length; i++) {
				Long chartId = chartIds[i];
				ChartReport chart = chartReportDAO.get(chartId);
				chartList.add(chart);
			}
		}
		categories.setCharts(chartList);
		saveOrUpdateReportCategory(categories);
	}

	@Override
	public Boolean findTextIsEntityByTextAndCategory(Long textId,
			Long categoryId) {
		return categoryReportDAO.findTextIsEntityByTextAndCategory(textId, categoryId);
	}

	@Override
	public Boolean findChartIsEntityByChartAndCategory(Long chartId,
			Long categoryId) {
		return categoryReportDAO.findChartIsEntityByChartAndCategory(chartId, categoryId);
	}
}
