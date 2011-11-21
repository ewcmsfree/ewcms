/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.sf.jasperreports.engine.JRParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.report.manager.dao.TextReportDAO;
import com.ewcms.plugin.report.manager.util.ParameterSetValueUtil;
import com.ewcms.plugin.report.manager.util.TextDesignUtil;
import com.ewcms.plugin.report.model.Parameter;
import com.ewcms.plugin.report.model.ParametersType;
import com.ewcms.plugin.report.model.TextReport;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class TextReportService implements TextReportServiceable {

	@Autowired
	private TextReportDAO textReportDAO;
	
	@Override
	public Long saveText(TextReport text) throws BaseException {
		byte[] reportFile = text.getTextEntity();

		if (reportFile != null && reportFile.length > 0) {
			InputStream in = new ByteArrayInputStream(reportFile);
			TextDesignUtil rd = new TextDesignUtil(in);
			List<JRParameter> paramList = rd.getParameters();
			
			Set<Parameter> icSet = new LinkedHashSet<Parameter>();
			if (!paramList.isEmpty()) {
				for (JRParameter param : paramList) {
					Parameter ic = getParameterValue(param);
					icSet.add(ic);
				}
				text.setParameters(icSet);
			}
		}
		textReportDAO.persist(text);
		return text.getId();
	}

	@Override
	public Long updateText(TextReport text) throws BaseException {
		TextReport entity = textReportDAO.get(text.getId());
		
		entity.setBaseDS(text.getBaseDS());
		entity.setTextName(text.getTextName());
		entity.setHidden(text.getHidden());
		entity.setRemarks(text.getRemarks());
		
		byte[] reportFile = text.getTextEntity();
		if (reportFile != null && reportFile.length > 0) {
			entity.setTextEntity(text.getTextEntity());
			
			InputStream in = new ByteArrayInputStream(reportFile);
			TextDesignUtil rd = new TextDesignUtil(in);

			List<JRParameter> paramList = rd.getParameters();
			Set<Parameter> icSet = entity.getParameters();

			Set<Parameter> icNewList = new LinkedHashSet<Parameter>();
			for (JRParameter param : paramList) {
				Parameter ic = findListEntity(icSet, param);
				if (ic == null) {
					ic = getParameterValue(param);
				}
				icNewList.add(ic);
			}
			entity.setParameters(icNewList);
		}
		textReportDAO.merge(entity);
		return entity.getId();
	}

	@Override
	public void deletedText(Long textId){
		textReportDAO.removeByPK(textId);
	}

	@Override
	public TextReport findByText(Long textId){
		return textReportDAO.get(textId);
	}

	@Override
	public List<TextReport> findAllText() {
		return textReportDAO.findAll();
	}
	
	@Override
	public Long updateTextParam(Long textId, Parameter parameter) throws BaseException {
		if (textId == null || textId.intValue() == 0)
			throw new BaseException("", "报表编号不存在，请重新选择！");
		TextReport text = textReportDAO.get(textId);
		if (text == null)
			throw new BaseException("", "报表不存在，请重新选择！");
		
		parameter = ParameterSetValueUtil.setParametersValue(parameter);
		
		Set<Parameter> parameters = text.getParameters();
		parameters.remove(parameter);
		parameters.add(parameter);
		text.setParameters(parameters);
		
		textReportDAO.merge(text);
		
		return parameter.getId();
	}
	
	/**
	 * 把报表文件里的参数转换数据参数
	 * 
	 * @param param
	 *            报表参数对象
	 * @return Parameters
	 */
	private Parameter getParameterValue(JRParameter param) {
		Parameter ic = new Parameter();

		ic.setEnName(param.getName());
		ic.setClassName(param.getValueClassName());
		if (param.getDefaultValueExpression() == null){
			ic.setDefaultValue("");
		}else{
			ic.setDefaultValue(param.getDefaultValueExpression().getText());
		}
		ic.setDescription(param.getDescription());
		ic.setType(Conversion(param.getValueClassName()));

		return ic;
	}

	/**
	 * 根据报表参数名查询数据库中的报表参数集合
	 * 
	 * @param icSet
	 *            数据库中的报表参数集合
	 * @param JRParameter
	 *            报表参数
	 * @return ReportParameter
	 */
	private Parameter findListEntity(Set<Parameter> icSet, JRParameter param) {
		for (Parameter ic : icSet) {
			String rpEnName = ic.getEnName();
			String jrEnName = param.getName();
			if (jrEnName.trim().equals(rpEnName.trim())) {
				return ic;
			}
		}
		return null;
	}

	/**
	 * 把类型名转换成枚举
	 * 
	 * @param className
	 *            类型名
	 * @return InputControlEnum 枚举
	 */
	private ParametersType Conversion(String className) {
		if (className.toLowerCase().indexOf("boolean") > -1) {
			return ParametersType.BOOLEAN;
		}
		return ParametersType.TEXT;
	}

}
