/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.generator.freemarker.FreemarkerUtil;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.uri.UriRuleable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 页面跳转标签
 *
 * @author wangwei
 */
public class SkipDirective extends SkipBaseDirective{
    private static final Logger logger = LoggerFactory.getLogger(SkipDirective.class);
    
    private static final Map<String,SkipPageable> mapSkips = initTypeMapSkips();
    private static final String TYPE_PARAM_NAME = "type";
    private static final String LABEL_PARAM_NAME = "label";
    
    private String typeParam = TYPE_PARAM_NAME;
    private String labelParam = LABEL_PARAM_NAME;
    
    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params,
            TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        Integer pageNumber = getPageNumberValue(env);
        logger.debug("Page number is {}",pageNumber);
        Integer pageCount = getPageCountValue(env);
        logger.debug("Page count is {}",pageCount);
        String label = getLabelValue(params);
        logger.debug("Label is {}",label);

        if (pageCount == 1) {
            return;
        }
        String type = getTypeValue(params);
        SkipPageable skip = getSkipPage(type);
        UriRuleable rule = getUriRule(env);
        PageOut pageOut = skip.skip(pageCount, pageNumber, label, rule);
        
        if (EmptyUtil.isArrayNotEmpty(loopVars)) {
            loopVars[0] = env.getObjectWrapper().wrap(pageOut);
            if(EmptyUtil.isNull(body)){
                logger.warn("Body is null");
            }else{
                body.render(env.getOut());
                env.getOut().flush();    
            }
        } else {
            Writer writer = env.getOut();
            FreemarkerUtil.setVariable(env, GlobalVariable.PAGE_OUT.toString(),pageOut);
            body.render(writer);
            FreemarkerUtil.removeVariable(env, GlobalVariable.PAGE_OUT.toString());
        }
    }
    
    @SuppressWarnings("rawtypes")
    private String getTypeValue(Map params) throws TemplateException {
        String value = FreemarkerUtil.getString(params, typeParam);
        if (EmptyUtil.isNull(value)) {
            throw new TemplateModelException("Type must setting.");
        }
        return value;
    }

    @SuppressWarnings("rawtypes")
    private String getLabelValue(Map params) throws TemplateException {
        return FreemarkerUtil.getString(params, labelParam);
    }
    
    SkipPageable getSkipPage(String type)throws TemplateException{
        SkipPageable skipPage = mapSkips.get(type);
        if(EmptyUtil.isNull(skipPage)){
            logger.error("Skip page has not {} of types",type);
            throw new TemplateModelException("Skip page has not " + type + "of types.");
        }
        return skipPage;
    }
    
    static Map<String,SkipPageable> initTypeMapSkips(){
        Map<String,SkipPageable> map = new HashMap<String,SkipPageable>();
        
        map.put("f",new SkipPageFirst());
        map.put("first", new SkipPageFirst());
        map.put("首", new SkipPageFirst());
        map.put("首页", new SkipPageFirst());
        
        map.put("p", new SkipPagePrevious());
        map.put("prev", new SkipPagePrevious());
        map.put("上", new SkipPagePrevious());
        map.put("上一页", new SkipPagePrevious());
        
        map.put("n", new SkipPageNext());
        map.put("next", new SkipPageNext());
        map.put("下", new SkipPageNext());
        map.put("下一页", new SkipPageNext());
        
        map.put("l", new SkipPageLast());
        map.put("last", new SkipPageLast());
        map.put("末", new SkipPageLast());
        map.put("末页", new SkipPageLast());
        
        return map;
    }
}
