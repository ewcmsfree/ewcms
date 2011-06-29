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
import org.springframework.util.Assert;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.content.document.model.Content;
import com.ewcms.generator.freemarker.FreemarkerUtil;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.freemarker.directive.DirectiveOutable;

import freemarker.core.Environment;
import freemarker.template.TemplateException;

/**
 * 文章内容输出标签
 * 
 * @author wangwei
 */
public class ContentDirectiveOut implements DirectiveOutable{
    private static final Logger logger = LoggerFactory.getLogger(ContentDirectiveOut.class);
    
    @SuppressWarnings("rawtypes")
    @Override
    public Object loopValue(Object value, Environment env, Map params)throws TemplateException {
        Assert.notNull(value);
        return getContent(value,env);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String constructOut(Object value, Environment env, Map params)throws TemplateException {
        Assert.notNull(value);
        return getContent(value,env);
    }
    
    @SuppressWarnings("unchecked")
    String getContent(Object value,Environment env)throws TemplateException {
        List<Content> contents = (List<Content>)value;
        if(EmptyUtil.isCollectionEmpty(contents)){
            logger.debug("Loop value is empty");
            return null;
        }
        int pageNumber = getPageNumber(env);
        if(pageNumber >= contents.size()){
            logger.warn("Page number {} but it max {},because it start 0.",pageNumber,contents.size()-1);
            return null;
        }else{
            Content content = contents.get(pageNumber);
            return content.getDetail();
        }
    }
    
    int getPageNumber(Environment env)throws TemplateException{
        Integer number = FreemarkerUtil.getInteger(env, GlobalVariable.PAGE_NUMBER.toString());
        return number == null ? 0 : number;
    }
}
