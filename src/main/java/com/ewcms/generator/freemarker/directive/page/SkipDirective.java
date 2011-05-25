/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.page;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.generator.freemarker.directive.DirectiveException;
import com.ewcms.generator.freemarker.directive.DirectiveUtil;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;

import freemarker.core.Environment;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author wangwei
 */
@Service("direcitve.skip")
public class SkipDirective implements TemplateDirectiveModel {

    private static final String PARAM_NAME_Type = "type";
    private static final String PARAM_NAME_LABEL = "label";

    enum SkipType {

        First(new SkipFirst()),
        Last(new SkipLast()),
        Previous(new SkipPrevious()),
        Next(new SkipNext());
        private Skip skip;

        private SkipType(Skip skip) {
            this.skip = skip;
        }

        Skip skip() {
            return skip;
        }
    };

    @Override
    public void execute(Environment env, Map params,
            TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        try {

            if (EmptyUtil.isNull(body)) {
                throw new DirectiveException("没有显示内容");
            }

            PageParam pageParam = getPageParamVariable(env);
            if (!isShow(pageParam)) {
                return;
            }
            String skipName = getSkipParam(params, PARAM_NAME_Type);
            Page page = generatorPages(pageParam, getSkipType(skipName));
            String label = getLabelParam(params, PARAM_NAME_LABEL);
            if (EmptyUtil.isNotNull(label)) {
                page.setLabel(label);
            }

            if (EmptyUtil.isArrayNotEmpty(loopVars)) {
                loopVars[0] = env.getObjectWrapper().wrap(page);
                body.render(env.getOut());
            } else {
                Writer writer = env.getOut();
                DirectiveUtil.setVariable(env, DirectiveVariable.Page.toString(), page);
                body.render(writer);
                DirectiveUtil.removeVariable(env, DirectiveVariable.Page.toString());
            }
        } catch (DirectiveException e) {
            if (DirectiveUtil.isDebug(env)) {
                e.render(env.getOut());
            }
        }
    }

    private PageParam getPageParamVariable(final Environment env) throws TemplateModelException, DirectiveException {
        PageParam param = (PageParam) DirectiveUtil.getBean(env, DirectiveVariable.PageParam.toString());
        Assert.notNull(param, "PageParam is null");
        return param;
    }

    private String getSkipParam(final Map params, String name) throws TemplateModelException, DirectiveException {
        String skipName = DirectiveUtil.getString(params, name);
        if (EmptyUtil.isNull(skipName)) {
            throw new DirectiveException("skip需要设置。");
        }
        return skipName;
    }

    private boolean isShow(final PageParam pageParam) {
        return pageParam.getCount() > 1 ? true : false;
    }

    SkipType getSkipType(String skp) throws DirectiveException {
        if (skp.equals("f") || skp.equals("first")) {
            return SkipType.First;
        }
        if (skp.equals("l") || skp.equals("last")) {
            return SkipType.Last;
        }
        if (skp.equals("n") || skp.equals("next")) {
            return SkipType.Next;
        }
        if (skp.equals("p") || skp.equals("prev")) {
            return SkipType.Previous;
        }

        throw new DirectiveException("skip参数只能设置f、l、p、n，或first、last、prev、next值");
    }

    private String getLabelParam(final Map params, final String name) throws TemplateException {
        return DirectiveUtil.getString(params, name);
    }

    /**
     * 缺省分页输出
     *
     * @param env
     * @param page
     * @throws TemplateException
     * @throws IOException
     */
    private Page generatorPages(PageParam pageParam, SkipType type) {
        Skip skip = type.skip();
        return skip.skip(pageParam);
    }
}
