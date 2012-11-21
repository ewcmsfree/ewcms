/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.service;

import java.util.List;

import com.ewcms.plugin.visit.manager.vo.LoyaltyVo;

/**
 * 忠诚度分析
 * 
 * @author wu_zhijun
 *
 */
public interface LoyaltyServiceable {

	/**
	 * 访问频率表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List VisitFreqVo对象集合
	 */
	public List<LoyaltyVo> findFrequencyTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 访问频率图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findFrequencyReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 访问频率之时间趋势
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param frequency 访问频率数
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findFrequencyTrendReport(String startDate, String endDate, Long frequency, Integer labelCount, Integer siteId);

	/**
	 * 访问深度表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List VisitFreqVo对象集合
	 */
	public List<LoyaltyVo> findDepthTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 访问深度图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findDepthReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 访问深度之时间趋势
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param frequency 访问频率数
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findDepthTrendReport(String startDate, String endDate, Long depth, Integer labelCount, Integer siteId);
	
	/**
	 * 回头率表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List LoyaltyVo对象集合
	 */
	public List<LoyaltyVo> findVisitorTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 回头率图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findVisitorReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	/**
	 * 停留时间表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List LoyaltyVo对象集合
	 */
	public List<LoyaltyVo> findStickTimeTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 停留时间图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findStickTimeReport(String startDate, String endDate, Integer labelCount, Integer siteId);
}
