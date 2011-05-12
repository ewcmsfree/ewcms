/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.directive.page;

import com.ewcms.generator.directive.ElementDirective;
import com.ewcms.generator.directive.DirectiveVariable;


/**
 *
 * @author wangwei
 */
public abstract class PageElementDirective extends ElementDirective<Page>{

    @Override
    protected String defaultVariable() {
        return DirectiveVariable.Page.toString();
    }

}
