/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.struts2.scheduling.component.paser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ewcms.plugin.report.generate.vo.PageShowParam;
import com.ewcms.web.struts2.scheduling.component.paser.type.BooleanParser;
import com.ewcms.web.struts2.scheduling.component.paser.type.CheckBoxParser;
import com.ewcms.web.struts2.scheduling.component.paser.type.DateParser;
import com.ewcms.web.struts2.scheduling.component.paser.type.ListParser;
import com.ewcms.web.struts2.scheduling.component.paser.type.SessionParser;
import com.ewcms.web.struts2.scheduling.component.paser.type.TextParser;
import com.ewcms.web.struts2.scheduling.component.paser.type.TypeParserable;

import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * @author 吴智俊
 */
public class ParameterMethod implements TemplateMethodModelEx {
	
	private static final Log log = LogFactory.getLog(ParameterMethod.class);
			
	private static Map<String, Object> typeMap = new HashMap<String, Object>();

	static {
		typeMap.put("boolean", new BooleanParser());
		typeMap.put("check", new CheckBoxParser());
		typeMap.put("date", new DateParser());
		typeMap.put("list", new ListParser());
		typeMap.put("session", new SessionParser());
		typeMap.put("text", new TextParser());
	}

	@SuppressWarnings({ "rawtypes" })
	public Object exec(List params) throws TemplateModelException {
		if (params.size() != 1) {
			log.error("参数错误");
			throw new TemplateModelException("参数错误");
		}
		StringModel stringModel = (StringModel)params.get(0);
		PageShowParam pagesParam = (PageShowParam)stringModel.getWrappedObject();
		return ((TypeParserable) typeMap.get(pagesParam.getType().name().toLowerCase())).parser(pagesParam);
	}

}
