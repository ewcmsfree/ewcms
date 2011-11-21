/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.struts2.scheduling.view.freemarker.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.freemarker.tags.TagModel;

import com.ewcms.web.struts2.scheduling.component.ReportParameter;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author 吴智俊
 */
public class ReportParameterModel extends TagModel {

	public ReportParameterModel(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		super(stack, req, res);
	}

	protected Component getBean() {
		return new ReportParameter(this.stack,this.req,this.res);
	}

}
