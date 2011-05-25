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
@Service("direcitve.page.label")
public class PageLabelDirective extends PageElementDirective{

    @Override
    protected String constructOutValue(Page object) {
        return object.getLabel();
    }
}
