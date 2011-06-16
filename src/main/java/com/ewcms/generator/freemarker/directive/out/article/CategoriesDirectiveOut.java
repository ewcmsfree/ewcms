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
import com.ewcms.content.document.model.ArticleCategory;
import com.ewcms.generator.freemarker.directive.out.HtmlDirectiveOut;

import freemarker.core.Environment;
import freemarker.template.TemplateModelException;

/**
 * 文章分类标签输出
 * 
 * @author wangwei
 */
public class CategoriesDirectiveOut extends HtmlDirectiveOut {
    private static final Logger logger = LoggerFactory.getLogger(CategoriesDirectiveOut.class);

    @SuppressWarnings("rawtypes")
    @Override
    public Object loopValue(Object value, Environment env, Map params)throws TemplateModelException {
        Assert.notNull(value);
        return value;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public String constructOut(Object value, Environment env, Map params)throws TemplateModelException {
        Assert.notNull(value);
      
        List<ArticleCategory> categories = (List<ArticleCategory>)value;
        if(categories.isEmpty()){
            logger.debug("Construct value is empty");
            return null;
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("<ul");
        String c = getClassValue(params);
        if(EmptyUtil.isNotNull(c)){builder.append(" class=\"").append(c).append("\"");}
        String s = getStyleValue(params);
        if(EmptyUtil.isNotNull(s)){builder.append(" style=\"").append(s).append("\"");}
        builder.append(">");
        for(ArticleCategory category : categories){
            builder.append("<li>").append(category.getCategoryName()).append("</li>");
        }
        builder.append("</ul>");
        return builder.toString();
    }

}
