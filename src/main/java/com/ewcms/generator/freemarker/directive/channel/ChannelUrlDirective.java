/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.channel;

import com.ewcms.core.site.model.Channel;
import org.springframework.stereotype.Service;

/**
 *
 * @author wangwei
 */
@Service("direcitve.channel.url")
public class ChannelUrlDirective extends ChannelElementDirective {

    @Override
    protected String constructOutValue(Channel channel) {
        return channel.getAbsUrl() == null ? "" : channel.getAbsUrl();
    }
}
