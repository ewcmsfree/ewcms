/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.struts2.scheduling.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.TagLibrary;

import com.ewcms.web.struts2.scheduling.view.freemarker.tag.ReportParameterModel;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 * @author 吴智俊
 */
public class ReportParameterTagLibrary implements TagLibrary {

    public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new ReportParameterModel(stack, req, res);
    }

    @SuppressWarnings({ "rawtypes" })
    public List<Class> getVelocityDirectiveClasses() {
        return new ArrayList<Class>();
    }
}
