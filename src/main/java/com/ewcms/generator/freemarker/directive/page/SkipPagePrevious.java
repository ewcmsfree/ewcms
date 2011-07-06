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
 * "上一页"跳转
 * 
 * @author wangwei
 */
class SkipPagePrevious extends SkipPageBase{

    private static final String DEFAULT_LABEL="上一页";

    @Override
    public PageOut skip(Integer count,Integer number,String label,UriRuleable rule)throws TemplateException{
        if(StringUtils.isBlank(label)){
            label = DEFAULT_LABEL;
        }
        int prev = number -1;
        String url = null;
        if(prev < 0){
            prev = 0;
        }else{
            url = getUriValue(rule, prev);
        }
        return new PageOut(count,prev,label,url);
    }
}
