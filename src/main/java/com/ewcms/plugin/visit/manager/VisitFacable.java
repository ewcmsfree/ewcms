package com.ewcms.plugin.visit.manager;

import java.util.List;

import com.ewcms.plugin.visit.manager.vo.ClickRateVo;
import com.ewcms.plugin.visit.manager.vo.TrafficVo;
import com.ewcms.plugin.visit.manager.vo.ClientVo;
import com.ewcms.plugin.visit.manager.vo.EntryAndExitVo;
import com.ewcms.plugin.visit.manager.vo.RecentlyVisitedVo;
import com.ewcms.plugin.visit.manager.vo.OnlineVo;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
import com.ewcms.plugin.visit.manager.vo.LoyaltyVo;
import com.ewcms.plugin.visit.manager.vo.VisitorVo;
import com.ewcms.plugin.visit.model.IpRange;
import com.ewcms.plugin.visit.model.Visit;
import com.ewcms.plugin.visit.model.VisitItem;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface VisitFacable {
	
	public void addVisitByLoadEvent(Visit visit, VisitItem visitItem);
	
	public void addVisitByKeepAliveEvent(Visit visit, VisitItem visitItem);
	
	public void addVisitByUnloadEvent(Visit visit, VisitItem visitItem);
	
	public String findFirstDate(Integer siteId);
	
	public IpRange findIpRangeByIp(Long ipBegin, Long ipEnd);
	
	public Integer findDays(Integer siteId);
	
	public List<SummaryVo> findSummaryTable(Integer siteId);

	public String findSummaryReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<SummaryVo> findSiteTable(String startDate, String endDate, Integer siteId);
	
	public List<RecentlyVisitedVo> findLastTable(String startDate, String endDate, Integer siteId);
	
	public List<SummaryVo> findHourTable(String startDate, String endDate, Integer siteId);
	
	public String findHourReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<EntryAndExitVo> findEntranceTable(String startDate, String endDate, Integer siteId);
	
	public String findEmtranceTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<EntryAndExitVo> findExitTable(String startDate, String endDate, Integer siteId);
	
	public String findExitTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<SummaryVo> findHostTable(String startDate, String endDate, Integer siteId);
	
	public String findHostTrendReport(String host, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public String findHostReport(String startDate, String endDate, Integer siteId);
	
	public List<SummaryVo> findCountryTable(String startDate, String endDate, Integer siteId);
	
	public String findCountryReport(String startDate, String endDate, Integer siteId);
	
	public String findCountryTrendReport(String startDate, String endDate, String country, Integer labelCount, Integer siteId);
	
	public String findOnlineReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<OnlineVo> findOnlineTable(String startDate, String endDate, Integer siteId);

	public List<ClientVo> findClientTable(String startDate, String endDate, String fieldName, Integer siteId);
	
	public String findClientReport(String startDate, String endDate,  String fieldName, Integer siteId);
	
	public String findClientTrendReport(String startDate, String endDate, String fieldName, String fieldValue, Integer labelCount, Integer siteId);
	
	public List<ClientVo> findClientBooleanTable(String startDate, String endDate, String fieldName, Integer siteId);
	
	public String findClientBooleanReport(String startDate, String endDate,  String fieldName, Integer siteId);
	
	public String findClientTrendBooleanReport(String startDate, String endDate, String fieldName, Boolean enabled, Integer labelCount, Integer siteId);

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
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findChannelTrendReport(String startDate, String endDate, Integer channelId, Integer labelCount, Integer siteId);
	
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
	 * @return 
	 */
	public List<VisitorVo> findVisitorTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 回头率表格
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
	 * @return List VisitFreqVo对象集合
	 */
	public List<VisitorVo> findStickTimeTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 停留时间图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findStickTimeReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
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
