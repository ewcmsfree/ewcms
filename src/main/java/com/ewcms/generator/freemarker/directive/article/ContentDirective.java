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

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.content.document.model.Content;
import com.ewcms.generator.freemarker.directive.DirectiveException;
import com.ewcms.generator.freemarker.directive.DirectiveUtil;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.page.PageParam;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
/**
 *
 * @author wangwei
 */
@Service("direcitve.article.content")
public class ContentDirective extends ArticleElementDirective {

    @Override
    public void execute(final Environment env, final Map params, final TemplateModel[] loopVars, final TemplateDirectiveBody body) throws TemplateException, IOException {

        try {
            ArticleRmc articleRmc = this.getVariableValue(env, params, PARAM_NAME_VALUE);
            if (EmptyUtil.isNull(articleRmc)) {
                return;
            }
            List<Content> contents = articleRmc.getArticle().getContents();
            if (EmptyUtil.isCollectionEmpty(contents)) {
                return;
            }
            PageParam pageParam = getPageParamVariable(env);
            if (EmptyUtil.isNull(pageParam)) {
                env.getOut().write(contents.get(0).getDetail());
            } else {
                if (pageParam.getPage() < contents.size()) {
                    env.getOut().write(contents.get(pageParam.getPage()).getDetail());
                }
            }
        } catch (DirectiveException e) {
            e.render(env.getOut());
        } catch(Exception e){
            DirectiveException ex = new DirectiveException(e);
            ex.render(env.getOut());
        }
    }

    private PageParam getPageParamVariable(Environment env) throws TemplateModelException, DirectiveException {
        PageParam param = (PageParam) DirectiveUtil.getBean(env, DirectiveVariable.PageParam.toString());
        return param;
    }

    @Override
    protected String constructOutValue(ArticleRmc articleRmc) {
        //execute method by override
        return "";
    }
}
