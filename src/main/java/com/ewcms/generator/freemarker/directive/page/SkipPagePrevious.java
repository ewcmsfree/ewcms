/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import org.apache.commons.lang.StringUtils;

/**
 * "上一页"跳转
 * 
 * @author wangwei
 */
class SkipPagePrevious implements SkipPageable<PageOut>{

    private static final String DEFAULT_LABEL="上一页";

    @Override
    public PageOut skip(Integer count,Integer number,String label,String url) {
        if(StringUtils.isBlank(label)){
            label = DEFAULT_LABEL;
        }
        int prev = number -1;
        if(prev < 0){
            url = null;
            prev = 0;
        }
        return new PageOut(count,prev,label,url);
    }
}
