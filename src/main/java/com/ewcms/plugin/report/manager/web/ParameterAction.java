/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.Parameter;
import com.ewcms.web.CrudBaseAction;

/**
 * 
 * @author wuzhijun
 *
 */
public class ParameterAction extends CrudBaseAction<Parameter, Long>{

	private static final long serialVersionUID = 8488899222887176952L;

	@Autowired
	private ReportFacable reportFac;
	private String reportType;
	private Long reportId;
	
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public List<Long> getSelections() {
        return super.getOperatorPK();
    }
	
	public void setSelections(List<Long> selections) {
        super.setOperatorPK(selections);
    }
	
	public Parameter getParameterVo(){
		return super.getVo();
	}
	
	public void setParameterVo(Parameter parameter){
		super.setVo(parameter);
	}
	
	@Override
	protected Long getPK(Parameter vo) {
		return vo.getId();
	}

	@Override
	protected Parameter getOperator(Long pk) {
		return reportFac.findParameterById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
	}

	@Override
	protected Long saveOperator(Parameter vo, boolean isUpdate) {
		if (getReportId() != null && vo.getId() != null){
			try{
				if (reportType.equals("text"))
					return reportFac.updTextReportParameter(getReportId(), vo);
				if (reportType.equals("chart"))
					return reportFac.updChartReportParameter(getReportId(), vo);
			}catch(BaseException e){
				
			}
		}
		return null;
	}

	@Override
	protected Parameter createEmptyVo() {
		return null;
	}

}
