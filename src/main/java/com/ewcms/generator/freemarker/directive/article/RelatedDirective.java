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
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Related;
import com.ewcms.generator.freemarker.directive.DirectiveException;
import com.ewcms.generator.freemarker.directive.DirectiveUtil;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 *
 * @author wangwei
 */
@Service("direcitve.article.related")
public class RelatedDirective extends ArticleElementDirective {

    private static final String PARAM_NAME_NAME = "name";

    @Override
    public void execute(final Environment env,
            final Map params, final TemplateModel[] loopVars,
            final TemplateDirectiveBody body) throws TemplateException, IOException {

        try {
            if (EmptyUtil.isNull(body)) {
                throw new DirectiveException("没有显示内容");
            }
            Article articleRmc = this.getVariableValue(env, params, PARAM_NAME_VALUE);
            if (EmptyUtil.isNull(articleRmc)
                    || EmptyUtil.isCollectionEmpty(articleRmc.getRelateds())) {
                return;
            }
            if (EmptyUtil.isArrayNotEmpty(loopVars)) {
                List<Article> relateds = new ArrayList<Article>();
                for(Related related : articleRmc.getRelateds()){
                    relateds.add(related.getArticle());
                }
                loopVars[0] = env.getObjectWrapper().wrap(relateds);
                body.render(env.getOut());
            } else {
                Writer writer = env.getOut();
                String variable = getNameParam(params, PARAM_NAME_NAME);
                for (Related relation :  articleRmc.getRelateds()) {
                    DirectiveUtil.setVariable(env, variable, relation.getArticle());
                    body.render(writer);
                    DirectiveUtil.removeVariable(env, variable);
                }
                writer.flush();
            }
       } catch (DirectiveException e) {
            e.render(env.getOut());
        } catch(Exception e){
            DirectiveException ex = new DirectiveException(e);
            ex.render(env.getOut());
        }
    }

    /**
     * 得到Name参数值
     *
     * @param params
     * @return
     * @throws TemplateModelException
     */
    private String getNameParam(final Map params, final String name) throws TemplateModelException {
        String value = DirectiveUtil.getString(params, name);
        return value == null ? DirectiveVariable.ArtilceRelation.toString() : value;
    }

    @Override
    protected String constructOutValue(Article article) {
        //
        return null;
    }
}
