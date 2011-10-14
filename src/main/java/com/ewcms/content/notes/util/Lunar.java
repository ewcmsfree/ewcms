/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.notes.util;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * è®¡ç®—å†œå†æ—¶é—´
 * 
 * @author wu_zhijun
 */
public class Lunar {
	protected static final Logger logger = LoggerFactory.getLogger(Lunar.class);

	private static int monCyl, dayCyl, yearCyl;

	private static int year, month, day;

	private static boolean isLeap;

	private static int[] lunarInfo = { 0x04bd8, 0x04ae0, 0x0a570, 0x054d5,
			0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, 0x04ae0,
			0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2,
			0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40,
			0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0,
			0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7,
			0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0,
			0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355,
			0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
			0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263,
			0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0,
			0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0,
			0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46,
			0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50,
			0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954,
			0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0,
			0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0,
			0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50,
			0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
			0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6,
			0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0,
			0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0 };

	private static String[] Gan = { "ç”?, "ä¹?, "ä¸?, "ä¸?, "æˆ?, "å·?, "åº?, "è¾?,
			"å£?, "ç™? };

	private static String[] Zhi = { "å­?, "ä¸?, "å¯?, "å?, "è¾?, "å·?, "å?, "æœ?,
			"ç”?, "é…?, "æˆ?, "äº? };

	private static String[] Animals = { "é¼?, "ç‰?, "è™?, "å…?, "é¾?, "è›?, "é©?, "ç¾?,
			"çŒ?, "é¸?, "ç‹?, "çŒ? };

	private static String[] nStr1 = { "æ—?, "ä¸?, "äº?, "ä¸?, "å›?, "äº?, "å…?, "ä¸?,
			"å…?, "ä¹?, "å? };

	private static String[] nStr2 = { "åˆ?, "å?, "å»?, "å?, "ã€? };

	private static String[] monthNong = { "æ­?, "æ­?, "äº?, "ä¸?, "å›?, "äº?, "å…?,
			"ä¸?, "å…?, "ä¹?, "å?, "åä¸€", "åäºŒ" };

	private static String[] yearName = { "é›?, "å£?, "è´?, "å?, "è‚?, "ä¼?, "é™?,
			"æŸ?, "æ?, "ç? };

	public Lunar() {
	}

	// å†œå† yå¹´çš„æ€»å¤©æ•?
	private static int lYearDays(int y) {
		int i;
		int sum = 348; // 29*12
		for (i = 0x8000; i > 0x8; i >>= 1) {
			// OurLog.debug("i="+i);
			sum += (lunarInfo[y - 1900] & i) == 0 ? 0 : 1; // å¤§æœˆ+1å¤?
		}
		return (sum + leapDays(y)); // +é—°æœˆçš„å¤©æ•?
	}

	// å†œå† yå¹´é—°æœˆçš„å¤©æ•°
	private static int leapDays(int y) {
		if (leapMonth(y) != 0)
			return ((lunarInfo[y - 1900] & 0x10000) == 0 ? 29 : 30);
		else
			return (0);
	}

	// å†œå† yå¹´é—°å“ªä¸ªæœ?1-12 , æ²¡é—°ä¼ å› 0
	private static int leapMonth(int y) {
		return (lunarInfo[y - 1900] & 0xf);
	}

	// å†œå† yå¹´mæœˆçš„æ€»å¤©æ•?
	private static int monthDays(int y, int m) {
		return ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0 ? 29 : 30);
	}

	// ç®—å‡ºå†œå†, ä¼ å…¥æ—¥æœŸç‰©ä»¶, è¿”å›å†œå†æ—¥æœŸç‰©ä»¶
	// å±æ?æœ?.year .month .day .isLeap .yearCyl .dayCyl .monCyl
	private static void Lunar1(Date objDate) {
		int i, leap = 0, temp = 0;
		// int monCyl,dayCyl,yearCyl;
		// int year,month,day;
		// boolean isLeap;
		Calendar cl = Calendar.getInstance();
		cl.set(1900, 0, 31); // 1900-01-31æ˜¯å†œå?900å¹´æ­£æœˆåˆä¸?
		Date baseDate = cl.getTime();
		// 1900-01-31æ˜¯å†œå?900å¹´æ­£æœˆåˆä¸?
		int offset = (int) ((objDate.getTime() - baseDate.getTime()) / 86400000); // å¤©æ•°(86400000=24*60*60*1000)
		// System.out.println(offset);
		dayCyl = offset + 40; // 1899-12-21æ˜¯å†œå?899å¹´è…Šæœˆç”²å­æ—¥
		monCyl = 14; // 1898-10-01æ˜¯å†œå†ç”²å­æœˆ
		// å¾—åˆ°å¹´æ•°
		for (i = 1900; i < 2050 && offset > 0; i++) {
			temp = lYearDays(i); // å†œå†æ¯å¹´å¤©æ•°
			offset -= temp;
			monCyl += 12;
		}
		if (offset < 0) {
			offset += temp;
			i--;
			monCyl -= 12;
		}
		year = i; // å†œå†å¹´ä»½
		yearCyl = i - 1864; // 1864å¹´æ˜¯ç”²å­å¹?
		leap = leapMonth(i); // é—°å“ªä¸ªæœˆ
		isLeap = false;
		for (i = 1; i < 13 && offset > 0; i++) {
			// é—°æœˆ
			if (leap > 0 && i == (leap + 1) && isLeap == false) {
				--i;
				isLeap = true;
				temp = leapDays(year);
			} else {
				temp = monthDays(year, i);
			}
			// è§£é™¤é—°æœˆ
			if (isLeap == true && i == (leap + 1))
				isLeap = false;
			offset -= temp;
			if (isLeap == false)
				monCyl++;
		}
		if (offset == 0 && leap > 0 && i == leap + 1)
			if (isLeap) {
				isLeap = false;
			} else {
				isLeap = true;
				--i;
				--monCyl;
			}
		if (offset < 0) {
			offset += temp;
			--i;
			--monCyl;
		}
		month = i; // å†œå†æœˆä»½
		day = offset + 1; // å†œå†å¤©ä»½
	}

	private static int getYear() {
		return (year);
	}

	private static int getMonth() {
		return (month);
	}

	private static int getDay() {
		return (day);
	}

	private static int getMonCyl() {
		return (monCyl);
	}

	private static int getYearCyl() {
		return (yearCyl);
	}

	private static int getDayCyl() {
		return (dayCyl);
	}

	private static boolean getIsLeap() {
		return (isLeap);
	}

	// ä¼ å…¥ offset è¿”å›å¹²æ”¯, 0=ç”²å­
	private static String cyclical(int num) {
		return (Gan[num % 10] + Zhi[num % 12]);
	}

	// ä¸­æ–‡æ—¥æœŸ
	public static String cDay(int d) {
		String s;
		switch (d) {
		case 10:
			s = "åˆå";
			break;
		case 20:
			s = "äºŒå";
			break;
		case 30:
			s = "ä¸‰å";
			break;
		default:
			s = nStr2[(int) (d / 10)];// å–å•†
			s += nStr1[d % 10];// å–ä½™
		}
		return (s);
	}

	private static String cYear(int y) {
		String s = "";
		int d;
		while (y > 0) {
			d = y % 10;
			y = (y - d) / 10;
			s = yearName[d] + s;
		}
		return (s);
	}

	public static String getLunar(String year, String month, String day) {
		Date sDObj;
		String s;
		int SY, SM, SD;

		int sy;
		SY = Integer.parseInt(year);
		SM = Integer.parseInt(month);
		SD = Integer.parseInt(day);
		sy = (SY - 4) % 12;
		logger.debug("SY=" + SY + "SM=" + SM + "SD=" + SD + "sy=" + sy);
		Calendar cl = Calendar.getInstance();
		cl.set(SY, SM - 1, SD);
		sDObj = cl.getTime();

		// æ—¥æœŸ
		Lunar1(sDObj); // å†œå†
		s = "å†œå† " + "ã€? + Animals[sy] + "ã€? + cYear(getYear()) + "å¹? + " ";

		s += (getIsLeap() ? "é—? : "") + monthNong[getMonth()] + "æœ?
				+ (monthDays(getYear(), getMonth()) == 29 ? "å°? : "å¤?);
		s += cDay(getDay()) + " ";
		s += cyclical(getYearCyl()) + "å¹? + cyclical(getMonCyl()) + "æœ?
				+ cyclical(getDayCyl()) + "æ—?;
		return s;
	}

	public static String getLunarDay(int year, int month, int day) {
		Calendar cl = Calendar.getInstance();
		cl.set(year, month - 1, day);
		Date sDObj = cl.getTime();
		Lunar1(sDObj);
		String dayValue = cDay(getDay());
		if (dayValue.equals("åˆä¸€"))
			return monthNong[getMonth()] + "æœ?;
		return dayValue;
	}
}
