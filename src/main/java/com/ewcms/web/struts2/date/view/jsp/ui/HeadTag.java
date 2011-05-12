/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.struts2.date.view.jsp.ui;

import com.ewcms.web.struts2.date.component.Head;
import com.opensymphony.xwork2.util.ValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 * 错误信息输出标签
 * 
 * @author 周冬初
 */
public class HeadTag extends AbstractUITag {
	private static final long serialVersionUID = 1L;
	@Override
    public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        return new Head(stack, request, response);
    }
}
