/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.struts2.scheduling.view.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.ewcms.web.struts2.scheduling.component.ReportParameter;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author 吴智俊
 */
public class ReportParameterTag extends AbstractUITag {
	
	private static final long serialVersionUID = 5791757747548282718L;

	public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        return new ReportParameter(stack , request , response);
    }
    
    protected void populateParams(){
        super.populateParams();
    }
}
