package com.ewcms.generator.freemarker.directive.page;

import java.util.Map;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.generator.freemarker.FreemarkerUtil;
import com.ewcms.generator.freemarker.GlobalVariable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

public abstract class SkipBaseDirective implements TemplateDirectiveModel {

    private static final Integer DEFAULT_PAGE_COUNT = 1;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;

    protected Integer getPageNumberValue(Environment env)throws TemplateModelException {
        Integer value = FreemarkerUtil.getInteger(env,GlobalVariable.PAGE_NUMBER.toString());
        return EmptyUtil.isNull(value) ? DEFAULT_PAGE_NUMBER : value;
    }

    protected Integer getPageCountValue(Environment env)throws TemplateModelException {
        Integer value = FreemarkerUtil.getInteger(env, GlobalVariable.PAGE_COUNT.toString());
        return EmptyUtil.isNull(value) ? DEFAULT_PAGE_COUNT : value;
    }

    @SuppressWarnings("rawtypes")
    protected String getUrlValue(Map params) throws TemplateException {
        return "";
    }
}
