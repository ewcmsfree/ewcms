/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.Parameter;
import com.ewcms.security.manage.model.User;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.ComboBoxString;

/**
 * 
 * @author wuzhijun
 *
 */
public class ParameterAction extends CrudBaseAction<Parameter, Long>{

	private static final long serialVersionUID = 8488899222887176952L;

	@Autowired
	private ReportFacable reportFac;
	@Autowired
	private QueryFactory queryFactory;
	private String reportType;
	private Long reportId;
	private String sessionValue;
	
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

	public String getSessionValue() {
		return sessionValue;
	}

	public void setSessionValue(String sessionValue) {
		this.sessionValue = sessionValue;
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
		Parameter parameter = reportFac.findParameterById(pk);
		if (parameter.getType() == Parameter.Type.SESSION){
			setSessionValue(parameter.getDefaultValue());
		}
		return parameter;
	}

	@Override
	protected void deleteOperator(Long pk) {
	}

	@Override
	protected Long saveOperator(Parameter vo, boolean isUpdate) {
		if (getReportId() != null && vo.getId() != null){
			try{
				if (vo.getType() == Parameter.Type.SESSION){
					vo.setDefaultValue(getSessionValue());
				}
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

	private Long parameterId;
	
	public Long getParameterId() {
		return parameterId;
	}

	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}

	public void sessionInfo(){
		EntityQueryable query = queryFactory.createEntityQuery(User.class);
		List<Object> resultList = query.queryResult().getResultList();
		List<ComboBoxString> comboBoxUsers = new ArrayList<ComboBoxString>();
		ComboBoxString comboBoxUser = null;
		for (Object object : resultList){
			comboBoxUser = new ComboBoxString();
			User user = (User)object;
			comboBoxUser.setId(user.getUsername());
			comboBoxUser.setText(user.getUserInfo().getName());
			if (getParameterId() != null){
				Boolean isEntity = reportFac.findSessionIsEntityByParameterIdAndUserName(getParameterId(), user.getUsername());
				if (isEntity) comboBoxUser.setSelected(true);
			}
			
			comboBoxUsers.add(comboBoxUser);
		}
		Struts2Util.renderJson(JSONUtil.toJSON(comboBoxUsers.toArray(new ComboBoxString[0])));
	}

}
