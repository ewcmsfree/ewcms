/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.uri;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
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
    
    private String uriPatter;
    private List<String[]> variables ;
    private Map<String,Object> parameters = initParameters();
    
    @Override
    public void setParameters(Map<String,Object> ps){
        parameters.putAll(ps);
    }
    
    @Override
    public void parse(String patter)throws PublishException{
        Assert.notNull(patter);
        uriPatter = patter;
        variables = parseVariables(patter);
    }
    
    @Override
    public void putParameter(String parameter,Object value){
        parameters.put(parameter, value);
    }
    
    @Override
    public String getUri()throws PublishException {
        Assert.notNull(parameters);
        
        String uri = uriPatter;
        if(uri == null){
            logger.error("Patter must setting");
            throw new PublishException("First call \"parse\" method");
        }
        
        if(variables.isEmpty()){
            return uri;
        }
        
        for(String[] var : variables){
            Object value = getVariableValue(var[0],parameters);
            String format = formatValue(value,var[1]);
            String exp ;
            if(var[1] == null){
                exp = "${"+var[0]+"}";
            }else{
                exp = "${"+var[0]+"?"+var[1]+"}";
            }
            uri = StringUtils.replace(uri, exp, format);
        }
        
        return uri;
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
     * 解析Uri模板得到变量名和数据显示格式
     * 
     * @param patter
     * @return
     */
    List<String[]> parseVariables(String patter)throws PublishException{
        
        String[] tokens = StringUtils.splitPreserveAllTokens(patter, "$");
        List<String[]> variables = new ArrayList<String[]>();
        for(String token : tokens){
           logger.debug("Token with \"$\" {}",token);
           if(StringUtils.startsWith(token, "{")) {
               if(token.length()< 3 || token.indexOf("}")< 0){
                   logger.error("Uri {} is error",token);
                   throw new PublishException("Uri patter is error.");
               }
               token = StringUtils.substring(token,1);
               String name = StringUtils.splitByWholeSeparator(token, "}")[0];
               String format = null;
               if(StringUtils.indexOf(name, "?") > 0){
                   String[] s = StringUtils.splitByWholeSeparator(name, "?");
                   name = s[0];
                   format = s[1];
               }
               
               variables.add(new String[]{name,format});
           }
        }
        return variables;
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
        
        map.put("a", GlobalVariable.DOCUMENT.toString());
        map.put("c", GlobalVariable.CHANNEL.toString());
        map.put("s", GlobalVariable.SITE.toString());
        map.put("p", GlobalVariable.PAGE_NUMBER.toString());
        
        map.put("article", GlobalVariable.DOCUMENT.toString());
        map.put("channel", GlobalVariable.CHANNEL.toString());
        map.put("site", GlobalVariable.SITE.toString());
        map.put("page", GlobalVariable.PAGE_NUMBER.toString());
        
        map.put("文章", GlobalVariable.DOCUMENT.toString());
        map.put("频道", GlobalVariable.CHANNEL.toString());
        map.put("站点", GlobalVariable.SITE.toString());
        map.put("页数", GlobalVariable.PAGE_NUMBER.toString());
        
        map.put(GlobalVariable.DOCUMENT.toString(), GlobalVariable.DOCUMENT.toString());
        map.put(GlobalVariable.CHANNEL.toString(), GlobalVariable.CHANNEL.toString());
        map.put(GlobalVariable.SITE.toString(), GlobalVariable.SITE.toString());
        map.put(GlobalVariable.PAGE_NUMBER.toString(), GlobalVariable.PAGE_NUMBER.toString());
        
        map.put("now", "now");
        map.put("today", "now");
        
        return map;
    }
}
