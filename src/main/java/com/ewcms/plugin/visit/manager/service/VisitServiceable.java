/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.service;

import com.ewcms.plugin.visit.model.Visit;

public interface VisitServiceable {
	
	public Long addVisit(Visit visit);
	
	public Long updVisit(Visit visit);
	
	public void delVisit(Long visitId);
	
	public Visit findVisitById(Long visitId);
}
