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
			list = visitDAO.findArticle(null, siteId);
		} else{
			List<Integer> channelIds = new ArrayList<Integer>();
			getChannelId(channelIds, channelId);
			channelIds.add(channelId);
			list = visitDAO.findArticle(channelIds, siteId);
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
		
		List<TrafficVo> list = visitDAO.findUrl(start, end, siteId);
		Collections.sort(list, new ClickVoPvDescComparator());
		
		Long sumPv = visitDAO.findPvSum(start, end, siteId);
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
			Long countPv = visitDAO.findPvCountInDayByUrl(DateTimeUtil.getStringToDate(category), url, siteId);
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
		
		List<TrafficVo> list = visitDAO.findChannel(start, end, channelParentId, siteId);
		if (list == null || list.isEmpty()) return new ArrayList<TrafficVo>();
		
		TrafficVo vo = null;
		List<Integer> channelIds = null;
		Long sumPv = 0L;
		for (int i = 0; i < list.size(); i++){
			vo = list.get(i);
			if (vo == null) continue;
			Integer channelId = list.get(i).getChannelId();
			if (channelId == null) continue;
			channelIds = new ArrayList<Integer>();
			getChannelId(channelIds, channelId);
			
			TrafficVo sumVo = visitDAO.findChannelThisLevelAndChildren(start, end, channelId, channelIds, siteId);
			sumPv += sumVo.getPageView();
			vo.setPageView(sumVo.getPageView());
			vo.setStickTime(sumVo.getStickTime());
			if (channelIds == null || channelIds.isEmpty()){
				vo.setIsChannelChildren(false);
			}else{
				vo.setIsChannelChildren(true);
			}
			list.set(i, vo);
		}
		
		if (sumPv == 0) return list;
		
		for (int i = 0; i < list.size(); i++){
			vo = list.get(i);
			vo.setPvRate(NumberUtil.percentage(vo.getPageView(), sumPv));
			list.set(i, vo);
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
			Long countPv = visitDAO.findPvCountInDayByChannelId(DateTimeUtil.getStringToDate(category), channelId, siteId);
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
