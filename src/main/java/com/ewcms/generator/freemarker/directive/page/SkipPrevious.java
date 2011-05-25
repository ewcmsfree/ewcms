/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.freemarker.directive.page;

/**
 *
 * @author wangwei
 */
class SkipPrevious implements Skip{

    private static final String DEFAULT_LABEL="上一页";

    @Override
    public Page skip(PageParam pageParam) {
        int pageNumber = pageParam.getPage();
        boolean enabled = isEnabled(pageNumber);
        String urlParttern = pageParam.getUrlPattern();
        Page page = new Page();
        page.setEnabled(enabled);
        if(enabled){
            String url = String.format(urlParttern, (pageNumber - 1));
            page.setUrl(url);
        }else{
            String url = String.format(urlParttern, pageNumber);
            page.setUrl(url);
        }
        page.setLabel(DEFAULT_LABEL);
        return page;
    }

    private boolean isEnabled(int page){
        return page == 0 ? false : true;
    }
}
