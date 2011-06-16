/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.out;

import java.util.Map;

import org.springframework.util.Assert;

import com.ewcms.generator.freemarker.directive.DirectiveOutable;

import freemarker.core.Environment;
import freemarker.template.TemplateModelException;

/**
 * 缺省标签输出
 * 
 * <p>输出对象的toString方法值</p>
 * 
 * @author wangwei
 */
public class DefaultDirectiveOut implements DirectiveOutable {

    @SuppressWarnings("rawtypes")
    @Override
    public String constructOut(Object value,Environment env,Map params)throws TemplateModelException {
        Assert.notNull(value);
        return value.toString();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object loopValue(Object value, Environment env, Map params)throws TemplateModelException {
        Assert.notNull(value);
        return value;
    }    
}
