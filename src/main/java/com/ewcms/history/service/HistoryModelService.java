/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.history.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.history.dao.HistoryModelDAO;
import com.ewcms.history.model.HistoryModel;

/**
 * @author 吴智俊
 */
@Service
public class HistoryModelService implements HistoryModelServiceable {

	@Autowired
	private HistoryModelDAO historyModelDAO;
	
	@Override
	public List<HistoryModel> findByHistoryModel(String className, String idName, String idValue, Date startDate, Date endDate) {
		return historyModelDAO.findByHistoryModel(className, idName, idValue, startDate, endDate);
	}

	@Override
	public HistoryModel findByHistoryModel(Long historyId) {
		return (HistoryModel)historyModelDAO.get(historyId);
	}

	@Override
	public void delHistoryModel(Long historyId) {
		historyModelDAO.removeByPK(historyId);
	}

	@Override
	public void delHistoryModelBeforeDate() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -3);
		date = calendar.getTime();
		
		historyModelDAO.deleteHistoryModelByBeforeDate(date);
	}
}
