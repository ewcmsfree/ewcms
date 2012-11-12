package com.ewcms.plugin.visit.manager.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.plugin.visit.manager.dao.VisitDAO;
import com.ewcms.plugin.visit.manager.dao.VisitItemDAO;
import com.ewcms.plugin.visit.manager.vo.ArticleVisitVo;
import com.ewcms.plugin.visit.manager.vo.InAndExitVo;
import com.ewcms.plugin.visit.manager.vo.LastVisitVo;
import com.ewcms.plugin.visit.manager.vo.OnlineVo;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
import com.ewcms.plugin.visit.model.Visit;
import com.ewcms.plugin.visit.model.VisitItem;
import com.ewcms.plugin.visit.util.ChartVisitUtil;
import com.ewcms.plugin.visit.util.DateTimeUtil;
import com.ewcms.plugin.visit.util.NumberUtil;
import com.ewcms.plugin.visit.util.VisitUtil;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class VisitService implements VisitServiceable {

	@Autowired
	private VisitDAO visitDAO;
	@Autowired
	private VisitItemDAO visitItemDAO;

	@Override
	public void addVisitByLoadEvent(Visit visit, VisitItem visitItem) {
		Visit dbVisit = findVisitEntity(visit);
		if (dbVisit == null) {
			visitDAO.persist(visit);
			visitItem.setUniqueId(visit.getUniqueId());
			visitItem.setPageView(1L);
			visitItemDAO.persist(visitItem);
		} else {
			VisitItem dbVisitItem = visitItemDAO.findVisitItemByVisitItemPK(dbVisit.getUniqueId(), visitItem.getSiteId(), visitItem.getChannelId(), visitItem.getArticleId(), visitItem.getUrl());
			if (dbVisitItem == null){
				visitItem.setUniqueId(dbVisit.getUniqueId());
				visitItem.setPageView(1L);
				visitItemDAO.persist(visitItem);
			}else{
				dbVisitItem.setPageView(dbVisitItem.getPageView() + 1);
				visitItemDAO.merge(dbVisitItem);
			}
			dbVisit.setRvFlag(visit.getRvFlag());
			visitDAO.merge(dbVisit);
		}
	}

	@Override
	public void addVisitByKeepAliveEvent(Visit visit, VisitItem visitItem) {
		Visit dbVisit = findVisitEntity(visit);
		if (dbVisit != null){
			VisitItem dbVisitItem = visitItemDAO.findVisitItemByVisitItemPK(dbVisit.getUniqueId(), visitItem.getSiteId(), visitItem.getChannelId(), visitItem.getArticleId(), visitItem.getUrl());
			if (dbVisitItem != null){
				dbVisitItem.setStickTime(visitItem.getStickTime() + dbVisitItem.getStickTime());
				visitItemDAO.merge(dbVisitItem);
			}
		}
	}
	
	@Override
	public void addVisitByUnloadEvent(Visit visit, VisitItem visitItem){
		VisitItem dbVisitItem = visitItemDAO.findVisitItemByVisitItemPK(visit.getUniqueId(), visitItem.getSiteId(), visitItem.getChannelId(), visitItem.getArticleId(), visitItem.getUrl());
		if (dbVisitItem != null){
			dbVisitItem.setEvent(VisitUtil.UNLOAD_EVENT);
			visitItemDAO.merge(dbVisitItem);
		}
	}

	private Visit findVisitEntity(Visit visit) {
		String uniqueId = visit.getUniqueId();
		Date addDate = visit.getAddDate();
		String ip = visit.getIp();
		if (EmptyUtil.isStringNotEmpty(uniqueId) && EmptyUtil.isNotNull(addDate) && EmptyUtil.isNotNull(ip))
			return visitDAO.findVisitByVisitPK(uniqueId, addDate, ip);
		else
			return null;
	}

	@Override
	public String findVisitSummaryReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		Map<String, Map<String, Integer>> dataSet = new LinkedHashMap<String, Map<String, Integer>>();
		Map<String, Integer> mapIP = findVisitSummaryIp(categories, siteId);
		dataSet.put("IP", mapIP);
		Map<String, Integer> mapUV = findVisitSummaryUv(categories, siteId);
		dataSet.put("UV", mapUV);
		Map<String, Integer> mapPV = findVisitSummaryPv(categories, siteId);
		dataSet.put("PV", mapPV);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}
	
	@Override
	public String findHourVisitReport(String startDate, String endDate, Integer labelCount, Integer siteId){
		List<String> categories = DateTimeUtil.getTimeArea();
		List<SummaryVo> summaryVos = new ArrayList<SummaryVo>();
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		SummaryVo vo = null;
		for (int i = 0; i <= 23; i++){
			String start = "" + i + ":00";
			String end = "" + i + ":59";
			vo = new SummaryVo();
			vo.setName(String.format("%02d", i));
			Integer countIp = 0, countUv = 0, countPv = 0, countRv = 0;
			for (String dateValue : dateArea){
				Date date = DateTimeUtil.getStringToDate(dateValue);
				Date startTime = DateTimeUtil.getStringToTime(start);
				Date endTime = DateTimeUtil.getStringToTime(end);
				
				countIp += visitDAO.findHourVisitCountIp(date, startTime, endTime, siteId);
				countUv += visitDAO.findHourVisitCountUv(date, startTime, endTime, siteId);
				countPv += visitDAO.findHourVisitCountPv(date, startTime, endTime, siteId);
				countRv += visitDAO.findHourVisitCountRv(date, startTime, endTime, siteId);
			}
			vo.setIp(countIp);
			vo.setUv(countUv);
			vo.setPv(countPv);
			vo.setRv(countRv);
			summaryVos.add(vo);
			
		}
		
		Map<String, Map<String, Integer>> dataSet = new LinkedHashMap<String, Map<String, Integer>>();
		
		Map<String, Integer> mapIp = new LinkedHashMap<String, Integer>();
		Map<String, Integer> mapUv = new LinkedHashMap<String, Integer>();
		Map<String, Integer> mapPv = new LinkedHashMap<String, Integer>();
		for (SummaryVo summaryVo : summaryVos){
			mapIp.put(summaryVo.getName(), summaryVo.getIp());
			mapUv.put(summaryVo.getName(), summaryVo.getUv());
			mapPv.put(summaryVo.getName(), summaryVo.getPv());
		}
		
		dataSet.put("IP", mapIp);
		dataSet.put("UV", mapUv);
		dataSet.put("PV", mapPv);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}
	
	private Map<String, Integer> findVisitSummaryIp(List<String> categories, Integer siteId){
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (String category : categories){
			Date date = DateTimeUtil.getStringToDate(category);
			Integer value = visitDAO.findVisitCountIp(date, siteId);
			result.put(category, value);
		}
		return result;
	}
	
	private Map<String, Integer> findVisitSummaryPv(List<String> categories, Integer siteId){
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (String category : categories){
			Date date = DateTimeUtil.getStringToDate(category);
			Integer value = visitDAO.findVisitCountPv(date, siteId);
			result.put(category, value);
		}
		return result;
	}

	private Map<String, Integer> findVisitSummaryUv(List<String> categories, Integer siteId){
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (String category : categories){
			Date date = DateTimeUtil.getStringToDate(category);
			Integer value = visitDAO.findVisitCountUv(date, siteId);
			result.put(category, value);
		}
		return result;
	}
	
	@Override
	public List<SummaryVo> findHourVisitTable(String startDate, String endDate, Integer siteId){
		List<SummaryVo> list = new ArrayList<SummaryVo>();
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		
		list.add(null);
		
		SummaryVo vo = null;
		Integer sumIp = 0, sumUv = 0, sumPv = 0, sumRv = 0;
		for (int i = 0; i <= 23; i++){
			String start = "" + i + ":00";
			String end = "" + i + ":59";
			vo = new SummaryVo();
			vo.setName(start + " —— " + end);
			Integer countIp = 0, countUv = 0, countPv = 0, countRv = 0;
			for (String dateValue : dateArea){
				Date date = DateTimeUtil.getStringToDate(dateValue);
				Date startTime = DateTimeUtil.getStringToTime(start);
				Date endTime = DateTimeUtil.getStringToTime(end);
				
				countIp += visitDAO.findHourVisitCountIp(date, startTime, endTime, siteId);
				countUv += visitDAO.findHourVisitCountUv(date, startTime, endTime, siteId);
				countPv += visitDAO.findHourVisitCountPv(date, startTime, endTime, siteId);
				countRv += visitDAO.findHourVisitCountRv(date, startTime, endTime, siteId);
			}
			vo.setIp(countIp);
			vo.setUv(countUv);
			vo.setPv(countPv);
			vo.setRv(countRv);
			list.add(vo);
			
			sumIp += countIp;
			sumUv += countUv;
			sumPv += countPv;
			sumRv += countRv;
		}
		
		SummaryVo sumVo = new SummaryVo();
		sumVo.setName("总计");
		sumVo.setIp(sumIp);
		sumVo.setUv(sumUv);
		sumVo.setPv(sumPv);
		sumVo.setRv(sumRv);
		sumVo.setPvRate("100.00%");
		list.set(0, sumVo);
		
		for(int i = 1; i < list.size(); i++){
			vo = list.get(i);
			if (vo == null) continue;
			vo.setPvRate(NumberUtil.percentage(vo.getPv(), sumRv));
			list.set(i, vo);
		}
		
		return list;
	}
	
	@Override
	public List<SummaryVo> findVisitSummaryTable(Integer siteId){
		List<SummaryVo> list = new ArrayList<SummaryVo>();
		
		Date current = new Date(Calendar.getInstance().getTime().getTime());
		
		SummaryVo currentVo = assign("今日", siteId, current);
		list.add(currentVo);
		
		Date tomorrow = DateTimeUtil.getPreviousDay(current);
		SummaryVo tomorrowVo = assign("昨天", siteId, tomorrow);
		list.add(tomorrowVo);
		
		List<String> weekList = DateTimeUtil.getThisWeek();
		SummaryVo weekVo = assign("本周", siteId, weekList);
		list.add(weekVo);
		
		List<String> monthMap = DateTimeUtil.getThisMonth();
		SummaryVo monthVo = assign("本月", siteId, monthMap);
		list.add(monthVo);
		
		SummaryVo avgVo = new SummaryVo();
		avgVo.setName("平均");
		String startDate = visitDAO.findVisitFirstAddDate(siteId);
		if (startDate != ""){
			String endDate = DateTimeUtil.getDateToString(current);
			List<String> allMap = DateTimeUtil.getDateArea(startDate, endDate);
			SummaryVo allVo = assign("全部", siteId, allMap);
			list.add(allVo);
			
			Integer day = endDate.compareTo(startDate) + 1;
			
			avgVo.setIp(allVo.getIp()/day);
			avgVo.setPv(allVo.getPv()/day);
			avgVo.setUv(allVo.getUv()/day);
			avgVo.setAvgTime("");
			avgVo.setRvRate("");
		}
		list.add(avgVo);
	
		return list;
	}
	
	@Override
	public List<SummaryVo> findVisitSiteTable(String startDate, String endDate, Integer siteId){
		List<SummaryVo> list = new ArrayList<SummaryVo>();
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		SummaryVo vo = null;
		for (String dateValue : dateArea){
			Date date = DateTimeUtil.getStringToDate(dateValue);
			vo = assign(dateValue, siteId, date);
			list.add(vo);
		}
		return list;
	}
	
	private SummaryVo assign(String name, Integer siteId, Date date){
		SummaryVo vo = new SummaryVo();
		vo.setName(name);
		Integer countIp = visitDAO.findVisitCountIp(date, siteId);
		vo.setIp(countIp);
		Integer countUv = visitDAO.findVisitCountUv(date, siteId);
		vo.setUv(countUv);
		Integer countPv = visitDAO.findVisitCountPv(date, siteId);
		vo.setPv(countPv);
		Integer countSt = visitDAO.findVisitItemCountSt(date, siteId);
		Integer sumSt = visitDAO.findVisitItemSumSt(date, siteId);
		Integer avgTime = (countSt == 0 ? 0 : sumSt/countSt);
		vo.setAvgTime(Integer.toString(avgTime));
		Integer countRv = visitDAO.findVisitCountRv(date, siteId);
		vo.setRv(countRv);
		Long count = visitDAO.findVisitCount(date, siteId);
		vo.setRvRate(NumberUtil.percentage(countRv, count.intValue()));
		return vo;
	}
	
	private SummaryVo assign(String name, Integer siteId, List<String> dateArea){
		SummaryVo vo = new SummaryVo();
		vo.setName(name);
		Integer countIp = 0;
		Integer countUv = 0;
		Integer countPv = 0;
		Integer avgTime = 0;
		Integer countRv = 0;
		Long count = 0L;
		for(String dateValue : dateArea){
			Date date = DateTimeUtil.getStringToDate(dateValue);
			countIp += visitDAO.findVisitCountIp(date, siteId);
			countUv += visitDAO.findVisitCountUv(date, siteId);
			countPv += visitDAO.findVisitCountPv(date, siteId);
			Integer sumSt = visitDAO.findVisitItemSumSt(date, siteId);
			Integer countSt = visitDAO.findVisitItemCountSt(date, siteId);
			avgTime = avgTime + (countSt == 0 ? 0 : sumSt / countSt);
			countRv += visitDAO.findVisitCountRv(date, siteId);
			count += visitDAO.findVisitCount(date, siteId);
		}
		vo.setIp(countIp);
		vo.setPv(countPv);
		vo.setUv(countUv);
		vo.setAvgTime(Integer.toString(avgTime));
		vo.setRv(countRv);
		vo.setRvRate(NumberUtil.percentage(countRv, count.intValue()));
		
		return vo;
	}
	
	@Override
	public String findVisitFirstAddDate(Integer siteId) {
		return visitDAO.findVisitFirstAddDate(siteId);
	}

	@Override
	public Integer findVisitDay(Integer siteId) {
		String firstAddDate = visitDAO.findVisitFirstAddDate(siteId);
		String lastAddDate = DateTimeUtil.getDateToString(DateTimeUtil.getCurrent());
		
		return lastAddDate.compareTo(firstAddDate) + 1;
	}

	@Override
	public List<LastVisitVo> findLastVisit(String startDate, String endDate, Integer rows, Integer siteId) {
		return visitDAO.findLastVisit(DateTimeUtil.getStringToDate(startDate), DateTimeUtil.getStringToDate(endDate), rows, siteId);
	}
	
	@Override
	public List<InAndExitVo> findEntranceVisit(String startDate, String endDate, Integer rows, Integer siteId){
		List<InAndExitVo>  list = visitDAO.findEntranceVisit(DateTimeUtil.getStringToDate(startDate), DateTimeUtil.getStringToDate(endDate), rows, siteId);
		Long sum = visitDAO.findEntranceVisitSum(DateTimeUtil.getStringToDate(startDate), DateTimeUtil.getStringToDate(endDate), rows, siteId);
		return conversion(list, sum);
	}

	@Override
	public String findEmtranceVisitCountPv(String url, String startDate, String endDate, Integer labelCount, Integer siteId){
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		Map<String, Map<String, Integer>> dataSet = new LinkedHashMap<String, Map<String, Integer>>();
		Map<String, Integer> dataValue = new LinkedHashMap<String, Integer>();
		for (String category : categories){
			Integer countPv = visitDAO.findEmtranceVisitCountPv(DateTimeUtil.getStringToDate(category), url, siteId);
			dataValue.put(category, countPv);
		}
		dataSet.put(url, dataValue);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}
	
	@Override
	public List<InAndExitVo> findExitVisit(String startDate, String endDate, Integer rows, Integer siteId) {
		List<InAndExitVo> list = visitDAO.findExitVisit(DateTimeUtil.getStringToDate(startDate), DateTimeUtil.getStringToDate(endDate), rows, siteId);
		Long sum = visitDAO.findExitVisitSum(DateTimeUtil.getStringToDate(startDate), DateTimeUtil.getStringToDate(endDate), rows, siteId);
		return conversion(list, sum);
	}
	
	public String findExitVisitCountPv(String url, String startDate, String endDate, Integer labelCount, Integer siteId){
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		Map<String, Map<String, Integer>> dataSet = new LinkedHashMap<String, Map<String, Integer>>();
		Map<String, Integer> dataValue = new LinkedHashMap<String, Integer>();
		for (String category : categories){
			Integer countPv = visitDAO.findExitVisitCountPv(DateTimeUtil.getStringToDate(category), url, siteId);
			dataValue.put(category, countPv);
		}
		dataSet.put(url, dataValue);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}

	private List<InAndExitVo> conversion(List<InAndExitVo> list, Long sum){
		if (list == null) return new ArrayList<InAndExitVo>(0);
		if (sum == 0) return list;
		
		InAndExitVo vo = null;
		for (int i = 0; i < list.size(); i++){
			vo = list.get(i);
			vo.setRate(NumberUtil.percentage(vo.getCount().intValue(), sum.intValue()));
			list.set(i, vo);
		}
		return list;
	}
	
	@Override
	public List<SummaryVo> findHostVisit(String startDate, String endDate, Integer rows, Integer siteId) {
		List<SummaryVo> list = visitDAO.findHostVisit(DateTimeUtil.getStringToDate(startDate), DateTimeUtil.getStringToDate(endDate), rows, siteId);
		Long sum = visitDAO.findHostVisitSum(DateTimeUtil.getStringToDate(startDate), DateTimeUtil.getStringToDate(endDate), rows, siteId);
		if (list == null) return new ArrayList<SummaryVo>(0);
		if (sum == 0) return list;
		SummaryVo vo = null;
		for (int i = 0; i < list.size(); i++){
			vo = list.get(i);
			vo.setPvRate(NumberUtil.percentage(vo.getSumPv().intValue(), sum.intValue()));
			list.set(i, vo);
		}
		return list;
	}
	
	@Override
	public String findHostVisitCountPv(String host, String startDate, String endDate, Integer labelCount, Integer siteId){
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		Map<String, Map<String, Integer>> dataSet = new LinkedHashMap<String, Map<String, Integer>>();
		Map<String, Integer> dataValue = new LinkedHashMap<String, Integer>();
		for (String category : categories){
			Integer countPv = visitDAO.findHostVisitCountPv(DateTimeUtil.getStringToDate(category), host, siteId);
			dataValue.put(category, countPv);
		}
		dataSet.put(host, dataValue);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}

	@Override
	public String findHostVisitReport(String startDate, String endDate, Integer rows, Integer siteId){
		List<SummaryVo> list = visitDAO.findHostVisit(DateTimeUtil.getStringToDate(startDate), DateTimeUtil.getStringToDate(endDate), rows, siteId);
		Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
		for (SummaryVo vo : list){
			dataSet.put(vo.getName(), vo.getSumPv());
		}
		return ChartVisitUtil.getPie3DChart(dataSet);
	}
	
	@Override
	public List<SummaryVo> findCountryVisitTable(String startDate, String endDate, Integer siteId){
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		List<String> countries = visitDAO.findCountryVisitName(start, end, siteId);
		List<SummaryVo> summarys = new ArrayList<SummaryVo>();
		Long sumPv = visitDAO.findCountryVisitPvSum(start, end, siteId);
		SummaryVo vo = null;
		for (String country : countries){
			vo = new SummaryVo();
			Integer countPv = 0, countUv = 0, countIp = 0;
			for (String category : categories){
				Date categoryDate = DateTimeUtil.getStringToDate(category);
				countPv += visitDAO.findCountryVisitPv(categoryDate, country, siteId).intValue();
				countUv += visitDAO.findCountryVisitUv(categoryDate, country, siteId);
				countIp += visitDAO.findCountryVisitIp(categoryDate, country, siteId);
			}
			vo.setIp(countIp);
			vo.setUv(countUv);
			vo.setPv(countPv);
			vo.setPvRate(NumberUtil.percentage(countPv.intValue(), sumPv.intValue()));
			vo.setName(country);
			
			summarys.add(vo);
		}
		return summarys;
	}
	
	@Override
	public String findCountryVisitReport(String startDate, String endDate, Integer siteId){
		List<SummaryVo> list = findCountryVisitTable(startDate, endDate, siteId);
		Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
		for (SummaryVo vo : list){
			dataSet.put(vo.getName(), vo.getPv().longValue());
		}
		return ChartVisitUtil.getPie3DChart(dataSet);
	}
	
	@Override
	public String findCountryTrendVisitReport(String startDate, String endDate, String country, Integer labelCount, Integer siteId){
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		
		Map<String, Map<String, Integer>> dataSet = new LinkedHashMap<String, Map<String, Integer>>();
		Map<String, Integer> dataValuePv = new LinkedHashMap<String, Integer>();
		Map<String, Integer> dataValueUv = new LinkedHashMap<String, Integer>();
		Map<String, Integer> dataValueIp = new LinkedHashMap<String, Integer>();
		for (String category : categories){
			Integer countPv = visitDAO.findCountryVisitPv(DateTimeUtil.getStringToDate(category), country, siteId).intValue();
			dataValuePv.put(category, countPv);
			Integer countUv = visitDAO.findCountryVisitUv(DateTimeUtil.getStringToDate(category), country, siteId);
			dataValueUv.put(category, countUv);
			Integer countIp = visitDAO.findCountryVisitIp(DateTimeUtil.getStringToDate(category), country, siteId);
			dataValueIp.put(category, countIp);
		}
		dataSet.put("PV", dataValuePv);
		dataSet.put("UV", dataValueUv);
		dataSet.put("IP", dataValueIp);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}
	
	@Override
	public String findOnlineVisitReport(String startDate, String endDate, Integer labelCount, Integer siteId){
		List<String> categories = DateTimeUtil.getTimeArea();
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		
		Map<String, Map<String, Integer>> dataSet = new LinkedHashMap<String, Map<String, Integer>>();
		
		Map<String, Integer> mapFive = new LinkedHashMap<String, Integer>();
		Map<String, Integer> mapTen = new LinkedHashMap<String, Integer>();
		Map<String, Integer> mapFifteen = new LinkedHashMap<String, Integer>();
		
		for (int i = 0; i <= 23; i++){
			String start = "" + i + ":00";
			String end = "" + i + ":59";
			String hour = String.format("%02d", i);
			Integer countFive = 0, countTen = 0, countFifteen = 0;
			for (String dateValue : dateArea){
				Date date = DateTimeUtil.getStringToDate(dateValue);
				Date startTime = DateTimeUtil.getStringToTime(start);
				Date endTime = DateTimeUtil.getStringToTime(end);
				
				List<Long> stickTimes = visitDAO.findOnlineVisit(date, startTime, endTime, siteId);
				for (Long stickTime : stickTimes){
					if (stickTime <= 5*60)	countFive = countFive + 1;
					else if (stickTime > 5*60 && stickTime <= 10*60) countTen = countTen + 1;
					else countFifteen = countFifteen + 1;
				}
			}
			mapFive.put(hour, countFive);
			mapTen.put(hour, countTen);
			mapFifteen.put(hour, countFifteen);
		}
		dataSet.put("5分钟", mapFive);
		dataSet.put("10分钟", mapTen);
		dataSet.put("15分钟", mapFifteen);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}
	
	@Override
	public List<OnlineVo> findOnlineVisitTable(String startDate, String endDate, Integer siteId){
		List<OnlineVo> list = new ArrayList<OnlineVo>();
		List<String> dateArea = DateTimeUtil.getDateArea(startDate, endDate);
		
		OnlineVo vo = null;
		for (int i = 0; i <= 23; i++){
			String start = "" + i + ":00";
			String end = "" + i + ":59";
			vo = new OnlineVo();
			vo.setName(start + " —— " + end);
			Integer countFive = 0, countTen = 0, countFifteen = 0;
			for (String dateValue : dateArea){
				Date date = DateTimeUtil.getStringToDate(dateValue);
				Date startTime = DateTimeUtil.getStringToTime(start);
				Date endTime = DateTimeUtil.getStringToTime(end);
				
				List<Long> stickTimes = visitDAO.findOnlineVisit(date, startTime, endTime, siteId);
				for (Long stickTime : stickTimes){
					if (stickTime <= 5*60)	countFive = countFive + 1;
					else if (stickTime > 5*60 && stickTime <= 10*60) countTen = countTen + 1;
					else countFifteen = countFifteen + 1;
				}
			}
			vo.setFive(countFive);
			vo.setTen(countTen);
			vo.setFifteen(countFifteen);
			list.add(vo);
		}
		
		return list;
	}

	@Override
	public List<ArticleVisitVo> findArticleVisit(Integer rows, Integer siteId) {
		return visitDAO.findArticleVisit(rows, siteId);
	}

	@Override
	public List<SummaryVo> findClientVisitTable(String startDate, String endDate, String fieldName, Integer siteId) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		List<String> fieldNames = visitDAO.findClientVisitName(start, end, fieldName, siteId);
		List<SummaryVo> summarys = new ArrayList<SummaryVo>();
		Long sumPv = visitDAO.findCountryVisitPvSum(start, end, siteId);
		SummaryVo vo = null;
		for (String name : fieldNames){
			vo = new SummaryVo();
			Integer countPv = 0;
			for (String category : categories){
				Date categoryDate = DateTimeUtil.getStringToDate(category);
				countPv += visitDAO.findClientVisitPv(categoryDate, fieldName, name, siteId).intValue();
			}
			vo.setPv(countPv);
			vo.setPvRate(NumberUtil.percentage(countPv.intValue(), sumPv.intValue()));
			vo.setName(name);
			
			summarys.add(vo);
		}
		return summarys;
	}

	@Override
	public String findClientVisitReport(String startDate, String endDate, String fieldName, Integer siteId) {
		List<SummaryVo> list = findClientVisitTable(startDate, endDate, fieldName, siteId);
		Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
		for (SummaryVo vo : list){
			dataSet.put(vo.getName(), vo.getPv().longValue());
		}
		return ChartVisitUtil.getPie3DChart(dataSet);
	}

	@Override
	public String findClientTrendVisitReport(String startDate, String endDate, String fieldName, String fieldValue, Integer labelCount, Integer siteId) {
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		
		Map<String, Map<String, Integer>> dataSet = new LinkedHashMap<String, Map<String, Integer>>();
		Map<String, Integer> dataValuePv = new LinkedHashMap<String, Integer>();
		for (String category : categories){
			Integer countPv = visitDAO.findClientVisitPv(DateTimeUtil.getStringToDate(category), fieldName, fieldValue, siteId).intValue();
			dataValuePv.put(category, countPv);
		}
		dataSet.put("PV", dataValuePv);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}
	
	@Override
	public List<SummaryVo> findClientBooleanVisitTable(String startDate, String endDate, String fieldName, Integer siteId) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		boolean[] fieldNames = {true, false};
		List<SummaryVo> summarys = new ArrayList<SummaryVo>();
		Long sumPv = visitDAO.findCountryVisitPvSum(start, end, siteId);
		SummaryVo vo = null;
		String fieldZhName = "Cookie";
		if (fieldName.equals("javaEnabled")){
			fieldZhName = "Applet";
		}
		for (Boolean name : fieldNames){
			vo = new SummaryVo();
			Integer countPv = 0;
			for (String category : categories){
				Date categoryDate = DateTimeUtil.getStringToDate(category);
				countPv += visitDAO.findClientBooleanVisitPv(categoryDate, fieldName, name, siteId).intValue();
			}
			vo.setPv(countPv);
			vo.setPvRate(NumberUtil.percentage(countPv.intValue(), sumPv.intValue()));
			
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
	public String findClientBooleanVisitReport(String startDate, String endDate, String fieldName, Integer siteId) {
		List<SummaryVo> list = findClientBooleanVisitTable(startDate, endDate, fieldName, siteId);
		Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
		for (SummaryVo vo : list){
			dataSet.put(vo.getName(), vo.getPv().longValue());
		}
		return ChartVisitUtil.getPie3DChart(dataSet);
	}

	@Override
	public String findClientBooleanTrendVisitReport(String startDate, String endDate, String fieldName, Boolean enabled, Integer labelCount, Integer siteId) {
		List<String> categories = DateTimeUtil.getDateArea(startDate, endDate);
		
		Map<String, Map<String, Integer>> dataSet = new LinkedHashMap<String, Map<String, Integer>>();
		Map<String, Integer> dataValuePv = new LinkedHashMap<String, Integer>();
		for (String category : categories){
			Integer countPv = visitDAO.findClientBooleanVisitPv(DateTimeUtil.getStringToDate(category), fieldName, enabled, siteId).intValue();
			dataValuePv.put(category, countPv);
		}
		dataSet.put("PV", dataValuePv);
		
		return ChartVisitUtil.getLine2DChart(categories, dataSet, labelCount);
	}

}
