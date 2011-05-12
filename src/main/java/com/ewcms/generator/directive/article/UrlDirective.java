/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.directive.article;

import com.ewcms.core.document.model.ArticleRmc;
import org.springframework.stereotype.Service;

/**
 *
 * @author wangwei
 */
@Service("direcitve.article.url")
public class UrlDirective extends ArticleElementDirective{

    @Override
    protected String constructOutValue(ArticleRmc articleRmc) {
        return articleRmc.getUrl()== null ? "" : articleRmc.getUrl() ;
    }
}
