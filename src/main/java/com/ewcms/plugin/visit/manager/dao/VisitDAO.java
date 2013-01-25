/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.visit.manager.vo.InteractiveVo;
import com.ewcms.plugin.visit.manager.vo.PublishedVo;
import com.ewcms.plugin.visit.manager.vo.TrafficVo;
import com.ewcms.plugin.visit.manager.vo.LoyaltyVo;
import com.ewcms.plugin.visit.manager.vo.ClickRateVo;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
import com.ewcms.plugin.visit.model.Visit;
import com.ewcms.plugin.visit.util.DateTimeUtil;
import com.ewcms.plugin.visit.util.VisitUtil;

/**
 * 统计访问DAO
 * 
 * @author wu_zhijun
 * 
 */
@Repository
public class VisitDAO extends JpaDAO<Long, Visit> {

	private static final String SUMMARY_CLASS_NAME = SummaryVo.class.getPackage().getName() + "." + SummaryVo.class.getSimpleName();
	private static final String LOYALTY_CLASS_NAME = LoyaltyVo.class.getPackage().getName() + "." + LoyaltyVo.class.getSimpleName();
	private static final String SOURCE_CLASS_NAME = ClickRateVo.class.getPackage().getName() + "." + ClickRateVo.class.getSimpleName();
	private static final String PUBLISHED_CLASS_NAME = PublishedVo.class.getPackage().getName() + "." + PublishedVo.class.getSimpleName();
	private static final String Interactive_CLASS_NAME = InteractiveVo.class.getPackage().getName() + "." + InteractiveVo.class.getSimpleName();

	/**
	 * 查询开始统计分析最早的日期
	 * 
	 * @param siteId
	 *            站点编号
	 * @return String 日期字符串
	 */
	public String findFirstDate(final Integer siteId) {
		String hql = "From Visit Where siteId=:siteId Order By addDate";
		TypedQuery<Visit> query = this.getEntityManager().createQuery(hql, Visit.class);
		query.setParameter("siteId", siteId);
		List<Visit> list = query.getResultList();
		if (list == null || list.isEmpty()) return "";
		return DateTimeUtil.getDateToString(list.get(0).getAddDate());
	}

	/**
	 * 查询一天中的IP量，去除重复的记录
	 * 
	 * @param date
	 *            访问日期
	 * @param siteId
	 *            站点编号
	 * @return Integer
	 */
	public Long findIpCountInDay(final Date date, final Integer siteId) {
		String hql = "Select Count(Distinct v.ip) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.siteId=:siteId "
				+ "Group By v.addDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 查询UV（用户访问）量
	 * 
	 * @param date
	 *            访问日期
	 * @param siteId
	 *            站点编号
	 * @return Integer
	 */
	public Long findUvCountInDay(final Date date, final Integer siteId) {
		String hql = "Select Count(Distinct v.uniqueId) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.siteId=:siteId "
				+ "Group By v.addDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 查询PV（页面访问）量
	 * 
	 * @param date
	 *            访问日期
	 * @param siteId
	 *            站点编号
	 * @return Integer
	 */
	public Long findPvCountInDay(final Date date, final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.siteId=:siteId "
				+ "Group By i.visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 查询RV（回头率）量
	 * 
	 * @param date
	 *            访问日期
	 * @param siteId
	 *            站点编号
	 * @return Integer
	 */
	public Long findRvCountInDay(final Date date, final Integer siteId) {
		String hql = "Select Count(v.rvFlag) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.siteId=:siteId And v.rvFlag=true";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 查询访问记录数
	 * 
	 * @param date
	 * @param siteId
	 * @return
	 */
	public Long findAcCountInDay(final Date date, final Integer siteId) {
		String hql = "Select Count(v.uniqueId) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 查询访问时长合计
	 * 
	 * @param date
	 *            访问日期
	 * @param siteId
	 *            站点编号
	 * @return Integer
	 */
	public Long findStSumInDay(final Date date, final Integer siteId) {
		String hql = "Select Sum(i.stickTime) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.siteId=:siteId "
				+ "Group By i.visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 查询访问时长记录数
	 * 
	 * @param date
	 *            访问日期
	 * @param siteId
	 *            站点编号
	 * @return Long
	 */
	public Long findStCountInDay(final Date date, final Integer siteId) {
		String hql = "Select Count(i.stickTime) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.siteId=:siteId "
				+ "Group By i.visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 查询访问记录是否存在
	 * 
	 * @param uniqueId
	 *            用户编号
	 * @param date
	 *            访问日期
	 * @param ip
	 *            IP
	 * @return Visit
	 */
	public Visit findVisitByVisitPK(final String uniqueId, final Date date, final String ip) {
		String hql = "Select v "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.uniqueId=:uniqueId And v.addDate=:date And v.ip=:ip "
				+ "Order by v.addDate Desc";
		TypedQuery<Visit> query = this.getEntityManager().createQuery(hql, Visit.class);
		query.setParameter("uniqueId", uniqueId);
		query.setParameter("date", date);
		query.setParameter("ip", ip);

		List<Visit> list = query.getResultList();
		if (list == null || list.isEmpty()) return null;
		return list.get(0);
	}

	/**
	 * 返回最近访问记录
	 * 
	 * @param siteId
	 *            站点编号
	 * @return List
	 */
	public List<SummaryVo> findAcInDateInterval(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select new " + SUMMARY_CLASS_NAME + "(v.ip, v.country, v.province, v.city, i.url, i.visitDate, i.visitTime, i.referer, v.browser, v.os, v.screen, v.language, v.flashVersion) "
				+ "From VisitItem As i, Visit As v "
				+ "Where i.uniqueId = v.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId "
				+ "Order By i.visitDate Desc, i.visitTime Desc";
		TypedQuery<SummaryVo> query = this.getEntityManager().createQuery(hql, SummaryVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);

		return query.getResultList();
	}

	/**
	 * 查询时间段Pv量
	 * 
	 * @param siteId
	 */
	public Long findPvCountInDayByHour(final Date date, final Integer hour, final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And Hour(i.visitTime)=:hour And i.siteId=:siteId ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) result = 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 查询时间段IP量
	 * 
	 * @param date
	 *            访问日期
	 * @param siteId
	 *            站点编号
	 * @return Integer
	 */
	public Long findIpCountInDayByHour(final Date date, final Integer hour, final Integer siteId) {
		String hql = "Select Count(Distinct v.ip) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And Hour(i.visitTime)=:hour And i.siteId=:siteId ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 查询时间段UV（用户访问）量
	 * 
	 * @param date
	 *            访问日期
	 * @param siteId
	 *            站点编号
	 * @return Integer
	 */
	public Long findUvCountInDayByHour(final Date date, final Integer hour, final Integer siteId) {
		String hql = "Select Count(Distinct v.uniqueId) "
				+ "From Visit As v, VisitItem As i Where v.uniqueId=i.uniqueId And i.visitDate=:date And Hour(i.visitTime)=:hour And i.siteId=:siteId ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 查询时间段RV（回头率）量
	 * 
	 * @param date
	 *            访问日期
	 * @param siteId
	 *            站点编号
	 * @return Integer
	 */
	public Long findRvCountInDayByHour(final Date date, final Integer hour, final Integer siteId) {
		String hql = "Select Count(v.rvFlag) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And Hour(i.visitTime)=:hour And v.siteId=:siteId And v.rvFlag=true "
				+ "Group By v.ip, v.uniqueId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 入口
	 * 
	 * @param startDate
	 * @param endDate
	 * @param siteId
	 * @return
	 */
	public List<SummaryVo> findEntranceInDateInterval(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select new "
				+ SUMMARY_CLASS_NAME
				+ "(i.url, Count(i.url), '100%') "
				+ "From Visit As v, VisitItem As i Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.url Is Not Null "
				+ "Group By i.url " + "Order By Count(i.url) Desc";
		TypedQuery<SummaryVo> query = this.getEntityManager().createQuery(hql, SummaryVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findUrlCountInDateInterval(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select Count(i.url) " 
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.url Is Not Null";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Long findPvSumInDayByUrl(final Date date, final String url, final Integer siteId) {
		String hql = "Select Sum(i.pageView) " 
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.url=:url And i.siteId=:siteId ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("url", url);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Long findUrlCountInDayByUrl(final Date date, final String url, final Integer siteId) {
		String hql = "Select Count(i.url) " + "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.url=:url And i.siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("url", url);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Long findUrlCountInDayByUrlAndEvent(final Date date, final String url, final Integer siteId) {
		String hql = "Select Count(i.url) " + "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.url=:url And i.event=:event And i.siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("url", url);
		query.setParameter("event", VisitUtil.UNLOAD_EVENT);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 出口
	 * 
	 * @param startDate
	 * @param endDate
	 * @param siteId
	 * @return
	 */
	public List<SummaryVo> findExitInDateIntervalByEvent(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select new "
				+ SUMMARY_CLASS_NAME
				+ "(i.url, Count(i.url), '100%') "
				+ "From Visit As v, VisitItem As i Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.event=:event And i.url Is Not Null "
				+ "Group By i.url "
				+ "Order By Count(i.url) Desc";
		TypedQuery<SummaryVo> query = this.getEntityManager().createQuery(hql, SummaryVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setParameter("event", VisitUtil.UNLOAD_EVENT);
		return query.getResultList();
	}

	public Long findUrlCountInDateIntervalByEvent(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select Count(i.url) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.event=:event And i.url Is Not Null ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setParameter("event", VisitUtil.UNLOAD_EVENT);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Long findPvCountInDayByUrlAndEvent(final Date date, final String url, final Integer siteId) {
		String hql = "Select Sum(i.pageView) " + "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.url=:url And i.siteId=:siteId And i.event=:event And i.url Is Not Null "
				+ "Group By i.visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("url", url);
		query.setParameter("siteId", siteId);
		query.setParameter("event", VisitUtil.UNLOAD_EVENT);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public List<SummaryVo> findHostInDateInterval(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select new " + SUMMARY_CLASS_NAME + "(i.host, Sum(i.pageView)) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.host Is Not Null "
				+ "Group By i.host "
				+ "Order By Sum(i.pageView) Desc";
		TypedQuery<SummaryVo> query = this.getEntityManager().createQuery(hql, SummaryVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findPvSumInDayByHost(final Date date, final String host, final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.host=:host And i.siteId=:siteId And i.host Is Not Null "
				+ "Group By i.visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("host", host);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public List<String> findCountryInDateInterval(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select v.country "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate>=:startDate And v.addDate<=:endDate And v.siteId=:siteId And v.country Is Not Null "
				+ "Group By v.country";
		TypedQuery<String> query = this.getEntityManager().createQuery(hql, String.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}
	
	public List<String> findProvinceInDateIntervalByCountry(final Date start, final Date end, final String country, final Integer siteId){
		String hql = "Select v.province "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate>=:start And v.addDate<=:end And v.country=:country And v.siteId=:siteId And v.country Is Not Null And v.province Is Not Null "
				+ "Group By v.country, v.province";
		TypedQuery<String> query = this.getEntityManager().createQuery(hql, String.class);
		query.setParameter("start", start);
		query.setParameter("end", end);
		query.setParameter("country", country);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}
	
	public List<String> findProvinceInDateIntervalByCountryAndProvince(final Date start, final Date end, final String country, final String province, final Integer siteId){
		String hql = "Select v.city "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate>=:start And v.addDate<=:end And v.country=:country And v.province=:province And v.siteId=:siteId And v.country Is Not Null And v.province Is Not Null And v.city Is Not null "
				+ "Group By v.country, v.province, v.city";
		TypedQuery<String> query = this.getEntityManager().createQuery(hql, String.class);
		query.setParameter("start", start);
		query.setParameter("end", end);
		query.setParameter("country", country);
		query.setParameter("province", province);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findPvSumInDayByCountry(final Date date, final String country, final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId = i.uniqueId And v.addDate=:date And v.country=:country And v.siteId=:siteId And v.country Is Not Null "
				+ "Group By v.country";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}
	
	public Long findPvSumInDateIntervalByCountry(final Date start, final Date end, final String country, final Integer siteId){
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And v.country=:country And i.siteId=:siteId And v.country Is Not Null ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("startDate", start);
		query.setParameter("endDate", end);
		query.setParameter("country", country);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}
	
	public Long findPvSumInDayByCountryAndProvince(final Date date, final String country, final String province, final Integer siteId){
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.country=:country And v.province=:province And v.siteId=:siteId And v.country Is Not Null And v.province Is Not Null "
				+ "Group By v.country, v.province";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("province", province);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
			if (result == null) return 0L;
		}catch (Exception e){
		}
		return result;
	}
	
	public Long findPvSumInDateIntervalByCountryAndProvince(final Date start, final Date end, final String country, final String province, final Integer siteId){
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And v.country=:country And v.province=:province And i.siteId=:siteId And v.country Is Not Null And v.province Is Not Null And v.city Is Not Null ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("startDate", start);
		query.setParameter("endDate", end);
		query.setParameter("country", country);
		query.setParameter("province", province);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}
	
	public Long findPvSumInDayByCountryAndProvinceAndCity(final Date date, final String country, final String province, final String city, final Integer siteId){
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And v.country=:country And v.province=:province And v.city=:city And i.siteId=:siteId And v.country Is Not Null And v.province Is Not Null And v.city Is Not Null And v.city Is Not Null ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("province", province);
		query.setParameter("city", city);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Long findUvCountInDayByCountry(final Date date, final String country, final Integer siteId) {
		String hql = "Select Count(Distinct v.uniqueId) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.country=:country And v.siteId=:siteId And v.country Is Not Null "
				+ "Group By v.country";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}
	
	public Long findUvCountInDayByCountryAndProvince(final Date date, final String country, final String province, final Integer siteId){
		String hql = "Select Count(Distinct v.uniqueId) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.country=:country And v.province=:province And v.siteId=:siteId And v.country Is Not Null And v.province Is Not Null "
				+ "Group By v.country, v.province";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("province", province);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
			if (result == null) return 0L;
		}catch(Exception e){
		}
		return result;
	}
	
	public Long findUvCountInDayByCountryAndProvinceAndCity(final Date date, final String country, final String province, final String city, final Integer siteId){
		String hql = "Select Count(Distinct v.uniqueId) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.country=:country And v.province=:province And v.city=:city And v.siteId=:siteId And v.country Is Not Null And v.province Is Not Null And v.city Is Not Null "
				+ "Group By v.country, v.province, v.city";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("province", province);
		query.setParameter("city", city);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
			if (result == null) return 0L;
		}catch(Exception e){
		}
		return result;
	}

	public Long findIpCountInDayByCountry(final Date date, final String country, final Integer siteId) {
		String hql = "Select Count(Distinct v.ip) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.country=:country And v.siteId=:siteId And v.country Is Not Null "
				+ "Group By v.country";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Long findIpCountInDayByCountryAndProvince(final Date date, final String country, final String province, final Integer siteId){
		String hql = "Select Count(Distinct v.ip) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.country=:country And v.province=:province And v.siteId=:siteId And v.country Is Not Null And v.province Is Not Null "
				+ "Group By v.country, v.province";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("province", province);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
			if (result == null) return 0L;
		}catch(Exception e){
		}
		return result;
	}
	
	public Long findIpCountInDayByCountryAndProvinceAndCity(final Date date, final String country, final String province, final String city, final Integer siteId){
		String hql = "Select Count(Distinct v.ip) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.country=:country And v.province=:province And v.city=:city And v.siteId=:siteId And v.country Is Not Null And v.province Is Not Null And v.city Is Not Null "
				+ "Group By v.country, v.province, v.city";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("province", province);
		query.setParameter("city", city);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
			if (result == null) return 0L;
		}catch(Exception e){
		}
		return result;
	}
	
	public List<Long> findStInHour(final Date date, final Integer hour, final Integer siteId) {
		String hql = "Select i.stickTime "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And Hour(i.visitTime)=:hour And i.siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public List<String> findClientNameInDateIntervalByFieldName(final Date startDate, final Date endDate, final String fieldName, final Integer siteId) {
		String hql = "Select v." + fieldName + " " 
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate>=:startDate And v.addDate<=:endDate And v.siteId=:siteId "
				+ "Group By v." + fieldName;
		TypedQuery<String> query = this.getEntityManager().createQuery(hql, String.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findPvSumInDayByStringField(final Date date, final String fieldName, final String fieldValue, final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId = i.uniqueId And v.addDate=:date And v." + fieldName
				+ "=:fieldValue And v.siteId=:siteId " + "Group By v." + fieldName;
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("fieldValue", fieldValue);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Long findPvSumInDayByBooleanField(final Date date, final String fieldName, final Boolean fieldValue, final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId = i.uniqueId And v.addDate=:date And v." + fieldName
				+ "=:fieldValue And v.siteId=:siteId " + "Group By v." + fieldName;
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("fieldValue", fieldValue);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public List<TrafficVo> findArticleByChannelIds(final List<Integer> channelIds, final Integer siteId) {
		List<TrafficVo> list = new ArrayList<TrafficVo>();

		String hql = "Select c.name, a.title, i.url, a.owner, Sum(i.pageView), Avg(i.stickTime) "
				+ "From Visit As v, VisitItem As i, Channel As c, Article As a "
				+ "Where v.uniqueId=i.uniqueId And i.channelId=c.id And i.articleId=a.id And i.siteId=:siteId ";

		if (channelIds != null && !channelIds.isEmpty()) {
			hql += " And i.channelId In(";
			for (Integer channelId : channelIds) {
				hql += channelId + ",";
			}
			hql = hql.substring(0, hql.length() - 1) + ") ";
		}

		hql += " Group By c.name, a.title, i.url, a.owner ";

		TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql, Object[].class);
		query.setParameter("siteId", siteId);

		TrafficVo vo = null;
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			String channelName = (String) result[0];
			String title = (String) result[1];
			String url = (String) result[2];
			String owner = (String) result[3];
			Long sumPv = 0L;
			if (result[4] != null) sumPv = (Long) result[4];
			Long avgSt = 0L;
			if (result[5] != null) avgSt = ((Double) result[5]).longValue();
			vo = new TrafficVo(channelName, title, url, owner, sumPv, avgSt);
			list.add(vo);
		}
		return list;
	}

	public List<TrafficVo> findUrlInDateInterval(final Date startDate, final Date endDate, final Integer siteId) {
		List<TrafficVo> list = new ArrayList<TrafficVo>();

		String hql = "Select i.url, Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId "
				+ "Group By i.url";

		TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql, Object[].class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);

		TrafficVo vo = null;
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			String url = (String) result[0];
			Long sumPv = 0L;
			if (result[1] != null) sumPv = (Long) result[1];
			vo = new TrafficVo(url, sumPv);
			list.add(vo);
		}
		return list;
	}

	public Long findPvSumInDateInterval(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public TrafficVo findChannelInDateIntervalByChannelIds(final Date startDate, final Date endDate, final List<Integer> channelIds, final Integer siteId) {
		if (channelIds == null || channelIds.isEmpty())	return null;

		String hql = "Select Sum(i.pageView), Avg(i.stickTime) From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.channelId In :channelIds And i.siteId=:siteId ";

		TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql, Object[].class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setParameter("channelIds", channelIds);

		TrafficVo vo = null;
		Object[] result = query.getSingleResult();
		Long sumPv = 0L;
		if (result[0] != null)	sumPv = (Long) result[0];
		Long avgSt = 0L;
		if (result[1] != null) avgSt = ((Double) result[1]).longValue();
		vo = new TrafficVo(sumPv, avgSt);
		return vo;
	}

	public TrafficVo findChannelInDateIntervalByChannelParentIdAndChannelIds(final Date startDate, final Date endDate, final Integer channelParentId,
			final List<Integer> channelIds, final Integer siteId) {
		String hql = "Select Sum(i.pageView), Avg(i.stickTime) "
				+ "From Visit As v, VisitItem As i, Channel As c "
				+ "Where v.uniqueId=i.uniqueId And i.channelId=c.id And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId";

		hql += " And i.channelId In(" + channelParentId + ",";
		if (channelIds != null && !channelIds.isEmpty()) {
			for (Integer channelId : channelIds) {
				hql += channelId + ",";
			}
		}
		hql = hql.substring(0, hql.length() - 1) + ")";

		TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql, Object[].class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);

		TrafficVo vo = null;
		try {
			Object[] result = query.getSingleResult();
			Long sumPv = 0L;
			if (result[0] != null) sumPv = (Long) result[0];
			Long avgSt = 0L;
			if (result[1] != null) avgSt = ((Double) result[1]).longValue();
			vo = new TrafficVo(sumPv, avgSt);
		} catch (Exception e) {

		}
		return vo;
	}

	public Long findPvSumInDayByChannelId(final Date date, final Integer channelId, final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.channelId=:channelId And i.siteId=:siteId And i.pageView Is Not Null";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("channelId", channelId);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public List<LoyaltyVo> findFrequencyInDateInterval(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select new " + LOYALTY_CLASS_NAME + "(i.frequency, Count(i.frequency)) " + "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.frequency Is Not Null "
				+ "Group By i.frequency "
				+ "Order By i.frequency Desc";
		TypedQuery<LoyaltyVo> query = this.getEntityManager().createQuery(hql, LoyaltyVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findFrequencyCountInDateInterval(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select Count(i.frequency) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.frequency Is Not Null ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Long findFrequencyInDayByFrequency(final Date date, final Long frequency, final Integer siteId) {
		String hql = "Select Count(i.frequency) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.siteId=:siteId And i.frequency Is Not Null ";

		if (frequency < 31L) {
			hql += " And i.frequency=:frequency ";
		} else {
			hql += " And i.frequency>=:frequency ";
		}

		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		query.setParameter("frequency", frequency);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public List<LoyaltyVo> findDepthInDateInterval(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select new " + LOYALTY_CLASS_NAME + "(i.depth, Count(i.depth)) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.depth Is Not Null "
				+ "Group By i.depth " + "Order By i.depth Desc";
		TypedQuery<LoyaltyVo> query = this.getEntityManager().createQuery(hql, LoyaltyVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findDepthCountInDateInterval(final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select Count(i.depth) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.depth Is Not Null ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Long findDepthCountInDateByDepth(final Date date, final Long depth, final Integer siteId) {
		String hql = "Select Count(i.depth) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.siteId=:siteId And i.depth Is Not Null";

		if (depth < 31L) {
			hql += " And i.depth=:depth ";
		} else {
			hql += " And i.depth>=:depth ";
		}

		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		query.setParameter("depth", depth);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Long findRvCountInDayByRvFlag(final Date date, final Boolean rvFlag, final Integer siteId) {
		String hql = "Select Count(Distinct v.rvFlag) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.siteId=:siteId And v.rvFlag=:rvFlag And v.rvFlag Is Not Null "
				+ "Group By v.addDate ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("rvFlag", rvFlag);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public List<ClickRateVo> findSourceInDay(final Date date, final Integer siteId) {
		String hql = "Select new " + SOURCE_CLASS_NAME + "(i.referer, Count(Distinct v.uniqueId)) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.siteId=:siteId "
				+ "Group By i.referer";
		TypedQuery<ClickRateVo> query = this.getEntityManager().createQuery(hql, ClickRateVo.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findUvCountInDayByDomain(final Date date, final String domain, final Integer siteId) {
		String hql = "Select Count(Distinct v.uniqueId) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And i.referer Like :domain And v.siteId=:siteId ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("domain", "http://%." + domain + "%");
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Long findUvCountInDayByWebSite(final Date date, final String webSite, final Integer siteId) {
		String hql = "Select Count(Distinct v.uniqueId) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And i.referer Like :webSite And v.siteId=:siteId ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("webSite", "http://" + webSite + "/%");
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
		}
		return result;
	}

	public Map<Date, Long> findIpMaxValue(final Integer siteId) {
		Map<Date, Long> map = new LinkedHashMap<Date, Long>();
		String hql = "Select v.addDate, Count(Distinct v.ip) As cIp "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.siteId=:siteId "
				+ "Group By v.addDate "
				+ "Order By cIp Desc";

		TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql, Object[].class);
		query.setParameter("siteId", siteId);
		try {
			List<Object[]> results = query.getResultList();
			Object[] result = results.get(0);
			Date date = (Date) result[0];
			Long maxIp = 0L;
			if (result[1] != null) maxIp = (Long) result[1];
			map.put(date, maxIp);
		} catch (Exception e) {
		}
		return map;
	}

	public Map<Date, Long> findUvMaxValue(final Integer siteId) {
		Map<Date, Long> map = new LinkedHashMap<Date, Long>();
		String hql = "Select v.addDate, Count(Distinct v.uniqueId) As cUv "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.siteId=:siteId "
				+ "Group By v.addDate "
				+ "Order By cUv Desc";

		TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql, Object[].class);
		query.setParameter("siteId", siteId);
		try {
			List<Object[]> results = query.getResultList();
			Object[] result = results.get(0);
			Date date = (Date) result[0];
			Long maxIp = 0L;
			if (result[1] != null) maxIp = (Long) result[1];
			map.put(date, maxIp);
		} catch (Exception e) {
		}
		return map;
	}

	public Map<Date, Long> findPvMaxValue(final Integer siteId) {
		Map<Date, Long> map = new LinkedHashMap<Date, Long>();
		String hql = "Select v.addDate, Sum(i.pageView) As sPv "
		        + "From Visit As v, VisitItem As i " 
		        + "Where v.uniqueId=i.uniqueId And v.siteId=:siteId "
				+ "Group By v.addDate "
		        + "Order By sPv Desc";

		TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql, Object[].class);
		query.setParameter("siteId", siteId);
		try {
			List<Object[]> results = query.getResultList();
			Object[] result = results.get(0);
			Date date = (Date) result[0];
			Long maxIp = 0L;
			if (result[1] != null) maxIp = (Long) result[1];
			map.put(date, maxIp);
		} catch (Exception e) {
		}
		return map;
	}

	/**
	 * 人员发布统计
	 * 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param siteId 站点编号
	 * @param channelId 频道编号
	 * @return List PublishedVo对象集合
	 */
	public List<PublishedVo> findStaffReleased(final Date start, final Date end, final Integer siteId, final Integer channelId) {
		String hql = "Select new " + PUBLISHED_CLASS_NAME + "(o.name, i.username, i.name "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a, com.ewcms.core.site.model.Channel As c Left Join c.site As s "
				+ "      Where m.reference = false and a.delete = false and a.status = 'DRAFT' and a.owner=i.username And m.channelId=c.id "
				+ "            And s.id=:siteId @startCreate@ @endCreate@ @channelId@) As draftSum "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a, com.ewcms.core.site.model.Channel As c Left Join c.site As s "
				+ "      Where m.reference = false and a.delete = false and a.status = 'REEDIT' and a.owner=i.username And m.channelId=c.id "
				+ "            And s.id=:siteId @startModified@ @endModified@ @channelId@) As reeditSum "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a, com.ewcms.core.site.model.Channel As c Left Join c.site As s "
				+ "      Where m.reference = false and a.delete = false and a.status = 'REVIEW' and a.owner=i.username And m.channelId=c.id "
				+ "            And s.id=:siteId @startModified@ @endModified@ @channelId@) As reviewSum "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a, com.ewcms.core.site.model.Channel As c Left Join c.site As s "
				+ "      Where m.reference = false and a.delete = false and a.status = 'RELEASE' and a.owner=i.username And m.channelId=c.id "
				+ "            And s.id=:siteId @startPublished@ @endPublished@ @channelId@) As releaseSum) "
				+ "From com.ewcms.security.manage.model.User As u Left Join u.userInfo As i Left Join u.organ As o "
				+ "Group By o.name, i.username, i.name "
				+ "Order By releaseSum Desc, reviewSum Desc, reeditSum Desc, draftSum Desc";
		
		String startCreateTimeHql = " And a.createTime>=:start ";
		String endCreateTimeHql = " And a.createTime<=:end ";
		String startModifiedHql = " And a.modified>=:start ";
		String endModifiedHql = " And a.modified<=:end ";
		String startPublishedHql = " And a.published>=:start ";
		String endPublishedHql = " And a.published<=:end ";
		String channelIdHql = " And c.id=:channelId ";
		
		if (start != null){
			hql = hql.replace("@startCreate@", startCreateTimeHql).replace("@startModified@", startModifiedHql).replace("@startPublished@", startPublishedHql);
		}else{
			hql = hql.replace("@startCreate@", "").replace("@startModified@", "").replace("@startPublished@", "");
		}
		
		if (end != null){
			hql = hql.replace("@endCreate@", endCreateTimeHql).replace("@endModified@", endModifiedHql).replace("@endPublished@", endPublishedHql);
		}else{
			hql = hql.replace("@endCreate@", "").replace("@endModified@", "").replace("@endPublished@", "");
		}
		
		if (channelId != null){
			hql = hql.replace("@channelId@", channelIdHql);
		}else{
			hql = hql.replace("@channelId@", "");
		}
		
		TypedQuery<PublishedVo> query = this.getEntityManager().createQuery(hql, PublishedVo.class);
		
		query.setParameter("siteId", siteId);
		if (start != null){
			query.setParameter("start", start);
		}
		if (end != null){
			query.setParameter("end", end);
		}
		if (channelId != null){
			query.setParameter("channelId", channelId);
		}
		
		return query.getResultList();
	}
	
	/**
	 * 栏目发布统计
	 * 
	 * @param start 开始日期
	 * @param end 结束日期
	 * @param siteId 站点编号
	 */
	public PublishedVo findChannelReleased(final Date start, final Date end, final Integer channelId, final Integer siteId){
		String hql = "Select new " + PUBLISHED_CLASS_NAME + "(c.name "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a "
				+ "      Where m.reference = false and a.delete = false and a.status = 'DRAFT' and m.channelId=c.id "
				+ "            @startCreate@ @endCreate@) As draftSum "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a "
				+ "      Where m.reference = false and a.delete = false and a.status = 'REEDIT' and m.channelId=c.id "
				+ "            @startModified@ @endModified@) As reeditSum "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a "
				+ "      Where m.reference = false and a.delete = false and a.status = 'REVIEW' and m.channelId=c.id "
				+ "            @startModified@ @endModified@) As reviewSum "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a "
				+ "      Where m.reference = false and a.delete = false and a.status = 'RELEASE' and m.channelId=c.id "
				+ "            @startPublished@ @endPublished@) As releaseSum) "
				+ "From com.ewcms.core.site.model.Channel As c Left Join c.site As s "
				+ "Where c.id=:channelId And s.id=:siteId "
				+ "Group By c.id, c.name ";
		
		String startCreateTimeHql = " And a.createTime>=:start ";
		String endCreateTimeHql = " And a.createTime<=:end ";
		String startModifiedHql = " And a.modified>=:start ";
		String endModifiedHql = " And a.modified<=:end ";
		String startPublishedHql = " And a.published>=:start ";
		String endPublishedHql = " And a.published<=:end ";
		
		if (start != null){
			hql = hql.replace("@startCreate@", startCreateTimeHql).replace("@startModified@", startModifiedHql).replace("@startPublished@", startPublishedHql);
		}else{
			hql = hql.replace("@startCreate@", "").replace("@startModified@", "").replace("@startPublished@", "");
		}
		
		if (end != null){
			hql = hql.replace("@endCreate@", endCreateTimeHql).replace("@endModified@", endModifiedHql).replace("@endPublished@", endPublishedHql);
		}else{
			hql = hql.replace("@endCreate@", "").replace("@endModified@", "").replace("@endPublished@", "");
		}
		
		TypedQuery<PublishedVo> query = this.getEntityManager().createQuery(hql, PublishedVo.class);
		
		query.setParameter("siteId", siteId);
		query.setParameter("channelId", channelId);
		if (start != null){
			query.setParameter("start", start);
		}
		if (end != null){
			query.setParameter("end", end);
		}
		
		try{
			return query.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 组织机构发布统计
	 * 
	 * @param start 开始日期
	 * @param end 结束日期
	 * @param siteId 站点编号
	 */
	public PublishedVo findOrganReleased(final Date start, final Date end, final Integer organId, final Integer siteId){
		String hql = "Select new " + PUBLISHED_CLASS_NAME + "(o.name "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a "
				+ "    ,com.ewcms.core.site.model.Channel As c Left Join c.site As s "
				+ "      Where m.reference = false and a.delete = false and a.status = 'DRAFT' and a.owner=u.username"
				+ "            and s.id=:siteId and m.channelId=c.id "
				+ "            @startCreate@ @endCreate@) As draftSum "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a "
				+ "    ,com.ewcms.core.site.model.Channel As c Left Join c.site As s "
				+ "      Where m.reference = false and a.delete = false and a.status = 'REEDIT' and a.owner=u.username "
				+ "            and s.id=:siteId and m.channelId=c.id "
				+ "            @startModified@ @endModified@) As reeditSum "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a "
				+ "    ,com.ewcms.core.site.model.Channel As c Left Join c.site As s "
				+ "      Where m.reference = false and a.delete = false and a.status = 'REVIEW' and a.owner=u.username "
				+ "            and s.id=:siteId and m.channelId=c.id "
				+ "            @startModified@ @endModified@) As reviewSum "
				+ ",(Select Count(a.id) From ArticleMain As m Left Join m.article As a "
				+ "    ,com.ewcms.core.site.model.Channel As c Left Join c.site As s "
				+ "      Where m.reference = false and a.delete = false and a.status = 'RELEASE' and a.owner=u.username "
				+ "            and s.id=:siteId and m.channelId=c.id "
				+ "            @startPublished@ @endPublished@) As releaseSum) "
				+ "From com.ewcms.security.manage.model.User As u Right Join u.organ As o "
				+ "Where o.id=:organId "
				+ "Group By o.id, o.name, u.username ";
		
		String startCreateTimeHql = " And a.createTime>=:start ";
		String endCreateTimeHql = " And a.createTime<=:end ";
		String startModifiedHql = " And a.modified>=:start ";
		String endModifiedHql = " And a.modified<=:end ";
		String startPublishedHql = " And a.published>=:start ";
		String endPublishedHql = " And a.published<=:end ";
		
		if (start != null){
			hql = hql.replace("@startCreate@", startCreateTimeHql).replace("@startModified@", startModifiedHql).replace("@startPublished@", startPublishedHql);
		}else{
			hql = hql.replace("@startCreate@", "").replace("@startModified@", "").replace("@startPublished@", "");
		}
		
		if (end != null){
			hql = hql.replace("@endCreate@", endCreateTimeHql).replace("@endModified@", endModifiedHql).replace("@endPublished@", endPublishedHql);
		}else{
			hql = hql.replace("@endCreate@", "").replace("@endModified@", "").replace("@endPublished@", "");
		}
		
		TypedQuery<PublishedVo> query = this.getEntityManager().createQuery(hql, PublishedVo.class);
		
		query.setParameter("siteId", siteId);
		query.setParameter("organId", organId);
		if (start != null){
			query.setParameter("start", start);
		}
		if (end != null){
			query.setParameter("end", end);
		}
		
		try{
			return query.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}	
	/**
	 * 政民互动统计
	 * 
	 * @param start 开始日期
	 * @param end 结束日期
	 * @param organId 组织编号
	 */
	public InteractiveVo findInteractive(final Date start, final Date end, final Integer organId){
		String hql = "Select new " + Interactive_CLASS_NAME + "(o.name "
				+ ",(Select Count(i.id) From Interaction As i Where i.type=1 And i.state=0 and i.organId=o.id" 
				+ "            @startDate@ @endDate@) As zxblCount"
				+ ",(Select Count(i.id) From Interaction As i Where i.type=1 And i.state=1 and i.organId=o.id" 
				+ "            @startReplayDate@ @endReplayDate@) As zxhfCount"
				+ ",(Select Count(i.id) From Interaction As i Where i.type=1 And i.checked=true And i.organId=o.id"
				+ "            @startReplayDate@ @endReplayDate@) As zxtgCount"
				+ ",(Select Count(i.id) From Interaction As i Where i.type=1 And i.checked=false And i.organId=o.id"
				+ "            @startDate@ @endDate@) As zxwtgCount"
				+ ",(Select Count(i.id) From Interaction As i Where i.type=2 And i.state=0 and i.organId=o.id"
				+ "            @startDate@ @endDate@) As tsblCount"
				+ ",(Select Count(i.id) From Interaction As i Where i.type=2 And i.state=1 and i.organId=o.id"
				+ "            @startReplayDate@ @endReplayDate@) As tshfCount"
				+ ",(Select Count(i.id) From Interaction As i Where i.type=2 And i.checked=true And i.organId=o.id"
				+ "            @startReplayDate@ @endReplayDate@) As tstgCount"
				+ ",(Select Count(i.id) From Interaction As i Where i.type=2 And i.checked=false And i.organId=o.id"
				+ "            @startDate@ @endDate@) As tswtgCount"
				+ ",(Select Count(i.id) From Interaction As i Where i.type=3 And i.state=0 and i.organId=o.id"
				+ "            @startDate@ @endDate@) As jyblCount"
				+ ",(Select Count(i.id) From Interaction As i Where i.type=3 And i.state=1 and i.organId=o.id"
				+ "            @startReplayDate@ @endReplayDate@) As jyhfCount"
				+ ",(Select Count(i.id) From Interaction As i Where i.type=3 And i.checked=true And i.organId=o.id"
				+ "            @startReplayDate@ @endReplayDate@) As jytgCount"
				+ ",(Select Count(i.id) From Interaction As i Where i.type=3 And i.checked=false And i.organId=o.id"
				+ "            @startDate@ @endDate@) As jytgCount) "
				+ "From Organ As o "
				+ "Where o.id=:organId "
				+ "Group By o.id, o.name";
		
		String startDate = " And i.date>=:start ";
		String endDate = " And i.date<=:end ";
		String startReplayDate = " And i.replayDate>=:start ";
		String endReplayDate = " And i.replayDate<=:end ";
		
		if (start != null){
			hql = hql.replace("@startDate@", startDate).replace("@startReplayDate@", startReplayDate);
		}else{
			hql = hql.replace("@startDate@", "").replace("@startReplayDate@", "");
		}
		
		if (end != null){
			hql = hql.replace("@endDate@", endDate).replace("@endReplayDate@", endReplayDate);
		}else{
			hql = hql.replace("@endDate@", "").replace("@endReplayDate@", "");
		}
		
		TypedQuery<InteractiveVo> query = this.getEntityManager().createQuery(hql, InteractiveVo.class);
		query.setParameter("organId", organId);

		if (start != null){
			query.setParameter("start", start);
		}
		if (end != null){
			query.setParameter("end", end);
		}
		
		try{
			return query.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 网上咨询统计
	 * 
	 * @param start 开始日期
	 * @param end 结束日期
	 * @param organId 组织编号
	 */
	public InteractiveVo findAdvisory(final Date start, final Date end, final Integer organId){
		String hql = "Select new " + Interactive_CLASS_NAME + "(o.name "
				+ ",(Select Count(i.id) From Advisor As i Where i.state=1 and i.organ.id=o.id" 
				+ "            @startReplayDate@ @endReplayDate@) As tgCount"
				+ ",(Select Count(i.id) From Advisor As i Where i.state=1 and i.organ.id=o.id" 
				+ "            @startDate@ @endDate@) As wtgCount)"
				+ "From Organ As o "
				+ "Where o.id=:organId "
				+ "Group By o.id, o.name";
		
		String startDate = " And i.date>=:start ";
		String endDate = " And i.date<=:end ";
		String startReplayDate = " And i.replayDate>=:start ";
		String endReplayDate = " And i.replayDate<=:end ";
		
		if (start != null){
			hql = hql.replace("@startDate@", startDate).replace("@startReplayDate@", startReplayDate);
		}else{
			hql = hql.replace("@startDate@", "").replace("@startReplayDate@", "");
		}
		
		if (end != null){
			hql = hql.replace("@endDate@", endDate).replace("@endReplayDate@", endReplayDate);
		}else{
			hql = hql.replace("@endDate@", "").replace("@endReplayDate@", "");
		}
		
		TypedQuery<InteractiveVo> query = this.getEntityManager().createQuery(hql, InteractiveVo.class);
		query.setParameter("organId", organId);

		if (start != null){
			query.setParameter("start", start);
		}
		if (end != null){
			query.setParameter("end", end);
		}
		
		try{
			return query.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
}