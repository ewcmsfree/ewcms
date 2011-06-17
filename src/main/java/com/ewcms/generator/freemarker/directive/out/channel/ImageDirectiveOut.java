/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
 
package com.ewcms.generator.freemarker.directive.out.channel;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ewcms.generator.freemarker.directive.DirectiveOutable;

import freemarker.core.Environment;
import freemarker.template.TemplateModelException;

/**
 * 频道图片标签显示
 * 
 * @author wangwei
 */
public class ImageDirectiveOut implements DirectiveOutable{

    private static final String DEFAULT_IMAGE_NAME = "icon.png";
    
    private String imageName = DEFAULT_IMAGE_NAME;
    
    @SuppressWarnings("rawtypes")
    @Override
    public Object loopValue(Object value, Environment env, Map params)throws TemplateModelException {
        return constructOut(value,env,params);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String constructOut(Object value, Environment env, Map params)throws TemplateModelException {
        if(StringUtils.endsWith((String)value, "/")){
            return value.toString() + imageName;
        }else{
            return value.toString() + "/" + imageName;    
        }
    }
    
    public void setImageName(String name){
        imageName = name;
    }
}
