package com.ewcms.plugin.visit.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.visit.manager.dao.VisitDAO;
import com.ewcms.plugin.visit.model.Visit;

@Service
public class VisitService implements VisitServiceable {

	@Autowired
	private VisitDAO visitDAO;
	
	@Override
	public String addAndUpdVisit(Visit visit) {
		Visit dbVisit = visitDAO.get(visit.getUniqueID());
		if (dbVisit == null){
			visitDAO.persist(visit);
		}else{
			dbVisit.setStickTime(visit.getStickTime());
			visitDAO.merge(dbVisit);
		}
		return visit.getUniqueID();
	}

	@Override
	public void delVisit(String uniqueID) {
		visitDAO.removeByPK(uniqueID);
	}

	@Override
	public Visit findVisitById(String uniqueID) {
		return visitDAO.get(uniqueID);
	}
}
