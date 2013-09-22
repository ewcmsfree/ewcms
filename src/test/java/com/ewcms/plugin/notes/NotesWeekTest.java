/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.notes;

import java.util.Calendar;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.plugin.notes.manager.service.MemorandaService;
import com.ewcms.plugin.notes.manager.service.MemorandaServiceable;


public class NotesWeekTest {
	protected static final Logger logger = LoggerFactory.getLogger(NotesWeekTest.class);

	@Test
	public void FirstDayOfMonth(){
		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.YEAR, 2011); 
//		calendar.set(Calendar.MONTH, 9);
		logger.info("Year : " + calendar.get(Calendar.YEAR));
		logger.info("Month : " + calendar.get(Calendar.MONTH));
		logger.info("Monday : " + calendar.get(Calendar.MONDAY));
		logger.info("Days : " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		logger.info("WEEK : " + calendar.getActualMaximum(Calendar.WEEK_OF_MONTH));
		logger.info("Now Day : " + calendar.get(Calendar.DATE));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
		logger.info("First Day Of Month : " + firstDayOfMonth);
	}
	
	@Test
	public void getInitCalendarToHtml(){
		MemorandaServiceable ms = new MemorandaService();
		
		logger.info(ms.getInitCalendarToHtml(2011, 9).toString());
	}
}
