/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.struts2.date.view.freemarker.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.freemarker.tags.TagModel;

import com.ewcms.web.struts2.date.component.Body;
import com.opensymphony.xwork2.util.ValueStack;

/**
 *功能说明

 * @author 周冬初
 *
 */
public class BodyModel extends TagModel {
    public BodyModel(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack, req, res);
    }

    @Override
    protected Component getBean() {
        return new Body(this.stack, this.req, this.res);
    }
}
