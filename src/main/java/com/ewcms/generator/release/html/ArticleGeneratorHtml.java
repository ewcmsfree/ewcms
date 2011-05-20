/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.release.html;

import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.directive.DirectiveVariable;
import com.ewcms.generator.directive.page.PageParam;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wangwei
 */
public class ArticleGeneratorHtml extends GeneratorHtml<ArticleRmc> {

    @Override
    protected Template getTemplate(final Configuration cfg, final ArticleRmc articleRmc) throws IOException {
        Channel channel = articleRmc.getChannel();
        String path = (channel.getDetailTPL() == null ? null : channel.getDetailTPL().getUniquePath());
        if(path == null){
            return null;
        }
        return cfg.getTemplate(path);
    }

    @Override
    protected Map constructParams(final ArticleRmc articleRmc, final PageParam pageParam, final boolean debug) {
        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), articleRmc.getChannel());
        params.put(DirectiveVariable.CurrentSite.toString(), articleRmc.getChannel().getSite());
        params.put(DirectiveVariable.Article.toString(), articleRmc);
        params.put(DirectiveVariable.PageParam.toString(), pageParam);
        if (debug) {
            params.put(DirectiveVariable.Debug.toString(), true);
        }
        return params;
    }
}
