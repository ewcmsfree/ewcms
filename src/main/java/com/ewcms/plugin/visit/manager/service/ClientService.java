package com.ewcms.plugin.visit.manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.visit.manager.dao.VisitDAO;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
import com.ewcms.plugin.visit.util.ChartVisitUtil;
import com.ewcms.plugin.visit.util.DateTimeUtil;
import com.ewcms.plugin.visit.util.NumberUtil;

@Service
public class ClientService implements ClientServiceable {

	@Autowired
	private VisitDAO visitDAO;
	
	@Override
	public List<SummaryVo> findClientTable(String startDate, String endDate, String fieldName, Integer siteId) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		List<String> fieldNames = visitDAO.findClientName(start, end, fieldName, siteId);
		List<SummaryVo> summarys = new ArrayList<SummaryVo>();
		Long sumPv = visitDAO.findPvSumInCountry(start, end, siteId);
		SummaryVo vo = null;
		for (String name : fieldNames){
			vo = new SummaryVo();
			Long countPv = 0L;
			for (String category : categories){
				Date categoryDate = DateTimeUtil.getStringToDate(category);
				countPv += visitDAO.findPvSumInDayByStringField(categoryDate, fieldName, name, siteId).intValue();
			}
			vo.setPv(countPv);
			vo.setPvRate(NumberUtil.percentage(countPv, sumPv));
			vo.setName(name);
			
			summarys.add(vo);
		}
		return summarys;
	}

	@Override
	public String findClientReport(String startDate, String endDate, String fieldName, Integer siteId) {
		List<SummaryVo> list = findClientTable(startDate, endDate, fieldName, siteId);
		Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
		for (SummaryVo vo : list){
			dataSet.put(vo.getName(), vo.getPv().longValue());
		}
		return ChartVisitUtil.getPie3DChart(dataSet);
	}

	@Override
	public String findClientTrendReport(String startDate, String endDate, String fieldName, String fieldValue, Integer labelCount, Integer siteId) {
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		
		Map<String, Map<String, Long>> dataSet = new LinkedHashMap<String, Map<String, Long>>();
		Map<String, Long> dataValuePv = new LinkedHashMap<String, Long>();
		for (String category : categories){
			Long countPv = visitDAO.findPvSumInDayByStringField(DateTimeUtil.getStringToDate(category), fieldName, fieldValue, siteId);
			dataValuePv.put(category, countPv);
		}
		dataSet.put("PV", dataValuePv);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}

	@Override
	public List<SummaryVo> findClientBooleanTable(String startDate, String endDate, String fieldName, Integer siteId) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		boolean[] fieldNames = {true, false};
		List<SummaryVo> summarys = new ArrayList<SummaryVo>();
		Long sumPv = visitDAO.findPvSumInCountry(start, end, siteId);
		SummaryVo vo = null;
		String fieldZhName = "Cookie";
		if (fieldName.equals("javaEnabled")){
			fieldZhName = "Applet";
		}
		for (Boolean name : fieldNames){
			vo = new SummaryVo();
			Long countPv = 0L;
			for (String category : categories){
				Date categoryDate = DateTimeUtil.getStringToDate(category);
				countPv += visitDAO.findPvSumInDayByBooleanField(categoryDate, fieldName, name, siteId).intValue();
			}
			vo.setPv(countPv);
			vo.setPvRate(NumberUtil.percentage(countPv, sumPv));
			
			if (name){
				if (fieldZhName.equals("Cookie")){
					vo.setName("允许Cookie");
				}else{
					vo.setName("支持Applet");
				}
			} else {
				if (fieldZhName.equals("Cookie")){
					vo.setName("不允许Cookie");
				}else{
					vo.setName("不支持Applet");
				}
			}
			
			summarys.add(vo);
		}
		return summarys;
	}

	@Override
	public String findClientBooleanReport(String startDate, String endDate, String fieldName, Integer siteId) {
		List<SummaryVo> list = findClientBooleanTable(startDate, endDate, fieldName, siteId);
		Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
		for (SummaryVo vo : list){
			dataSet.put(vo.getName(), vo.getPv().longValue());
		}
		return ChartVisitUtil.getPie3DChart(dataSet);
	}

	@Override
	public String findClientTrendBooleanReport(String startDate, String endDate, String fieldName, Boolean enabled, Integer labelCount, Integer siteId) {
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		
		Map<String, Map<String, Long>> dataSet = new LinkedHashMap<String, Map<String, Long>>();
		Map<String, Long> dataValuePv = new LinkedHashMap<String, Long>();
		for (String category : categories){
			Long countPv = visitDAO.findPvSumInDayByBooleanField(DateTimeUtil.getStringToDate(category), fieldName, enabled, siteId);
			dataValuePv.put(category, countPv);
		}
		dataSet.put("PV", dataValuePv);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}

}
