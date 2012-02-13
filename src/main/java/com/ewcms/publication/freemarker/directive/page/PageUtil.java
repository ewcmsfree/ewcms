/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.page;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.publication.freemarker.FreemarkerUtil;
import com.ewcms.publication.freemarker.GlobalVariable;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * Page工具类
 * <br>
 * 
 * 得到当前页数和总页数
 * 
 * @author wangwei
 */
public class PageUtil {
    
    private static final Integer DEFAULT_PAGE_COUNT = 1;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;

    /**
     * 得到当前页数
     * 
     * @param env 
     *         Freemarker 环境变量
     * @return
     * @throws TemplateModelException
     */
    public static Integer getPageNumberValue(Environment env)throws TemplateException {
        Integer value = FreemarkerUtil.getInteger(env,GlobalVariable.PAGE_NUMBER.toString());
        return EmptyUtil.isNull(value) ? DEFAULT_PAGE_NUMBER : value;
    }

    /**
     * 得到总页数
     * 
     * @param env 
     *         Freemarker 环境变量
     * @return
     * @throws TemplateModelException
     */
    public static Integer getPageCountValue(Environment env)throws TemplateException {
        Integer value = FreemarkerUtil.getInteger(env, GlobalVariable.PAGE_COUNT.toString());
        return EmptyUtil.isNull(value) ? DEFAULT_PAGE_COUNT : value;
    }

}
