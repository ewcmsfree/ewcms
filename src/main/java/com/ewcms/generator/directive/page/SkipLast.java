/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.directive.page;

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
