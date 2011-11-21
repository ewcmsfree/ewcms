package com.ewcms.plugin.report.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.CategoryReport;
import com.ewcms.web.CrudBaseAction;

/**
 * 
 * @author wuzhijun
 * 
 */
public class CategoryReportAction extends CrudBaseAction<CategoryReport, Long> {

	private static final long serialVersionUID = -877919389056277148L;

	@Autowired
	private ReportFacable reportFac;

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	public CategoryReport getReportCategoryVo() {
		return super.getVo();
	}

	public void setReportCategoryVo(CategoryReport reportCategory) {
		super.setVo(reportCategory);
	}

	@Override
	protected Long getPK(CategoryReport vo) {
		return vo.getId();
	}

	@Override
	protected CategoryReport getOperator(Long pk) {
		return reportFac.findByCategory(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		reportFac.deletedReportCategory(pk);
	}

	@Override
	protected Long saveOperator(CategoryReport vo, boolean isUpdate) {
		return reportFac.saveOrUpdateReportCategory(vo);
	}

	@Override
	protected CategoryReport createEmptyVo() {
		return new CategoryReport();
	}
}
