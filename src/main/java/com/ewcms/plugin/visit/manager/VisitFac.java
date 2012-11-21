/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.visit.manager.service.ClickRateServiceable;
import com.ewcms.plugin.visit.manager.service.TrafficServiceable;
import com.ewcms.plugin.visit.manager.service.ClientServiceable;
import com.ewcms.plugin.visit.manager.service.LoyaltyServiceable;
import com.ewcms.plugin.visit.manager.service.SummaryServiceable;
import com.ewcms.plugin.visit.manager.service.VisitServiceable;
import com.ewcms.plugin.visit.manager.vo.ClickRateVo;
import com.ewcms.plugin.visit.manager.vo.TrafficVo;
import com.ewcms.plugin.visit.manager.vo.ClientVo;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
import com.ewcms.plugin.visit.manager.vo.LoyaltyVo;
import com.ewcms.plugin.visit.model.Visit;
import com.ewcms.plugin.visit.model.VisitItem;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class VisitFac implements VisitFacable {

	@Autowired
	private VisitServiceable visitService;
	@Autowired
	private SummaryServiceable summaryService;
	@Autowired
	private ClientServiceable clientService;
	@Autowired
	private TrafficServiceable trafficService;
	@Autowired
	private LoyaltyServiceable loyaltyService;
	@Autowired
	private ClickRateServiceable clickRateService;

	@Override
	public void addVisitByLoadEvent(Visit visit, VisitItem visitItem) {
		visitService.addVisitByLoadEvent(visit, visitItem);
	}

	@Override
	public void addVisitByKeepAliveEvent(Visit visit, VisitItem visitItem) {
		visitService.addVisitByKeepAliveEvent(visit, visitItem);
	}

	@Override
	public void addVisitByUnloadEvent(Visit visit, VisitItem visitItem) {
		visitService.addVisitByUnloadEvent(visit, visitItem);
	}

	@Override
	public String findFirstDate(Integer siteId) {
		return summaryService.findFirstDate(siteId);
	}

	@Override
	public Integer findDays(Integer siteId) {
		return summaryService.findDays(siteId);
	}

	@Override
	public List<SummaryVo> findSummaryTable(Integer siteId) {
		return summaryService.findSummaryTable(siteId);
	}

	@Override
	public String findSummaryReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		return summaryService.findSummaryReport(startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<SummaryVo> findSiteTable(String startDate, String endDate, Integer siteId) {
		return summaryService.findSiteTable(startDate, endDate, siteId);
	}

	@Override
	public List<SummaryVo> findLastTable(String startDate, String endDate, Integer siteId) {
		return summaryService.findLastTable(startDate, endDate, siteId);
	}

	@Override
	public List<SummaryVo> findHourTable(String startDate, String endDate, Integer siteId) {
		return summaryService.findHourTable(startDate, endDate, siteId);
	}

	@Override
	public String findHourReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		return summaryService.findHourReport(startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<SummaryVo> findEntranceTable(String startDate, String endDate, Integer siteId) {
		return summaryService.findEntranceTable(startDate, endDate, siteId);
	}

	@Override
	public String findEmtranceTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId) {
		return summaryService.findEmtranceTrendReport(url, startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<SummaryVo> findExitTable(String startDate, String endDate, Integer siteId) {
		return summaryService.findExitTable(startDate, endDate, siteId);
	}

	@Override
	public String findExitTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId) {
		return summaryService.findExitTrendReport(url, startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<SummaryVo> findHostTable(String startDate, String endDate, Integer siteId) {
		return summaryService.findHostTable(startDate, endDate, siteId);
	}

	@Override
	public String findHostTrendReport(String host, String startDate, String endDate, Integer labelCount, Integer siteId) {
		return summaryService.findHostTrendReport(host, startDate, endDate, labelCount, siteId);
	}

	@Override
	public String findHostReport(String startDate, String endDate, Integer siteId) {
		return summaryService.findHostReport(startDate, endDate, siteId);
	}

	@Override
	public List<SummaryVo> findCountryTable(String startDate, String endDate, Integer siteId) {
		return summaryService.findCountryTable(startDate, endDate, siteId);
	}

	@Override
	public String findCountryReport(String startDate, String endDate, Integer siteId) {
		return summaryService.findCountryReport(startDate, endDate, siteId);
	}
	
	@Override
	public List<SummaryVo> findProvinceTable(String startDate, String endDate, String country, Integer siteId){
		return summaryService.findProvinceTable(startDate, endDate, country, siteId);
	}
	
	@Override
	public String findProvinceReport(String startDate, String endDate, String country, Integer siteId){
		return summaryService.findProvinceReport(startDate, endDate, country, siteId);
	}
	
	@Override
	public List<SummaryVo> findCityTable(String startDate, String endDate, String country, String province, Integer siteId){
		return summaryService.findCityTable(startDate, endDate, country, province, siteId);
	}
	
	@Override
	public String findCityReport(String startDate, String endDate, String country, String province, Integer siteId){
		return summaryService.findCityReport(startDate, endDate, country, province, siteId);
	}

	@Override
	public String findCountryTrendReport(String startDate, String endDate, String country, Integer labelCount, Integer siteId) {
		return summaryService.findCountryTrendReport(startDate, endDate, country, labelCount, siteId);
	}

	@Override
	public String findProvinceTrendReport(String startDate, String endDate, String country, String province, Integer labelCount, Integer siteId){
		return summaryService.findProvinceTrendReport(startDate, endDate, country, province, labelCount, siteId);
	}
	
	@Override
	public String findCityTrendReport(String startDate, String endDate, String country, String province, String city, Integer labelCount, Integer siteId){
		return summaryService.findCityTrendReport(startDate, endDate, country, province, city, labelCount, siteId);
	}
	
	@Override
	public String findOnlineReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		return summaryService.findOnlineReport(startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<SummaryVo> findOnlineTable(String startDate, String endDate, Integer siteId) {
		return summaryService.findOnlineTable(startDate, endDate, siteId);
	}

	@Override
	public List<ClientVo> findClientTable(String startDate, String endDate, String fieldName, Integer siteId) {
		return clientService.findClientTable(startDate, endDate, fieldName, siteId);
	}

	@Override
	public String findClientReport(String startDate, String endDate, String fieldName, Integer siteId) {
		return clientService.findClientReport(startDate, endDate, fieldName, siteId);
	}

	@Override
	public String findClientTrendReport(String startDate, String endDate, String fieldName, String fieldValue, Integer labelCount, Integer siteId) {
		return clientService.findClientTrendReport(startDate, endDate, fieldName, fieldValue, labelCount, siteId);
	}

	@Override
	public List<ClientVo> findClientBooleanTable(String startDate, String endDate, String fieldName, Integer siteId) {
		return clientService.findClientBooleanTable(startDate, endDate, fieldName, siteId);
	}

	@Override
	public String findClientBooleanReport(String startDate, String endDate, String fieldName, Integer siteId) {
		return clientService.findClientBooleanReport(startDate, endDate, fieldName, siteId);
	}

	@Override
	public String findClientTrendBooleanReport(String startDate, String endDate, String fieldName, Boolean enabled, Integer labelCount, Integer siteId) {
		return clientService.findClientTrendBooleanReport(startDate, endDate, fieldName, enabled, labelCount, siteId);
	}

	@Override
	public List<TrafficVo> findArticleTable(Integer channelId, Integer siteId) {
		return trafficService.findArticleTable(channelId, siteId);
	}

	@Override
	public List<TrafficVo> findUrlTable(String startDate, String endDate, Integer siteId) {
		return trafficService.findUrlTable(startDate, endDate, siteId);
	}

	@Override
	public String findUrlTrendReport(String startDate, String endDate, String url, Integer labelCount, Integer siteId) {
		return trafficService.findUrlTrendReport(startDate, endDate, url, labelCount, siteId);
	}

	@Override
	public List<TrafficVo> findChannelTable(String startDate, String endDate, Integer channelParentId, Integer siteId) {
		return trafficService.findChannelTable(startDate, endDate, channelParentId, siteId);
	}

	@Override
	public String findChannelReport(String startDate, String endDate, Integer channelParentId, Integer labelCount, Integer siteId) {
		return trafficService.findChannelReport(startDate, endDate, channelParentId, labelCount, siteId);
	}

	@Override
	public String findChannelTrendReport(String startDate, String endDate, Integer channelId, Integer labelCount, Integer siteId) {
		return trafficService.findChannelTrendReport(startDate, endDate, channelId, labelCount, siteId);
	}

	@Override
	public List<LoyaltyVo> findFrequencyTable(String startDate, String endDate, Integer siteId) {
		return loyaltyService.findFrequencyTable(startDate, endDate, siteId);
	}

	@Override
	public String findFrequencyReport(String startDate, String endDate, Integer siteId) {
		return loyaltyService.findFrequencyReport(startDate, endDate, siteId);
	}

	@Override
	public String findFrequencyTrendReport(String startDate, String endDate, Long frequency, Integer labelCount, Integer siteId) {
		return loyaltyService.findFrequencyTrendReport(startDate, endDate, frequency, labelCount, siteId);
	}

	@Override
	public List<LoyaltyVo> findDepthTable(String startDate, String endDate, Integer siteId) {
		return loyaltyService.findDepthTable(startDate, endDate, siteId);
	}

	@Override
	public String findDepthReport(String startDate, String endDate, Integer siteId) {
		return loyaltyService.findDepthReport(startDate, endDate, siteId);
	}

	@Override
	public String findDepthTrendReport(String startDate, String endDate, Long depth, Integer labelCount, Integer siteId) {
		return loyaltyService.findDepthTrendReport(startDate, endDate, depth, labelCount, siteId);
	}

	@Override
	public List<LoyaltyVo> findVisitorTable(String startDate, String endDate, Integer siteId) {
		return loyaltyService.findVisitorTable(startDate, endDate, siteId);
	}

	@Override
	public String findVisitorReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		return loyaltyService.findVisitorReport(startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<LoyaltyVo> findStickTimeTable(String startDate, String endDate, Integer siteId) {
		return loyaltyService.findStickTimeTable(startDate, endDate, siteId);
	}

	@Override
	public String findStickTimeReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		return loyaltyService.findStickTimeReport(startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<ClickRateVo> findSourceTable(String startDate, String endDate,
			Integer siteId) {
		return clickRateService.findSourceTable(startDate, endDate, siteId);
	}

	@Override
	public String findSourceReport(String startDate, String endDate, Integer siteId) {
		return clickRateService.findSourceReport(startDate, endDate, siteId);
	}

	@Override
	public List<ClickRateVo> findSearchTable(String startDate, String endDate,
			Integer siteId) {
		return clickRateService.findSearchTable(startDate, endDate, siteId);
	}

	@Override
	public String findSearchReport(String startDate, String endDate,
			Integer siteId) {
		return clickRateService.findSearchReport(startDate, endDate, siteId);
	}

	@Override
	public String findSearchTrendReport(String startDate, String endDate, String domain,
			Integer labelCount, Integer siteId) {
		return clickRateService.findSearchTrendReport(startDate, endDate, domain, labelCount, siteId);
	}

	@Override
	public List<ClickRateVo> findWebSiteTable(String startDate, String endDate,
			Integer siteId) {
		return clickRateService.findWebSiteTable(startDate, endDate, siteId);
	}

	@Override
	public String findWebSiteReport(String startDate, String endDate,
			Integer siteId) {
		return clickRateService.findWebSiteReport(startDate, endDate, siteId);
	}

	@Override
	public String findWebSiteTrendReport(String startDate, String endDate,
			String webSite, Integer labelCount, Integer siteId) {
		return clickRateService.findWebSiteTrendReport(startDate, endDate, webSite, labelCount, siteId);
	}
}
