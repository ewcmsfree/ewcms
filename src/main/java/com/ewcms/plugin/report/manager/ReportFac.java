/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.report.manager.service.CategoryReportServiceable;
import com.ewcms.plugin.report.manager.service.ChartReportServiceable;
import com.ewcms.plugin.report.manager.service.ParameterServiceable;
import com.ewcms.plugin.report.manager.service.RepositoryServiceable;
import com.ewcms.plugin.report.manager.service.TextReportServiceable;
import com.ewcms.plugin.report.model.CategoryReport;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.Parameter;
import com.ewcms.plugin.report.model.Repository;
import com.ewcms.plugin.report.model.TextReport;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class ReportFac implements ReportFacable {

	@Autowired
	private ChartReportServiceable chartReportService;
	@Autowired
	private ParameterServiceable parameterService;
	@Autowired
	private CategoryReportServiceable categoryReportService;
	@Autowired
	private TextReportServiceable textReportService;
	@Autowired
	private RepositoryServiceable repositoryService;
	
	@Override
	public Long saveChart(ChartReport chart) {
		return chartReportService.saveChart(chart);
	}

	@Override
	public Long updateChart(ChartReport chart){
		return chartReportService.updateChart(chart);
	}

	@Override
	public void deletedChart(Long chartId) {
		chartReportService.deletedChart(chartId);
	}

	@Override
	public ChartReport findByChart(Long chartId){
		return chartReportService.findByChart(chartId);
	}

	@Override
	public List<ChartReport> findAllChart() {
		return chartReportService.findAllChart();
	}

	@Override
	public Long saveOrUpdateReportCategory(CategoryReport reportCategory) {
		return categoryReportService.saveOrUpdateReportCategory(reportCategory);
	}

	@Override
	public void deletedReportCategory(Long reportCategoryId){
		categoryReportService.deletedReportCategory(reportCategoryId);
	}

	@Override
	public CategoryReport findByCategory(Long reportCategoryId) {
		return categoryReportService.findByCategory(reportCategoryId);
	}

	@Override
	public List<CategoryReport> findAllReportCategory(){
		return categoryReportService.findAllReportCategory();
	}

	@Override
	public void saveTextToCategories(Long reportCategoryId, Long[] textIds){
		categoryReportService.saveTextToCategories(reportCategoryId, textIds);
	}

	@Override
	public void saveChartToCategories(Long reportCategoryId, Long[] chartIds) {
		categoryReportService.saveChartToCategories(reportCategoryId, chartIds);
	}

	@Override
	public Long saveText(TextReport text) throws BaseException {
		return textReportService.saveText(text);
	}

	@Override
	public Long updateText(TextReport text) throws BaseException {
		return textReportService.updateText(text);
	}

	@Override
	public void deletedText(Long textId) {
		textReportService.deletedText(textId);
	}

	@Override
	public TextReport findByText(Long textId) {
		return textReportService.findByText(textId);
	}

	@Override
	public List<TextReport> findAllText() {
		return textReportService.findAllText();
	}

	@Override
	public Long addRepository(Repository repository) {
		return repositoryService.addRepository(repository);
	}

	@Override
	public Long updRepository(Repository repository) {
		return repositoryService.updRepository(repository);
	}

	@Override
	public Repository findRepository(Long repositoryId){
		return repositoryService.findRepository(repositoryId);
	}

	@Override
	public void delRepository(Long repositoryId) {
		repositoryService.delRepository(repositoryId);
	}

	@Override
	public Long updateChartParam(Long chartId, Parameter parameter)
			throws BaseException {
		return chartReportService.updateChartParam(chartId, parameter);
	}

	@Override
	public Long updateTextParam(Long textId, Parameter parameter)
			throws BaseException {
		return textReportService.updateTextParam(textId, parameter);
	}

	@Override
	public Parameter findParameterById(Long parameterId) {
		return parameterService.findParameterById(parameterId);
	}

	@Override
	public Boolean findTextIsEntityByTextAndCategory(Long textId,
			Long categoryId) {
		return categoryReportService.findTextIsEntityByTextAndCategory(textId, categoryId);
	}

	@Override
	public Boolean findChartIsEntityByChartAndCategory(Long chartId,
			Long categoryId) {
		return categoryReportService.findChartIsEntityByChartAndCategory(chartId, categoryId);
	}
}
