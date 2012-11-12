package com.ewcms.plugin.visit.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.visit.manager.service.ClientServiceable;
import com.ewcms.plugin.visit.manager.service.IpRangeServiceable;
import com.ewcms.plugin.visit.manager.service.SummaryServiceable;
import com.ewcms.plugin.visit.manager.service.VisitServiceable;
import com.ewcms.plugin.visit.manager.vo.InAndExitVo;
import com.ewcms.plugin.visit.manager.vo.LastVisitVo;
import com.ewcms.plugin.visit.manager.vo.OnlineVo;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
import com.ewcms.plugin.visit.model.IpRange;
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
	private IpRangeServiceable ipRangeService;
	@Autowired
	private SummaryServiceable summaryService;
	@Autowired
	private ClientServiceable clientService;

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

	public IpRange findIpRangeByIp(Long ipBegin, Long ipEnd){
		return ipRangeService.findIpRangeByIp(ipBegin, ipEnd);
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
	public List<LastVisitVo> findLastTable(String startDate, String endDate, Integer rows, Integer siteId) {
		return summaryService.findLastTable(startDate, endDate, rows, siteId);
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
	public List<InAndExitVo> findEntranceTable(String startDate, String endDate, Integer rows, Integer siteId) {
		return summaryService.findEntranceTable(startDate, endDate, rows, siteId);
	}

	@Override
	public String findEmtranceTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId) {
		return summaryService.findEmtranceTrendReport(url, startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<InAndExitVo> findExitTable(String startDate, String endDate, Integer rows, Integer siteId) {
		return summaryService.findExitTable(startDate, endDate, rows, siteId);
	}

	@Override
	public String findExitTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId) {
		return summaryService.findExitTrendReport(url, startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<SummaryVo> findHostTable(String startDate, String endDate, Integer rows, Integer siteId) {
		return summaryService.findHostTable(startDate, endDate, rows, siteId);
	}

	@Override
	public String findHostTrendReport(String host, String startDate, String endDate, Integer labelCount, Integer siteId) {
		return summaryService.findHostTrendReport(host, startDate, endDate, labelCount, siteId);
	}

	@Override
	public String findHostReport(String startDate, String endDate, Integer rows, Integer siteId) {
		return summaryService.findHostReport(startDate, endDate, rows, siteId);
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
	public String findCountryTrendReport(String startDate, String endDate, String country, Integer labelCount, Integer siteId) {
		return summaryService.findCountryTrendReport(startDate, endDate, country, labelCount, siteId);
	}

	@Override
	public String findOnlineReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		return summaryService.findOnlineReport(startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<OnlineVo> findOnlineTable(String startDate, String endDate, Integer siteId) {
		return summaryService.findOnlineTable(startDate, endDate, siteId);
	}

	@Override
	public List<SummaryVo> findClientTable(String startDate, String endDate, String fieldName, Integer siteId) {
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
	public List<SummaryVo> findClientBooleanTable(String startDate, String endDate, String fieldName, Integer siteId) {
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
}
