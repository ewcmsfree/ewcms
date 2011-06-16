/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.out;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ewcms.generator.freemarker.FreemarkerUtil;
import com.ewcms.generator.freemarker.directive.DirectiveOutable;

import freemarker.core.Environment;
import freemarker.template.TemplateModelException;

/**
 * 限定标签输出字符长度
 * 
 * @author wangwei
 */
public class LengthDirectiveOut implements DirectiveOutable {
    private static final Logger logger = LoggerFactory.getLogger(LengthDirectiveOut.class);
    
    private static final String LENGTH_PARAM_NAME = "length";
    private static final String MARK_PARAM_NAME = "mark";
    private static final int NOT_LIMIT_LENGTH = -1;
    private static final String DEFAULT_MARK = "...";
    
    private String lengthParam = LENGTH_PARAM_NAME;
    private String markParam = MARK_PARAM_NAME;
    
    @SuppressWarnings("rawtypes")
    @Override
    public Object loopValue(Object value, Environment env, Map params)throws TemplateModelException {
        Assert.notNull(value);
        return constructOut(value,env,params);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String constructOut(Object value, Environment env, Map params)throws TemplateModelException {
        Assert.notNull(value);
        String s = getString(value);
        int length = getLength(params);
        if(length == NOT_LIMIT_LENGTH){
            return s;
        }else{
            return s.substring(0, length) + getMark(params);
        }
    }
    
    private String getString(Object value)throws TemplateModelException{
        if(value instanceof String){
            return (String)value;
        }else{
            logger.error("Value is not string");
            throw new TemplateModelException("LengthDriectiveOut only user string");
        }
    }
    
    @SuppressWarnings("rawtypes")
    private int getLength(Map params)throws TemplateModelException {
        Integer length= FreemarkerUtil.getInteger(params, lengthParam);
        return length == null ? NOT_LIMIT_LENGTH : length;
    }
    
    @SuppressWarnings("rawtypes")
    private String getMark(Map params)throws TemplateModelException{
        String mark = FreemarkerUtil.getString(params, markParam);
        return mark == null ? DEFAULT_MARK : mark;
    }
    /**
     * 设置长度参数名
     * 
     * @param param 参数名
     */
    public void setLengthParam(String param){
        lengthParam = param;
    }
    
    /**
     * 省略标识参数名 
     * 
     * @param param 参数名
     */
    public void setMarkParam(String param){
        markParam = param;
    }
}
