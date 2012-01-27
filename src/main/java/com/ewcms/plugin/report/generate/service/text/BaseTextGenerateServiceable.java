/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.generate.service.text;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.plugin.BaseRuntimeException;
import com.ewcms.plugin.report.model.TextReport.Type;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * 抽象报表引擎
 * 
 * @author 吴智俊
 */
public abstract class BaseTextGenerateServiceable {

	private static final Logger logger = LoggerFactory.getLogger(BaseTextGenerateServiceable.class);

	public byte[] export(InputStream in, Type type,	Map<String, Object> parameters, HttpServletResponse response,HttpServletRequest request) {
		try {
			JasperDesign design = JRXmlLoader.load(in);
			JasperReport jasperReport = JasperCompileManager.compileReport(design);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
			return generate(jasperPrint, response, request);
		} catch (Exception e) {
			logger.error("Base Text Generate Exception", e);
			throw new BaseRuntimeException(e.toString());
		}
	}

	/**
	 * 实现报表类型实例
	 * 
	 * @param out
	 * @param jasperPrint
	 */
	protected abstract byte[] generate(JasperPrint jasperPrint, HttpServletResponse response, HttpServletRequest request);
}
