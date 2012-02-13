/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.page;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.publication.freemarker.FreemarkerUtil;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.freemarker.directive.PropertyDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 显示页数属性标签
 * 
 * @author wangwei
 */
public class PageOutDirective extends PropertyDirective {

    private static final Logger logger = LoggerFactory.getLogger(PageOutDirective.class);
    private static final String ACTIVE_PARAM_NAME = "active";

    private String activeParam = ACTIVE_PARAM_NAME;
    private Map<String,String> aliasProperties = initDefaultAliasProperties();
    
    @Override
    @SuppressWarnings("rawtypes") 
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException {
        
        Boolean active = getActiveValue(params);
        if(active == null){
            super.execute(env, params, loopVars, body);
            return;
        }
        Object objectValue = getObjectValue(env, params);
        if(objectValue != null && objectValue instanceof PageOut){
            PageOut page = (PageOut)objectValue;
            if(page.isActive().booleanValue() == active.booleanValue()){
                body.render(env.getOut());
            }
        }
    }
    
    /**
     * 得到是否激活页面数
     * 
     * @param params 参数集合
     * @return
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private Boolean getActiveValue(Map params) throws TemplateException {
        return FreemarkerUtil.getBoolean(params, activeParam);
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    protected Object defaultObjectValue(Environment env, Map params)throws TemplateException{
        Integer count = this.getPageCountValue(env);
        Integer number = this.getPageNumberValue(env);
        return new PageOut(count,number);
     }
    
    /**
     * 得到当前页数
     * 
     * @param env 
     *         Freemarker 环境变量
     * @return
     * @throws TemplateModelException
     */
    private Integer getPageNumberValue(Environment env)throws TemplateException {
        return PageUtil.getPageNumberValue(env);
    }

    /**
     * 得到总页数
     * 
     * @param env 
     *         Freemarker 环境变量
     * @return
     * @throws TemplateModelException
     */
    private Integer getPageCountValue(Environment env)throws TemplateException {
        return PageUtil.getPageCountValue(env);
    }
    
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
    
    public void setActiveParam(String activeParam){
        this.activeParam = activeParam;
    }
}
