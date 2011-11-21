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
	 * 新增或修改报表分类对象
	 * 
	 * @param reportCategory 分类对象
	 * @throws BaseException
	 */
	public Long saveOrUpdateReportCategory(CategoryReport reportCategory);

	/**
	 * 删除报表分类对象
	 * 
	 * @param reportCategoryId 分类编号
	 * @throws BaseException
	 */
	public void deletedReportCategory(Long reportCategoryId);
	
	/**
	 * 查询报表分类对象
	 * 
	 * @param reportCategoryId 分类编号
	 * @return Categories 对象
	 * @throws BaseException
	 */
	public CategoryReport findByCategory(Long reportCategoryId);

	/**
	 * 查询所有分类对象
	 * 
	 * @return List对象
	 * @throws BaseException
	 */
	public List<CategoryReport> findAllReportCategory();

	/**
	 * 根据分类编号保存报表
	 * 
	 * @param reportCategoryId 分类编号
	 * @param textIds 报表编号数组
	 * @throws BaseException
	 */
	public void saveTextToCategories(Long reportCategoryId, Long[] textIds);

	/**
	 * 根据分类编号保存图型
	 * 
	 * @param reportCategoryId 分类编号
	 * @param chartIds 图型编号数组
	 * @throws BaseException
	 */
	public void saveChartToCategories(Long reportCategoryId,Long[] chartIds);
	
	public Boolean findTextIsEntityByTextAndCategory(Long textId, Long categoryId);
	
	public Boolean findChartIsEntityByChartAndCategory(Long chartId, Long categoryId);
	
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
	
	public Parameter findParameterById(Long parameterId);

	/**
	 * 新增存储
	 * 
	 * @param repository
	 * @throws BaseException
	 */
    public Long addRepository(Repository repository);

    /**
     * 修改存储
     * 
     * @param repository
     * @throws BaseException
     */
    public Long updRepository(Repository repository);

    /**
     * 查询存储
     * 
     * @param repositoryId
     * @return
     * @throws BaseException
     */
    public Repository findRepository(Long repositoryId);
    
    /**
     * 删除存储
     * 
     * @param repositoryId
     * @throws BaseException
     */
    public void delRepository(Long repositoryId);

	/**
	 * 保存报表文件并把报表中的参数存入数据库中
	 * 
	 * @param text 报表对象
	 * @throws BaseException
	 */
	public Long saveText(TextReport text) throws BaseException;

	/**
	 * 修改报表文件
	 * 
	 * @param text 报表对象
	 * @throws BaseException
	 */
	public Long updateText(TextReport text) throws BaseException;

	/**
	 * 删除报表
	 * 
	 * @param textId 报表编号
	 * @throws BaseException
	 */
	public void deletedText(Long textId);
	
	/**
	 * 查询报表
	 * 
	 * @param textId 报表编号
	 * @return Report 报表对象
	 * @throws BaseException
	 */
	public TextReport findByText(Long textId);
	
	/**
	 * 查询所有报表
	 * 
	 * @return List
	 * @throws BaseException
	 */
	public List<TextReport> findAllText() ;
	
	/**
	 * 更新报表参数
	 * 
	 * @param textId 报表编号
	 * @param pagesList 页面参数集合
	 * @throws BaseException
	 */
	public Long updateTextParam(Long textId,Parameter parameter) throws BaseException;
}
