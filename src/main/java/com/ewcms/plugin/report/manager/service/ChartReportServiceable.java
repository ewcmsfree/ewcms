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
	 * @param chartReport 图型报表对象
	 * @return Long 图型报表编号
	 */
	public Long addChartReport(ChartReport chartReport);
	
	/**
	 * 修改图型报表
	 * 
	 * @param chartReport 图型报表对象
	 * @return Long 图型报表编号
	 */
	public Long updChartReport(ChartReport chartReport);

	/**
	 * 删除图型报表对象
	 * 
	 * @param chartReportId 图型报表编号
	 */
	public void delChartReport(Long chartReportId);
	
	/**
	 * 查询图型报表对象
	 * 
	 * @param chartReportId 图型报表编号
	 * @return ChartReport 图型报表对象
	 */
	public ChartReport findChartReportById(Long chartReportId);

	/**
	 * 查询所有图型报表
	 * 
	 * @return List 图型报表集合
	 */
	public List<ChartReport> findAllChartReport();
	
	/**
	 * 更新图型参数
	 * 
	 * @param chartReportId 图型编号
	 * @param pagesList 页面参数集合
	 * @throws BaseException
	 */
	public Long updChartReportParameter(Long chartReportId,Parameter parameter) throws BaseException;
}
