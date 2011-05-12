/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.struts2.date.view.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.ewcms.web.struts2.date.component.Body;
import com.opensymphony.xwork2.util.ValueStack;

/**
 *功能说明

 * @author 周冬初
 *
 */
public class BodyTag extends AbstractUITag {
	private static final long serialVersionUID = 1L;
	private String name;
	private String option = "inputsimple";
	private String lang = "auto";
	private String format = "yyyy-MM-dd";
	@Override
    public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        return new Body(stack, request, response);
    }
	public void setName(String name) {
		this.name = name;
	}
	
    public void setOption(String option) {
		this.option = option;
	}
       
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	@Override
    protected void populateParams() {
        super.populateParams();

        Body body = (Body) this.component;

        body.setName(name);
        body.setOption(option);
        body.setLang(lang);
        body.setFormat(format);
    }	
}
