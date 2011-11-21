/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ewcms.plugin.BaseException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *  解析报表文件
 *  
 * @author 吴智俊
 */

public class TextDesignUtil {
	
	/**
	 * 报表固定参数，得到报表参数时需要剔除部分
	 */
	private final static Map<String,String> PARAMMAP = new HashMap<String,String>();
	
	static{
		PARAMMAP.put("REPORT_PARAMETERS_MAP","");
		PARAMMAP.put("REPORT_CONNECTION","");
		PARAMMAP.put("REPORT_MAX_COUNT","");
		PARAMMAP.put("REPORT_DATA_SOURCE","");
		PARAMMAP.put("REPORT_SCRIPTLET","");
		PARAMMAP.put("REPORT_LOCALE","");
		PARAMMAP.put("REPORT_RESOURCE_BUNDLE","");
		PARAMMAP.put("REPORT_TIME_ZONE","");
		PARAMMAP.put("REPORT_VIRTUALIZER", "");
		PARAMMAP.put("REPORT_CLASS_LOADER","");
		PARAMMAP.put("REPORT_URL_HANDLER_FACTORY","");
		PARAMMAP.put("REPORT_FILE_RESOLVER", "");
		PARAMMAP.put("REPORT_FORMAT_FACTORY", "");
		PARAMMAP.put("IS_IGNORE_PAGINATION","");
		PARAMMAP.put("REPORT_TEMPLATES", "");
		
	}
	
	private JasperDesign design = null;

	/**
	 * 构造函数
	 * 
	 * @param in 报表数据流
	 * @throws ReportEngineException
	 */
	public TextDesignUtil(InputStream in) throws BaseException {
		try {
			design = JRXmlLoader.load(in);
		} catch (JRException e) {
			throw new BaseException();
		}
	}

	/**
	 * 得到报表参数
	 * 
	 * @return List<JRParameter>
	 * @throws ReportEngineException
	 */
	public List<JRParameter> getParameters() throws BaseException {
		
		List<JRParameter> list = new ArrayList<JRParameter>();
		try {
			JRParameter[] jrplist = design.getParameters();
			for (JRParameter jrp : jrplist) {
				String value = PARAMMAP.get(jrp.getName());
				if (value != null) continue;
				list.add(jrp);
			}

			return list;
		} catch (Exception e) {
			throw new BaseException();
		}
	}

	/**
	 * 得到报表SQL语句
	 * 
	 * @return SQL语句
	 * @throws ReportEngineException
	 */
	public String getQueryString() throws BaseException {
		try {
			JRQuery jrq = design.getQuery();
			String sql = jrq.getText();
			return sql;
		} catch (Exception e) {
			throw new BaseException();
		}
	}
}
