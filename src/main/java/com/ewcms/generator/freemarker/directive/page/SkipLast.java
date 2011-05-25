/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.freemarker.directive.page;

/**
 *
 * @author wangwei
 */
class SkipLast implements Skip {

    private static final String DEFAULT_LABEL="末页";
    
    @Override
    public Page skip(PageParam pageParam) {
        String url = String.format(pageParam.getUrlPattern(),(pageParam.getCount() - 1));
        Page page = new Page();
        page.setUrl(url);
        page.setEnabled(isEnabled(pageParam.getPage(),pageParam.getCount()));
        page.setLabel(DEFAULT_LABEL);

        return page;

    }

    private boolean isEnabled(int page,int count){
        return page == (count -1) ? false : true;
    }
}
