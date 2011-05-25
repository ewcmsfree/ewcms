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
class SkipNext implements Skip{

    private static final String DEFAULT_LABEL="下一页";

    @Override
    public Page skip(PageParam pageParam) {
        int pageNumber = pageParam.getPage();
        int pageCount = pageParam.getCount();
        boolean enabled = isEnabled(pageNumber,pageCount);
        String urlPattern = pageParam.getUrlPattern();
        Page page = new Page();
        page.setEnabled(enabled);
        if(enabled){
            String url = String.format(urlPattern, (pageNumber + 1));
            page.setUrl(url);
        }else{
            String url = String.format(urlPattern, pageNumber);
            page.setUrl(url);
        }
        page.setLabel(DEFAULT_LABEL);
        return page;
    }

    private boolean isEnabled(int page,int count){
        return page == (count -1) ? false : true;
    }

}
