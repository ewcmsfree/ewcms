/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.plugin.visit.manager.dao.VisitDAO;
import com.ewcms.plugin.visit.manager.vo.TrafficVo;
import com.ewcms.plugin.visit.util.ChartVisitUtil;
import com.ewcms.plugin.visit.util.DateTimeUtil;
import com.ewcms.plugin.visit.util.NumberUtil;

@Service
public class TrafficService implements TrafficServiceable {

	@Autowired
	private VisitDAO visitDAO;
	@Autowired
	private ChannelDAO channelDAO;
	
	@Override
	public List<TrafficVo> findArticleTable(Integer channelId, Integer siteId) {
		List<TrafficVo> list = new ArrayList<TrafficVo>();
		if (channelId == 0) {
			list = visitDAO.findArticleByChannelIds(null, siteId);
		} else{
			List<Integer> channelIds = new ArrayList<Integer>();
			getChannelId(channelIds, channelId);
			channelIds.add(channelId);
			list = visitDAO.findArticleByChannelIds(channelIds, siteId);
		}
		Collections.sort(list, new ClickVoPvDescComparator());
		return list;
	}
	
	private void getChannelId(List<Integer> channelIds, Integer parentId){
		List<Channel> channels = channelDAO.getChannelChildren(parentId);
		if (!channels.isEmpty()){
			for (Channel channel : channels){
				channelIds.add(channel.getId());
				getChannelId(channelIds, channel.getId());
			}
		}
	}
	
	@Override
	public List<TrafficVo> findUrlTable(String startDate, String endDate, Integer siteId) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		
		List<TrafficVo> list = visitDAO.findUrlInDateInterval(start, end, siteId);
		Collections.sort(list, new ClickVoPvDescComparator());
		
		Long sumPv = visitDAO.findPvSumInDateInterval(start, end, siteId);
		TrafficVo vo = null;
		for (int i = 0; i < list.size(); i++){
			vo = list.get(i);
			if (vo == null) continue;
			vo.setPvRate(NumberUtil.percentage(vo.getPageView(), sumPv));
			list.set(i, vo);
		}
		return list;
	}
	
	@Override
	public String findUrlTrendReport(String startDate, String endDate, String url, Integer labelCount, Integer siteId) {
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		Map<String, Map<String, Long>> dataSet = new LinkedHashMap<String, Map<String, Long>>();
		Map<String, Long> dataValue = new LinkedHashMap<String, Long>();
		for (String category : categories){
			Long countPv = visitDAO.findPvSumInDayByUrl(DateTimeUtil.getStringToDate(category), url, siteId);
			if (countPv == null) countPv = 0L;
			dataValue.put(category, countPv);
		}
		dataSet.put(url, dataValue);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}
	
	@Override
	public List<TrafficVo> findChannelTable(String startDate, String endDate, Integer channelParentId, Integer siteId) {
		if (channelParentId == null){
			Channel rootChannel = channelDAO.getChannelRoot(siteId);
			if (rootChannel == null) return null;
			channelParentId = rootChannel.getId();
		}
		
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		
		TrafficVo childrenVo = null;
		TrafficVo levelVo = null;
		List<TrafficVo> list = new ArrayList<TrafficVo>();
		List<Channel> channels = channelDAO.getChannelChildren(channelParentId);
		for (Channel channel : channels){
			List<Integer> channelIds = new ArrayList<Integer>();
			getChannelId(channelIds, channel.getId());
			childrenVo = visitDAO.findChannelInDateIntervalByChannelIds(start, end, channelIds, siteId);
			if (childrenVo == null) {
				childrenVo = new TrafficVo();
			}else{
				childrenVo.setIsChannelChildren(true);
			}
			childrenVo.setChannelId(channel.getId());
			childrenVo.setChannelName(channel.getName());
			
			List<Integer> levelChannelIds = new ArrayList<Integer>();
			levelChannelIds.add(channel.getId());
			levelVo = visitDAO.findChannelInDateIntervalByChannelIds(start, end, levelChannelIds, siteId);
			if (levelVo != null) {
				childrenVo.setLevelPv(levelVo.getPageView());
				childrenVo.setLevelSt(levelVo.getStickTime());
			}
			
			list.add(childrenVo);
		}
		
		return list;
	}
	
	@Override
	public String findChannelReport(String startDate, String endDate, Integer channelParentId, Integer labelCount, Integer siteId){
		List<TrafficVo> list = findChannelTable(startDate, endDate, channelParentId, siteId);
		Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
		for (TrafficVo vo : list){
			dataSet.put(vo.getChannelName(), vo.getPageView().longValue());
		}
		return ChartVisitUtil.getPie3DChart(dataSet);
	}
	
	@Override
	public String findChannelTrendReport(String startDate, String endDate, Integer channelId, Integer labelCount, Integer siteId){
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		Map<String, Map<String, Long>> dataSet = new LinkedHashMap<String, Map<String, Long>>();
		Map<String, Long> dataValue = new LinkedHashMap<String, Long>();
		for (String category : categories){
			Long countPv = visitDAO.findPvSumInDayByChannelId(DateTimeUtil.getStringToDate(category), channelId, siteId);
			if (countPv == null) countPv = 0L;
			dataValue.put(category, countPv);
		}
		dataSet.put("PV", dataValue);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}
	
	class ClickVoPvDescComparator implements Comparator<TrafficVo>{
		@Override
		public int compare(TrafficVo o1, TrafficVo o2) {
			if (o1.getPageView() < o2.getPageView()){
				return 1;
			}else{
				if (o1.getPageView() == o2.getPageView())
					return 0;
				else
					return -1;
			}
		}
	}
}
