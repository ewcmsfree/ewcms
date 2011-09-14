/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.notes.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	private SimpleDateFormat warnDateFormat = new SimpleDateFormat("HH:mm:00");

	@Autowired
	private NotesFacable notesFac;

	private Integer year;
	private Integer month;
	private Integer day;
	private Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
	private Integer currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
	private String result;
	private String toDayLunar;
	private String warnTime;

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

	public String getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(String warnTime) {
		this.warnTime = warnTime;
	}

	public void changeDate() {
		Struts2Util.renderJson(JSONUtil.toJSON(notesFac.getInitCalendarToHtml(getYear(), getMonth()).toString()));
	}

	public void toDay() {
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
		Memoranda memoranda = notesFac.findMemoranda(pk);
		
		if (memoranda.getWarn() && memoranda.getWarnTime() != null){
			setWarnTime(warnDateFormat.format(memoranda.getWarnTime()));
		}
		
		return memoranda;
	}

	@Override
	protected Long getPK(Memoranda vo) {
		return vo.getId();
	}

	@Override
	protected Long saveOperator(Memoranda vo, boolean isUpdate) {
		if (vo.getWarn() && getWarnTime() != null){
			try {
				vo.setWarnTime(warnDateFormat.parse(getWarnTime()));
			} catch (ParseException e) {
				vo.setWarnTime(null);
			}
		}
		if (isUpdate) {
			return notesFac.updMemoranda(vo);
		} else {
			return notesFac.addMemoranda(vo, getYear(), getMonth(), getDay());
		}
	}

	@Override
	public String save() throws Exception {
		try{
		Long memoId = null;
		if (getPK(vo) == null) {
			operatorState = OperatorState.ADD;
			memoId = saveOperator(vo, false);
		} else {
			operatorState = OperatorState.UPDATE;
			memoId = saveOperator(vo, true);
		}
		if (memoId != null){
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
		return NONE;
	}

	public String index() throws Exception {
		Calendar calendar = Calendar.getInstance();

		int currentDay = calendar.get(Calendar.DATE);

		setToDayLunar(Lunar.getLunar("" + getCurrentYear(), "" + getCurrentMonth(), "" + currentDay));

		if (getYear() == null || getMonth() == null) {
			setYear(currentYear);
			setMonth(currentMonth);
		}
		setResult(notesFac.getInitCalendarToHtml(getYear(), getMonth()).toString());

		return SUCCESS;
	}
	
	private Long memoId;
	private Integer dropDay;
	
	public Long getMemoId() {
		return memoId;
	}

	public void setMemoId(Long memoId) {
		this.memoId = memoId;
	}

	public Integer getDropDay() {
		return dropDay;
	}

	public void setDropDay(Integer dropDay) {
		this.dropDay = dropDay;
	}

	public void drop(){
		if (getMemoId() != null && getDropDay() != null){
			notesFac.updMemoranda(getMemoId(), getYear(), getMonth(), getDropDay());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}
	}
	
	private String clientTime;
	
	public String getClientTime() {
		return clientTime;
	}

	public void setClientTime(String clientTime) {
		this.clientTime = clientTime;
	}

	public void notesRemind(){
		List<Memoranda> memorandas = notesFac.getMemorandaFireTime(getClientTime());
		if (!memorandas.isEmpty()){
			Struts2Util.renderJson(JSONUtil.toJSON(memorandas));
		}else{
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
