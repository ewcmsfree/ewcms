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
 * 报表管理服务接口
 * 
 * @author 吴智俊
 */
public interface TextReportServiceable {
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
