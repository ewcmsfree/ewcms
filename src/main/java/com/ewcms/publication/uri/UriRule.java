/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.uri;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.GlobalVariable;

/**
 * 实现统一资源地址规则实现
 * 
 * @author wangwei
 */
public class UriRule implements UriRuleable{

    private static final Logger logger = LoggerFactory.getLogger(UriRule.class);
    
    private static final DateFormat DEFAULT_DATA_FORMAT =new SimpleDateFormat("yyyy-MM-dd");
    private static final DecimalFormat DEFAULT_NUMBER_FORMAT = new DecimalFormat("#");
    private static final Map<String,String> ALIAS_PARAMETERS = initAliasParameters();
    
    private final RuleParseable ruleParse ;
    private Map<String,Object> parameters = initParameters();
    
    public UriRule(RuleParseable ruleParse){
        this.ruleParse = ruleParse;
    }
    
    @Override
    public void setParameters(Map<String,Object> ps){
        parameters.putAll(ps);
    }
        
    @Override
    public void putParameter(String parameter,Object value){
        parameters.put(parameter, value);
    }
    
    @Override
    public String getPatter(){
        return ruleParse.getPatter();
    }
    
    @Override
    public String getUri()throws PublishException {
        Assert.notNull(parameters);

        Map<String,String> variables = ruleParse.getVariables();
        if(variables.isEmpty()){
            return ruleParse.getPatter();
        }
        
        String uri = ruleParse.getPatter();
        for(String name : variables.keySet()){
            String format = variables.get(name);
            String exp = 
                (format == null ?
                        String.format("${%s}", name): 
                        String.format("${%s?%s}", name,format));
            Object value = getVariableValue(name,parameters);
            String formatValue = formatValue(value,format);
            uri = StringUtils.replace(uri, exp, formatValue);
        }
        
        return FilenameUtils.normalize(uri);
    }
    
    /**
     * 初始化参数列表
     */
    private Map<String,Object> initParameters(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("now", new Date());
        
        return map;
    }
    
    /**
     * 得到变量的值
     * 
     * @param variable 变量名称
     * @param parameters 参数集合
     * @return 变量值
     * @throws PublishException
     */
    Object getVariableValue(String variable,Map<String,Object> parameters)throws PublishException {
        logger.debug("Variable is {}",variable);
        String p = StringUtils.splitPreserveAllTokens(variable,".")[0];
        logger.debug("Parameter name is {}",p);
        
        String parameter = ALIAS_PARAMETERS.get(p);
        if(parameter == null){
            logger.warn("\"{}\" parameter is not exist",p);
            parameter = p;
        }
        Object object = parameters.get(parameter);
        if(object == null){
            logger.error("\"{}\" parameter is not exist",parameter);
            throw new PublishException(variable + " is not exist");
        }
        try{
            if(!p.equals(variable)){
                String property = StringUtils.removeStart(variable, p + ".");
                logger.debug("Property name is {}",property);
                return PropertyUtils.getProperty(object, property);
            }else{
                return object;
            }
        }catch(Exception e){
            logger.error("Get variable value is error:{}",e.toString());
            throw new PublishException(e);
        }
    }
    
    /**
     * 格式化输出值
     * 
     * @param value 值
     * @param patter 格式模式
     * @return 格式后字符串
     */
    String formatValue(Object value,String patter){
        
        logger.debug("Format type is {}",value.getClass().getName());
        
        if(value instanceof Date){
            if(patter == null || patter.length() == 0){
                return DEFAULT_DATA_FORMAT.format(value);
            }else{
                DateFormat dateFormat = new SimpleDateFormat(patter);
                return dateFormat.format(value);
            }
        }else if(value instanceof Number){
            if(patter == null || patter.length() == 0){
                return DEFAULT_NUMBER_FORMAT.format(value);
            }else{
                DecimalFormat numberFormat = new DecimalFormat(patter);
                return numberFormat.format(value);
            }
        }else{
            return value.toString();
        }
    }
    
    private static Map<String,String> initAliasParameters(){
        Map<String,String> map = new HashMap<String,String>();
        
        map.put("a", GlobalVariable.ARTICLE.toString());
        map.put("c", GlobalVariable.CHANNEL.toString());
        map.put("s", GlobalVariable.SITE.toString());
        map.put("p", GlobalVariable.PAGE_NUMBER.toString());
        
        map.put("article", GlobalVariable.ARTICLE.toString());
        map.put("channel", GlobalVariable.CHANNEL.toString());
        map.put("site", GlobalVariable.SITE.toString());
        map.put("page", GlobalVariable.PAGE_NUMBER.toString());
        
        map.put("文章", GlobalVariable.ARTICLE.toString());
        map.put("频道", GlobalVariable.CHANNEL.toString());
        map.put("站点", GlobalVariable.SITE.toString());
        map.put("页数", GlobalVariable.PAGE_NUMBER.toString());
        
        map.put(GlobalVariable.ARTICLE.toString(), GlobalVariable.ARTICLE.toString());
        map.put(GlobalVariable.CHANNEL.toString(), GlobalVariable.CHANNEL.toString());
        map.put(GlobalVariable.SITE.toString(), GlobalVariable.SITE.toString());
        map.put(GlobalVariable.PAGE_NUMBER.toString(), GlobalVariable.PAGE_NUMBER.toString());
        
        map.put("now", "now");
        map.put("today", "now");
        
        return map;
    }
}
