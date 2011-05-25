/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.struts2.date.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;

/**
 *功能说明

 * @author 周冬初
 *
 */
@StrutsTag(description = "Render HTML output date body.",
		name = "datepicker", tldTagClass = "com.ewcms.web.struts2.date.view.jsp.ui.BodyTag")
public class Body extends UIBean {
	private static final String TEMPLATE = "date/body";
	private String name;
	private String option;
	private String lang;
	private String format;
	
    public Body(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack,request,response);
    }	
    
    @StrutsTagAttribute(description = "field name",type="String",required=true)
	public void setName(String name) {
		this.name = name;
	}

    @StrutsTagAttribute(description = "date display option",type="String")
	public void setOption(String option) {
		this.option = option;
	}
    
    @StrutsTagAttribute(description = "date display lang",type="String")
	public void setLang(String lang) {
		this.lang = lang;
	}
    @StrutsTagAttribute(description = "date display format",type="String")
	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	} 
	@Override
	public void evaluateExtraParams() {
		addParameter("name", name);
		addParameter("option", option);
		addParameter("lang",lang);
		addParameter("format",format);
	}	
}
