/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.article;

/**
 * 链接地址标签
 *
 * @deprecated
 * @author wangwei
 */
public class UrlDirective extends ArticlePropertyDirective{
   
    @Override
    protected String getPropertyName() {
        return "url";
    }
}
