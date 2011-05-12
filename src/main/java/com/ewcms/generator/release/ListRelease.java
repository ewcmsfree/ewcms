/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.release;

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.dao.GeneratorDAOable;
import com.ewcms.generator.directive.page.PageParam;
import com.ewcms.generator.release.html.ListGeneratorHtml;
import com.ewcms.generator.release.html.GeneratorHtmlable;
import freemarker.template.Configuration;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ewcms.util.FileUtil;

/**
 *
 * @author wangwei
 */
public class ListRelease implements Releaseable {

    private static final Log log = LogFactory.getLog(ListRelease.class);
    private static final GeneratorHtmlable DEFAULT_GENERATORHTML = new ListGeneratorHtml();
    private static final String DEFAULT_ENCODER = "utf-8";
    private GeneratorHtmlable generatorHtml = DEFAULT_GENERATORHTML;
    private String charsetName = DEFAULT_ENCODER;

    public void setGeneratorHtml(GeneratorHtmlable generatorHtml) {
        this.generatorHtml = generatorHtml;
    }

    @Override
    public void setEncoder(String charsetName) {
        this.charsetName = charsetName;
    }

    @Override
    public void release(final Configuration cfg, final GeneratorDAOable dao, final Channel channel) throws ReleaseException {
        release(cfg, dao, channel, false);
    }

    @Override
    public void releaseDebug(final Configuration cfg, final GeneratorDAOable dao, final Channel channel) throws ReleaseException {
        release(cfg, dao, channel, true);
    }

    private void release(final Configuration cfg, final GeneratorDAOable dao, final Channel channel, final boolean debug) throws ReleaseException {
        int row = channel.getListSize();
        Integer maxRow = channel.getMaxSize();
        maxRow = (maxRow == null ? 2000 : maxRow);
        int count = dao.getArticleCount(channel.getId());
        count = (count > maxRow ? maxRow : count);
        int pageCount = (count + row - 1) / row;
        pageCount = (pageCount == 0 ? 1 : pageCount);
        String dir = getDir(channel);
        mkdir(dir);
        String filePattern = getFilePattern(dir);
        PageParam pageParam = constructPageParam(
                pageCount, row, getUrlPattern(channel.getAbsUrl()));
        for (int i = 0; i < pageCount; ++i) {
            try {
                pageParam.setPage(i);
                String path = String.format(filePattern, i);
                Writer writer = createWriter(path);
                try {
                    if (debug) {
                        generatorHtml.processDebug(cfg, writer, channel, pageParam);
                    } else {
                        generatorHtml.process(cfg, writer, channel, pageParam);
                    }
                } catch (ReleaseException e) {
                    e.render(writer);
                }
                writer.flush();
                writer.close();
            } catch (IOException e) {
                //TODO错误
                throw new ReleaseException(e);
            } catch (Exception e) {
                String message = String.format("频道编号为%d文章列表为%d发布错误\n %s", channel.getId(), i, e.toString());
                log.error(message, e);
            }
        }
    }

    private String getDir(final Channel channel) {
        String rootDir = channel.getSite().getServerDir();
        if (rootDir.endsWith("/")) {
            return String.format("%s%s", rootDir, channel.getPubPath());
        } else {
            return String.format("%s/%s", rootDir, channel.getPubPath());
        }
    }

    private Writer createWriter(String fileName) throws IOException {
        OutputStream stream = new FileOutputStream(fileName);
        return new OutputStreamWriter(stream, this.charsetName);
    }

    String getUrlPattern(final String url) {
        return String.format("%s/%s", url, "%d.html");
    }

    String getFilePattern(final String dir) {
        return String.format("%s/%s", dir, "%d.html");
    }

    private PageParam constructPageParam(final int count, final int row, final String urlPattern) {
        PageParam pageParam = new PageParam();
        pageParam.setCount(count);
        pageParam.setUrlPattern(urlPattern);
        pageParam.setRow(row);

        return pageParam;
    }

    private void mkdir(final String dir) throws ReleaseException {
        try {
            FileUtil.createDirs(dir, true);
        } catch (IOException e) {
            //TODO错误信息
            throw new ReleaseException(e);
        }
    }
}
