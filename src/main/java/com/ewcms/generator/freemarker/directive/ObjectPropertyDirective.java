/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ewcms.common.lang.EmptyUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 得到对象属性值
 * 
 * <p>通过设置指定属性名，得到该属性的值</p>
 * 
 * @author wangwei
 */
public class ObjectPropertyDirective implements TemplateDirectiveModel {

    private static Logger logger = LoggerFactory.getLogger(ObjectPropertyDirective.class);
    
    private static final String VALUE_PARAM_NAME = "value";
    private static final String NAME_PARAM_NAME = "name";
    private static final String DEFAULT_Variable_NAME = "o";
    
    private String valueParam = VALUE_PARAM_NAME;
    private String nameParam = NAME_PARAM_NAME;
    private String variableName = DEFAULT_Variable_NAME;
    
    @Override
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException {
        
        Object objectValue = getObjectValue(env, params);
        String propertyName = getPropertyName(params);
        if(EmptyUtil.isNull(propertyName)){
            logger.error("\"name\" parameter must set");
            throw new TemplateModelException("\"name\" parameter must set");
        }
        
        try {
            Object value = getValue(objectValue,propertyName);
 
            if(EmptyUtil.isArrayNotEmpty(loopVars)){
                loopVars[0] = env.getObjectWrapper().wrap(value);
                if(EmptyUtil.isNull(body)){
                    logger.error("body is empty");
                    throw new TemplateModelException("body is empty");
                }else{
                    body.render(env.getOut());
                }
            }else if(EmptyUtil.isNotNull(body)){
                DirectiveUtil.setVariable(env, variableName, value);
                body.render(env.getOut());
                DirectiveUtil.removeVariable(env, variableName);
            }else{
                Writer out = env.getOut();
                String outValue = constructOut(value);
                out.write(outValue.toString());
                out.flush();
            }
        } catch (NoSuchMethodException e) {
            Writer out = env.getOut();
            out.write(e.toString());
            out.flush();
            throw new TemplateModelException(e.getMessage());
        }
    }
    
    /**
     * 构造输出字符串
     * 
     * @param value
     *            被构造的值
     * @return
     */
    protected String constructOut(final Object value){
        return value.toString();
    }
    
    /**
     * 得到对象的值
     * 
     * @param env
     *            环境
     * @param params
     *            标签参数集合
     * @return
     * @throws TemplateModelException
     */
    @SuppressWarnings("rawtypes")
    protected Object getObjectValue(final Environment env, final Map params)throws TemplateModelException {

        Object object =  DirectiveUtil.getBean(params, valueParam);
        if (EmptyUtil.isNotNull(object)) {
            logger.debug("Get value is {}",object);
            return object;
        }

        String variable = DirectiveUtil.getString(params, valueParam);
        if (EmptyUtil.isNotNull(variable)) {
            logger.debug("Get value param is {}", variable);
            object = DirectiveUtil.getBean(env, variable);
            if (EmptyUtil.isNotNull(object)) {
                logger.debug("Get value is {}",object);
                return object;
            }
        }
        
        logger.error("\"{}\"  has not value",valueParam);
        throw new TemplateModelException("Object value not exist");
    }
    
    /**
     * 得到属性名
     * 
     * @param params
     *            标签参数集合
     * @return
     * @throws TemplateModelException
     */
    @SuppressWarnings("rawtypes")
    protected String getPropertyName(final Map params)throws TemplateModelException {
        String property = DirectiveUtil.getString(params, nameParam);
        logger.debug("Property name is {}",property);
        return property;
    }
    
    /**
     * 得到对象属性值
     * 
     * @param objectValue
     *                对象
     * @param property 
     *                属性名
     * @return           
     */
    protected Object getValue(final Object objectValue,final String property)throws NoSuchMethodException{
        if(property.indexOf(".") > -1){
            StringTokenizer parse = new StringTokenizer(property,".");
            Object value = objectValue;
            while(parse.hasMoreTokens()){
                String name = parse.nextToken();
                value = getPropertyValue(value,name);
            }
            return value;
        }else{
            return getPropertyValue(objectValue,property);
        }
    }

    /**
     * java bean get方法名类型
     * 
     * @author wangwei
     *
     */
    enum BeanGetMethodType {get,is}
    
    /**
     * 得到属性值
     * 
     * <p>
     * 该属性不是包含对象的属性，也就是属性名不含"."
     * </p>
     * 
     * @param object
     *            对象
     * @param name
     *            属性名
     * @return 属性值
     * @throws NoSuchMethodException
     */
    private Object getPropertyValue(final Object object,final String name)throws NoSuchMethodException{
        Assert.isTrue(name.indexOf('.') == -1,"simple property name has \".\" char");
        
        Class<?> clazz = object.getClass();
        
        for(BeanGetMethodType type : BeanGetMethodType.values()){
            try{
                String methodName = type.name() + StringUtils.capitalize(name);
                Method method = clazz.getMethod(methodName);
                logger.debug("Method is {}",method);
                return method.invoke(object);    
            }catch(Exception e){
                logger.debug("get{} value exception:{}",name,e);
            }
        }
                
        try{
            Field filed = clazz.getDeclaredField(name);
            logger.debug("Filed is {}",filed);
            return filed.get(object);
        }catch(Exception e){
            logger.debug("{} value exception:{}",name,e);
        }
                
        throw new NoSuchMethodException("No property \"" + name + "\" on instance of " + object.getClass().getName());
    }

    /**
     * 设置值对象参数名
     * 
     * @param name 参数名
     */
    public void setValueParam(String name) {
        valueParam = name;
    }

    /**
     * 设置对象属性参数名
     * 
     * @param name 参数名
     */
    public void setNameParam(String name){
        nameParam = name;
    }
    
    /**
     * 设置属性值放入Freemarker环境中变量名
     * 
     * @param name 变量名
     */
    public void setVariable(String name){
        variableName = name;
    }
}
