/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.service;

import java.util.List;

import com.ewcms.plugin.visit.manager.vo.SummaryVo;

/**
 * 总体情况统计
 * 
 * @author wu_zhijun
 *
 */
public interface SummaryServiceable {
	
	/**
	 * 查询开始统计分析最早的日期，如未查找到，则使用当前日期
	 * 
	 * @param siteId 站点编号
	 * @return String 日期字符串
	 */
	public String findFirstDate(Integer siteId);
	
	/**
	 * 总天数
	 * 
	 * @param siteId 站点编号
	 * @return Integer 天数
	 */
	public Integer findDays(Integer siteId);
	
	/**
	 * 综合报告统计表格
	 * 
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findSummaryTable(Integer siteId);

	/**
	 * 综合报告统计图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findSummaryReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	/**
	 * 全站点击率统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findSiteTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 访问记录统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findLastTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 时段分布统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findHourTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 时段分布统计图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findHourReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	/**
	 * 入口分析统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SummaryVo集合对象
	 */
	public List<SummaryVo> findEntranceTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 入口分析统计之时间趋势图形
	 * 
	 * @param url URL地址
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findEmtranceTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	/**
	 * 出口分析统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findExitTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 出口分析统计之时间趋势图形
	 * 
	 * @param url URL地址
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findExitTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	/**
	 * 被访主机分析统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findHostTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 被访主机分析统计图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findHostReport(String startDate, String endDate, Integer siteId);

	/**
	 * 被访问主机分析统计之时间趋势图形
	 * 
	 * @param host 主机地址
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findHostTrendReport(String host, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	/**
	 * 区域分布(国家)统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findCountryTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 区域分布(国家)统计图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findCountryReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 区域分布(省份) 统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findProvinceTable(String startDate, String endDate, String country, Integer siteId);
	
	/**
	 * 区域分布(省份)统计图形	
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findProvinceReport(String startDate, String endDate, String country, Integer siteId);
	
	/**
	 * 区域分布(城市) 统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param province 省份
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findCityTable(String startDate, String endDate, String country, String province, Integer siteId);
	
	/**
	 * 区域分布(城市) 统计图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param province 省份
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findCityReport(String startDate, String endDate, String country, String province, Integer siteId);
	
	/**
	 * 区域分布(国家)统计之时间趋势图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findCountryTrendReport(String startDate, String endDate, String country, Integer labelCount, Integer siteId);
	
	/**
	 * 区域分布(省份)统计之时间趋势图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param province 省份名称
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findProvinceTrendReport(String startDate, String endDate, String country, String province, Integer labelCount, Integer siteId);
	
	/**
	 * 区域分布(城市)统计之时间趋势图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param province 省份名称
	 * @param city 城市名称
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findCityTrendReport(String startDate, String endDate, String country, String province, String city, Integer labelCount, Integer siteId);
	
	/**
	 * 在线情况统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findOnlineTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 在线情况统计图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findOnlineReport(String startDate, String endDate, Integer labelCount, Integer siteId);
}
