/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

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
@Service("direcitve.channel.title")
public class ChannelTitleDirective extends ChannelElementDirective{

    @Override
    protected String constructOutValue(Channel channel) {
        return channel.getName()== null ? "" : channel.getName() ;
    }

}
