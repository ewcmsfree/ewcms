/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.page;

import org.apache.commons.lang.StringUtils;

import com.ewcms.publication.freemarker.directive.page.SkipBaseDirective.GeneratorUrl;
import com.ewcms.publication.uri.UriRuleable;

import freemarker.template.TemplateException;

/**
 * "下一页"跳转
 * 
 * @author wangwei
 */
class SkipPageNext implements SkipPageable{

    private static final String DEFAULT_LABEL="下一页";

    @Override
    public PageOut skip(Integer count,Integer number,String label,UriRuleable rule)throws TemplateException{
       label = StringUtils.isBlank(label) ? DEFAULT_LABEL : label;
       ++number;
       boolean active = (number < count);
       int next = number>= count ? count -1 : number;
       GeneratorUrl generatorUrl = new GeneratorUrl(rule,number);
       String url = generatorUrl.getUriValue(next);
       
       return new PageOut(count,next,label,url,active);
    }
}
