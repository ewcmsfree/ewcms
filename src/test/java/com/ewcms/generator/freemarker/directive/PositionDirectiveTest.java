/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.freemarker.directive;

import com.ewcms.content.document.model.Article;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.PositionDirective;
import com.ewcms.generator.freemarker.directive.channel.ChannelTitleDirective;
import com.ewcms.generator.freemarker.directive.channel.ChannelUrlDirective;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wangwei
 */
public class PositionDirectiveTest  extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(PositionDirectiveTest.class);
    
    @Override
    protected void setDirective(Configuration cfg) {
        cfg.setSharedVariable("position", new PositionDirective());
        cfg.setSharedVariable("channel_title", new ChannelTitleDirective());
        cfg.setSharedVariable("channel_url", new ChannelUrlDirective());
    }


     @Test
    public void testExecute()throws Exception{
         Template template = cfg.getTemplate("www/position/position_channel.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getChannel());
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("http://www.jict.org/channel") == -1);
        Assert.assertTrue(value.indexOf("channel") != -1);
        Assert.assertTrue(value.indexOf("http://www.jict.org/grand") != -1);
        Assert.assertTrue(value.indexOf("grand") != -1);
        Assert.assertTrue(value.indexOf("&gt;") != -1);
    }

     @Test
    public void testExecuteLoop()throws Exception{
         Template template = cfg.getTemplate("www/position/position_loop.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getChannel());
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("http://www.jict.org/channel") == -1);
        Assert.assertTrue(value.indexOf("channel") != -1);
        Assert.assertTrue(value.indexOf("http://www.jict.org/grand") != -1);
        Assert.assertTrue(value.indexOf("grand") != -1);
        Assert.assertTrue(value.indexOf("&gt;") != -1);
    }

    private Channel getChannel(){
        Channel channel = new Channel();

        channel.setName("channel");
        channel.setUrl("http://www.jict.org/channel");

        Channel parent = new Channel();
        parent.setName("parnet");
        parent.setUrl("http:/www.jict.org/parent");
        channel.setParent(parent);

        Channel grand = new Channel();
        grand.setName("grand");
        grand.setUrl("http://www.jict.org/grand");
        parent.setParent(grand);

        return channel;
    }
}
