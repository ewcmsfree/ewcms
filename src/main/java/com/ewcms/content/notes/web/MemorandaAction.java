/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.notes.web;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.notes.NotesFacable;
import com.ewcms.content.notes.model.Memoranda;
import com.ewcms.content.notes.util.Lunar;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author wu_zhijun
 *
 */
public class MemorandaAction extends CrudBaseAction<Memoranda, Long> {

	private static final long serialVersionUID = 7925268963440319845L;

	@Autowired
	private NotesFacable notesFac;
	
	private Integer year;
	private Integer month;
	private Integer day;
	private Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
	private Integer currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
	private String result;
	private String toDayLunar;
	
	public Memoranda getMemorandaVo() {
		return super.getVo();
	}

	public void setMemorandaVo(Memoranda memorandaVo) {
		super.setVo(memorandaVo);
	}
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getCurrentYear() {
		return currentYear;
	}

	public Integer getCurrentMonth() {
		return currentMonth;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getToDayLunar() {
		return toDayLunar;
	}

	public void setToDayLunar(String toDayLunar) {
		this.toDayLunar = toDayLunar;
	}

	public void changeDate(){
		Struts2Util.renderJson(JSONUtil.toJSON(notesFac.getInitCalendarToHtml(getYear(), getMonth()).toString()));
	}
	
	public void toDay(){
		Struts2Util.renderJson(JSONUtil.toJSON(notesFac.getInitCalendarToHtml(getCurrentYear(), getCurrentMonth()).toString()));
	}

	@Override
	protected Memoranda createEmptyVo() {
		return new Memoranda();
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}
	
	@Override
	protected void deleteOperator(Long pk) {
		notesFac.delMemoranda(pk);
	}

	@Override
	protected Memoranda getOperator(Long pk) {
		return notesFac.findMemoranda(pk);
	}

	@Override
	protected Long getPK(Memoranda vo) {
		return vo.getId();
	}

	@Override
	protected Long saveOperator(Memoranda vo, boolean isUpdate) {
		if (isUpdate) {
			return notesFac.updMemoranda(vo);
		} else {
			return notesFac.addMemoranda(vo,getYear(),getMonth(),getDay());
		}
	}
	
	public String index() throws Exception{
		Calendar calendar = Calendar.getInstance();
		
		int	currentDay = calendar.get(Calendar.DATE);
		
		setToDayLunar(Lunar.getLunar("" + getCurrentYear(), "" + getCurrentMonth(), "" + currentDay));
		
		if (getYear() == null || getMonth() == null){
			setYear(currentYear);
			setMonth(currentMonth);
		}
		setResult(notesFac.getInitCalendarToHtml(getYear(), getMonth()).toString());
		
		return SUCCESS;
	}
}
