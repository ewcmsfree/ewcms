/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.page;

import com.ewcms.generator.freemarker.directive.DirectiveUtil;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author wangwei
 */
@Service("direcitve.page")
public class PageDirective implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        PageParam pageParam = getPageParamVariable(env);
        Writer out = env.getOut();
        out.write(String.valueOf(pageParam.getPage()+1));
        out.flush();
    }

    private PageParam getPageParamVariable(Environment env) throws TemplateModelException {
        PageParam param = (PageParam) DirectiveUtil.getBean(env, DirectiveVariable.PageParam.toString());
        Assert.notNull(param, "PageParam is null");
        return param;
    }
}
