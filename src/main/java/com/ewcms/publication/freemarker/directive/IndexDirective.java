/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.publication.freemarker.FreemarkerUtil;
import com.ewcms.publication.freemarker.GlobalVariable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 序号标签
 * <br>
 * 显示文章列表序号或频道列表序�? * 
 * @author wangwei
 */
public class IndexDirective implements TemplateDirectiveModel {

    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException {
        
        Integer index = getIndexValue(env);
        if(EmptyUtil.isNotNull(index)){
            Writer out = env.getOut();
            out.write(index.toString());
            out.flush();
        }
    }
    
    /**
     * 得到序号
     * 
     * @param env
     *          Freemarker环境
     * @return
     * @throws TemplateException
     */
    private Integer getIndexValue(Environment env)throws TemplateException{
        Integer index = FreemarkerUtil.getInteger(env, GlobalVariable.INDEX.toString());
        return index;
    }
}
