/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.notes.service;

import java.util.List;

import com.ewcms.content.notes.model.Memoranda;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface MemorandaServiceable {
	public StringBuffer getInitCalendarToHtml(final int year, final int month);
	
	public Long addMemoranda(Memoranda memoranda, Integer year, Integer month, Integer day);
	
	public Long updMemoranda(Memoranda memoranda);
	
	public Memoranda findMemoranda(Long memorandaId);
	
	public void delMemoranda(Long memorandaId);
	
	public List<Memoranda> findMemorandaByDate(Integer year, Integer month, Integer day);
	
	public void updMemoranda(Long memorandaId, Integer year, Integer month, Integer day);
	
	public List<Memoranda> findMemorandaByWarn();
	
	public void updMemorandaNextFireTime(Long memorandaId);
	
	public List<Memoranda> getMemorandaFireTime(String clientTime);
	
	public List<Memoranda> findMemorandaByUserName();
}
