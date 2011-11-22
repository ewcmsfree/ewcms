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
	public Long addChartReport(ChartReport chartReport) {
		return chartReportService.addChartReport(chartReport);
	}

	@Override
	public Long updChartReport(ChartReport chartReport){
		return chartReportService.updChartReport(chartReport);
	}

	@Override
	public void delChartReport(Long chartReportId) {
		chartReportService.delChartReport(chartReportId);
	}

	@Override
	public ChartReport findChartReportById(Long chartReportId){
		return chartReportService.findChartReportById(chartReportId);
	}

	@Override
	public List<ChartReport> findAllChartReport() {
		return chartReportService.findAllChartReport();
	}

	@Override
	public Long addOrUpdCategoryReport(CategoryReport categoryReport) {
		return categoryReportService.addOrUpdCategoryReport(categoryReport);
	}

	@Override
	public void delCategoryReport(Long categoryReportId){
		categoryReportService.delCategoryReport(categoryReportId);
	}

	@Override
	public CategoryReport findCategoryReportById(Long categoryReportId) {
		return categoryReportService.findCategoryReportById(categoryReportId);
	}

	@Override
	public List<CategoryReport> findAllCategoryReport(){
		return categoryReportService.findAllCategoryReport();
	}

	@Override
	public void addTextToCategories(Long categoryReportId, Long[] textIds){
		categoryReportService.addTextToCategories(categoryReportId, textIds);
	}

	@Override
	public void addChartToCategories(Long categoryReportId, Long[] chartIds) {
		categoryReportService.addChartToCategories(categoryReportId, chartIds);
	}

	@Override
	public Long addTextReport(TextReport textReport) throws BaseException {
		return textReportService.addTextReport(textReport);
	}

	@Override
	public Long updTextReport(TextReport textReport) throws BaseException {
		return textReportService.updTextReport(textReport);
	}

	@Override
	public void delTextReport(Long textReportId) {
		textReportService.delTextReport(textReportId);
	}

	@Override
	public TextReport findTextReportById(Long textReportId) {
		return textReportService.findTextReportById(textReportId);
	}

	@Override
	public List<TextReport> findAllTextReport() {
		return textReportService.findAllTextReport();
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
	public Repository findRepositoryById(Long repositoryId){
		return repositoryService.findRepositoryById(repositoryId);
	}

	@Override
	public void delRepository(Long repositoryId) {
		repositoryService.delRepository(repositoryId);
	}

	@Override
	public Long updChartReportParameter(Long chartId, Parameter parameter)
			throws BaseException {
		return chartReportService.updChartReportParameter(chartId, parameter);
	}

	@Override
	public Long updTextReportParameter(Long textId, Parameter parameter)
			throws BaseException {
		return textReportService.updTextReportParameter(textId, parameter);
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
