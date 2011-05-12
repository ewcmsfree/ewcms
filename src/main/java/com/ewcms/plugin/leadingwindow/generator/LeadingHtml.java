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
public class LeadingHtml extends GeneratorHtml<Channel> {

    private static final String MENU_PARAM_NAME = "menu";
    private static final String LEADER_PARAM_NAME = "leader";
    
    private LeadingMenu menu;
    private Leader leader;

    public LeadingHtml(LeadingMenu menu, Leader leader) {
        this.menu = menu;
        this.leader = leader;
    }

    @Override
    protected Template getTemplate(final Configuration cfg, final Channel channel) throws IOException {
        String templatePath = null;
        if (channel.getHomeTPL() != null) {
            templatePath = channel.getHomeTPL().getUniquePath();
        }
        if (channel.getListTPL() != null) {
            templatePath = channel.getListTPL().getUniquePath();
        }
        if (templatePath == null) {
            return null;
        }
        return cfg.getTemplate(templatePath);
    }

    @Override
    protected Map constructParams(final Channel channel, final PageParam pageParam, final boolean debug) {
        Map params = new HashMap();

        params.put(DirectiveVariable.CurrentChannel.toString(), channel);
        params.put(DirectiveVariable.CurrentSite.toString(), channel.getSite());
        params.put(MENU_PARAM_NAME, menu);
        filterArticle(leader);
        params.put(LEADER_PARAM_NAME, leader);

        if (debug) {
            params.put(DirectiveVariable.Debug.toString(), true);
        }

        return params;
    }

    private void filterArticle(Leader leader){
        for(LeaderChannel channel :leader.getLeaderChannels()){
            List<ArticleRmc> list = new ArrayList<ArticleRmc>();
            for(ArticleRmc arti : channel.getArticleRmcs()){
                if(isEnableArticle(arti)){
                    list.add(arti);
                }
            }
            channel.setArticleRmcs(list);
        }
    }

    private boolean isEnableArticle(ArticleRmc arti){
        return arti.getStatus() == ArticleRmcStatus.RELEASE ? true : false;
    }
}
