/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.generator.dao.GeneratorDAOable;
import com.ewcms.generator.freemarker.directive.ChannelDirective;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.channel.ChannelTitleDirective;
import com.ewcms.generator.freemarker.directive.channel.ChannelUrlDirective;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wangwei
 */
public class ChannelDirectiveTest extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(ChannelDirectiveTest.class);

    @Test
    public void testFilterChannel(){
        ChannelDirective channelDirective = new ChannelDirective();
        List<Channel> channels = getChildren(1,5);
        channels.get(1).setPublicenable(false);
        channels.get(3).setPublicenable(false);

        int size = channelDirective.filterChannel(channels, true).size();
        Assert.assertEquals(size, 5);
        size = channelDirective.filterChannel(channels, false).size();
        Assert.assertEquals(size, 3);
    }
    @Override
    protected void setDirective(Configuration cfg) {
        cfg.setSharedVariable("channel_title", new ChannelTitleDirective());
        cfg.setSharedVariable("channel_url", new ChannelUrlDirective());
    }

    private void setChannelDirective(final int id) {
      //TODO mockito instend jmock
//        Mockery context = new Mockery();
//        ChannelDirective channelDirective = new ChannelDirective();
//        final GeneratorDAOable dao = context.mock(GeneratorDAOable.class);
//        channelDirective.setGeneratorDAO(dao);
//        context.checking(new Expectations() {
//
//            {
//                oneOf(dao).getChannel(with(aNonNull(Integer.class)));
//                will(returnValue(getChannel(id)));
//            }
//        });
//        cfg.setSharedVariable("channel", channelDirective);
    }

    @Test
    public void testExecute() throws Exception {
        setChannelDirective(1);
        Template template = cfg.getTemplate("www/channel/channel.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getCurrentChannel());
        params.put(DirectiveVariable.CurrentSite.toString(), getCurrentChannel().getSite());
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("频道1") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/1") != -1);
    }

    @Test
    public void testExecuteChild() throws Exception {
        setChannelDirective(1);
        Template template = cfg.getTemplate("www/channel/channel_children.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getCurrentChannel());
        params.put(DirectiveVariable.CurrentSite.toString(), getCurrentChannel().getSite());
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("频道_1_0") != -1);
        Assert.assertTrue(value.indexOf("频道_1_4") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/1/0") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/1/4") != -1);
    }

    @Test
    public void testExecuteDefault() throws Exception {
        setChannelDirective(1);
        Template template = cfg.getTemplate("www/channel/channel_default.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getCurrentChannel());
        params.put(DirectiveVariable.CurrentSite.toString(), getCurrentChannel().getSite());
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("当前频道") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/this") != -1);
    }

    private void setChannelDirectiveList() {
      //TODO mockito instend jmock
//          Mockery context = new Mockery();
//        ChannelDirective channelDirective = new ChannelDirective();
//        final GeneratorDAOable dao = context.mock(GeneratorDAOable.class);
//        channelDirective.setGeneratorDAO(dao);
//        context.checking(new Expectations() {{
//                exactly(3).of(dao).getChannel(with(aNonNull(Integer.class)));
//                will(onConsecutiveCalls(
//                        returnValue(getChannel(1)),
//                        returnValue(getChannel(2)),
//                        returnValue(getChannel(3))));
//                oneOf(dao).getChannelByUrlOrDir(with(aNonNull(Integer.class)), with(aNonNull(String.class)));
//                will(returnValue(getChannel(10)));
//        }});
//        cfg.setSharedVariable("channel", channelDirective);
    }
     @Test
    public void testExecuteList() throws Exception {
        setChannelDirectiveList();
        Template template = cfg.getTemplate("www/channel/channel_list.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getCurrentChannel());
        params.put(DirectiveVariable.CurrentSite.toString(), getCurrentChannel().getSite());
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("频道1") != -1);
        Assert.assertTrue(value.indexOf("频道3") != -1);
        Assert.assertTrue(value.indexOf("频道10") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/1") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/3") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/10") != -1);
    }

     @Test
    public void testExecuteListRow() throws Exception {
        setChannelDirectiveList();
        Template template = cfg.getTemplate("www/channel/channel_list_row.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getCurrentChannel());
        params.put(DirectiveVariable.CurrentSite.toString(), getCurrentChannel().getSite());
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("频道1") != -1);
        Assert.assertTrue(value.indexOf("频道3") != -1);
        Assert.assertTrue(value.indexOf("频道10") == -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/1") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/3") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/10") == -1);
    }

    @Test
    public void testExecuteLoop() throws Exception {
        setChannelDirectiveList();
        Template template = cfg.getTemplate("www/channel/channel_loop.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getCurrentChannel());
        params.put(DirectiveVariable.CurrentSite.toString(), getCurrentChannel().getSite());
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("频道1") != -1);
        Assert.assertTrue(value.indexOf("频道3") != -1);
        Assert.assertTrue(value.indexOf("频道10") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/1") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/3") != -1);
        Assert.assertTrue(value.indexOf("http://www.sina.com/10") != -1);
    }


    private Channel getCurrentChannel() {
        Channel channel = new Channel();
        channel.setId(10);
        channel.setName("当前频道");
        channel.setUrl("http://www.sina.com/this");
        Site site = new Site();
        site.setId(1);
        channel.setSite(site);
        channel.setPublicenable(true);

        return channel;
    }

    private Channel getChannel(int id) {
        Channel channel = new Channel();

        channel.setId(id);
        channel.setName("频道" + String.valueOf(id));
        channel.setDir(channel.getName());
        channel.setUrl("http://www.sina.com/" + String.valueOf(id));
        channel.setPublicenable(true);
        channel.setListSize(20);
        Site site = new Site();
        site.setId(1);
        channel.setSite(site);
        //TODO already remove children field 
        //channel.setChildren(getChildren(id, 5));

        return channel;
    }

    private List<Channel> getChildren(int parentId, int count) {
        List<Channel> children = new ArrayList<Channel>();

        for (int i = 0; i < count; ++i) {
            Channel channel = new Channel();
            channel.setId(parentId * 10 + i);
            channel.setName(String.format("%s_%d_%d", "频道", parentId, i));
            channel.setUrl(String.format("%s/%d/%d", "http://www.sina.com", parentId, i));
            channel.setPublicenable(true);
            channel.setListSize(20);
            Site site = new Site();
            site.setId(1);
            channel.setSite(site);
            children.add(channel);
        }
        return children;
    }
}
