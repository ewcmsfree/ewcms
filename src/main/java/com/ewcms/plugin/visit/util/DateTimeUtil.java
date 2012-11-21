/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author wu_zhijun
 *
 */
public class DateTimeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);
	
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private static final String DEFAULT_TIME_FORMAT = "HH:mm";
	
	/**
	 * 取日期区间集合
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getDateArea(String startDate, String endDate){
		return getDateArea(startDate, endDate, DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 取日期区间集合
	 * 
	 * @return
	 */
	public static List<String> getDateArea(String startDate, String endDate, String format) {
		List<String> list = new ArrayList<String>();
		try {
			SimpleDateFormat simple = new SimpleDateFormat(format);
			Date beforeDate = simple.parse(startDate);
			Date afterDate = simple.parse(endDate);
			Long mid = afterDate.getTime() - beforeDate.getTime() + 1;
			int day = (int) (mid / (1000 * 60 * 60 * 24));

			list.add(simple.format(beforeDate.getTime()));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(beforeDate);
			for (int i = 0; i < day; i++) {
				calendar.add(Calendar.DATE, 1);
				list.add(simple.format(calendar.getTime()));
			}
		} catch (Exception e) {
			logger.warn("日期转换错误");
		}
		return list;
	}
	
	public static List<String> getTimeArea(){
		List<String> list = new ArrayList<String>();
		try{
			for (int i = 0; i <= 23; i++){
				list.add(String.format("%02d", i));
			}
		}catch (Exception e){
			logger.warn("日间转换错误");
		}
		return list;
	}
	
	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static Date getCurrent(){
		return getCurrent(DEFAULT_DATE_FORMAT);
	}
	
	public static Date getCurrent(String format) {
		SimpleDateFormat simple = new SimpleDateFormat(format);
		Date current = new Date(Calendar.getInstance().getTime().getTime());
		try {
			current = simple.parse(simple.format(current));
		} catch (Exception e) {
		}
		return current;
	}

	public static String getDateToString(Date date){
		return getDateToString(date, DEFAULT_DATE_FORMAT);
	}
	
	public static String getDateToString(Date date, String format) {
		SimpleDateFormat simple = new SimpleDateFormat(format);
		return simple.format(date);
	}
	
	public static Date getStringToTime(String time){
		return getStringToTime(time, DEFAULT_TIME_FORMAT);
	}

	public static Date getStringToTime(String time, String format){
		SimpleDateFormat simple = new SimpleDateFormat(format);
		try {
			return simple.parse(time);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 字符型转换成日期型
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStringToDate(String date){
		return getStringToDate(date, DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 字符型转换成日期型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date getStringToDate(String date, String format) {
		SimpleDateFormat simple = new SimpleDateFormat(format);
		try {
			return simple.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date getPreviousDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}

	/**
	 * 获取本周区间集合(使用默认日期格式化)
	 * 
	 * @return
	 */
	public static List<String> getThisWeek(){
		return getThisWeek(DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 获取本周区间集合(使用传入日期格式化)
	 * 
	 * @param format 
	 * @return
	 */
	public static List<String> getThisWeek(String format) {
		List<String> list = new ArrayList<String>();
		try{
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simple = new SimpleDateFormat(format);
			Date current = new Date(Calendar.getInstance().getTime().getTime());
	
			calendar.setTime(current);
			int weekDay = calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : calendar.get(Calendar.DAY_OF_WEEK);
			calendar.add(Calendar.DATE, Calendar.MONDAY - weekDay);
			Date start = calendar.getTime();
			calendar.add(Calendar.DATE, 6);
			Date end = calendar.getTime();
			
			Long mid = end.getTime() - start.getTime() + 1;
			int day = (int) (mid / (1000 * 60 * 60 * 24));
			
			calendar.setTime(start);
			list.add(simple.format(start.getTime()));
			for (int i = 0; i < day; i++) {
				calendar.add(Calendar.DATE, 1);
				list.add(simple.format(calendar.getTime()));
			}
		}catch (Exception e){
		}
		return list;
	}
	
	public static List<String> getThisMonth(){
		return getThisMonth(DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 获取本月日期区间(使用传入日期格式化)
	 * 
	 * @param format
	 * @return
	 */
	public static List<String> getThisMonth(String format){
		List<String> list = new ArrayList<String>();
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date firstDate = calendar.getTime();
			
			SimpleDateFormat simple = new SimpleDateFormat(format);
			Date current = new Date(Calendar.getInstance().getTime().getTime());
			
			Long mid = current.getTime() - firstDate.getTime() + 1;
			int day = (int) (mid / (1000 * 60 * 60 * 24));
			
			calendar.setTime(firstDate);
			list.add(simple.format(firstDate.getTime()));
			for (int i = 0; i < day; i++) {
				calendar.add(Calendar.DATE, 1);
				list.add(simple.format(calendar.getTime()));
			}
		}catch (Exception e){
			
		}
		return list;
	}
	
	/**
	 * 计算当天调用时间与第二天凌晨之间相差的秒数
	 * 
	 * @return Integer 
	 */
	public static Integer getCurrentToNextDaySecond(){
		try{
			Date current = new Date(Calendar.getInstance().getTime().getTime());
			
			Calendar calendar = Calendar.getInstance();
			current.setTime(current.getTime());
			
			calendar.add(Calendar.DATE, 1);
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Date nextDay = simple.parse(simple.format(calendar.getTime()));
			
			Long interval = (nextDay.getTime() - current.getTime() - 1) / 1000;
			
			return interval.intValue();
		}catch(Exception e){
			return -1;
		}
	}

	public static void main(String[] args) {
		Long online = 15L%3;
		logger.info(online + "");
		
		logger.info("=====================");
		
		List<String> timeArea = getTimeArea();
		for(String time : timeArea){
			logger.info(time);
		}
		logger.info("=====================");
		
		logger.info("Second : " + getCurrentToNextDaySecond());
		
		logger.info("=====================");
		
		List<String> listArea = getDateArea("2012-10-07", "2012-11-08");
		for (String area : listArea){
			logger.info(area);
		}
		
		logger.info("=====================");
		
		List<String> thisWeek = getThisWeek();
		for(String week : thisWeek){
			logger.info(week);
		}
	}
}
