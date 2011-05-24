/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive;

import com.ewcms.common.lang.EmptyUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.util.Map;
import java.io.Writer;

/**
 * 文章元素显示
 *
 * @author wangwei
 */
public abstract class ElementDirective<T> implements TemplateDirectiveModel {

    protected static final String PARAM_NAME_VALUE = "value";

    @Override
    public void execute(final Environment env,
            final Map params, final TemplateModel[] loopVars,
            final TemplateDirectiveBody body) throws TemplateException, IOException {

        try {
            T value = getVariableValue(env, params, PARAM_NAME_VALUE);
            Writer out = env.getOut();
            String outValue = constructOutValue(value);
            out.write(outValue.toString());
        } catch (DirectiveException e) {
            e.render(env.getOut());
        } catch(Exception e){
            DirectiveException ex = new DirectiveException(e);
            ex.render(env.getOut());
        }
    }

    /**
     * 标签显示类容
     * 
     * @param Object
     * @return
     */
    protected abstract String constructOutValue(T object);

    /**
     * 得到变量中的值
     * 
     * @param env        环境
     * @param params     参数
     * @param paramName  标签参数名
     * @return
     * @throws TemplateModelException
     */
    protected T getVariableValue(final Environment env, final Map params, final String name)
            throws TemplateModelException, DirectiveException {

        T bean = (T) DirectiveUtil.getBean(params, name);
        if (EmptyUtil.isNotNull(bean)) {
            return bean;
        }

        String variable = DirectiveUtil.getString(params, name);
        variable = (variable == null ? defaultVariable() : variable);
        if (EmptyUtil.isNotNull(variable)) {
            bean = (T) DirectiveUtil.getBean(env, variable);
            if (EmptyUtil.isNotNull(bean)) {
                return bean;
            }
        }

        String message = String.format("%s的值不存在", variable);
        throw new DirectiveException(message);
    }

    /**
     * 缺省变量名
     * 
     * @return
     */
    protected abstract String defaultVariable();
}
