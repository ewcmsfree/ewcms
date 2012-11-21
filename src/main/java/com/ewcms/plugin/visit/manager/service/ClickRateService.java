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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.plugin.visit.manager.dao.VisitDAO;
import com.ewcms.plugin.visit.manager.vo.ClickRateVo;
import com.ewcms.plugin.visit.util.ChartVisitUtil;
import com.ewcms.plugin.visit.util.DateTimeUtil;
import com.ewcms.plugin.visit.util.NumberUtil;
import com.ewcms.plugin.visit.util.SourceUtil;

@Service
public class ClickRateService implements ClickRateServiceable {

	@Autowired
	private VisitDAO visitDAO;
	
	@Override
	public List<ClickRateVo> findSourceTable(String startDate, String endDate,
			Integer siteId) {
		List<ClickRateVo> list = new ArrayList<ClickRateVo>();
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		ClickRateVo vo = null;
		list.add(vo);
		Long dSum = 0L, sSum = 0L, oSum = 0L;
		for (String dateValue : dateArea){
			Date date = DateTimeUtil.getStringToDate(dateValue);
			List<ClickRateVo> sources = visitDAO.findSourceInDay(date, siteId);
			Long dCount = 0L, sCount = 0L, oCount = 0L;
			for (ClickRateVo sourceVo : sources){
				String referer = sourceVo.getReferer();
				if (EmptyUtil.isStringEmpty(referer)){
					dCount += sourceVo.getRefererCount();
					dSum += sourceVo.getRefererCount();
				}else if (EmptyUtil.isNotNull(SourceUtil.getDomainName(referer))){
					sCount += sourceVo.getRefererCount();
					sSum += sourceVo.getRefererCount();
				}else{
					oCount += sourceVo.getRefererCount();
					oSum += sourceVo.getRefererCount();
				}
			}
			vo = new ClickRateVo(dateValue, dCount, sCount, oCount);
			list.add(vo);
		}
		vo = new ClickRateVo("总计", dSum, sSum, oSum);
		list.set(0, vo);
		
		return list;
	}

	@Override
	public String findSourceReport(String startDate, String endDate, Integer siteId) {
		List<ClickRateVo> list = findSourceTable(startDate, endDate, siteId);
		ClickRateVo vo = list.get(0);
		
		Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
		dataSet.put("搜索引擎", vo.getSearchCount());
		dataSet.put("直接输入", vo.getDirectCount());
		dataSet.put("其他网站", vo.getOtherCount());

		return ChartVisitUtil.getPie3DChart(dataSet);
	}

	@Override
	public List<ClickRateVo> findSearchTable(String startDate, String endDate,
			Integer siteId) {
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		Map<String, Long> searchMap = new LinkedHashMap<String, Long>();
		Long sSum = 0L;
		for (String dateValue : dateArea){
			Date date = DateTimeUtil.getStringToDate(dateValue);
			List<ClickRateVo> sources = visitDAO.findSourceInDay(date, siteId);
			for (ClickRateVo sourceVo : sources){
				String referer = sourceVo.getReferer();
				
				if (EmptyUtil.isStringEmpty(referer)) continue;
				String domain = SourceUtil.getDomain(referer);
				if (domain == null) continue;
				String domainName = SourceUtil.getDomainName(referer);
				if (domainName == null) continue;
				
				if (searchMap.containsKey(domain)){
					searchMap.put(domain,searchMap.get(domain) + 1);
				}else{
					searchMap.put(domain, 1L);
				}
				
				sSum += 1;
			}
		}
		
		List<ClickRateVo> list = new ArrayList<ClickRateVo>();
		Iterator<Entry<String, Long>> it = searchMap.entrySet().iterator();
		ClickRateVo vo = null;
		while (it.hasNext()){
			Map.Entry<String, Long> entry = it.next();
			String domain = entry.getKey();
			String domainName = SourceUtil.searchEngineMap.get(domain);
			Long domainCount = entry.getValue();
			String searchRate = NumberUtil.percentage(domainCount, sSum);
			vo = new ClickRateVo(domain, domainName, domainCount, searchRate);
			list.add(vo);
		}
		
		Collections.sort(list, new SearchUvDescComparator());
		
		return list;
	}

	@Override
	public String findSearchReport(String startDate, String endDate, Integer siteId) {
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		Map<String, Long> searchMap = new LinkedHashMap<String, Long>();
		for (String dateValue : dateArea){
			Date date = DateTimeUtil.getStringToDate(dateValue);
			List<ClickRateVo> sources = visitDAO.findSourceInDay(date, siteId);
			for (ClickRateVo sourceVo : sources){
				String referer = sourceVo.getReferer();
				
				if (EmptyUtil.isStringEmpty(referer)) continue;
				String domain = SourceUtil.getDomain(referer);
				if (domain == null) continue;
				String domainName = SourceUtil.getDomainName(referer);
				if (domainName == null) continue;
				
				if (searchMap.containsKey(domainName)){
					searchMap.put(domainName,searchMap.get(domainName) + 1);
				}else{
					searchMap.put(domainName, 1L);
				}
			}
		}
		
		return ChartVisitUtil.getPie3DChart(searchMap);
	}

	@Override
	public String findSearchTrendReport(String startDate, String endDate, String domain, Integer labelCount, Integer siteId) {
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		
		Map<String, Map<String, Long>> dataSet = new LinkedHashMap<String, Map<String, Long>>();
		Map<String, Long> dataValueUv = new LinkedHashMap<String, Long>();
		for (String category : categories){
			Long countPv = visitDAO.findUvCountInDayByDomain(DateTimeUtil.getStringToDate(category), domain, siteId);
			dataValueUv.put(category, countPv);
		}
		dataSet.put("UV", dataValueUv);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}

	@Override
	public List<ClickRateVo> findWebSiteTable(String startDate, String endDate,
			Integer siteId) {
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		Map<String, Long> searchMap = new LinkedHashMap<String, Long>();
		Long wsSum = 0L;
		for (String dateValue : dateArea){
			Date date = DateTimeUtil.getStringToDate(dateValue);
			List<ClickRateVo> sources = visitDAO.findSourceInDay(date, siteId);
			for (ClickRateVo sourceVo : sources){
				String referer = sourceVo.getReferer();
				
				if (EmptyUtil.isStringEmpty(referer)) continue;
				String webSite = SourceUtil.getWebSiteUrl(referer);
				if (webSite == null) continue;
				
				if (searchMap.containsKey(webSite)){
					searchMap.put(webSite,searchMap.get(webSite) + 1);
				}else{
					searchMap.put(webSite, 1L);
				}
				
				wsSum += 1;
			}
		}
		
		List<ClickRateVo> list = new ArrayList<ClickRateVo>();
		Iterator<Entry<String, Long>> it = searchMap.entrySet().iterator();
		ClickRateVo vo = null;
		while (it.hasNext()){
			Map.Entry<String, Long> entry = it.next();
			String webSite = entry.getKey();
			Long webSiteCount = entry.getValue();
			String searchRate = NumberUtil.percentage(webSiteCount, wsSum);
			vo = new ClickRateVo(webSite, webSiteCount, searchRate);
			list.add(vo);
		}
		
		Collections.sort(list, new WebSiteUvDescComparator());
		
		return list;
	}

	@Override
	public String findWebSiteReport(String startDate, String endDate, Integer siteId) {
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		Map<String, Long> searchMap = new LinkedHashMap<String, Long>();
		for (String dateValue : dateArea){
			Date date = DateTimeUtil.getStringToDate(dateValue);
			List<ClickRateVo> sources = visitDAO.findSourceInDay(date, siteId);
			for (ClickRateVo sourceVo : sources){
				String referer = sourceVo.getReferer();
				
				if (EmptyUtil.isStringEmpty(referer)) continue;
				String webSite = SourceUtil.getWebSiteUrl(referer);
				if (webSite == null) continue;
				
				if (searchMap.containsKey(webSite)){
					searchMap.put(webSite,searchMap.get(webSite) + 1);
				}else{
					searchMap.put(webSite, 1L);
				}
			}
		}
		
		return ChartVisitUtil.getPie3DChart(searchMap);
	}

	@Override
	public String findWebSiteTrendReport(String startDate, String endDate, String webSite, Integer labelCount, Integer siteId) {
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		
		Map<String, Map<String, Long>> dataSet = new LinkedHashMap<String, Map<String, Long>>();
		Map<String, Long> dataValueUv = new LinkedHashMap<String, Long>();
		for (String category : categories){
			Long countPv = visitDAO.findUvCountInDayByWebSite(DateTimeUtil.getStringToDate(category), webSite, siteId);
			dataValueUv.put(category, countPv);
		}
		dataSet.put("UV", dataValueUv);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}
	
	class SearchUvDescComparator implements Comparator<ClickRateVo>{
		@Override
		public int compare(ClickRateVo o1, ClickRateVo o2) {
			if (o1.getDomainCount() < o2.getDomainCount()){
				return 1;
			}else{
				if (o1.getDomainCount() == o2.getDomainCount())
					return 0;
				else
					return -1;
			}
		}
	}
	
	class WebSiteUvDescComparator implements Comparator<ClickRateVo>{
		@Override
		public int compare(ClickRateVo o1, ClickRateVo o2) {
			if (o1.getWebSiteCount() < o2.getWebSiteCount()){
				return 1;
			}else{
				if (o1.getWebSiteCount() == o2.getWebSiteCount())
					return 0;
				else
					return -1;
			}
		}
	}
}
