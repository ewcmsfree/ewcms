/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.notes.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.notes.manager.service.MemorandaServiceable;
import com.ewcms.plugin.notes.model.Memoranda;

/**
 * 
 * @author wu_zhijun
 */
@Service
public class NotesFac implements NotesFacable {

	@Autowired
	private MemorandaServiceable memorandaService;
	
	@Override
	public StringBuffer getInitCalendarToHtml(final int year, final int month) {
		return memorandaService.getInitCalendarToHtml(year, month);
	}

	@Override
	public Long addMemoranda(Memoranda memoranda, Integer year,	Integer month, Integer day) {
		return memorandaService.addMemoranda(memoranda, year, month, day);
	}

	@Override
	public void delMemoranda(Long memorandaId) {
		memorandaService.delMemoranda(memorandaId);
	}

	@Override
	public Memoranda findMemoranda(Long memorandaId) {
		return memorandaService.findMemoranda(memorandaId);
	}

	@Override
	public Long updMemoranda(Memoranda memoranda) {
		return memorandaService.updMemoranda(memoranda);
	}

	@Override
	public void updMemoranda(Long memorandaId, Integer year, Integer month, Integer day) {
		memorandaService.updMemoranda(memorandaId, year, month, day);
	}

	@Override
	public  List<Memoranda> getMemorandaFireTime(String userName, String clientTime) {
		return memorandaService.getMemorandaFireTime(userName, clientTime);
	}

	@Override
	public List<Memoranda> findMemorandaByUserName() {
		return memorandaService.findMemorandaByUserName();
	}

}
