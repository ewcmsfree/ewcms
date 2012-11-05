package com.ewcms.plugin.visit.manager;

import com.ewcms.plugin.visit.model.Visit;

public interface VisitFacable {
	public String addAndUpdVisit(Visit visit);
	
	public void delVisit(String uniqueID);
	
	public Visit findVisitById(String uniqueID);
}
