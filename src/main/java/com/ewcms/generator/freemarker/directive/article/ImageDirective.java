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

import com.ewcms.content.document.model.Article;

import org.springframework.stereotype.Service;
/**
 *
 * @author wangwei
 */
@Service("direcitve.article.image")
public class ImageDirective extends ArticleElementDirective {

    @Override
    protected String constructOutValue(Article articleRmc) {
        Article article = this.getArticle(articleRmc);
        return article.getImage() == null ? "" : article.getImage();
    }
}
