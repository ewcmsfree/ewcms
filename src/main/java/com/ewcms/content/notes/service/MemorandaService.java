/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.notes.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.notes.dao.MemorandaDAO;
import com.ewcms.content.notes.model.Memoranda;
import com.ewcms.content.notes.util.Lunar;
import com.ewcms.content.notes.util.SolarTerm;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 
 * @author wu_zhijun
 * 
 */
@Service
public class MemorandaService implements MemorandaServiceable {

	@Autowired
	private MemorandaDAO memorandaDAO;
	
	private int dayCount;
	private int currentDay;
	private int selYear;
	private int selMonth;
	
	@Override
	public StringBuffer getInitCalendarToHtml(final int year, final int month) {
		StringBuffer sb = new StringBuffer();
		
		Calendar calendar = Calendar.getInstance();
		
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);
		
		if (currentYear == year && currentMonth == month - 1){
			currentDay = calendar.get(Calendar.DATE);
		}else{
			currentDay = 0;
		}
		
		calendar.set(Calendar.YEAR, year); 
		calendar.set(Calendar.MONTH, month - 1);
		
		selYear = year;
		selMonth = month;
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int week = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		dayCount = 1;
		
		sb.append(getTitleHtml());
		sb.append(generatorFirstWeekHtml(firstDayOfMonth).toString());
		sb.append(generatorMiddleWeekHtml(week).toString());
		sb.append(generatorLastWeekHtml(week,days,firstDayOfMonth).toString());
		
		return sb;
	}

	private StringBuffer generatorFirstWeekHtml(final int firstDayOfMonth) {
		StringBuffer sb = new StringBuffer();

		sb.append("<tr class='notes_tr' valign='top'>\n");
		for (int i = 1; i <= 7; i++) {
			if (i < firstDayOfMonth){
				sb.append("  <td>&nbsp;</td>\n");
			}else{
				sb.append(getContentHtml(i, dayCount).toString());
				dayCount++;
			}
		}
		sb.append("</tr>\n");

		return sb;
	}
	
	private StringBuffer generatorMiddleWeekHtml(final int week){
		StringBuffer sb = new StringBuffer();
		
		int middleWeek = week - 1;
		
		for (int j = 1; j < middleWeek; j++){
			sb.append("<tr class='notes_tr' valign='top'>\n");
			for (int i = 1; i <= 7; i++){
				sb.append(getContentHtml((j * 7 + i), dayCount).toString());
				dayCount++;
			}
			sb.append("</tr>\n");
		}
		return sb;
	}
	
	private StringBuffer generatorLastWeekHtml(final int week, final int days, final int firstDayOfMonth){
		StringBuffer sb = new StringBuffer();
		
		int lastDays = days - dayCount + 1;
		
		sb.append("<tr class='notes_tr' valign='top'>\n");
		for (int i = 1; i <= lastDays; i++){
			sb.append(getContentHtml(((week - 1) * 7 + i), dayCount).toString());
			dayCount++;
		}
		
		int blankDay = 7 - lastDays;
		
		for (int i = 1; i <= blankDay; i++){
			sb.append("  <td>&nbsp;</td>\n");
		}
		sb.append("</tr>\n");
		
		return sb;
	}
	
	private StringBuffer getContentHtml(int notesTd, int dayValue){
		StringBuffer sb = new StringBuffer();
		
		String lunarValue= getLunarDay(dayValue);
		if (!getSolarTerms(dayValue).equals("")) lunarValue = getSolarTerms(dayValue);
		
		List<Memoranda> memos = findMemorandaByDate(selYear, selMonth, dayValue);
		StringBuffer memoSb = new StringBuffer();
		for (Memoranda memo : memos){
			memoSb.append("<a id='a_title_" + memo.getId() + "' onclick='edit(" + memo.getId() + ");' style='cursor:pointer;' herf='#;'><span id='title_" + memo.getId() + "'>" + memo.getTitle() + "</span></a><br>\n");
		}
		
		sb.append("  <td id='notes_td" + notesTd + "'>\n");
		sb.append("    <table width='100%' cellspacing='0' cellpadding='0' border='0' ondblclick='add(" + dayValue + ");'>\n");
		sb.append("      <tr>\n");
		sb.append("        <td width='60' valign='middle' height='20' align='center' style='border-bottom: #aaccee 1px solid; border-right: #aaccee 1px solid; background-color: #DCF0FB' id='notes_td" + notesTd + "'>" + dayValue + " " + lunarValue + "<br></td>\n");
		sb.append("        <td bgcolor='#E9F0F8'></td>\n");
		sb.append("      </tr>\n");
		sb.append("      <tr valign='top'>\n");
		if (currentDay != dayValue){
			sb.append("        <td height='65' onmouseout=this.bgColor='' onmouseover=this.bgColor='#EDFBD2' colspan='2'>\n");
		}else{
			sb.append("        <td height='65' bgcolor='#FFFFCC' onmouseout=this.bgColor='#FFFFCC' onmouseover=this.bgColor='#EDFBD2' colspan='2'>\n");
		}
		sb.append(memoSb.toString());
		sb.append("        </td>\n");
	    sb.append("      </tr>\n");
	    sb.append("    </table>\n");
		sb.append("  </td>\n");
		
		return sb;
	}
	
	private StringBuffer getTitleHtml(){
		StringBuffer sb = new StringBuffer();
		sb.append("<tr class='notes_tr' bgcolor='#DCF0FB' align='center'>");
		sb.append("  <td width='15%' height='30'><font color='#14AD00'>星期日</font></td>");
		sb.append("  <td width='14%'>星期一</td>");
		sb.append("  <td width='14%'>星期二</td>");
		sb.append("  <td width='14%'>星期三</td>");
		sb.append("  <td width='14%'>星期四</td>");
		sb.append("  <td width='14%'>星期五</td>");
		sb.append("  <td width='15%'><font color='#14AD00'>星期六</font></td>");
		sb.append("</tr>");
		return sb;
	}
	
	private String getLunarDay(int day){
		return Lunar.getLunarDay(selYear, selMonth, day);
	}
	
	private String getSolarTerms(int day){
		return SolarTerm.getSoralTerm(selYear, selMonth, day);
	}

	@Override
	public Long addMemoranda(Memoranda memoranda, Integer year, Integer month, Integer day) {
		memoranda.setUserName(EwcmsContextUtil.getUserDetails().getUsername());
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		memoranda.setNoteTime(calendar.getTime());
		
		memorandaDAO.persist(memoranda);
		
		return memoranda.getId();
	}

	@Override
	public void delMemoranda(Long memorandaId) {
		memorandaDAO.removeByPK(memorandaId);
	}

	@Override
	public Memoranda findMemoranda(Long memorandaId) {
		return memorandaDAO.get(memorandaId);
	}

	@Override
	public Long updMemoranda(Memoranda memoranda) {
		memorandaDAO.merge(memoranda);
		return memoranda.getId();
	}

	@Override
	public List<Memoranda> findMemorandaByDate(Integer year, Integer month, Integer day) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(year, month - 1, day - 1);
		Date beginDate = calendar.getTime();
		
		calendar.set(year, month - 1, day);
		Date endDate = calendar.getTime();
		
		return memorandaDAO.findMemorandaByDate(beginDate, endDate, EwcmsContextUtil.getUserDetails().getUsername());
	}
}
