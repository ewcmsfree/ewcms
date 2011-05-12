/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.directive.page;

/**
 *
 * @author wangwei
 */
class SkipFirst implements Skip{

    private static final String DEFAULT_LABEL="首页";

    @Override
    public Page skip(PageParam pageParam) {
        int pageNumber = pageParam.getPage();
        String url = String.format(pageParam.getUrlPattern(),0);
        Page page = new Page();
        page.setUrl(url);
        page.setEnabled(isEnabled(pageNumber));
        page.setLabel(DEFAULT_LABEL);

        return page;
    }

    private boolean isEnabled(int page){
        return page == 0 ? false: true;
    }
}
