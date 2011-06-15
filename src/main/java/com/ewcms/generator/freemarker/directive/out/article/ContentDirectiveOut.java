/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.out.article;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.content.document.model.Content;
import com.ewcms.generator.freemarker.FreemarkerUtil;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.freemarker.directive.DirectiveOutable;

import freemarker.core.Environment;
import freemarker.template.TemplateModelException;

/**
 * 文章内容输出标签
 * 
 * @author wangwei
 */
public class ContentDirectiveOut implements DirectiveOutable{
    private static final Logger logger = LoggerFactory.getLogger(ContentDirectiveOut.class);
    
    @SuppressWarnings("rawtypes")
    @Override
    public Object loopValue(Object value, Environment env, Map params)throws TemplateModelException {
        return getContent(value,env);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String constructOut(Object value, Environment env, Map params)throws TemplateModelException {
        return getContent(value,env);
    }
    
    @SuppressWarnings("unchecked")
    private String getContent(Object value,Environment env)throws TemplateModelException {
        if(value == null){
            logger.debug("Loop value is null");
            return null;
        }
        List<Content> contents = (List<Content>)value;
        if(EmptyUtil.isCollectionEmpty(contents)){
            logger.debug("Loop value is empty");
            return null;
        }
        int pageNumber = getPageNumber(env);
        if(pageNumber >= contents.size()){
            return null;
        }else{
            Content content = contents.get(pageNumber);
            return content.getDetail();
        }
    }
    
    private int getPageNumber(Environment env)throws TemplateModelException{
        Integer number = FreemarkerUtil.getInteger(env, GlobalVariable.PAGE_NUMBER.toString());
        return number == null ? 0 : number;
    }
}
