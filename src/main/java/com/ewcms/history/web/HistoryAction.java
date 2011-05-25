/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.history.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.history.fac.HistoryModelFacable;
import com.ewcms.history.model.HistoryModel;
import com.ewcms.web.CrudBaseAction;

/**
 *
 * @author 吴智俊
 */
public class HistoryAction extends CrudBaseAction<HistoryModel, Long> {

	private static final long serialVersionUID = 2667146571103157163L;

	@Autowired
	private HistoryModelFacable historyModelFac;
	
	public List<Long> getSelections() {
        return super.getOperatorPK();
    }
	
	public void setSelections(List<Long> selections) {
        super.setOperatorPK(selections);
    }

    public String query() throws Exception {
        return SUCCESS;
    }
	
	@Override
	protected HistoryModel createEmptyVo() {
		return null;
	}

	@Override
	protected void deleteOperator(Long pk) {
		historyModelFac.delHistoryModel(pk);
	}

	@Override
	protected HistoryModel getOperator(Long pk) {
		return historyModelFac.findByHistoryModel(pk);
	}

	@Override
	protected Long getPK(HistoryModel vo) {
		return vo.getId();
	}

	@Override
	protected Long saveOperator(HistoryModel vo, boolean isUpdate) {
		return null;
	}
}
