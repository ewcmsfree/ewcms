/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.visit.manager.vo.TrafficVo;
import com.ewcms.plugin.visit.manager.vo.EntryAndExitVo;
import com.ewcms.plugin.visit.manager.vo.LoyaltyVo;
import com.ewcms.plugin.visit.manager.vo.RecentlyVisitedVo;
import com.ewcms.plugin.visit.manager.vo.ClickRateVo;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
import com.ewcms.plugin.visit.model.Visit;
import com.ewcms.plugin.visit.util.DateTimeUtil;
import com.ewcms.plugin.visit.util.VisitUtil;

/**
 * 访问DAO
 * 
 * @author wu_zhijun
 * 
 */
@Repository
public class VisitDAO extends JpaDAO<Long, Visit> {

	private static final String SUMMARY_CLASS_NAME = SummaryVo.class
			.getPackage().getName() + "." + SummaryVo.class.getSimpleName();
	private static final String ENTRYANDEXIT_CLASS_NAME = EntryAndExitVo.class
			.getPackage().getName()
			+ "."
			+ EntryAndExitVo.class.getSimpleName();
	private static final String RECENTLYVISITED_CLASS_NAME = RecentlyVisitedVo.class
			.getPackage().getName()
			+ "."
			+ RecentlyVisitedVo.class.getSimpleName();
	private static final String LOYALTY_CLASS_NAME = LoyaltyVo.class
			.getPackage().getName() + "." + LoyaltyVo.class.getSimpleName();
	private static final String SOURCE_CLASS_NAME = ClickRateVo.class.getPackage().getName() + "." + ClickRateVo.class.getSimpleName();

	/**
	 * 查询开始统计分析最早的日期
	 * 
	 * @param siteId
	 *            站点编号
	 * @return String 日期字符串
	 */
	public String findFirstDate(final Integer siteId) {
		String hql = "From Visit Where siteId=:siteId Order By addDate";
		TypedQuery<Visit> query = this.getEntityManager().createQuery(hql,
				Visit.class);
		query.setParameter("siteId", siteId);
		List<Visit> list = query.getResultList();
		if (list == null || list.isEmpty())
			return "";
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
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
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
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
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
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
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
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
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
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
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
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
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
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
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
	public Visit findVisitByVisitPK(final String uniqueId, final Date date,
			final String ip) {
		String hql = "Select v From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.uniqueId=:uniqueId And v.addDate=:date And v.ip=:ip "
				+ "Order by v.addDate Desc";
		TypedQuery<Visit> query = this.getEntityManager().createQuery(hql,
				Visit.class);
		query.setParameter("uniqueId", uniqueId);
		query.setParameter("date", date);
		query.setParameter("ip", ip);

		List<Visit> list = query.getResultList();
		if (list == null || list.isEmpty())
			return null;
		return list.get(0);
	}

	/**
	 * 返回最近访问记录
	 * 
	 * @param siteId
	 *            站点编号
	 * @return List
	 */
	public List<RecentlyVisitedVo> findAcRecordInDateInterval(
			final Date startDate, final Date endDate, final Integer siteId) {
		String hql = "Select new "
				+ RECENTLYVISITED_CLASS_NAME
				+ "(v.ip, v.country, i.url, i.visitDate, i.visitTime, i.referer, v.browser, v.os, v.screen, v.language, v.flashVersion) "
				+ "From VisitItem As i, Visit As v "
				+ "Where i.uniqueId = v.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId "
				+ "Order By i.visitDate Desc, i.visitTime Desc";
		TypedQuery<RecentlyVisitedVo> query = this.getEntityManager()
				.createQuery(hql, RecentlyVisitedVo.class);
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
	public Long findPvCountInHour(final Date date, final Integer hour,
			final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And Hour(i.visitTime)=:hour And i.siteId=:siteId ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null)
				result = 0L;
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
	public Long findIpCountInHour(final Date date, final Integer hour,
			final Integer siteId) {
		String hql = "Select Count(Distinct v.ip) From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And Hour(i.visitTime)=:hour And i.siteId=:siteId ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
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
	public Long findUvCountInHour(final Date date, final Integer hour,
			final Integer siteId) {
		String hql = "Select Count(Distinct v.uniqueId) "
				+ "From Visit As v, VisitItem As i Where v.uniqueId=i.uniqueId And i.visitDate=:date And Hour(i.visitTime)=:hour And i.siteId=:siteId ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
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
	public Long findRvCountInHour(final Date date, final Integer hour,
			final Integer siteId) {
		String hql = "Select Count(v.rvFlag) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And Hour(i.visitTime)=:hour And v.siteId=:siteId And v.rvFlag=true "
				+ "Group By v.ip, v.uniqueId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
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
	public List<EntryAndExitVo> findEntrance(final Date startDate,
			final Date endDate, final Integer siteId) {
		String hql = "Select new "
				+ ENTRYANDEXIT_CLASS_NAME
				+ "(i.url, Count(i.url)) "
				+ "From Visit As v, VisitItem As i Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.url Is Not Null "
				+ "Group By i.url " + "Order By Count(i.url) Desc";
		TypedQuery<EntryAndExitVo> query = this.getEntityManager().createQuery(
				hql, EntryAndExitVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findUrlSumInEntrance(final Date startDate, final Date endDate,
			final Integer siteId) {
		String hql = "Select Count(i.url) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.url Is Not Null";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
		}
		return result;
	}

	public Long findPvCountInEntrance(final Date date, final String url,
			final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.url=:url And i.siteId=:siteId And i.url Is Not Null "
				+ "Group By i.visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
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

	/**
	 * 出口
	 * 
	 * @param startDate
	 * @param endDate
	 * @param siteId
	 * @return
	 */
	public List<EntryAndExitVo> findExit(final Date startDate,
			final Date endDate, final Integer siteId) {
		String hql = "Select new "
				+ ENTRYANDEXIT_CLASS_NAME
				+ "(i.url, Count(i.url)) "
				+ "From Visit As v, VisitItem As i Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.url Is Not Null And i.event=:event "
				+ "Group By url " + "Order By Count(i.url) Desc";
		TypedQuery<EntryAndExitVo> query = this.getEntityManager().createQuery(
				hql, EntryAndExitVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setParameter("event", VisitUtil.UNLOAD_EVENT);
		return query.getResultList();
	}

	public Long findUrlSumInExit(final Date startDate, final Date endDate,
			final Integer siteId) {
		String hql = "Select Count(i.url) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.url Is Not Null And i.event=:event";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setParameter("event", VisitUtil.UNLOAD_EVENT);
		Long result = 0L;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
		}
		return result;
	}

	public Long findPvCountInExit(final Date date, final String url,
			final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.url=:url And i.siteId=:siteId And i.event=:event And i.url Is Not Null "
				+ "Group By i.visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
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

	public List<SummaryVo> findHost(final Date startDate, final Date endDate,
			final Integer siteId) {
		String hql = "Select new "
				+ SUMMARY_CLASS_NAME
				+ "(i.host, Sum(i.pageView)) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.host Is Not Null "
				+ "Group By i.host " + "Order By Sum(i.pageView) Desc";
		TypedQuery<SummaryVo> query = this.getEntityManager().createQuery(hql,
				SummaryVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findPvSumInHost(final Date startDate, final Date endDate,
			final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.host Is Not Null";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
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

	public Long findPvCountInHost(final Date date, final String host,
			final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.host=:host And i.siteId=:siteId And i.host Is Not Null "
				+ "Group By i.visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
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

	public Long findPvSumInCountry(final Date startDate, final Date endDate,
			final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
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

	public List<String> findCountryName(final Date startDate,
			final Date endDate, final Integer siteId) {
		String hql = "Select v.country "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate>=:startDate And v.addDate<=:endDate And v.siteId=:siteId "
				+ "Group By v.country";
		TypedQuery<String> query = this.getEntityManager().createQuery(hql,
				String.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findPvSumInCountryByCountryName(final Date date,
			final String country, final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId = i.uniqueId And v.addDate=:date And v.country=:country And v.siteId=:siteId "
				+ "Group By v.country";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
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

	public Long findUvCountInDayByCountryName(final Date date,
			final String country, final Integer siteId) {
		String hql = "Select Count(Distinct v.uniqueId) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.country=:country And v.siteId=:siteId "
				+ "Group By v.country";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
		}
		return result;
	}

	public Long findIpCountInDayByCountryName(final Date date,
			final String country, final Integer siteId) {
		String hql = "Select Count(Distinct v.ip) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.country=:country And v.siteId=:siteId "
				+ "Group By v.country";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
		}
		return result;
	}

	public List<Long> findOnline(final Date date, final Integer hour,
			final Integer siteId) {
		String hql = "Select i.stickTime "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And Hour(i.visitTime)=:hour And i.siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public List<String> findClientName(final Date startDate,
			final Date endDate, final String fieldName, final Integer siteId) {
		String hql = "Select v."
				+ fieldName
				+ " "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate>=:startDate And v.addDate<=:endDate And v.siteId=:siteId "
				+ "Group By v." + fieldName;
		TypedQuery<String> query = this.getEntityManager().createQuery(hql,
				String.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findPvSumInDayByStringField(final Date date,
			final String fieldName, final String fieldValue,
			final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId = i.uniqueId And v.addDate=:date And v."
				+ fieldName + "=:fieldValue And v.siteId=:siteId "
				+ "Group By v." + fieldName;
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
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

	public Long findPvSumInDayByBooleanField(final Date date,
			final String fieldName, final Boolean fieldValue,
			final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId = i.uniqueId And v.addDate=:date And v."
				+ fieldName + "=:fieldValue And v.siteId=:siteId "
				+ "Group By v." + fieldName;
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
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

	public List<TrafficVo> findArticle(final List<Integer> channelIds,
			final Integer siteId) {
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

		TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql,
				Object[].class);
		query.setParameter("siteId", siteId);

		TrafficVo vo = null;
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			String channelName = (String) result[0];
			String title = (String) result[1];
			String url = (String) result[2];
			String owner = (String) result[3];
			Long sumPv = 0L;
			try {
				sumPv = (Long) result[4];
			} catch (Exception e) {
			}
			Long avgSt = 0L;
			try {
				avgSt = ((Double) result[5]).longValue();
			} catch (Exception e) {

			}
			vo = new TrafficVo(channelName, title, url, owner, sumPv, avgSt);
			list.add(vo);
		}
		return list;
	}

	public List<TrafficVo> findUrl(final Date startDate, final Date endDate,
			final Integer siteId) {
		List<TrafficVo> list = new ArrayList<TrafficVo>();

		String hql = "Select i.url, Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId "
				+ "Group By url";

		TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql,
				Object[].class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);

		TrafficVo vo = null;
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			String url = (String) result[0];
			Long sumPv = 0L;
			try {
				sumPv = (Long) result[1];
			} catch (Exception e) {
			}
			vo = new TrafficVo(url, sumPv);
			list.add(vo);
		}

		return list;
	}

	public Long findPvSum(final Date startDate, final Date endDate,
			final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
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

	public Long findPvCountInDayByUrl(final Date date, final String url,
			final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.url=:url And i.siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("url", url);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
			return 0L;
		}
		return result;
	}

	public List<TrafficVo> findChannel(final Date startDate, final Date endDate,
			final Integer channelParentId, final Integer siteId) {
		List<TrafficVo> list = new ArrayList<TrafficVo>();

		if (channelParentId == null)
			return list;

		String hql = "Select i.channelId, c.name From Visit As v, VisitItem As i, Channel As c "
				+ "Where v.uniqueId=i.uniqueId And i.channelId=c.id And i.visitDate>=:startDate And i.visitDate<=:endDate And c.parent.id=:channelParentId And i.siteId=:siteId "
				+ "Group By i.channelId, c.name";

		TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql,
				Object[].class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setParameter("channelParentId", channelParentId);

		TrafficVo vo = null;
		List<Object[]> results = query.getResultList();
		for (Object[] result : results) {
			Integer channelId = (Integer) result[0];
			String channelName = (String) result[1];
			Long sumPv = 0L;
			try {
				sumPv = (Long) result[2];
			} catch (Exception e) {
			}
			Long avgSt = 0L;
			try {
				avgSt = ((Double) result[3]).longValue();
			} catch (Exception e) {

			}
			vo = new TrafficVo(channelId, channelName, sumPv, avgSt);
			list.add(vo);
		}
		return list;
	}

	public TrafficVo findChannelThisLevelAndChildren(final Date startDate,
			final Date endDate, final Integer channelParentId,
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

		TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql,
				Object[].class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);

		TrafficVo vo = null;
		try {
			Object[] result = query.getSingleResult();
			Long sumPv = 0L;
			try {
				sumPv = (Long) result[0];
			} catch (Exception e) {
			}
			Long avgSt = 0L;
			try {
				avgSt = ((Double) result[1]).longValue();
			} catch (Exception e) {
			}
			vo = new TrafficVo(sumPv, avgSt);
		} catch (Exception e) {

		}
		return vo;
	}

	public Long findPvCountInDayByChannelId(final Date date,
			final Integer channelId, final Integer siteId) {
		String hql = "Select Sum(i.pageView) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.channelId=:channelId And i.siteId=:siteId And i.pageView Is Not Null";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("channelId", channelId);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
			if (result == null) return 0L;
		} catch (Exception e) {
			return 0L;
		}
		return result;
	}

	public List<LoyaltyVo> findFrequency(final Date startDate,
			final Date endDate, final Integer siteId) {
		String hql = "Select new "
				+ LOYALTY_CLASS_NAME
				+ "(i.frequency, Count(i.frequency)) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.frequency Is Not Null "
				+ "Group By i.frequency " + "Order By i.frequency Desc";
		TypedQuery<LoyaltyVo> query = this.getEntityManager().createQuery(hql,
				LoyaltyVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findFrequencySum(final Date startDate, final Date endDate,
			final Integer siteId) {
		String hql = "Select Count(i.frequency) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.frequency Is Not Null ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
		}
		return result;
	}

	public Long findFrequencyTrend(final Date date, final Long frequency,
			final Integer siteId) {
		String hql = "Select Count(i.frequency) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.siteId=:siteId And i.frequency Is Not Null ";

		if (frequency < 31L) {
			hql += " And i.frequency=:frequency ";
		} else {
			hql += " And i.frequency>=:frequency ";
		}

		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		query.setParameter("frequency", frequency);
		Long result = 0L;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
		}
		return result;
	}

	public List<LoyaltyVo> findDepth(final Date startDate, final Date endDate,
			final Integer siteId) {
		String hql = "Select new "
				+ LOYALTY_CLASS_NAME
				+ "(i.depth, Count(i.depth)) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.depth Is Not Null "
				+ "Group By i.depth " + "Order By i.depth Desc";
		TypedQuery<LoyaltyVo> query = this.getEntityManager().createQuery(hql,
				LoyaltyVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}

	public Long findDepthSum(final Date startDate, final Date endDate,
			final Integer siteId) {
		String hql = "Select Count(i.depth) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId And i.depth Is Not Null ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
		}
		return result;
	}

	public Long findDepthTrend(final Date date, final Long depth,
			final Integer siteId) {
		String hql = "Select Count(i.depth) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.siteId=:siteId And i.depth Is Not Null";

		if (depth < 31L) {
			hql += " And i.depth=:depth ";
		} else {
			hql += " And i.depth>=:depth ";
		}

		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		query.setParameter("depth", depth);
		Long result = 0L;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
		}
		return result;
	}

	public Long findVisitorCountInDay(final Date date, final Boolean rvFlag, final Integer siteId) {
		String hql = "Select Count(Distinct v.rvFlag) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.siteId=:siteId And v.rvFlag=:rvFlag And v.rvFlag Is Not Null "
				+ "Group By v.addDate ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("rvFlag", rvFlag);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
		}
		return result;
	}
	
	public List<ClickRateVo> findSourceCountInDay(final Date date, final Integer siteId){
		String hql = "Select new " + SOURCE_CLASS_NAME + "(i.referer, Count(Distinct v.uniqueId)) " +
				     "From Visit As v, VisitItem As i " +
				     "Where v.uniqueId=i.uniqueId And v.addDate=:date And v.siteId=:siteId " +
				     "Group By i.referer";
		TypedQuery<ClickRateVo> query = this.getEntityManager().createQuery(hql, ClickRateVo.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}
	
	public Long findUvCountInDayByDomain(final Date date, final String domain, final Integer siteId) {
		String hql = "Select Count(Distinct v.uniqueId) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And i.referer Like :domain And v.siteId=:siteId ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("domain", "http://%." + domain + "%");
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
		}
		return result;
	}

	public Long findUvCountInDayByWebSite(final Date date, final String webSite, final Integer siteId){
		String hql = "Select Count(Distinct v.uniqueId) "
				+ "From Visit As v, VisitItem As i "
				+ "Where v.uniqueId=i.uniqueId And v.addDate=:date And i.referer Like :webSite And v.siteId=:siteId "
				+ "Group By v.uniqueId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql,
				Long.class);
		query.setParameter("date", date);
		query.setParameter("webSite", "http://" + webSite + "/%");
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
		}
		return result;
	}
}
