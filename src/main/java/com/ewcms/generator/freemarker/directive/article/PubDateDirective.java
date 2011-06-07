/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.article;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.content.document.model.Article;
import com.ewcms.generator.freemarker.directive.DirectiveUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
/**
 *
 * @author wangwei
 */
@Service("direcitve.article.pubDate")
public class PubDateDirective extends ArticleElementDirective {

    private static final String PARAM_NAME_FORMAT = "format";
    private static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateFormat;
    
    @Override
    public void execute(final Environment env,
            final Map params, final TemplateModel[] loopVars,
            final TemplateDirectiveBody body) throws TemplateException, IOException {

        dateFormat = getDateFormat(params,PARAM_NAME_FORMAT);
        super.execute(env,params,loopVars,body);
    }

    private SimpleDateFormat getDateFormat(final Map params, final String name) throws TemplateModelException {
        String format = DirectiveUtil.getString(params, name);
        if (EmptyUtil.isNull(format)) {
            return DEFAULT_FORMAT;
        }else{
            return new SimpleDateFormat(format);
        }
    }

    @Override
    protected String constructOutValue(Article articleRmc) {
        return articleRmc.getPublished() == null ? "" : dateFormat.format(articleRmc.getPublished());
    }
}
