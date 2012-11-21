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

import com.ewcms.plugin.visit.manager.dao.VisitDAO;
import com.ewcms.plugin.visit.manager.vo.LoyaltyVo;
import com.ewcms.plugin.visit.util.ChartVisitUtil;
import com.ewcms.plugin.visit.util.DateTimeUtil;
import com.ewcms.plugin.visit.util.NumberUtil;

@Service
public class LoyaltyService implements LoyaltyServiceable {

	@Autowired
	private VisitDAO visitDAO;
	
	@Override
	public List<LoyaltyVo> findFrequencyTable(String startDate, String endDate, Integer siteId) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		List<LoyaltyVo> list = visitDAO.findFrequencyInDateInterval(start, end, siteId);
		Long freqSum = visitDAO.findFrequencyCountInDateInterval(start, end, siteId);
		Long greaterThanThirty = 0L;
		List<LoyaltyVo> newVfVo = new ArrayList<LoyaltyVo>();
		for (LoyaltyVo visitFreqVo : list){
			if (visitFreqVo.getFrequency() < 31L){
				visitFreqVo.setRate(NumberUtil.percentage(visitFreqVo.getFreqCount(), freqSum));
				newVfVo.add(visitFreqVo);
			}else{
				greaterThanThirty += visitFreqVo.getFreqCount();
			}
		}
		LoyaltyVo visitFreqVo = new LoyaltyVo();
		visitFreqVo.setFrequency(31L);
		visitFreqVo.setFreqCount(greaterThanThirty);
		visitFreqVo.setRate(NumberUtil.percentage(greaterThanThirty, freqSum));
		newVfVo.add(visitFreqVo);
		
		Collections.sort(newVfVo, new VisitFreqVoPvDescComparator());
		
		return newVfVo;
	}

	@Override
	public String findFrequencyReport(String startDate, String endDate, Integer siteId) {
		List<LoyaltyVo> list = findFrequencyTable(startDate, endDate, siteId);
		Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
		for (LoyaltyVo vo : list){
			if (vo.getFrequency() < 31L){
				dataSet.put(vo.getFrequency() + "次", vo.getFreqCount());
			}else{
				dataSet.put(">30次", vo.getFreqCount());
			}
			
		}
		return ChartVisitUtil.getPie3DChart(dataSet);
	}

	@Override
	public String findFrequencyTrendReport(String startDate, String endDate, Long frequency, Integer labelCount, Integer siteId) {
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		
		Map<String, Map<String, Long>> dataSet = new LinkedHashMap<String, Map<String, Long>>();
		Map<String, Long> dataValueFreq = new LinkedHashMap<String, Long>();
		for (String category : categories){
			Long sumFreq = visitDAO.findFrequencyInDayByFrequency(DateTimeUtil.getStringToDate(category), frequency, siteId);
			dataValueFreq.put(category, sumFreq);
		}
		if (frequency < 31L){
			dataSet.put(frequency + "次", dataValueFreq);
		}else{
			dataSet.put(">30次", dataValueFreq);
		}
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}

	@Override
	public List<LoyaltyVo> findDepthTable(String startDate, String endDate, Integer siteId) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		List<LoyaltyVo> list = visitDAO.findDepthInDateInterval(start, end, siteId);
		Long freqSum = visitDAO.findDepthCountInDateInterval(start, end, siteId);
		Long greaterThanThirty = 0L;
		List<LoyaltyVo> newVfVo = new ArrayList<LoyaltyVo>();
		for (LoyaltyVo loyaltyVo : list){
			if (loyaltyVo.getFrequency() < 31L){
				loyaltyVo.setRate(NumberUtil.percentage(loyaltyVo.getFreqCount(), freqSum));
				newVfVo.add(loyaltyVo);
			}else{
				greaterThanThirty += loyaltyVo.getFreqCount();
			}
		}
		LoyaltyVo visitFreqVo = new LoyaltyVo();
		visitFreqVo.setFrequency(31L);
		visitFreqVo.setFreqCount(greaterThanThirty);
		visitFreqVo.setRate(NumberUtil.percentage(greaterThanThirty, freqSum));
		newVfVo.add(visitFreqVo);
		
		Collections.sort(newVfVo, new VisitFreqVoPvDescComparator());
		
		return newVfVo;
	}

	@Override
	public String findDepthReport(String startDate, String endDate, Integer siteId) {
		List<LoyaltyVo> list = findDepthTable(startDate, endDate, siteId);
		Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
		for (LoyaltyVo vo : list){
			if (vo.getFrequency() < 31L){
				dataSet.put(vo.getFrequency() + "页", vo.getFreqCount());
			}else{
				dataSet.put(">30页", vo.getFreqCount());
			}
			
		}
		return ChartVisitUtil.getPie3DChart(dataSet);
	}

	@Override
	public String findDepthTrendReport(String startDate, String endDate, Long depth, Integer labelCount, Integer siteId) {
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		
		Map<String, Map<String, Long>> dataSet = new LinkedHashMap<String, Map<String, Long>>();
		Map<String, Long> dataValueFreq = new LinkedHashMap<String, Long>();
		for (String category : categories){
			Long sumFreq = visitDAO.findDepthCountInDateByDepth(DateTimeUtil.getStringToDate(category), depth, siteId);
			dataValueFreq.put(category, sumFreq);
		}
		if (depth < 31L){
			dataSet.put(depth + "页", dataValueFreq);
		}else{
			dataSet.put(">30页", dataValueFreq);
		}
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}
	
	class VisitFreqVoPvDescComparator implements Comparator<LoyaltyVo>{
		@Override
		public int compare(LoyaltyVo o1, LoyaltyVo o2) {
			if (o1.getFrequency() < o2.getFrequency()){
				return 1;
			}else{
				if (o1.getFrequency() == o2.getFrequency())
					return 0;
				else
					return -1;
			}
		}
	}

	@Override
	public List<LoyaltyVo> findVisitorTable(String startDate, String endDate, Integer siteId) {
		List<LoyaltyVo> list = new ArrayList<LoyaltyVo>();
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		LoyaltyVo vo = new LoyaltyVo();
		list.add(vo);
		Long nvSum = 0L, rvSum = 0L;
		for (String dateValue : dateArea){
			Date date = DateTimeUtil.getStringToDate(dateValue);
			Long nv = visitDAO.findRvCountInDayByRvFlag(date, false, siteId);
			nvSum += nv;
			Long rv = visitDAO.findRvCountInDayByRvFlag(date, true, siteId);
			rvSum += rv;
			Double rr = 0d;
			if (nv + rv > 0) rr = new Double(NumberUtil.round(rv * 100.0D / (rv + nv), 2));
			vo = new LoyaltyVo(dateValue, nv, rv, rr);
			list.add(vo);
		}
		
		Double rrSum = 0d;
		if (nvSum + rvSum > 0) rrSum = new Double(NumberUtil.round(rvSum * 100.0D / (rvSum + nvSum), 2));
		vo = new LoyaltyVo("总计", nvSum, rvSum, rrSum);
		list.set(0, vo);
		
		return list;
	}

	@Override
	public String findVisitorReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		Map<String, Map<String, String>> dataSet = new LinkedHashMap<String, Map<String, String>>();
		
		Map<String, String> nvMap = new LinkedHashMap<String, String>();
		Map<String, String> rvMap = new LinkedHashMap<String, String>();
		Map<String, String> rrMap = new LinkedHashMap<String, String>();
		List<LoyaltyVo> list = findVisitorTable(startDate, endDate, siteId);
		
		list.remove(0);
		
		for (LoyaltyVo vo : list){
			nvMap.put(vo.getName(), String.valueOf(vo.getNewVisitor()));
			rvMap.put(vo.getName(), String.valueOf(vo.getReturnVisitor()));
			rrMap.put(vo.getName(), String.valueOf(vo.getReturningRage()));
		}
		dataSet.put("新访客", nvMap);
		dataSet.put("回头客", rvMap);
		dataSet.put("回头率", rrMap);
		
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		
		return ChartVisitUtil.getMixed2DChart(categories, dataSet, "回头率", labelCount);
	}

	@Override
	public List<LoyaltyVo> findStickTimeTable(String startDate, String endDate, Integer siteId) {
		List<LoyaltyVo> list = new ArrayList<LoyaltyVo>();
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		LoyaltyVo vo = null;
		for (String dateValue : dateArea){
			Date date = DateTimeUtil.getStringToDate(dateValue);
			Long stSum = visitDAO.findStSumInDay(date, siteId);
			Long stCount = visitDAO.findStCountInDay(date, siteId);
			vo = new LoyaltyVo(dateValue, stSum, (stCount == 0) ? 0L : (stSum / stCount));
			list.add(vo);
		}
		return list;
	}

	@Override
	public String findStickTimeReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		Map<String, Map<String, Long>> dataSet = new LinkedHashMap<String, Map<String, Long>>();
		
		Map<String, Long> stSumMap = new LinkedHashMap<String, Long>();
		Map<String, Long> stAvgMap = new LinkedHashMap<String, Long>();
		List<LoyaltyVo> list = findStickTimeTable(startDate, endDate, siteId);
		
		for (LoyaltyVo vo : list){
			stSumMap.put(vo.getDate(), vo.getStSum());
			stAvgMap.put(vo.getDate(), vo.getStAvg());
		}
		dataSet.put("会话停留", stSumMap);
		dataSet.put("页均停留", stAvgMap);
		
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}
}
