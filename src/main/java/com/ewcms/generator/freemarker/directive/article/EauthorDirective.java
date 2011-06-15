/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.article;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ewcms.generator.freemarker.directive.ArticleDirective;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 编辑姓名标签
 * 
 * @deprecated
 * @author wangwei
 */
@Service("direcitve.article.eauthor")
public class EauthorDirective extends ArticleDirective {

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException {
        
        params.put("name", new SimpleScalar("auditReal"));
        super.execute(env, params, loopVars, body);
    }

}
