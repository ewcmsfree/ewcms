/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.service;

import java.util.List;

import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.report.model.Parameter;
import com.ewcms.plugin.report.model.TextReport;

/**
 * 文字报表管理服务接口
 * 
 * @author 吴智俊
 */
public interface TextReportServiceable {
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
}
