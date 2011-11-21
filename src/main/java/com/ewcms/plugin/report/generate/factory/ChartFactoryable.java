/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.generate.factory;

import java.util.List;
import java.util.Map;

import com.ewcms.plugin.report.generate.vo.PageShowParam;
import com.ewcms.plugin.report.model.ChartReport;

/**
 * 图型报表生成器
 * 
 * @author 吴智俊
 */
public interface ChartFactoryable {
	/**
	 * 根据图型报表编号生成图型报表
	 * 
	 * @param report 图型报表对象
	 * @param pageParams 页面参数集合
	 * @return byte
	 * @throws Exception
	 */
	public byte[] export(ChartReport report,Map<String, String> pageParams) throws Exception;
	
	/**
	 * 查询图型参数并转换成PageShowParam对象集合
	 * 
	 * @param report 图型报表对象
	 * @return List<PageShowParam>
	 */
	public List<PageShowParam> chartParameters(ChartReport report);
}
