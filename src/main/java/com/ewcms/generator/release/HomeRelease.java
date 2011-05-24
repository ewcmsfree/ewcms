/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.release;

import com.ewcms.generator.directive.page.PageParam;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.dao.GeneratorDAOable;
import com.ewcms.generator.release.html.HomeGeneratorHtml;
import com.ewcms.generator.release.html.GeneratorHtmlable;
import freemarker.template.Configuration;
import java.io.IOException;

import static com.ewcms.common.io.FileUtil.createDirs;

/**
 *
 * @author wangwei
 */
public class HomeRelease implements Releaseable {

    private static final GeneratorHtmlable DEFAULT_GENERATORHTML = new HomeGeneratorHtml();
    private static final String DEFAULT_ENCODER = "utf-8";
    private GeneratorHtmlable generatorHtml = DEFAULT_GENERATORHTML;
    private static final String File_NAME = "index.html";
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
        try {
            String dir = getDir(channel);
            createDirs(dir, true);
            String path = getFilePath(dir);
            Writer writer = createWriter(path);
            PageParam pageParam = constructPageParam();
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
            //TODO 写错误信息
            throw new ReleaseException(e);
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

    private PageParam constructPageParam() {
        int pageCount = 1;

        PageParam pageParam = new PageParam();
        pageParam.setCount(pageCount);
        pageParam.setPage(0);
        pageParam.setRow(0);
        pageParam.setUrlPattern("");

        return pageParam;
    }

    String getFilePath(final String dir) {
        return String.format("%s/%s", dir, File_NAME);
    }

    private Writer createWriter(String fileName) throws IOException {
        OutputStream stream = new FileOutputStream(fileName);
        return new OutputStreamWriter(stream, this.charsetName);
    }
}
