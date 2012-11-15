package com.ewcms.plugin.visit.manager.service;

import java.util.List;

import com.ewcms.plugin.visit.manager.vo.TrafficVo;

/**
 * 访问量排行
 * 
 * @author wu_zhijun
 *
 */
public interface TrafficServiceable {
	
	/**
	 * 文章点击排行表格
	 * 
	 * @param channelId 频道编号
	 * @param siteId 站点编号
	 * @return List ClickVo对象集合
	 */
	public List<TrafficVo> findArticleTable(Integer channelId, Integer siteId);
	
	/**
	 * URL点击排行表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List ClickVo对象集合
	 */
	public List<TrafficVo> findUrlTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * URL点击排行之时间趋势图形
	 * 
	 * @param startDate 开始日期 
	 * @param endDate 结束日期
	 * @param url URL地址
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findUrlTrendReport(String startDate, String endDate, String url, Integer labelCount, Integer siteId);
	
	/**
	 * 栏目点击排行表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param channelParentId 父频道编号
	 * @param siteId 站点编号
	 * @return List ClickVo对象集合
	 */
	public List<TrafficVo> findChannelTable(String startDate, String endDate, Integer channelParentId, Integer siteId);
	
	/**
	 * 栏目点击排行图形
	 * 
	 * @param startDate 开始日期 
	 * @param endDate 结束日期
	 * @param channelParentId 父频道编号
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findChannelReport(String startDate, String endDate, Integer channelParentId, Integer labelCount, Integer siteId);
	
	/**
	 * 栏目点击排行之时间趋势图形
	 * 
	 * @param startDate 开始日期 
	 * @param endDate 结束日期
	 * @param channelId 频道编号
	 * @param channelName 频道名称
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findChannelTrendReport(String startDate, String endDate, Integer channelId, Integer labelCount, Integer siteId);
}
