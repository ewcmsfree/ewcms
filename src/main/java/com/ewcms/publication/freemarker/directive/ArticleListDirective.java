/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.content.document.model.Article;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.freemarker.FreemarkerUtil;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 文章列表标签
 * <br>
 * 通过设置频道id或地址，可以得到相应的文章记录。
 * 
 * @author wangwei
 */
public class ArticleListDirective implements TemplateDirectiveModel {
    private static final Logger logger = LoggerFactory.getLogger(ArticleListDirective.class);
    
    private static final Integer DEFAULT_ROW = 20;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Boolean DEFAULT_ABSOLUTE = Boolean.FALSE;
    
    private static final String CHANNEL_PARAM_NAME = "channel";
    private static final String ROW_PARAM_NAME = "row";
    private static final String NAME_PARAM_NAME = "name";
    private static final String TOP_PARAM_NAME = "top";
    private static final String ABSOLUTE_PARAM_NAME = "absolute ";
    
    private String channelParam = CHANNEL_PARAM_NAME;
    private String rowParam = ROW_PARAM_NAME;
    private String nameParam = NAME_PARAM_NAME;
    private String topParam = TOP_PARAM_NAME;
    private String absoluteParam = ABSOLUTE_PARAM_NAME;

    private ArticlePublishServiceable articleService;
    private ChannelPublishServiceable channelService;
    
    public ArticleListDirective(ChannelPublishServiceable channelService,ArticlePublishServiceable articleService){
        this.articleService  = articleService;
        this.channelService = channelService;
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Integer siteId = getSiteIdValue(env);

        Channel channel = getChannel(env, params, siteId);
        Channel currentChannel = getCurrentChannel(env);
        if(channel == null){
            channel = currentChannel;
        }
        
        boolean debug = FreemarkerUtil.isDebug(env);
        if (!debug && !channel.getPublicenable()) {
            logger.debug("Channel's id is {},it is not release.", channel.getId());
            return;
        }

        Integer row = getRowValue(params,channel,env);
        Integer pageNumber =getPageNumberValue(env
                    ,channel.equals(currentChannel)
                    ,getAbsoluteValue(params));
        Boolean top = getTopValue(params);

        List<Article> articles = articleService.findArticleReleasePage(channel.getId(), pageNumber, row,top); 

        if(EmptyUtil.isArrayNotEmpty(loopVars)){
            loopVars[0] = env.getObjectWrapper().wrap(articles);
            if(EmptyUtil.isNull(body)){
                logger.warn("Body is null");
            }else{
                body.render(env.getOut());    
            }
        }else if (EmptyUtil.isNotNull(body)) {
            String name = getNameValue(params);
            Writer writer = env.getOut();
            int start = pageNumber * row;
            for (int i = 0; i < articles.size(); i++) {
                Article article = articles.get(i);
                FreemarkerUtil.setVariable(env, name, article);
                FreemarkerUtil.setVariable(env, GlobalVariable.INDEX.toString(), Integer.valueOf(start+i+1));
                body.render(writer);
                FreemarkerUtil.removeVariable(env, GlobalVariable.INDEX.toString());
                FreemarkerUtil.removeVariable(env, name);
            }
            writer.flush();
        } else {
            logger.warn("Body and loopVars are all null");
        }
    }

    /**
     * 得到当前站点编号
     * 
     * @param env 
     *          Freemarker环境
     * @return
     * @throws TemplateException
     */
    private Integer getSiteIdValue(final Environment env) throws TemplateException {
        Site site = (Site) FreemarkerUtil.getBean(env, GlobalVariable.SITE.toString());
        if(EmptyUtil.isNull(site)){
            logger.error("Site is null in freemarker variable");
            throw new TemplateModelException("Site is null in freemarker variable");
        }
        logger.debug("Site is {}",site);
        return site.getId();
    }
    
    /**
     * 值得到频道
     * 
     * @param env
     *          Freemarker环境
     * @param params
     *          标签参数集合
     * @param siteId 
     *          站点编号
     * @param name
     *          标签属性名
     * @return
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private Channel getChannel(Environment env,Map params,int siteId)throws TemplateException{
        final String name = channelParam;
        
        Channel channel =null;
        Integer id = FreemarkerUtil.getInteger(params, name);
        if (EmptyUtil.isNotNull(id)) {
            logger.debug("Channel's id is {}",id);
            channel = channelService.getChannel(siteId,id);
            if(channel == null){
                logger.error("Channel's id is {},it is not exist.",id);
                //throw new TemplateModelException("Channel id is "+id+",it is not exist");
            }
            return channel;
        }

        String value = FreemarkerUtil.getString(params, name);
        if (EmptyUtil.isStringNotEmpty(value)) {
            logger.debug("Directive {} property is {}",name,value);
            channel = (Channel) FreemarkerUtil.getBean(env, value);
            if (EmptyUtil.isNotNull(channel)) {
                logger.debug("Channel is {}",channel.toString());
                return channel;
            }
            value = UriFormat.formatChannelPath(value);
            channel = channelService.getChannelByUrlOrPath(siteId, value);
            if(channel == null){
                logger.error("Channel's path or variable is {},it is not exist.",value);
                //throw new TemplateModelException("Channel's path or variable is "+value+",it is not exist");
            }
            return channel;
        }
        
        return channel;
    }
   
    /**
     * 得到当前频道
     *
     * @param env Environment
     * @return
     * @throws TemplateModelException
     */
    private Channel getCurrentChannel(final Environment env) throws TemplateException {
        String value = GlobalVariable.CHANNEL.toString();
        Channel channel = (Channel) FreemarkerUtil.getBean(env, value);
        if (channel == null) {
            logger.error("Default channel is null");
            throw new TemplateModelException("Default channel is null");
        }
        return channel;
    }
    
    /**
     * 得到显示行数
     *
     * @param params 
     *           标签参数集合
     * @return
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private Integer getRowValue(Map params,Channel channel,Environment env) throws TemplateException {
        Integer row = FreemarkerUtil.getInteger(params, rowParam);
        if(row != null){
            if(row > 0){
                return row;
            }else{
                logger.error("Row must than 0,but row is {}",row);
                throw new TemplateModelException("Row must than 0");
            }
        }
        
        Channel current = (Channel) FreemarkerUtil.getBean(env, GlobalVariable.CHANNEL.toString());
        if(channel.equals(current)){
            return channel.getListSize();
        }else{
            return DEFAULT_ROW;
        }
    }

    /**
     * 得到Name参数值
     *
     * @param params
     *            标签参数集合
     * @return
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private String getNameValue(final Map params) throws TemplateException {
        String value = FreemarkerUtil.getString(params, nameParam);
        return value == null ? GlobalVariable.ARTICLE.toString() : value;
    }

    /**
     * 得到Top参数值
     * <br>
     * 显示顶置新闻
     *
     * @param params
     *            标签参数集合
     * @return
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private Boolean getTopValue(Map params) throws TemplateException {
        Boolean top = FreemarkerUtil.getBoolean(params, topParam);
        return top;
    }

    /**
     * 得到是否按照绝对位置查询，忽略页数。
     *<br>
     * 解决在当前频道中只想显示当前频道指定的文章列表，不会随着页面改变而改变。
     * 
     *  @param params
     *            标签参数集合
     * @return
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private Boolean getAbsoluteValue(Map params) throws TemplateException {
        Boolean value = FreemarkerUtil.getBoolean(params, absoluteParam);
        return value == null ? DEFAULT_ABSOLUTE : value;
    }
    
    /**
     * 得到显示页面数
     *
     * @param env
     *          Freemarker环境
     * @param current 
     *          但前频道
     *  @param absolute 
     *           绝对位置
     * @return
     * @throws TemplateException
     */
    private Integer getPageNumberValue(Environment env,boolean current,boolean absolute) throws TemplateException {
        if(!current || absolute){
            return DEFAULT_PAGE_NUMBER;
        }
        Integer number = FreemarkerUtil.getInteger(env, GlobalVariable.PAGE_NUMBER.toString());
        logger.debug("Page is {}",number);
        return number == null ? DEFAULT_PAGE_NUMBER : number;
    }
    
    /**
     * 设置标签频道参数名
     * 
     * @param paramName 参数名
     */
    public void setChannelParam(String paramName) {
        this.channelParam = paramName;
    }

    /**
     * 设置标签显示行参数名
     * 
     * @param paramName 参数名
     */
    public void setRowParam(String paramName) {
        this.rowParam = paramName;
    }

    /**
     * 设置标签输出变量名
     * 
     * @param paramName 参数名称
     */
    public void setNameParam(String paramName) {
        this.nameParam = paramName;
    }

    /**
     * 设置标签顶置显示参数名
     * 
     * @param paramName
     */
    public void setTopParam(String paramName) {
        this.topParam = paramName;
    }

    /**
     * 设置标签绝对位置参数名
     * 
     * @param paramName
     */
    public void setAbsoluteParam(String absoluteParam) {
        this.absoluteParam = absoluteParam;
    }
}
