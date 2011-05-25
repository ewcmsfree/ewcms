/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.release.html;

import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.page.PageParam;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wangwei
 */
public class HomeGeneratorHtml extends GeneratorHtml<Channel> {

    @Override
    protected Template getTemplate(final Configuration cfg, final Channel channel) throws IOException {
        String path = (channel.getHomeTPL() == null ? null : channel.getHomeTPL().getUniquePath());
        if (path == null) {
            return null;
        }
        return cfg.getTemplate(path);
    }

    @Override
    protected Map constructParams(final Channel channel, final PageParam pageParam, final boolean debug) {
        Map params = new HashMap();

        params.put(DirectiveVariable.CurrentChannel.toString(), channel);
        params.put(DirectiveVariable.CurrentSite.toString(), channel.getSite());
        if (debug) {
            params.put(DirectiveVariable.Debug.toString(), true);
        }

        return params;
    }
}
