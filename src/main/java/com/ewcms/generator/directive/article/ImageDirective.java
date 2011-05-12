/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.directive.article;

import com.ewcms.core.document.model.Article;
import com.ewcms.core.document.model.ArticleRmc;
import org.springframework.stereotype.Service;
/**
 *
 * @author wangwei
 */
@Service("direcitve.article.image")
public class ImageDirective extends ArticleElementDirective {

    @Override
    protected String constructOutValue(ArticleRmc articleRmc) {
        Article article = this.getArticle(articleRmc);
        return article.getImage() == null ? "" : article.getImage();
    }
}
