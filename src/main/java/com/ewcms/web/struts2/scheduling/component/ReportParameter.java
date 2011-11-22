/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.struts2.scheduling.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.ewcms.plugin.report.generate.vo.PageShowParam;
import com.ewcms.web.struts2.scheduling.component.paser.ParameterMethod;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author 吴智俊
 */
@StrutsTag(name = "scheduling-reportparameter", tldTagClass = "com.ewcms.web.struts2.scheduling.view.jsp.ui.ReportParameterTag", allowDynamicAttributes = true, description = "Render HTML Popup Parameter tag.")
public class ReportParameter extends UIBean {

	private static final String TEMPLATE = "scheduling/reportparameter";

	public ReportParameter(ValueStack stack, HttpServletRequest request,HttpServletResponse response) {
		super(stack, request, response);
	}

	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	@SuppressWarnings("unchecked")
	public void evaluateExtraParams() {
		List<PageShowParam> list = (List<PageShowParam>) this.findValue("pageShowParams",List.class);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parameterMethod", new ParameterMethod());
		map.put("params", list);
		addAllParameters(map);
	}

}
