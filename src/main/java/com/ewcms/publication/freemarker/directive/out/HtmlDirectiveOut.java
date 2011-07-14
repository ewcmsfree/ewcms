/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.out;

import java.util.Map;

import com.ewcms.publication.freemarker.FreemarkerUtil;
import com.ewcms.publication.freemarker.directive.DirectiveOutable;

import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * html格式标签输出
 * 
 * @author wangwei
 */
public abstract class HtmlDirectiveOut implements DirectiveOutable{

    private static final String CLASS_PARAM_NAME = "class";
    private static final String STYLE_PARAM_NAME = "style";
    
    private String classParam = CLASS_PARAM_NAME;
    private String styleParam = STYLE_PARAM_NAME;
    
    /**
     * 得到class
     * 
     * @param params 参数集合
     * @return class
     * @throws TemplateModelException
     */
    @SuppressWarnings("rawtypes")
    protected String getClassValue(Map params)throws TemplateException{
        String value =  FreemarkerUtil.getString(params, classParam);
        return value;
    }
    
    /**
     * 得到style
     * 
     * @param params 参数集合
     * @return style
     * @throws TemplateModelException
     */
    @SuppressWarnings("rawtypes")
    protected String getStyleValue(Map params)throws TemplateException{
        return FreemarkerUtil.getString(params, styleParam);
    }

    /**
     * 设置class参数名
     * 
     * @param name 参数名
     */
    public void setClassParam(String name) {
        this.classParam = name;
    }

    /**
     * 设置style参数名
     * 
     * @param name 参数名
     */
    public void setStyleParam(String name) {
        this.styleParam = name;
    }
}
