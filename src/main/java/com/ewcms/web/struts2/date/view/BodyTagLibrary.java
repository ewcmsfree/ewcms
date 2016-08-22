/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.struts2.date.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.TagLibraryDirectiveProvider;
import org.apache.struts2.views.TagLibraryModelProvider;

import com.ewcms.web.struts2.date.view.freemarker.tag.BodyModel;
import com.opensymphony.xwork2.util.ValueStack;

/**
 *功能说明

 * @author 周冬初
 *
 */
public class BodyTagLibrary implements TagLibraryModelProvider, TagLibraryDirectiveProvider {
	
    public Object getModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new BodyModel(stack,req,res);
    }

	@SuppressWarnings("rawtypes")
	public List<Class> getDirectiveClasses() {
        return new ArrayList<Class>();
    }
}
