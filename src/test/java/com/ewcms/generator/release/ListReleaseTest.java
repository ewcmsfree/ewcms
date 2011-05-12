/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.release;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.generator.dao.GeneratorDAOable;
import com.ewcms.generator.directive.page.PageParam;
import com.ewcms.generator.release.html.GeneratorHtmlable;
import freemarker.template.Configuration;
import java.io.File;
import java.io.Writer;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wangwei
 */
public class ListReleaseTest {

    @Test
    public void testGetUrlPattern() {
        ListRelease list = new ListRelease();
        Channel channel = initChannel();
        String urlPattern = list.getUrlPattern(channel.getAbsUrl());
        Assert.assertEquals(urlPattern, "http://www.jict.org/test/%d.html");
    }

    @Test
    public void testGetFilePattern() {
        ListRelease list = new ListRelease();
        Channel channel = initChannel();
        String filePattern = list.getFilePattern(channel.getPubPath());
        Assert.assertEquals(filePattern, "test/%d.html");
    }

    @Test
    public void testRelease() throws Exception {
      //TODO mockito instend jmock
//        Mockery context = new Mockery();
//
//        final Channel channel = initChannel();
//        final GeneratorDAOable dao = context.mock(GeneratorDAOable.class);
//        final GeneratorHtmlable generator = context.mock(GeneratorHtmlable.class);
//
//        context.checking(new Expectations() {
//
//            {
//                exactly(2).of(generator).process(with(aNull(Configuration.class)), with(any(Writer.class)), with(aNonNull(Channel.class)), with(any(PageParam.class)));
//                oneOf(dao).getArticleCount(channel.getId());
//                will(returnValue(30));
//            }
//        });
//
//        mkdir(channel.getAbsUrl());
//        ListRelease release = new ListRelease();
//        release.setGeneratorHtml(generator);
//        release.release(null, dao, channel);
    }

    private Channel initChannel() {
        Channel channel = new Channel();

        channel.setId(1);
        channel.setListSize(20);
        channel.setPubPath("test");
        channel.setAbsUrl("http://www.jict.org/test");
        Site site = new Site();
        site.setServerDir("/tmp");
        channel.setSite(site);

        return channel;
    }

    private void mkdir(final String dir) {
        File file = new File(dir);
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }
}
