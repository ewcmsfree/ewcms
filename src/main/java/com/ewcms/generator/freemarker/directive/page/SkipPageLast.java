/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import org.apache.commons.lang.StringUtils;

/**
 * 跳转末页
 *
 * @author wangwei
 */
class SkipPageLast implements SkipPageable<PageOut> {

    private static final String DEFAULT_LABEL="未页";
    
    @Override
    public PageOut skip(Integer count,Integer number,String label,String url) {
        if(StringUtils.isBlank(label)){
            label = DEFAULT_LABEL;
        }
        int last = count -1;
        if(number == last){
            url = null;
        }
        return new PageOut(count,last,label,url);

    }
}
