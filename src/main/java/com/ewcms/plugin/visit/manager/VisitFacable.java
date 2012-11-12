package com.ewcms.plugin.visit.manager;

import java.util.List;

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
public interface VisitFacable {
	
	public void addVisitByLoadEvent(Visit visit, VisitItem visitItem);
	
	public void addVisitByKeepAliveEvent(Visit visit, VisitItem visitItem);
	
	public void addVisitByUnloadEvent(Visit visit, VisitItem visitItem);
	
	public String findFirstDate(Integer siteId);
	
	public IpRange findIpRangeByIp(Long ipBegin, Long ipEnd);
	
	public Integer findDays(Integer siteId);
	
	public List<SummaryVo> findSummaryTable(Integer siteId);

	public String findSummaryReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<SummaryVo> findSiteTable(String startDate, String endDate, Integer siteId);
	
	public List<LastVisitVo> findLastTable(String startDate, String endDate, Integer rows, Integer siteId);
	
	public List<SummaryVo> findHourTable(String startDate, String endDate, Integer siteId);
	
	public String findHourReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<InAndExitVo> findEntranceTable(String startDate, String endDate, Integer rows, Integer siteId);
	
	public String findEmtranceTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<InAndExitVo> findExitTable(String startDate, String endDate, Integer rows, Integer siteId);
	
	public String findExitTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<SummaryVo> findHostTable(String startDate, String endDate, Integer rows, Integer siteId);
	
	public String findHostTrendReport(String host, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public String findHostReport(String startDate, String endDate, Integer rows, Integer siteId);
	
	public List<SummaryVo> findCountryTable(String startDate, String endDate, Integer siteId);
	
	public String findCountryReport(String startDate, String endDate, Integer siteId);
	
	public String findCountryTrendReport(String startDate, String endDate, String country, Integer labelCount, Integer siteId);
	
	public String findOnlineReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<OnlineVo> findOnlineTable(String startDate, String endDate, Integer siteId);

	public List<SummaryVo> findClientTable(String startDate, String endDate, String fieldName, Integer siteId);
	
	public String findClientReport(String startDate, String endDate,  String fieldName, Integer siteId);
	
	public String findClientTrendReport(String startDate, String endDate, String fieldName, String fieldValue, Integer labelCount, Integer siteId);
	
	public List<SummaryVo> findClientBooleanTable(String startDate, String endDate, String fieldName, Integer siteId);
	
	public String findClientBooleanReport(String startDate, String endDate,  String fieldName, Integer siteId);
	
	public String findClientTrendBooleanReport(String startDate, String endDate, String fieldName, Boolean enabled, Integer labelCount, Integer siteId);
}
