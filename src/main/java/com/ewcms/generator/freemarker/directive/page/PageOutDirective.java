/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.freemarker.directive.ObjectPropertyDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * 显示页数属性标签
 * 
 * @author wangwei
 */
public class PageOutDirective extends ObjectPropertyDirective {

    private static final Logger logger = LoggerFactory.getLogger(PageOutDirective.class);
    
    private Map<String,String> aliasProperties = initDefaultAliasProperties();
    
    @Override
    protected String defaultValueParamValue(){
        return GlobalVariable.PAGE_OUT.toString();
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    protected String getPropertyName(Environment env,Map params)throws TemplateException{
        String value = super.getPropertyName(env,params);
        String property = aliasProperties.get(value);
        if(EmptyUtil.isNull(property)){
            logger.error("Get not property name of \"{}\"",value);
            throw new TemplateModelException("Get not property name of \""+value+"\"");
        }
        return property;
    }
    
    private Map<String,String> initDefaultAliasProperties(){
        
        Map<String,String> map = new HashMap<String,String>();
        
        map.put("页数", "number");
        map.put("n", "number");
        map.put("number", "number");
        
        map.put("总页数", "count");
        map.put("c", "count");
        map.put("count", "count");
        
        map.put("标签", "label");
        map.put("l", "label");
        map.put("label", "label");
        
        map.put("链接地址", "url");
        map.put("u", "url");
        map.put("url", "url");
        
        return map;
    }
}
