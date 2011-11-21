/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.report.manager.dao.ChartReportDAO;
import com.ewcms.plugin.report.manager.util.ChartAnalysisUtil;
import com.ewcms.plugin.report.manager.util.ParameterSetValueUtil;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.Parameter;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class ChartReportService implements ChartReportServiceable {

	@Autowired
	private ChartReportDAO chartReportDAO;
	
	@Override
	public Long saveChart(ChartReport chart){
		Assert.notNull(chart);
		Assert.hasLength(chart.getChartSql());

		Set<Parameter> parameters = ChartAnalysisUtil.analysisSql(chart.getChartSql());
		chart.setParameters(parameters);

		chartReportDAO.persist(chart);
		return chart.getId();
	}

	@Override
	public Long updateChart(ChartReport chart){
		Assert.notNull(chart);
		Assert.hasLength(chart.getChartSql());
		
		ChartReport entity = chartReportDAO.get(chart.getId());
		
		entity.setName(chart.getName());
		entity.setBaseDS(chart.getBaseDS());
		entity.setChartType(chart.getChartType());
		entity.setShowTooltips(chart.getShowTooltips());
		entity.setChartTitle(chart.getChartTitle());
		entity.setFontName(chart.getFontName());
		entity.setFontSize(chart.getFontSize());
		entity.setFontStyle(chart.getFontStyle());
		entity.setHorizAxisLabel(chart.getHorizAxisLabel());
		entity.setVertAxisLabel(chart.getVertAxisLabel());
		entity.setDataFontName(chart.getDataFontName());
		entity.setDataFontSize(chart.getDataFontSize());
		entity.setDataFontStyle(chart.getDataFontStyle());
		entity.setAxisFontName(chart.getAxisFontName());
		entity.setAxisFontSize(chart.getAxisFontSize());
		entity.setAxisFontStyle(chart.getAxisFontStyle());
		entity.setAxisTickFontName(chart.getAxisTickFontName());
		entity.setAxisTickFontSize(chart.getAxisTickFontSize());
		entity.setAxisTickFontStyle(chart.getAxisTickFontStyle());
		entity.setTickLabelRotate(chart.getTickLabelRotate());
		entity.setShowLegend(chart.getShowLegend());
		entity.setLegendPosition(chart.getLegendPosition());
		entity.setLegendFontName(chart.getLegendFontName());
		entity.setLegendFontSize(chart.getLegendFontSize());
		entity.setLegendFontStyle(chart.getLegendFontStyle());
		entity.setChartHeight(chart.getChartHeight());
		entity.setChartWidth(chart.getChartWidth());
		entity.setBgColorB(chart.getBgColorB());
		entity.setBgColorG(chart.getBgColorG());
		entity.setBgColorR(chart.getBgColorR());
		entity.setRemarks(chart.getRemarks());
		
		if (!entity.getChartSql().equals(chart.getChartSql())) {
			entity.setChartSql(chart.getChartSql());
			Set<Parameter> icNewList = new LinkedHashSet<Parameter>();
			
			Set<Parameter> oldParameters = entity.getParameters();
			Set<Parameter> newParameters = ChartAnalysisUtil.analysisSql(chart.getChartSql());
			for (Parameter newParameter : newParameters){
				Parameter ic = findListEntity(oldParameters,newParameter);
				if (ic == null){
					ic = newParameter;
				}
				icNewList.add(ic);
			}
			entity.setParameters(icNewList);
		}
		chartReportDAO.merge(entity);
		return chart.getId();
	}

	@Override
	public void deletedChart(Long chartId){
		chartReportDAO.removeByPK(chartId);
	}

	@Override
	public ChartReport findByChart(Long chartId){
		return chartReportDAO.get(chartId);
	}

	@Override
	public List<ChartReport> findAllChart() {
		return chartReportDAO.findAll();
	}
	
	@Override
	public Long updateChartParam(Long chartId, Parameter parameter) throws BaseException {
		if (chartId == null || chartId.intValue() == 0)
			throw new BaseException("", "图型编号不存在，请重新选择！");
		ChartReport chart = chartReportDAO.get(chartId);
		if (chart == null)
			throw new BaseException("", "图型不存在，请重新选择！");
		
		parameter = ParameterSetValueUtil.setParametersValue(parameter);
		
		Set<Parameter> parameters = chart.getParameters();
		parameters.remove(parameter);
		parameters.add(parameter);
		chart.setParameters(parameters);
		
		chartReportDAO.merge(chart);	
		
		return parameter.getId();
	}
	/**
	 * 根据参数名查询数据库中的参数集合
	 * 
	 * @param oldParameters
	 *            数据库中的报表参数集合
	 * @param newParameter 新参数
	 *            
	 * @return Parameter
	 */
	private Parameter findListEntity(Set<Parameter> oldParameters, Parameter newParameter) {
		for (Parameter parameter : oldParameters) {
			String rpEnName = parameter.getEnName();
			if (newParameter.getEnName().trim().equals(rpEnName.trim())) {
				parameter.setClassName(newParameter.getClassName());
				parameter.setDefaultValue(newParameter.getDefaultValue());
				parameter.setDescription(newParameter.getDescription());
				return parameter;
			}
		}
		return null;
	}
}
