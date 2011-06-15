/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive;

import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateModelException;

/**
 * 构造标签输出的内容
 * 
 * @author wangwei
 *
 * @param <T> 值的类型
 */
public interface DirectiveOutable{

    /**
     * 处理标签返回值
     * 
     * @param value
     *            被处理的值
     * @param env
     *            Freemarker环境
     * @param params
     *            标签参数集合
     * @return 返回值
     * @throws TemplateModelException
     */
    @SuppressWarnings("rawtypes")
    Object loopValue(Object value,Environment env,Map params)throws TemplateModelException;
    
    /**
     * 构造标签输出的内容
     * 
     * @param value
     *            被构造输出的值
     * @param env
     *            Freemarker环境
     * @param params
     *            标签参数集合
     *            
     * @return  输出内容
     * @throws TemplateModelException
     */
    @SuppressWarnings("rawtypes")
    String  constructOut(Object value,Environment env,Map params)throws TemplateModelException;
}
