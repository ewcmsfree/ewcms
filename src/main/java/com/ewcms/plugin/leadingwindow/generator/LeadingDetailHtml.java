/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.leadingwindow.generator;

import com.ewcms.core.document.model.ArticleRmc;
import com.ewcms.core.document.model.ArticleRmcStatus;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.directive.DirectiveVariable;
import com.ewcms.generator.directive.page.PageParam;
import com.ewcms.generator.release.html.GeneratorHtml;
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.plugin.leadingwindow.model.LeaderChannel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wangwei
 */
public class LeadingDetailHtml extends GeneratorHtml<Channel> {

    private static final String MENU_PARAM_NAME = "menu";
    private static final String LEADER_PARAM_NAME = "leader";
    private static final String LEADERCHANNEL_PARAM_NAME = "leaderchannel";
    private static final String ARTICLES_PARAM_NAME = "articles";
    private LeadingMenu menu;
    private Leader leader;
    private LeaderChannel leaderChannel;

    public LeadingDetailHtml(LeadingMenu menu, Leader leader, LeaderChannel leaderChannel) {
        this.menu = menu;
        this.leader = leader;
        this.leaderChannel = leaderChannel;
    }

    @Override
    protected Template getTemplate(Configuration cfg, Channel channel) throws IOException {
        String templatePath = null;
        if (channel.getDetailTPL() != null) {
            templatePath = channel.getDetailTPL().getUniquePath();
        }
        if (templatePath == null) {
            return null;
        }
        return cfg.getTemplate(templatePath);
    }

    @Override
    protected Map constructParams(Channel channel, PageParam pageParam, boolean debug) {
        Map params = new HashMap();

        params.put(DirectiveVariable.CurrentSite.toString(), channel.getSite());
        params.put(DirectiveVariable.CurrentChannel.toString(), channel);
        params.put(DirectiveVariable.PageParam.toString(), pageParam);
        params.put(MENU_PARAM_NAME, menu);
        params.put(LEADER_PARAM_NAME, leader);
        params.put(LEADERCHANNEL_PARAM_NAME, leaderChannel);
        int startRow = pageParam.getPage() * pageParam.getRow();
        int endRow = startRow + pageParam.getRow();

        List<ArticleRmc> list = filterArticle(leaderChannel.getArticleRmcs());
        int count = list.size() - 1;
        endRow = endRow > count ? count : endRow;

        params.put(ARTICLES_PARAM_NAME, list.subList(startRow, endRow));
        if (debug) {
            params.put(DirectiveVariable.Debug.toString(), true);
        }

        return params;
    }

    private List<ArticleRmc> filterArticle(List<ArticleRmc> articleRmcs) {
        List<ArticleRmc> list = new ArrayList<ArticleRmc>();
        for (ArticleRmc arti : articleRmcs) {
            if (isEnableArticle(arti)) {
                list.add(arti);
            }
        }
        return list;

    }

   private boolean isEnableArticle(ArticleRmc arti){
        return arti.getStatus() == ArticleRmcStatus.RELEASE ? true : false;
    }
}
