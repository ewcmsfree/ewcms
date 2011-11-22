/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager;

import java.util.List;

import com.ewcms.plugin.BaseException;
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
public interface ReportFacable {
	/**
	 * 保存报表文件并把报表中的参数存入数据库中
	 * 
	 * @param textReport 文字报表对象
	 * @return Long 文字报表编号
	 * @throws BaseException
	 */
	public Long addTextReport(TextReport textReport) throws BaseException;

	/**
	 * 修改报表文件
	 * 
	 * @param textReport 文字报表对象
	 * @return Long 文字报表编号
	 * @throws BaseException
	 */
	public Long updTextReport(TextReport textReport) throws BaseException;

	/**
	 * 删除报表
	 * 
	 * @param textReportId 文字报表编号
	 */
	public void delTextReport(Long textReportId);
	
	/**
	 * 查询报表
	 * 
	 * @param textReportId 文字报表编号
	 * @return TextReport 文字报表对象
	 */
	public TextReport findTextReportById(Long textReportId);
	
	/**
	 * 查询所有报表
	 * 
	 * @return List 文字报表集合
	 */
	public List<TextReport> findAllTextReport() ;
	
	/**
	 * 更新报表参数
	 * 
	 * @param textReportId 报表编号
	 * @param pagesList 页面参数集合
	 * @throws BaseException
	 */
	public Long updTextReportParameter(Long textReportId,Parameter parameter) throws BaseException;

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

	/**
	 * 查询参数对象
	 * 
	 * @param parameterId 参数编号
	 * @return Parameter 参数对象
	 */
	public Parameter findParameterById(Long parameterId);

	/**
	 * 新增或修改报表分类对象
	 * 
	 * @param categoryReport 分类对象
	 */
	public Long addOrUpdCategoryReport(CategoryReport categoryReport);

	/**
	 * 删除报表分类对象
	 * 
	 * @param categoryReportId 分类编号
	 */
	public void delCategoryReport(Long categoryReportId);
	
	/**
	 * 查询报表分类对象
	 * 
	 * @param categoryReportId 分类编号
	 * @return Categories 对象
	 */
	public CategoryReport findCategoryReportById(Long categoryReportId);

	/**
	 * 查询所有分类对象
	 * 
	 * @return List对象
	 */
	public List<CategoryReport> findAllCategoryReport();

	/**
	 * 根据分类编号保存报表
	 * 
	 * @param categoryReportId 分类编号
	 * @param textIds 报表编号数组
	 */
	public void addTextToCategories(Long categoryReportId, Long[] textIds);

	/**
	 * 根据分类编号保存图型
	 * 
	 * @param categoryReportId 分类编号
	 * @param chartIds 图型编号数组
	 */
	public void addChartToCategories(Long categoryReportId,Long[] chartIds);
	
	/**
	 * 判断文字报表是否在分类中
	 * 
	 * @param textId 文字报表编号
	 * @param categoryId 分类编号
	 * @return Boolean (false:不存在,true:存在)
	 */
	public Boolean findTextIsEntityByTextAndCategory(Long textId, Long categoryId);
	
	/**
	 * 判断图型报表是否在分类中
	 * 
	 * @param chartId 图型报表编号
	 * @param categoryId 分类编号
	 * @return Boolean (false:不存在,true:存在)
	 */
	public Boolean findChartIsEntityByChartAndCategory(Long chartId, Long categoryId);

	/**
	 * 新增存储
	 * 
	 * @param repository 存储对象
	 * @return Long 存储编号
	 */
    public Long addRepository(Repository repository);

    /**
     * 修改存储
     * 
     * @param repository 存储对象
     * @return Long 存储编号
     */
    public Long updRepository(Repository repository);

    /**
     * 查询存储
     * 
     * @param repositoryId 存储编号
     * @return Repository 存储对象
     */
    public Repository findRepositoryById(Long repositoryId);
    
    /**
     * 删除存储
     * 
     * @param repositoryId 存储编号
     */
    public void delRepository(Long repositoryId);

}
