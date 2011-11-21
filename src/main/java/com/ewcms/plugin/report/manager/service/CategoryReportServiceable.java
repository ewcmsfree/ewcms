/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.service;

import java.util.List;

import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.report.model.CategoryReport;

/**
 * 报表分类管理服务接口
 * 
 * @author 吴智俊
 */
public interface CategoryReportServiceable {
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
}
