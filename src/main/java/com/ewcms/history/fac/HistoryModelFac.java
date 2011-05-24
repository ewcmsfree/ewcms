/**
 * 创建日期 2011-3-31
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.history.fac;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.history.model.HistoryModel;
import com.ewcms.history.service.HistoryModelServiceable;

/**
 * @author 吴智俊
 */
@Service
public class HistoryModelFac implements HistoryModelFacable {

	@Autowired
	private HistoryModelServiceable historyModelService;
	
	@Override
	public List<HistoryModel> findByHistoryModel(String className, String idName, String idValue, Date startDate, Date endDate) {
		return historyModelService.findByHistoryModel(className, idName, idValue, startDate, endDate);
	}

	@Override
	public HistoryModel findByHistoryModel(Long historyId) {
		return historyModelService.findByHistoryModel(historyId);
	}

	@Override
	public void delHistoryModel(Long historyId) {
		historyModelService.delHistoryModel(historyId);
	}

	@Override
	public void delHistoryModelBeforeDate() {
		historyModelService.delHistoryModelBeforeDate();
	}

}
