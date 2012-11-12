package com.ewcms.plugin.visit.manager.service;

import java.util.List;

import com.ewcms.plugin.visit.manager.vo.SummaryVo;

/**
 * 客户端情况统计
 * 
 * @author wu_zhijun
 *
 */
public interface ClientServiceable {
	/**
	 * 客户端情况统计表格(字段类型为字符型)
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param fieldName 统计字段名称(字段类型为字符型)
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findClientTable(String startDate, String endDate, String fieldName, Integer siteId);
	
	/**
	 * 客户端情况统计图形(字段类型为字符型)
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param fieldName 统计字段名称(字段类型为字符型)
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findClientReport(String startDate, String endDate,  String fieldName, Integer siteId);
	
	/**
	 * 客户端情况统计之时间趋势图形(字段类型为字符型)
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param fieldName 统计字段名称(字段类型为字符型)
	 * @param fieldValue 统计字段值(字段值为字符型)
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findClientTrendReport(String startDate, String endDate, String fieldName, String fieldValue, Integer labelCount, Integer siteId);
	
	/**
	 * 客户端情况统计表格(字段类型为布尔型)
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param fieldName 统计字段名称(字段类型为布尔型)
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findClientBooleanTable(String startDate, String endDate, String fieldName, Integer siteId);
	
	/**
	 * 客户情况统计图形(字段类型为布尔型)
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param fieldName 统计字段名称(字段类型为布尔型)
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findClientBooleanReport(String startDate, String endDate,  String fieldName, Integer siteId);
	
	/**
	 * 客户端情况统计之时间趋势图形(字段类型为布尔型)
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param fieldName 统计字段名称(字段类型为布尔型)
	 * @param fieldValue 统计字段值(字段值为布尔型)
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findClientTrendBooleanReport(String startDate, String endDate, String fieldName, Boolean enabled, Integer labelCount, Integer siteId);

}
