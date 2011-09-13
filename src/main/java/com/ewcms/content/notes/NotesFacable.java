/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.notes;

import java.util.List;

import com.ewcms.content.notes.model.Memoranda;

public interface NotesFacable {
	public StringBuffer getInitCalendarToHtml(final int year, final int month);
	
	public Long addMemoranda(Memoranda memoranda, Integer year, Integer month, Integer day);
	
	public Long updMemoranda(Memoranda memoranda);
	
	public Memoranda findMemoranda(Long memorandaId);
	
	public void delMemoranda(Long memorandaId);
	
	public void updMemoranda(Long memorandaId, Integer year, Integer month, Integer day);
	
	public List<Memoranda> getMemorandaFireTime(String clientTime);
}
