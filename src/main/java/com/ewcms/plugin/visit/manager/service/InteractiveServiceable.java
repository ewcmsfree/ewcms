package com.ewcms.plugin.visit.manager.service;

import java.util.List;

import com.ewcms.web.vo.TreeGridNode;

/**
 * 互动服务统计
 * 
 * @author wu_zhijun
 *
 */
public interface InteractiveServiceable {
	/**
	 * 政民互动统计
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return List TreeGridNode对象集合
	 */
	public List<TreeGridNode> findInteractive(String startDate, String endDate);
	
	/**
	 * 网上咨询统计
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return List TreeGridNode对象集合
	 */
	public List<TreeGridNode> findAdvisory(String startDate, String endDate);
}
