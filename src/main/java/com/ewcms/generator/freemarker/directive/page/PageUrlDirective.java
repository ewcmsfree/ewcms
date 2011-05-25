/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.freemarker.directive.page;

import org.springframework.stereotype.Service;
/**
 *
 * @author wangwei
 */
@Service("direcitve.page.url")
public class PageUrlDirective extends PageElementDirective{

    @Override
    protected String constructOutValue(Page object) {
        return object.getUrl();
    }
}
