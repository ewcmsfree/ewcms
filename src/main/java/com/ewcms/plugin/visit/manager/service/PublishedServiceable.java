package com.ewcms.plugin.visit.manager.service;

import java.util.List;

import com.ewcms.plugin.visit.manager.vo.PublishedVo;
import com.ewcms.web.vo.TreeGridNode;

/**
 * 发布人员统计
 * 
 * @author wu_zhijun
 *
 */
public interface PublishedServiceable {
	/**
	 * 人员发布统计
	 * 
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param siteId 站点编号
	 * @param channelId 频道编号
	 * @return List PublishedVo对象集合
	 */
	public List<PublishedVo> findStaffReleased(String startDate, String endDate, Integer siteId, Integer channelId);
	
	/**
	 * 栏目发布统计
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List TreeGridNode对象集合
	 */
	public List<TreeGridNode> findChannelRelease(String startDate, String endDate, Integer siteId);
}
