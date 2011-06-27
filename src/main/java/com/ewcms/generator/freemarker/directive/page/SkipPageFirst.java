/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import org.apache.commons.lang.StringUtils;

/**
 * 跳转首页
 *
 * @author wangwei
 */
class SkipPageFirst implements SkipPageable<PageOut>{

    private static final String DEFAULT_LABEL="首页";

    @Override
    public PageOut skip(Integer count,Integer number,String label,String url) {
        
        if(StringUtils.isBlank(label)){
            label = DEFAULT_LABEL;
        }
        int first = 0;
        if(number == first){
            url = null;
        }
        return new PageOut(count,first,label,url);
    }
}
