/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.release;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.content.document.model.ArticleStatus;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.dao.GeneratorDAOable;
import com.ewcms.generator.freemarker.directive.page.PageParam;
import com.ewcms.generator.release.html.GeneratorHtmlable;
import com.ewcms.generator.release.html.ArticleGeneratorHtml;
import com.ewcms.web.util.GlobaPath;

import freemarker.template.Configuration;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.ewcms.common.io.FileUtil.createDirs;

/**
 *
 * @author wangwei
 */
public class ArticleRelease implements Releaseable {

    private static final Log log = LogFactory.getLog(ArticleRelease.class);
    private static final GeneratorHtmlable<ArticleRmc> DEFAULT_GENERATORHTML = new ArticleGeneratorHtml();
    private static final String DEFAULT_ENCODER = "utf-8";
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private GeneratorHtmlable<ArticleRmc> generatorHtml = DEFAULT_GENERATORHTML;
    private String charsetName = DEFAULT_ENCODER;

    public void setGeneratorHtml(GeneratorHtmlable<ArticleRmc> generatorHtml) {
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

        List<ArticleRmc> articles = dao.findPreReleaseArticle(channel.getId());
        String root = getPubRootPath(channel);
        for (ArticleRmc articleRmc : articles) {
            try {
                if (articleRmc.getArticle().getType() == ArticleStatus.TITLE) {
                    if (!debug) {
                        dao.articlePublish(articleRmc.getId(), articleRmc.getArticle().getLinkAddr());
                    }
                    continue;
                }
                if (!articleRmc.getArticle().getContents().isEmpty()) {
                    releaseArticle(cfg, dao, articleRmc, root, debug);
                }
            } catch (Exception e) {
                String message = String.format("编号为 %d 文章发布错误\n %s", articleRmc.getId(), e.toString());
                log.error(message, e);
            }
        }
    }

    private String getPubRootPath(final Channel channel) {
        return channel.getSite().getServerDir();
    }

    String mkdir(final String root, final Date date) throws ReleaseException {
        String day = format.format(date);
        String dir = String.format("%s/%s/%s", root, GlobaPath.DocumentDir.getPath(), day);
        dir = (dir.startsWith("/") ? dir : "/" + dir);
        try {
            createDirs(dir, true);
        } catch (IOException e) {
            //TODO 错误
            throw new ReleaseException();
        }

        return dir;
    }

    void releaseArticle(final Configuration cfg, final GeneratorDAOable dao,
            final ArticleRmc articleRmc, final String root, final boolean debug) throws ReleaseException {

        Date published = articleRmc.getPublished();
        published = (published == null ? new Date() : published);
        String dir = mkdir(root, published);
        String urlPattern = getUrlPattern(articleRmc.getId(), published);
        String filePattern = getFilePattern(dir, articleRmc.getId());

        Article article = articleRmc.getArticle();
        int pageCount = article.getContents().size();
        PageParam pageParam = constructPageParam(pageCount, urlPattern);
        for (int page = 0; page < pageCount; ++page) {
            try {
                pageParam.setPage(page);
                String path = String.format(filePattern, page);
                Writer writer = createWriter(path);
                try {
                    if (debug) {
                        generatorHtml.processDebug(cfg, writer, articleRmc, pageParam);
                    } else {
                        generatorHtml.process(cfg, writer, articleRmc, pageParam);
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
        if (!debug) {
            persistRelease(dao, articleRmc.getId(), urlPattern);
        }
    }

    String getUrlPattern(final int id, final Date date) {
        String day = format.format(date);
        String urlPattern = String.format("%s/%s/%d%s", GlobaPath.DocumentDir.getPath(), day, id, "_%d.html");
        return urlPattern;
    }

    String getFilePattern(final String dir, final int id) {
        return String.format("%s/%d%s", dir, id, "_%d.html");
    }

    private PageParam constructPageParam(final int count, final String urlPattern) {
        PageParam pageParam = new PageParam();
        pageParam.setCount(count);
        pageParam.setUrlPattern(urlPattern);
        pageParam.setRow(-1);

        return pageParam;
    }

    private void persistRelease(final GeneratorDAOable dao, final int id, final String urlPattern) {
        String url = String.format(urlPattern, 0);
        dao.articlePublish(id, url);
    }

    private Writer createWriter(String fileName) throws IOException {
        OutputStream stream = new FileOutputStream(fileName);
        return new OutputStreamWriter(stream, this.charsetName);
    }
}
