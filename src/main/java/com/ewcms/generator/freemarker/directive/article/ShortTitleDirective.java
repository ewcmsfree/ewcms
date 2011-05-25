/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.article;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleRmc;

import org.springframework.stereotype.Service;

/**
 *
 * @author wangwei
 */
@Service("direcitve.article.shortTitle")
public class ShortTitleDirective extends TitleDirective {

    @Override
    protected String constructOutValue(ArticleRmc articleRmc) {
        Article article = this.getArticle(articleRmc);
        return constructOutValue(article.getShortTitle(), article.getShortTitleStyle());
    }
}
