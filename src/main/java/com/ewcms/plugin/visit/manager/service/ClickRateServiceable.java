package com.ewcms.plugin.visit.manager.service;

import java.util.List;

import com.ewcms.plugin.visit.manager.vo.ClickRateVo;

/**
 * 点击量排行
 * 
 * @author wu_zhijun
 *
 */
public interface ClickRateServiceable {

	/**
	 * 来源组成表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SourceVo对象集合
	 */
	public List<ClickRateVo> findSourceTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 来源组成图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findSourceReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 搜索引擎表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SourceVo对象集合
	 */
	public List<ClickRateVo> findSearchTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 搜索引擎图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findSearchReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 搜索引擎之时间趋势图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param domain 域名
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findSearchTrendReport(String startDate, String endDate, String domain, Integer labelCount, Integer siteId);

	/**
	 * 来源网站表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SourceVo对象集合
	 */
	public List<ClickRateVo> findWebSiteTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 来源网站图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findWebSiteReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 来源网站之时间趋势图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param domain 域名
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findWebSiteTrendReport(String startDate, String endDate, String webSite, Integer labelCount, Integer siteId);
}
