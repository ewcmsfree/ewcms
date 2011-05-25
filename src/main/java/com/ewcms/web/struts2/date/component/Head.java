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

import com.opensymphony.xwork2.util.ValueStack;

/**
 *功能说明

 * @author 周冬初
 *
 */
@StrutsTag(description = "Render HTML output date.",
		name = "datepickerhead", tldTagClass = "com.ewcms.web.struts2.date.view.jsp.ui.HeadTag")
public class Head extends UIBean {
	private static final String TEMPLATE = "date/head";
    public Head(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack,request,response);
    }	
    
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	} 
}
