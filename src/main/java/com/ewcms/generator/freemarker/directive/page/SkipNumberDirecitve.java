/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.generator.freemarker.FreemarkerUtil;
import com.ewcms.generator.freemarker.GlobalVariable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 跳转页数标签
 * 
 * @author wangwei
 */
public class SkipNumberDirecitve extends SkipBaseDirective {
    private static final Logger logger = LoggerFactory.getLogger(SkipNumberDirecitve.class);
    
    private static final int DEFAULT_MAX_LENGTH = 5;
    private static final String DEFAULT_LABEL = "..";
    
    private static final String NUMBER_PARAM_NAME = "number";
    private static final String MAXLENGTH_PARAM_NAME = "max";
    private static final String LABEL_PARAM_NAME = "label";

    private String maxParam = MAXLENGTH_PARAM_NAME;
    private String numberParam = NUMBER_PARAM_NAME;
    private String labelParam = LABEL_PARAM_NAME;

    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException {
        
        int pageCount = getPageCountValue(env);
        int pageNumber = getPageNumberValue(env);
        
        int max = getMaxValue(params);
        if(max == DEFAULT_MAX_LENGTH){
            max = getNumberValue(params);
        }
        
        String label = getLabelValue(params);
        String url = getUrlValue(env);
        
        List<PageOut>  pages = getPageOuts(pageCount, pageNumber, max,url,label);

        if (EmptyUtil.isArrayNotEmpty(loopVars)) {
            loopVars[0] = env.getObjectWrapper().wrap(pages);
            if(EmptyUtil.isNull(body)){
                logger.warn("Body is null");
            }else{
                body.render(env.getOut());    
            }
        } else {
            Writer writer = env.getOut();
            for (PageOut page : pages) {
                FreemarkerUtil.setVariable(env, GlobalVariable.PAGE_OUT.toString(),page);
                body.render(writer);
                FreemarkerUtil.removeVariable(env,GlobalVariable.PAGE_OUT.toString());
            }
            writer.flush();
        }
    }

    @SuppressWarnings("rawtypes")
    private Integer getNumberValue(Map params) throws TemplateException {
        Integer page = FreemarkerUtil.getInteger(params, numberParam);
        return page == null ? DEFAULT_MAX_LENGTH : page;
    }

    @SuppressWarnings("rawtypes")
    private Integer getMaxValue(Map params) throws TemplateException {
        Integer page = FreemarkerUtil.getInteger(params, maxParam);
        return page == null ? DEFAULT_MAX_LENGTH : page;
    }
    
    @SuppressWarnings("rawtypes")
    private String getLabelValue(Map params) throws TemplateException{
        String value = FreemarkerUtil.getString(params, labelParam);
        return value == null ? DEFAULT_LABEL : value;
    }

    /**
     * 显示跳转页的集合
     * 
     * @param count
     *            总页数
     * @param number
     *            当前页数
     * @param max
     *            最大显页数
     * @param url
     *            链接地址
     * @return
     */
    List<PageOut> getPageOuts(int count, int number, int max, String url,String label) {

        int start = 0;
        int len = 0;
        if (count <= max) {
            start = 0;
            len = count;
        } else {
            start = number - (max - 1) / 2;
            start = (start < 0 ? 0 : start);
            len = max;
        }

        List<PageOut> pageOuts = new ArrayList<PageOut>();
        if (start > 0) {
            pageOuts.add(createMissPage(count,label));
        }
        for (int i = 0; i < len; i++) {
            pageOuts.add(new PageOut(count, start + i, url));
        }
        if ((start + len) < (count - 1)) {
            pageOuts.add(createMissPage(count,label));
        }

        return pageOuts;
    }

    private PageOut createMissPage(int count,String label) {
        return new PageOut(count, -1, label, null);
    }
}
