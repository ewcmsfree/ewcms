/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.freemarker.directive.article;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.ElementDirective;

/**
 *
 * @author wangwei
 */

public abstract class ArticleElementDirective extends ElementDirective<ArticleRmc>{

    @Override
    protected String defaultVariable() {
        return DirectiveVariable.Article.toString();
    }

    protected Article getArticle(ArticleRmc articleRmc){
        return articleRmc.getArticle();
    }
}
