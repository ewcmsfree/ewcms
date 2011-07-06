/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import org.apache.commons.lang.StringUtils;

import com.ewcms.generator.freemarker.directive.page.SkipBaseDirective.GeneratorUrl;
import com.ewcms.generator.uri.UriRuleable;

import freemarker.template.TemplateException;

/**
 * 跳转末页
 *
 * @author wangwei
 */
class SkipPageLast implements SkipPageable{

    private static final String DEFAULT_LABEL="未页";
    
    @Override
    public PageOut skip(Integer count,Integer number,String label,UriRuleable rule)throws TemplateException{
        if(StringUtils.isBlank(label)){
            label = DEFAULT_LABEL;
        }
        int last = count -1;
        String url = null;
        if(number != last){
            GeneratorUrl generatorUrl = new GeneratorUrl(rule);
            url = generatorUrl.getUriValue(last);
        }
        return new PageOut(count,last,label,url);

    }
}
