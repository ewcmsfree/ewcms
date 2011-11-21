package com.ewcms.plugin.report.manager.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.Repository;
import com.ewcms.web.CrudBaseAction;

public class RepositoryAction extends CrudBaseAction<Repository, Long> {

	private static final long serialVersionUID = -1093034528876795837L;

	@Autowired
	private ReportFacable reportFac;

	@Override
	protected Long getPK(Repository vo) {
		return vo.getId();
	}

	@Override
	protected Repository getOperator(Long pk) {
		return reportFac.findRepository(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		reportFac.delRepository(pk);
	}

	@Override
	protected Long saveOperator(Repository vo, boolean isUpdate) {
		return null;
	}

	@Override
	protected Repository createEmptyVo() {
		return null;
	}

}
