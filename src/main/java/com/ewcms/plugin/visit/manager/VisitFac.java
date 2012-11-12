package com.ewcms.plugin.visit.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.visit.manager.service.IpRangeServiceable;
import com.ewcms.plugin.visit.manager.service.VisitServiceable;
import com.ewcms.plugin.visit.manager.vo.ArticleVisitVo;
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

	@Override
	public String findVisitSummaryReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		return visitService.findVisitSummaryReport(startDate, endDate, labelCount, siteId);
	}

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
	public String findVisitFirstAddDate(Integer siteId) {
		return visitService.findVisitFirstAddDate(siteId);
	}

	@Override
	public Integer findVisitDay(Integer siteId) {
		return visitService.findVisitDay(siteId);
	}

	@Override
	public List<SummaryVo> findVisitSummaryTable(Integer siteId) {
		return visitService.findVisitSummaryTable(siteId);
	}

	@Override
	public IpRange findIpRangeByIp(Long ipBegin, Long ipEnd) {
		return ipRangeService.findIpRangeByIp(ipBegin, ipEnd);
	}

	@Override
	public List<SummaryVo> findVisitSiteTable(String startDate, String endDate, Integer siteId) {
		return visitService.findVisitSiteTable(startDate, endDate, siteId);
	}

	@Override
	public List<LastVisitVo> findLastVisit(String startDate, String endDate, Integer rows, Integer siteId){
		return visitService.findLastVisit(startDate, endDate, rows, siteId);
	}

	@Override
	public List<SummaryVo> findHourVisitTable(String startDate, String endDate, Integer siteId) {
		return visitService.findHourVisitTable(startDate, endDate, siteId);
	}

	@Override
	public String findHourVisitReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		return visitService.findHourVisitReport(startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<InAndExitVo> findEntranceVisit(String startDate, String endDate, Integer rows, Integer siteId) {
		return visitService.findEntranceVisit(startDate, endDate, rows, siteId);
	}
	
	@Override
	public String findEmtranceVisitCountPv(String url, String startDate, String endDate, Integer labelCount, Integer siteId){
		return visitService.findEmtranceVisitCountPv(url, startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<InAndExitVo> findExitVisit(String startDate, String endDate, Integer rows, Integer siteId) {
		return visitService.findExitVisit(startDate, endDate, rows, siteId);
	}
	
	@Override
	public String findExitVisitCountPv(String url, String startDate, String endDate, Integer labelCount, Integer siteId){
		return visitService.findExitVisitCountPv(url, startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<SummaryVo> findHostVisit(String startDate, String endDate, Integer rows, Integer siteId) {
		return visitService.findHostVisit(startDate, endDate, rows, siteId);
	}

	@Override
	public String findHostVisitCountPv(String host, String startDate, String endDate, Integer labelCount, Integer siteId) {
		return visitService.findHostVisitCountPv(host, startDate, endDate, labelCount, siteId);
	}

	@Override
	public String findHostVisitReport(String startDate, String endDate, Integer rows, Integer siteId) {
		return visitService.findHostVisitReport(startDate, endDate, rows, siteId);
	}

	@Override
	public List<SummaryVo> findCountryVisitTable(String startDate, String endDate, Integer siteId) {
		return visitService.findCountryVisitTable(startDate, endDate, siteId);
	}

	@Override
	public String findCountryVisitReport(String startDate, String endDate, Integer siteId) {
		return visitService.findCountryVisitReport(startDate, endDate, siteId);
	}

	@Override
	public String findCountryTrendVisitReport(String startDate, String endDate, String country, Integer labelCount, Integer siteId) {
		return visitService.findCountryTrendVisitReport(startDate, endDate, country, labelCount, siteId);
	}

	@Override
	public String findOnlineVisitReport(String startDate, String endDate, Integer labelCount, Integer siteId) {
		return visitService.findOnlineVisitReport(startDate, endDate, labelCount, siteId);
	}

	@Override
	public List<OnlineVo> findOnlineVisitTable(String startDate, String endDate, Integer siteId) {
		return visitService.findOnlineVisitTable(startDate, endDate, siteId);
	}

	@Override
	public List<ArticleVisitVo> findArticleVisit(Integer rows, Integer siteId) {
		return visitService.findArticleVisit(rows, siteId);
	}

	@Override
	public String findClientTrendVisitReport(String startDate, String endDate, String fieldName, String fieldValue, Integer labelCount, Integer siteId) {
		return visitService.findClientTrendVisitReport(startDate, endDate, fieldName, fieldValue, labelCount, siteId);
	}

	@Override
	public List<SummaryVo> findClientVisitTable(String startDate, String endDate, String fieldName, Integer siteId) {
		return visitService.findClientVisitTable(startDate, endDate, fieldName, siteId);
	}

	@Override
	public String findClientVisitReport(String startDate, String endDate, String fieldName, Integer siteId) {
		return visitService.findClientVisitReport(startDate, endDate, fieldName, siteId);
	}

	@Override
	public List<SummaryVo> findClientBooleanVisitTable(String startDate,
			String endDate, String fieldName, Integer siteId) {
		return visitService.findClientBooleanVisitTable(startDate, endDate, fieldName, siteId);
	}

	@Override
	public String findClientBooleanVisitReport(String startDate,
			String endDate, String fieldName, Integer siteId) {
		return visitService.findClientBooleanVisitReport(startDate, endDate, fieldName, siteId);
	}

	@Override
	public String findClientBooleanTrendVisitReport(String startDate,
			String endDate, String fieldName, Boolean enabled,
			Integer labelCount, Integer siteId) {
		return visitService.findClientBooleanTrendVisitReport(startDate, endDate, fieldName, enabled, labelCount, siteId);
	}
}
