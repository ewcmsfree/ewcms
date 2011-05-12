/**
 * 创建日期 2011-3-31
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.aspect.history.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.aspect.history.dao.HistoryModelDAO;
import com.ewcms.aspect.history.model.HistoryModel;

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
