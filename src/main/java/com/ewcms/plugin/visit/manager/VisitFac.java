package com.ewcms.plugin.visit.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.visit.manager.service.VisitServiceable;
import com.ewcms.plugin.visit.model.Visit;

@Service
public class VisitFac implements VisitFacable {

	@Autowired
	private VisitServiceable visitService;
	
	@Override
	public String addAndUpdVisit(Visit visit) {
		return visitService.addAndUpdVisit(visit);
	}

	@Override
	public void delVisit(String uniqueID) {
		visitService.delVisit(uniqueID);
	}

	@Override
	public Visit findVisitById(String uniqueID) {
		return visitService.findVisitById(uniqueID);
	}

}
