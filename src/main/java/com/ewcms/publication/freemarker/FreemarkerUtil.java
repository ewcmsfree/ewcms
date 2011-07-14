/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;

import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.SimpleObjectWrapper;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateSequenceModel;

/**
 * FreemarkerUtil工具类
 * <br>
 * 
 * 实现了得到Freemarker标签参数和其变量的值。
 * 
 * @author wangwei
 */
public class FreemarkerUtil {
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);
    
    private static BeansWrapper wrapper = SimpleObjectWrapper.getDefaultInstance();
    
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * 得到参数中布尔值
     * 
     * <pre>
     * xxx = true      = true
     * xxx = false     = true
     * xxx = "true"    = true
     * xxx = "false"   = false
     * </pre>
     *  
     * @param params 
     *            参数集合
     * @param name
     *            参数名
     * @return 如果不存在则返回null
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    public static Boolean getBoolean(Map params,String name) throws TemplateException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            TemplateModel value =(TemplateModel)params.get(name);
            return getBoolean(value,true);    
        }
    }

    /**
     * 得到Freemarker变量中的布尔值
     * 
     * @param env 
     *          Freemarker环境变量
     * @param name
     *          变量名
     * @return  如果不存在或不是布尔型则返回null
     * @throws TemplateException
     */
    public static Boolean getBoolean(Environment env, String name) throws TemplateException {
        TemplateModel value = env.getVariable(name);
        return getBoolean(value,false);
    }

    /**
     * 处理Freemarker数据模型，得到布尔值。
     * 
     * @param model 
     *           freemarker数据模型
     * @param parse
     *           如果是字符串是否解析
     * @return
     * @throws TemplateException
     */
    static Boolean getBoolean(TemplateModel model,boolean parse) throws TemplateException {
        
        if (EmptyUtil.isNull(model)) {
            logger.debug("Model variable is null");
            return null;
        }
        
        Object value = wrapper.unwrap(model);
        logger.debug("Value type is {}",value.getClass().getName());
        if (value instanceof Boolean) {
            return (Boolean)value;
        }else if((value instanceof String) && parse){
            return Boolean.valueOf((String)value);    
        }else{
            return null;
        }
    }

    /**
     * 得到参数的日期值
     * 
     * @param params
     *            参数集合
     * @param name
     *            参数名
     * @return 如果不存在则返回null
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    public static Date getDate(Map params,String name) throws TemplateException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            TemplateModel value =(TemplateModel)params.get(name);
            return getDate(value,true);
        }
    }

    /**
     * 得到Freemarker变量中的日期值
     * 
     * @param env
     *          Freemarker环境变量
     * @param name
     *          变量名
     * @return 如果不存在则返回null
     * @throws TemplateException
     */
    public static Date getDate(Environment env, String name) throws TemplateException {
        TemplateModel value = env.getVariable(name);
        return getDate(value,false);
    }

    /**
     * 处理Freemarker数据模型，得到日期值。
     * 
     * @param model 
     *           freemarker数据模型
     * @param parse
     *           如果是字符串是否解析
     * @return
     * @throws TemplateException
     */
    static Date getDate(TemplateModel model,boolean parse) throws TemplateException {
        
        if (EmptyUtil.isNull(model)) {
            logger.debug("Model variable is null");
            return null;
        }
        
        Object value = wrapper.unwrap(model);
        logger.debug("Value type is {}",value.getClass().getName());
        if (value instanceof Date) {
            return (Date)value;
        }else if(value instanceof String && parse){
            try {
                return dateFormat.parse((String)value);
            } catch (ParseException e) {
                logger.error("Date is {}.",value);
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 得到参数的整数值
     * 
     * @param params
     *            参数集合
     * @param name
     *            参数名
     * @return 如果不存在则返回null
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    public static Integer getInteger(Map params, String name) throws TemplateException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            TemplateModel value =(TemplateModel)params.get(name);
            return getInteger(value,true);    
        }
    }

    /**
     * 得到Freemarker变量中的整数值
     * 
     * @param env
     *          Freemarker环境变量
     * @param name
     *          变量名
     * @return 如果不存在则返回null
     * @throws TemplateException
     */
    public static Integer getInteger(Environment env,String name) throws TemplateException {
        TemplateModel value = env.getVariable(name);
        return getInteger(value,false);
    }

    /**
     * 处理Freemarker数据模型，得到整数值。
     * 
     * @param model 
     *           freemarker数据模型
     * @param parse
     *           如果是字符串是否解析
     * @return
     * @throws TemplateException
     */
    static Integer getInteger(TemplateModel model,boolean parse) throws TemplateException {
        
        if (EmptyUtil.isNull(model)) {
            logger.debug("Model variable is null");
            return null;
        }
        
        Object value = wrapper.unwrap(model);
        logger.debug("Value type is {}",value.getClass().getName());
        if (value instanceof Number) {
            return ((Number)value).intValue();
        }else if(value instanceof String && parse){
            try{
                return Integer.valueOf((String)value);    
            }catch(NumberFormatException e){
                logger.error("Value is {},not is Integer",value);
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 得到参数的字符值
     * 
     * @param params
     *            参数集合
     * @param name
     *            参数名
     * @return 如果不存在则返回null
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    public static String getString(Map params, String name) throws TemplateException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            TemplateModel value = (TemplateModel)params.get(name);
            return getString(value);    
        }
    }

    /**
     * 得到Freemarker中变量的字符值
     * 
     * @param env
     *          Freemarker环境变量
     * @param name
     *          变量名
     * @return 如果不存在则返回null
     * @throws TemplateException
     */
    public static String getString(Environment env,String name) throws TemplateException {
        TemplateModel value = env.getVariable(name);
        return getString(value);
    }

    /**
     * 处理Freemarker数据模型，得到字符值。
     * 
     * @param model 
     *           freemarker数据模型
     * @return
     * @throws TemplateException
     */
    static String getString(TemplateModel model) throws TemplateException {
        
        if (EmptyUtil.isNull(model)) {
            logger.debug("Model variable is null");
            return null;
        }
        
        Object value = wrapper.unwrap(model);
        logger.debug("Value type is {}",value.getClass().getName());
        if (value instanceof String) {
            return (String)value;
        }else{
            return null;
        }
    }

    /**
     * 得到参数的对象值
     * 
     * @param params
     *            参数集合
     * @param name
     *            参数名
     * @return 如果不存在则返回null
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    public static Object getBean(Map params, String name) throws TemplateException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            TemplateModel value = (TemplateModel)params.get(name);
            return getBean(value);
        }
    }

    /**
     * 得到Freemarker中变量的对象值
     * 
     * @param env
     *          Freemarker环境变量
     * @param name
     *          变量名
     * @return 如果不存在则返回null
     * @throws TemplateException
     */
    public static Object getBean(Environment env, String name) throws TemplateException {
        TemplateModel value = env.getVariable(name);
        return getBean(value);
    }

    /**
     * 处理Freemarker数据模型，得到对象值。
     * 
     * @param model 
     *           freemarker数据模型
     * @return
     * @throws TemplateException
     */
    static Object getBean(TemplateModel model) throws TemplateException {
        
        if (EmptyUtil.isNull(model)) {
            logger.debug("Model variable is null");
            return null;
        }
        
        logger.debug("Model type is {}",model.getClass().getName());
        if (model instanceof BeanModel) {
            return wrapper.unwrap(model);
        }else{
            return null;
        }
    }

    /**
     * 得到参数的数组值
     * 
     * @param params
     *            参数集合
     * @param name
     *            参数名
     * @return 如果不存在则返回null
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    public static List<?> getSequence(Map params, String name) throws TemplateException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            TemplateModel value =(TemplateModel)params.get(name);
            return getSequence(value);    
        }
    }

    /**
     * 得到Freemarker中变量的数组值
     * 
     * @param env
     *          Freemarker环境变量
     * @param name
     *          变量名
     * @return 如果不存在则返回null
     * @throws TemplateException
     */
    public static List<?> getSequence(Environment env, String name) throws TemplateException {
        TemplateModel value = env.getVariable(name);
        return getSequence(value);
    }

    static List<?> getSequence(TemplateModel model) throws TemplateException {
        if (EmptyUtil.isNull(model)) {
            return null;
        }
        
        logger.debug("Model type is {}",model.getClass().getName());
        if (model instanceof TemplateSequenceModel) {
            return (List<?>)wrapper.unwrap(model);
        }else{
            return null;
        }
    }
    
    /**
     * 是否是调试状态
     * 
     * @param env
     *          Freemarker环境变量
     * @return
     * @throws TemplateException
     */
    public static boolean isDebug(Environment env) throws TemplateException {
        Boolean debug = getBoolean(env, GlobalVariable.DEBUG.toString());
        return debug == null ? false : true;
    }

    /**
     * 设置Freemarker中变量
     * 
     * @param env
     *          Freemarker环境变量
     * @param name
     *          变量名
     * @param value
     *          变量值
     * @throws TemplateException
     */
    public static void setVariable(Environment env, String name, Object value) throws TemplateException {
        env.setVariable(name, env.getObjectWrapper().wrap(value));
    }

    /**
     * 删除Freemarker中变量
     * 
     * @param env
     *          Freemarker环境变量
     * @param name
     *          变量名
     * @throws TemplateException
     */
    public static void removeVariable(Environment env, String name) throws TemplateException {
        env.getCurrentNamespace().remove(name);
    }
}
