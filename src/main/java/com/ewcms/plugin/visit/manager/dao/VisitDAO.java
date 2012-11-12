/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.visit.manager.vo.ArticleVisitVo;
import com.ewcms.plugin.visit.manager.vo.InAndExitVo;
import com.ewcms.plugin.visit.manager.vo.LastVisitVo;
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
	
	private static final String SUMMARY_CLASS_NAME = SummaryVo.class.getPackage().getName() + "." + SummaryVo.class.getSimpleName();
	private static final String INANDEXIT_CLASS_NAME = InAndExitVo.class.getPackage().getName() + "." + InAndExitVo.class.getSimpleName();
	private static final String LASTVISIT_CLASS_NAME = LastVisitVo.class.getPackage().getName() + "." + LastVisitVo.class.getSimpleName();
	private static final String ARTICLE_CLASS_NAME = ArticleVisitVo.class.getPackage().getName() + "." + ArticleVisitVo.class.getSimpleName();
	
	/**
	 * 查询开始统计分析最早的日期
	 * 
	 * @param siteId 站点编号
	 * @return String 日期字符串
	 */
	public String findFirstDate(final Integer siteId){
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
	 * @param date 访问日期
	 * @param siteId 站点编号
	 * @return Integer 
	 */
	public Long findIpCountInDay(final Date date, final Integer siteId){
		String hql = "Select Count(Distinct ip) From Visit Where addDate=:date And siteId=:siteId Group By addDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 查询UV（用户访问）量
	 * 
	 * @param date 访问日期
	 * @param siteId 站点编号
	 * @return Integer
	 */
	public Long findUvCountInDay(final Date date, final Integer siteId){
		String hql = "Select Count(Distinct uniqueId) From Visit Where addDate=:date And siteId=:siteId Group By addDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 查询PV（页面访问）量
	 * 
	 * @param date 访问日期
	 * @param siteId 站点编号
	 * @return Integer
	 */
	public Long findPvCountInDay(final Date date, final Integer siteId){
		String hql = "Select Sum(pageView) From VisitItem Where visitDate=:date And siteId=:siteId Group By visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 查询RV（回头率）量
	 * 
	 * @param date 访问日期
	 * @param siteId 站点编号
	 * @return Integer
	 */
	public Long findRvCountInDay(final Date date, final Integer siteId){
		String hql = "Select Count(rvFlag) From Visit Where addDate=:date And siteId=:siteId And rvFlag=true";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
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
	public Long findAccessCountInDay(final Date date, final Integer siteId){
		String hql = "Select Count(uniqueId) From Visit Where addDate=:date And siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 查询访问时长合计
	 * 
	 * @param date 访问日期
	 * @param siteId 站点编号
	 * @return Integer
	 */
	public Long findStickTimeSumInDay(final Date date, final Integer siteId){
		String hql = "Select Sum(stickTime) From VisitItem Where visitDate=:date And siteId=:siteId Group By visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 查询访问时长记录数
	 * 
	 * @param date 访问日期
	 * @param siteId 站点编号
	 * @return Long
	 */
	public Long findStickTimeCountInDay(final Date date, final Integer siteId){
		String hql = "Select Count(stickTime) From VisitItem Where visitDate=:date And siteId=:siteId Group By visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 查询访问记录是否存在
	 * 
	 * @param uniqueId 用户编号
	 * @param date 访问日期
	 * @param ip IP
	 * @return Visit
	 */
	public Visit findVisitByVisitPK(final String uniqueId, final Date date, final String ip){
		String hql = "From Visit Where uniqueId=:uniqueId And addDate=:addDate And ip=:ip Order by addDate Desc";
		TypedQuery<Visit> query = this.getEntityManager().createQuery(hql, Visit.class);
		query.setParameter("uniqueId", uniqueId);
		query.setParameter("addDate", date);
		query.setParameter("ip", ip);
		
		List<Visit> list = query.getResultList();
		if (list == null || list.isEmpty()) return null;
		return list.get(0);
	}
	
	/**
	 * 返回最近访问记录
	 * 
	 * @param siteId 站点编号
	 * @param rows 返回记录数
	 * @return List
	 */
	public List<LastVisitVo> findLastAccess(final Date startDate, final Date endDate, final Integer rows, final Integer siteId){
		String hql = "Select new " + LASTVISIT_CLASS_NAME + "(v.ip, v.country, i.url, i.visitDate, i.visitTime, i.referer, v.browser, v.os, v.screen, v.language, v.flashVersion) " +
				      "From VisitItem As i, Visit As v " +
				      "Where i.uniqueId = v.uniqueId And i.visitDate>=:startDate And i.visitDate<=:endDate And i.siteId=:siteId " +
				      "Order By i.visitDate Desc, i.visitTime Desc";
		TypedQuery<LastVisitVo> query = this.getEntityManager().createQuery(hql, LastVisitVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setMaxResults(rows);
		
		return query.getResultList();
	}
	
	/**
	 * 查询时间段Pv量
	 * 
	 * @param siteId
	 */
	public Long findPvCountInHour(final Date date, final Date startTime, final Date endTime, final Integer siteId){
		String hql = "Select Sum(pageView) From VisitItem Where visitDate=:date And visitTime>=:startTime And visitTime<=:endTime And siteId=:siteId Group By visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 查询时间段IP量
	 * 
	 * @param date 访问日期
	 * @param siteId 站点编号
	 * @return Integer
	 */
	public Long findIpCountInHour(final Date date, final Date startTime, final Date endTime, final Integer siteId){
		String hql = "Select Count(Distinct v.ip) From Visit As v, VisitItem As i Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.visitTime>=:startTime And i.visitTime<=:endTime And i.siteId=:siteId Group By v.addDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 查询时间段UV（用户访问）量
	 * 
	 * @param date 访问日期
	 * @param siteId 站点编号
	 * @return Integer
	 */
	public Long findUvCountInHour(final Date date, final Date startTime, final Date endTime, final Integer siteId){
		String hql = "Select Count(Distinct v.uniqueId) From Visit As v, VisitItem As i Where v.uniqueId=i.uniqueId And i.visitDate=:date And i.visitTime>=:startTime And i.visitTime<=:endTime And i.siteId=:siteId Group By v.addDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 查询时间段RV（回头率）量
	 * 
	 * @param date 访问日期
	 * @param siteId 站点编号
	 * @return Integer
	 */
	public Long findRvCountInHour(final Date date, final Date startTime, final Date endTime, final Integer siteId){
		String hql = "Select Count(v.rvFlag) From Visit As v, VisitItem As i Where v.uniqueId=i.uniqueId And i.visitDate=:date And  i.visitTime>=:startTime And i.visitTime<=:endTime And v.siteId=:siteId And v.rvFlag=true";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 入口
	 * 
	 * @param startDate
	 * @param endDate
	 * @param rows
	 * @param siteId
	 * @return
	 */
	public List<InAndExitVo> findEntrance(final Date startDate, final Date endDate, final Integer rows, final Integer siteId){
		String hql = "Select new " + INANDEXIT_CLASS_NAME + "(url, Count(url)) From VisitItem Where visitDate>=:startDate And visitDate<=:endDate And siteId=:siteId And url Is Not Null Group By url Order By Count(url) Desc";
		TypedQuery<InAndExitVo> query = this.getEntityManager().createQuery(hql, InAndExitVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setMaxResults(rows);
		return query.getResultList();
	}
	
	public Long findUrlSumInEntrance(final Date startDate, final Date endDate, final Integer rows, final Integer siteId){
		String hql = "Select Count(url) From VisitItem Where visitDate>=:startDate And visitDate<=:endDate And siteId=:siteId And url Is Not Null";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setMaxResults(rows);
		return query.getSingleResult();
	}
	
	public Long findPvCountInEntrance(final Date date, final String url, final Integer siteId){
		String hql = "Select Sum(pageView) From VisitItem Where visitDate=:date And url=:url And siteId=:siteId And url Is Not Null Group By visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("url", url);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 出口
	 * 
	 * @param startDate
	 * @param endDate
	 * @param rows
	 * @param siteId
	 * @return
	 */
	public List<InAndExitVo> findExit(final Date startDate, final Date endDate, final Integer rows, final Integer siteId){
		String hql = "Select new " + INANDEXIT_CLASS_NAME + "(url, Count(url)) From VisitItem Where visitDate>=:startDate And visitDate<=:endDate And siteId=:siteId And url Is Not Null And event=:event Group By url Order By Count(url) Desc";
		TypedQuery<InAndExitVo> query = this.getEntityManager().createQuery(hql, InAndExitVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setParameter("event", VisitUtil.UNLOAD_EVENT);
		query.setMaxResults(rows);
		return query.getResultList();
	}
	
	public Long findUrlSumInExit(final Date startDate, final Date endDate, final Integer rows, final Integer siteId){
		String hql = "Select Count(url) From VisitItem Where visitDate>=:startDate And visitDate<=:endDate And siteId=:siteId And url Is Not Null And event=:event";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setParameter("event", VisitUtil.UNLOAD_EVENT);
		query.setMaxResults(rows);
		return query.getSingleResult();
	}
	
	public Long findPvCountInExit(final Date date,final String url, final Integer siteId){
		String hql = "Select Sum(pageView) From VisitItem Where visitDate=:date And url=:url And siteId=:siteId And event=:event And url Is Not Null Group By visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("url", url);
		query.setParameter("siteId", siteId);
		query.setParameter("event", VisitUtil.UNLOAD_EVENT);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	public List<SummaryVo> findHost(final Date startDate, final Date endDate, final Integer rows, final Integer siteId){
		String hql = "Select new " + SUMMARY_CLASS_NAME + "(host, Sum(pageView)) From VisitItem Where visitDate>=:startDate And visitDate<=:endDate And siteId=:siteId And host Is Not Null Group By host Order By Sum(pageView) Desc";
		TypedQuery<SummaryVo> query = this.getEntityManager().createQuery(hql, SummaryVo.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setMaxResults(rows);
		return query.getResultList();
	}
	
	public Long findPvSumInHost(final Date startDate, final Date endDate, final Integer rows, final Integer siteId){
		String hql = "Select Sum(pageView) From VisitItem Where visitDate>=:startDate And visitDate<=:endDate And siteId=:siteId And host Is Not Null";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		query.setMaxResults(rows);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	public Long findPvCountInHost(final Date date,final String host, final Integer siteId){
		String hql = "Select Sum(pageView) From VisitItem Where visitDate=:date And host=:host And siteId=:siteId And host Is Not Null Group By visitDate";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("host", host);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	public Long findPvSumInCountry(final Date startDate, final Date endDate, final Integer siteId){
		String hql = "Select Sum(pageView) From VisitItem Where visitDate>=:startDate And visitDate<=:endDate And siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	public List<String> findCountryName(final Date startDate, final Date endDate, final Integer siteId){
		String hql = "Select country From Visit Where addDate>=:startDate And addDate<=:endDate And siteId=:siteId Group By country";
		TypedQuery<String> query = this.getEntityManager().createQuery(hql, String.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}
	
	public Long findPvSumInCountryByCountryName(final Date date, final String country, final Integer siteId){
		String hql = "Select Sum(i.pageView) From Visit As v, VisitItem As i Where v.uniqueId = i.uniqueId And v.addDate=:date And v.country=:country And v.siteId=:siteId Group By v.country";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	public Long findUvCountInDayByCountryName(final Date date, final String country, final Integer siteId){
		String hql = "Select Count(Distinct uniqueId) From Visit Where addDate=:date And country=:country And siteId=:siteId Group By country";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	public Long findIpCountInDayByCountryName(final Date date, final String country, final Integer siteId){
		String hql = "Select Count(Distinct ip) From Visit Where addDate=:date And country=:country And siteId=:siteId Group By country";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("country", country);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	public List<Long> findOnline(final Date date, final Date startTime, final Date endTime, final Integer siteId){
		String hql = "Select stickTime From VisitItem Where visitDate=:date And visitTime>=:startTime And visitTime<=:endTime And siteId=:siteId";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}
	
	public List<ArticleVisitVo> findArticleVisit(final Integer rows, final Integer siteId){
		String hql = "Select new " + ARTICLE_CLASS_NAME + "(c.name, a.title, a.url, a.owner, i.pageView, i.stickTime) From VisitItem As i, Channel As c, Article As a " +
				     " Where i.channelId=c.id And i.articleId=a.id And i.siteId=:siteId " +
				     " Order By Sum(i.pageView) Desc";
		TypedQuery<ArticleVisitVo> query = this.getEntityManager().createQuery(hql, ArticleVisitVo.class);
		query.setParameter("siteId", siteId);
		query.setMaxResults(rows);
		return query.getResultList();
	}
	
	public List<String> findClientName(final Date startDate, final Date endDate, final String fieldName, final Integer siteId){
		String hql = "Select " + fieldName + " From Visit Where addDate>=:startDate And addDate<=:endDate And siteId=:siteId Group By " + fieldName;
		TypedQuery<String> query = this.getEntityManager().createQuery(hql, String.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("siteId", siteId);
		return query.getResultList();
	}
	
	public Long findPvSumInDayByStringField(final Date date, final String fieldName, final String fieldValue, final Integer siteId){
		String hql = "Select Sum(i.pageView) From Visit As v, VisitItem As i Where v.uniqueId = i.uniqueId And v.addDate=:date And v." + fieldName + "=:fieldValue And v.siteId=:siteId Group By v." + fieldName;
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("fieldValue", fieldValue);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
	
	public Long findPvSumInDayByBooleanField(final Date date, final String fieldName, final Boolean fieldValue, final Integer siteId){
		String hql = "Select Sum(i.pageView) From Visit As v, VisitItem As i Where v.uniqueId = i.uniqueId And v.addDate=:date And v." + fieldName + "=:fieldValue And v.siteId=:siteId Group By v." + fieldName;
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("date", date);
		query.setParameter("fieldValue", fieldValue);
		query.setParameter("siteId", siteId);
		Long result = 0L;
		try{
			result = query.getSingleResult();
		}catch(Exception e){
		}
		return result;
	}
}
