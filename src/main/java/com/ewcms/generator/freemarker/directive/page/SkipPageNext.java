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
 * "下一页"跳转
 * 
 * @author wangwei
 */
class SkipPageNext extends SkipPageBase{

    private static final String DEFAULT_LABEL="下一页";

    @Override
    public PageOut skip(Integer count,Integer number,String label,UriRuleable rule)throws TemplateException{
       if(StringUtils.isBlank(label)){
           label = DEFAULT_LABEL;
       }
       int next = number + 1;
       String url = null;
       if(next < count){
           url = getUriValue(rule, next);
       }else{
           next = count -1;
       }
       
       return new PageOut(count,next,label,url);
    }
}
