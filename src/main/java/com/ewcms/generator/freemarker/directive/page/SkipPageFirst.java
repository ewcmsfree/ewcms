/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import org.apache.commons.lang.StringUtils;

import com.ewcms.generator.uri.UriRuleable;

import freemarker.template.TemplateException;

/**
 * 跳转首页
 *
 * @author wangwei
 */
class SkipPageFirst extends SkipPageBase{

    private static final String DEFAULT_LABEL="首页";
    private static final int FIRST_NUMBER = 0;

    @Override
    public PageOut skip(Integer count,Integer number,String label,UriRuleable rule)throws TemplateException{
        
        if(StringUtils.isBlank(label)){
            label = DEFAULT_LABEL;
        }
        String url = null;
        if(number != FIRST_NUMBER){
            url = getUriValue(rule, FIRST_NUMBER);
        }
        return new PageOut(count,FIRST_NUMBER,label,url);
    }
}
