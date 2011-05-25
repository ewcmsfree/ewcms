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

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.generator.dao.GeneratorDAOable;
import com.ewcms.generator.freemarker.directive.page.PageParam;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author wangwei
 */
@Service("direcitve.articleList")
public class ArticleListDirective implements TemplateDirectiveModel {
    private static final int DEFAULT_ROWS = 20;
    private static final String PARAM_NAME_VALUE = "value";
    private static final String PARAM_NAME_ROW = "row";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_TOP = "top";
    @Autowired
    protected GeneratorDAOable dao;

    public void setGeneratorDAO(GeneratorDAOable dao) {
        this.dao = dao;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        boolean debug = DirectiveUtil.isDebug(env);
        try {
            if (EmptyUtil.isNull(body)) {
                throw new DirectiveException("没有显示内容");
            }
            Channel current = getCurrentChannel(env);
            Site currentSite = getCurrentSite(env);
            int channelId = getChannelId(
                    env, params, PARAM_NAME_VALUE,
                    currentSite.getId(), current.getId());

            if (!isChannelEnabled(channelId, current) && !debug) {
                env.getOut().write("<p style='color:red;'>频道，还未发布</p>");
                return;
            }

            int row = getParamRowValue(params, PARAM_NAME_ROW);
            int pageNumber = 0;
            PageParam page = getPageParam(env);
            if (channelId == current.getId() && EmptyUtil.isNotNull(page)) {
                pageNumber = page.getPage();
                row = page.getRow() != -1 ? page.getRow() : row;
            }

            Boolean top = this.getParamTopValue(params, PARAM_NAME_TOP);

            List list = (top == null ?
                findGeneratorData(channelId, pageNumber, row):
                findGeneratorDataTop(channelId,row,top));
            
            if (EmptyUtil.isArrayEmpty(loopVars)) {
                String name = getParamNameValue(params, PARAM_NAME_NAME);
                Writer writer = env.getOut();
                for (int i = 0; i < list.size(); i++) {
                    Object obj = list.get(i);
                    DirectiveUtil.setVariable(env, name, obj);
                    DirectiveUtil.setVariable(env, DirectiveVariable.ListIndex.toString(), i + 1);
                    body.render(writer);
                    DirectiveUtil.removeVariable(env, DirectiveVariable.ListIndex.toString());
                    DirectiveUtil.removeVariable(env, name);
                }
                writer.flush();
            } else {
                loopVars[0] = env.getObjectWrapper().wrap(list);
                body.render(env.getOut());
            }
        } catch (DirectiveException e) {
            e.render(env.getOut());
        } catch (Exception e) {
            DirectiveException ex = new DirectiveException(e);
            ex.render(env.getOut());
        }
    }

    /**
     * 得到当前站点
     *
     * @param env Environment
     * @return
     * @throws TemplateModelException
     */
    private Site getCurrentSite(final Environment env) throws TemplateModelException {
        String current = DirectiveVariable.CurrentSite.toString();
        Site site = (Site) DirectiveUtil.getBean(env, current);
        Assert.notNull(site);
        return site;
    }

    /**
     * 得到当前频道
     *
     * @param env Environment
     * @return
     * @throws TemplateModelException
     */
    private Channel getCurrentChannel(final Environment env) throws TemplateModelException {
        String currentChannel = DirectiveVariable.CurrentChannel.toString();
        Channel channel = (Channel) DirectiveUtil.getBean(env, currentChannel);
        Assert.notNull(channel);
        return channel;
    }

    /**
     * 得到频道指定频道编号
     *
     * @param env
     * @param params
     * @return
     * @throws TemplateModelException
     * @throws ParameterException
     */
    private int getChannelId(final Environment env, final Map params,
            final String name, final int currentSiteId, final int currentChanneldId)
            throws TemplateModelException, DirectiveException {

        Integer id = DirectiveUtil.getInteger(params, name);
        if (EmptyUtil.isNotNull(id)) {
            return id;
        }

        String varName = DirectiveUtil.getString(params, name);
        varName = (varName == null ? DirectiveVariable.Channel.toString() : varName);
        Channel channel = (Channel) DirectiveUtil.getBean(env, varName);
        if (EmptyUtil.isNotNull(channel)) {
            return channel.getId();
        }

        String url = DirectiveUtil.getString(params, name);
        if (EmptyUtil.isStringNotEmpty(url)) {
            url = url.trim();
            return getChannelIdByUrlOrDir(currentSiteId, url);
        }

        return currentChanneldId;
    }

    /**
     * 通过频道url得到频道编号
     *
     * @param url 频道url
     * @return
     * @throws ParameterException
     */
    private int getChannelIdByUrlOrDir(final int siteId, final String url) throws DirectiveException {
        Channel channel = dao.getChannelByUrlOrDir(siteId, url);
        if (EmptyUtil.isNull(channel)) {
            throw new DirectiveException(String.format("%s 不存在", url));
        }
        return channel.getId();
    }

    private boolean isChannelEnabled(int id, Channel current) throws DirectiveException {
        if (id == current.getId()) {
            return current.getPublicenable();
        }
        Channel channel = dao.getChannel(id);
        if (EmptyUtil.isNull(channel)) {
            throw new DirectiveException("频道不存在");
        }
        if (channel.getSite().getId() != current.getSite().getId()) {
            throw new DirectiveException("频道不存在");
        }
        return channel.getPublicenable();
    }

    private List findGeneratorData(int channelId, int page, int rows) {
        return dao.findArticlePage(channelId, page, rows);
    }

    private List findGeneratorDataTop(int channelId,int rows, Boolean top) {
        return dao.findArticlePageTop(channelId,rows, top);
    }

    /**
     * 得到显示行数
     *
     * @param params
     * @return
     * @throws TemplateModelException
     */
    private int getParamRowValue(final Map params, String name) throws TemplateModelException {
        Integer rows = DirectiveUtil.getInteger(params, name);
        return rows == null ? DEFAULT_ROWS : rows;
    }

    /**
     * 得到Name参数值
     *
     * @param params
     * @return
     * @throws TemplateModelException
     */
    private String getParamNameValue(final Map params, final String name) throws TemplateModelException {
        String value = DirectiveUtil.getString(params, name);
        return value == null ? DirectiveVariable.Article.toString() : value;
    }

    /**
     * 得到Top参数值
     * 设置是否得到顶置新闻
     *
     * @param params
     * @return
     * @throws TemplateModelException
     */
    private Boolean getParamTopValue(final Map params, final String name) throws TemplateModelException {
        return DirectiveUtil.getBoolean(params, name);
    }

    /**
     * 得到显示页面数
     *
     * @param env
     * @param id 变量标示
     * @return
     * @throws TemplateModelException
     */
    private PageParam getPageParam(final Environment env) throws TemplateModelException {
        PageParam page = (PageParam) DirectiveUtil.getBean(env, DirectiveVariable.PageParam.toString());
        return page;
    }
}
