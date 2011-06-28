/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import java.util.Map;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.generator.freemarker.FreemarkerUtil;
import com.ewcms.generator.freemarker.GlobalVariable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * 页面跳转基础标签类
 * <br>
 * 主要为标签得到通用参数值如：页数、总页数。
 * 
 * @author wangwei
 *
 */
public abstract class SkipBaseDirective implements TemplateDirectiveModel {

    private static final Integer DEFAULT_PAGE_COUNT = 1;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;

    protected Integer getPageNumberValue(Environment env)throws TemplateModelException {
        Integer value = FreemarkerUtil.getInteger(env,GlobalVariable.PAGE_NUMBER.toString());
        return EmptyUtil.isNull(value) ? DEFAULT_PAGE_NUMBER : value;
    }

    protected Integer getPageCountValue(Environment env)throws TemplateModelException {
        Integer value = FreemarkerUtil.getInteger(env, GlobalVariable.PAGE_COUNT.toString());
        return EmptyUtil.isNull(value) ? DEFAULT_PAGE_COUNT : value;
    }

    @SuppressWarnings("rawtypes")
    protected String getUrlValue(Map params) throws TemplateException {
        return "";
    }
}
