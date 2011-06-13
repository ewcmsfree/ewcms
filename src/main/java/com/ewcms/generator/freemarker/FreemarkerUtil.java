/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker;

import java.util.Date;
import java.util.Map;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;

import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;
import freemarker.template.TemplateSequenceModel;

/**
 *
 * @author wangwei
 */
public class FreemarkerUtil {

    public static Boolean getBoolean(final Map params, final String name) throws TemplateModelException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            Object value = params.get(name);
            return getBoolean(value);
        }
    }

    public static Boolean getBoolean(final Environment env, final String name) throws TemplateModelException {
        Object value = env.getVariable(name);
        return getBoolean(value);
    }

    private static Boolean getBoolean(final Object value) throws TemplateModelException {
        if (EmptyUtil.isNull(value)) {
            return null;
        }
        if (value instanceof TemplateBooleanModel) {
            return ((TemplateBooleanModel) value).getAsBoolean();
        }
        return null;
    }

    public static Date getDate(final Map params, final String name) throws TemplateModelException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            Object value = params.get(name);
            return getDate(value);
        }
    }

    public static Date getDate(final Environment env, final String name) throws TemplateModelException {
        Object value = env.getVariable(name);
        return getDate(value);
    }

    private static Date getDate(final Object value) throws TemplateModelException {
        if (EmptyUtil.isNull(value)) {
            return null;
        }
        if (value instanceof TemplateDateModel) {
            return ((TemplateDateModel) value).getAsDate();
        }
        return null;
    }

    public static Integer getInteger(final Map params, final String name) throws TemplateModelException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            Object value = params.get(name);
            return getInteger(value);    
        }
    }

    public static Integer getInteger(final Environment env, final String name) throws TemplateModelException {
        Object value = env.getVariable(name);
        return getInteger(value);
    }

    private static Integer getInteger(final Object value) throws TemplateModelException {
        if (EmptyUtil.isNull(value)) {
            return null;
        }
        if (value instanceof TemplateNumberModel) {
            return ((TemplateNumberModel) value).getAsNumber().intValue();
        }
        return null;
    }

    public static String getString(final Map params, final String name) throws TemplateModelException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            Object value = params.get(name);
            return getString(value);    
        }
    }

    public static String getString(final Environment env, final String name) throws TemplateModelException {
        Object value = env.getVariable(name);
        return getString(value);
    }

    private static String getString(final Object value) throws TemplateModelException {
        if (EmptyUtil.isNull(value)) {
            return null;
        }
        if (value instanceof TemplateScalarModel) {
            return ((TemplateScalarModel) value).getAsString();
        }
        return null;
    }

    public static Object getBean(final Map params, final String name) throws TemplateModelException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            Object value = params.get(name);
            return getBean(value);
        }
    }

    public static Object getBean(final Environment env, String name) throws TemplateModelException {
        Object value = env.getVariable(name);
        return getBean(value);
    }

    private static Object getBean(final Object value) throws TemplateModelException {
        if (EmptyUtil.isNull(value)) {
            return null;
        }
        if (value instanceof BeanModel) {
            BeanModel model = (BeanModel) value;
            if (model.isEmpty()) {
                return null;
            } else {
                return model.getWrappedObject();
            }
        }
        return null;
    }

    public static Object[] getSequence(final Map params, final String name) throws TemplateModelException {
        if(EmptyUtil.isMapEmpty(params)){
            return null;
        }else{
            Object value = params.get(name);
            return getSequence(value);    
        }
    }

    public static Object[] getSequence(final Environment env, final String name) throws TemplateModelException {
        Object value = env.getVariable(name);
        return getSequence(value);
    }

    private static Object[] getSequence(final Object value) throws TemplateModelException {
        if (EmptyUtil.isNull(value)) {
            return null;
        }
        if (value instanceof TemplateSequenceModel) {
            TemplateSequenceModel model = (TemplateSequenceModel) value;
            int size = model.size();
            if (size == 0) {
                return null;
            }
            Object[] values = new Object[size];
            for (int i = 0; i < size; ++i) {
                Object object =getSequenceObject(model.get(i));
                if(EmptyUtil.isNotNull(object)){
                    values[i] = object;
                }
            }
            return values;
        }
        return null;
    }

    private static Object getSequenceObject(final Object object) throws TemplateModelException {
        Integer integer = getInteger(object);
        if (EmptyUtil.isNotNull(integer)) {
            return integer;
        }
        String string = getString(object);
        if (EmptyUtil.isNotNull(string)) {
            return string;
        }
        Boolean bool = getBoolean(object);
        if(EmptyUtil.isNotNull(bool)){
            return bool;
        }
        Date date = getDate(object);
        if(EmptyUtil.isNotNull(date)){
            return date;
        }
        Object obj = getBean(object);
        if(EmptyUtil.isNotNull(obj)){
            return obj;
        }
        return null;
    }

    public static boolean isDebug(final Environment env) throws TemplateModelException {
        Boolean debug = getBoolean(env, DirectiveVariable.Debug.toString());
        return debug == null ? false : true;
    }

    public static void setVariable(final Environment env, String key, Object value) throws TemplateModelException {
        env.setVariable(key, env.getObjectWrapper().wrap(value));
    }

    public static void removeVariable(final Environment env, String key) throws TemplateModelException {
        env.getCurrentNamespace().remove(key);
    }
}
