/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.freemarker.directive.article;

import org.springframework.stereotype.Service;

import com.ewcms.content.document.model.Article;

/**
 *
 * @author wangwei
 */
@Service("direcitve.article.url")
public class UrlDirective extends ArticleElementDirective{

    @Override
    protected String constructOutValue(Article articleRmc) {
        return articleRmc.getUrl()== null ? "" : articleRmc.getUrl() ;
    }
}
