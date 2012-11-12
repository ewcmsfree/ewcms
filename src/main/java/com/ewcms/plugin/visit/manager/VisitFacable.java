package com.ewcms.plugin.visit.manager;

import java.util.List;

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
public interface VisitFacable {
	public void addVisitByLoadEvent(Visit visit, VisitItem visitItem);
	
	public void addVisitByKeepAliveEvent(Visit visit, VisitItem visitItem);
	
	public void addVisitByUnloadEvent(Visit visit, VisitItem visitItem);
	
	public String findVisitSummaryReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public String findVisitFirstAddDate(Integer siteId);
	
	public Integer findVisitDay(Integer siteId);
	
	public List<SummaryVo> findVisitSummaryTable(Integer siteId);
	
	public IpRange findIpRangeByIp(Long ipBegin, Long ipEnd);
	
	public List<SummaryVo> findVisitSiteTable(String startDate, String endDate, Integer siteId);
	
	public List<LastVisitVo> findLastVisit(String startDate, String endDate, Integer rows, Integer siteId);
	
	public List<SummaryVo> findHourVisitTable(String startDate, String endDate, Integer siteId);
	
	public String findHourVisitReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<InAndExitVo> findEntranceVisit(String startDate, String endDate, Integer rows, Integer siteId);
	
	public String findEmtranceVisitCountPv(String url, String startDate, String endDate, Integer labelCount, Integer siteId);

	public List<InAndExitVo> findExitVisit(String startDate, String endDate, Integer rows, Integer siteId);
	
	public String findExitVisitCountPv(String url, String startDate, String endDate, Integer labelCount, Integer siteId);

	public List<SummaryVo> findHostVisit(String startDate, String endDate, Integer rows, Integer siteId);
	
	public String findHostVisitCountPv(String host, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public String findHostVisitReport(String startDate, String endDate, Integer rows, Integer siteId);
	
	public List<SummaryVo> findCountryVisitTable(String startDate, String endDate, Integer siteId);
	
	public String findCountryVisitReport(String startDate, String endDate, Integer siteId);
	
	public String findCountryTrendVisitReport(String startDate, String endDate, String country, Integer labelCount, Integer siteId);
	
	public String findOnlineVisitReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<OnlineVo> findOnlineVisitTable(String startDate, String endDate, Integer siteId);
	
	public List<ArticleVisitVo> findArticleVisit(Integer rows, Integer siteId);
	
	public List<SummaryVo> findClientVisitTable(String startDate, String endDate, String fieldName, Integer siteId);
	
	public String findClientVisitReport(String startDate, String endDate,  String fieldName, Integer siteId);
	
	public String findClientTrendVisitReport(String startDate, String endDate, String fieldName, String fieldValue, Integer labelCount, Integer siteId);
	
	public List<SummaryVo> findClientBooleanVisitTable(String startDate, String endDate, String fieldName, Integer siteId);
	
	public String findClientBooleanVisitReport(String startDate, String endDate,  String fieldName, Integer siteId);
	
	public String findClientBooleanTrendVisitReport(String startDate, String endDate, String fieldName, Boolean enabled, Integer labelCount, Integer siteId);
}
