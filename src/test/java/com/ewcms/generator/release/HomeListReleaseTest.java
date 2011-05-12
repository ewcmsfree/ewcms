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
public class HomeListReleaseTest {

    @Test
    public void testGetFilePath() {
        HomeListRelease release = new HomeListRelease();
        Channel channel = initChannel();
        String filePath = release.getFilePath(channel.getPubPath());
        Assert.assertEquals(filePath, "/test/index.html");
    }

    @Test
    public void testGetUrlPattern(){
        HomeListRelease release = new HomeListRelease();
        Channel channel = initChannel();
        String urlPattern = release.getUrlPattern(channel.getAbsUrl());
        Assert.assertEquals(urlPattern, "/news/test/%d.html");
    }

    @Test
    public void testRelease() throws Exception{
      //TODO mockito instend jmock
//         Mockery context = new Mockery();
//
//        final Channel channel = initChannel();
//        final GeneratorDAOable dao = context.mock(GeneratorDAOable.class);
//        final GeneratorHtmlable generator = context.mock(GeneratorHtmlable.class);
//
//        context.checking(new Expectations(){{
//            PageParam pageParam= new PageParam();
//            pageParam.setCount(2);
//            pageParam.setPage(0);
//            pageParam.setRow(20);
//            pageParam.setUrlPattern("/news/test/%d.html");
//            oneOf(generator).process(with(aNull(Configuration.class)), with(any(Writer.class)), with(aNonNull(Channel.class)), with(equal(pageParam)));
//            oneOf(dao).getArticleCount(channel.getId());will(returnValue(30));
//        }});
//
//        HomeListRelease release = new HomeListRelease();
//        release.setGeneratorHtml(generator);
//        release.release(null, dao, channel);
    }

    private Channel initChannel() {
        Channel channel = new Channel();

        channel.setId(1);
        channel.setListSize(20);
        channel.setPubPath("/test");
        channel.setAbsUrl("/news/test");
        Site site =new Site();
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
