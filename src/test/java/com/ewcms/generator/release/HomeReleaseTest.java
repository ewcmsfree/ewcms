/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.release;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.generator.freemarker.directive.page.PageParam;
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
public class HomeReleaseTest {

    @Test
    public void testFilePath() {
        HomeRelease release = new HomeRelease();
        Channel channel = initChannel();
        String filePath = release.getFilePath(channel.getAbsUrl());
        Assert.assertEquals(filePath, "/test/index.html");
    }

    @Test
    public void testRelease() throws Exception {
      //TODO mockito instend jmock
//        Mockery context = new Mockery();
//
//        final Channel channel = initChannel();
//        final GeneratorHtmlable generator = context.mock(GeneratorHtmlable.class);
//        context.checking(new Expectations() {
//
//            {
//                oneOf(generator).process(with(aNull(Configuration.class)), with(any(Writer.class)), with(aNonNull(Channel.class)), with(aNull(PageParam.class)));
//            }
//        });
//        mkdir(channel.getAbsUrl());
//        HomeRelease release = new HomeRelease();
//        release.setGeneratorHtml(generator);
//        release.release(null, null, channel);
    }

    private Channel initChannel() {
        Channel channel = new Channel();

        channel.setId(1);
        channel.setAbsUrl("/test");
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
