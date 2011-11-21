/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.service;

import java.util.List;

import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.Parameter;

/**
 * 图型报表服务接口
 * 
 * @author 吴智俊
 */
public interface ChartReportServiceable {
	/**
	 * 新增图型报表并把图型报表中的参数存入数据库中
	 * 
	 * @param chart 图型对象
	 * @throws BaseException
	 */
	public Long saveChart(ChartReport chart);
	
	/**
	 * 修改图型报表
	 * 
	 * @param chart 图型对象
	 * @throws BaseException
	 */
	public Long updateChart(ChartReport chart);

	/**
	 * 删除图型报表对象
	 * 
	 * @param chartId 图型报表编号
	 * @throws BaseException
	 */
	public void deletedChart(Long chartId);
	
	/**
	 * 查询图型报表对象
	 * 
	 * @param chartId 图型报表编号
	 * @return Chart对象
	 * @throws BaseException
	 */
	public ChartReport findByChart(Long chartId);

	/**
	 * 查询所有图型报表
	 * 
	 * @return List
	 * @throws BaseException
	 */
	public List<ChartReport> findAllChart();
	
	/**
	 * 更新图型参数
	 * 
	 * @param chartId 图型编号
	 * @param pagesList 页面参数集合
	 * @throws BaseException
	 */
	public Long updateChartParam(Long chartId,Parameter parameter) throws BaseException;
}
