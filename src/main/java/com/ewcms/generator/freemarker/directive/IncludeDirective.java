/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.generator.freemarker.FreemarkerUtil;
import com.ewcms.generator.freemarker.GlobalVariable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 包含标签
 * 
 * @author wangwei
 */
public class IncludeDirective implements TemplateDirectiveModel {

    private final static Logger logger = LoggerFactory.getLogger(IncludeDirective.class);
    
    private final static String PATH_PARAM_NAME = "path";
    private final static String PARSE_PARAM_NAME = "parse";
    private final static String CHANNEL_PARAM_NAME = "channel";
    private final static String NAME_PARAM_NAME = "name";
    
    private String pathParam = PATH_PARAM_NAME;
    private String parseParam = PARSE_PARAM_NAME;
    private String channelParam = CHANNEL_PARAM_NAME;
    private String nameParam = NAME_PARAM_NAME;
    
    @SuppressWarnings("rawtypes")
    @Override
    public void execute(final Environment env, final Map params, final TemplateModel[] loopVars, final TemplateDirectiveBody body) throws TemplateException, IOException {
        String path = getPathValue(params);
        Integer siteId = getSiteValue(env).getId();
        String uniquePath = null;
        if(EmptyUtil.isNotNull(path)){
            uniquePath = getUniqueTemplatePath(siteId,path);
        }else{
            String name = getNameValue(params);
            if(EmptyUtil.isNotNull(name)){
                Integer channelId = getChannelIdValue(env,params);
                uniquePath = getChannelTemplatePath(siteId,channelId,name);
            }
        }
        if(EmptyUtil.isNull(uniquePath)){
            logger.error("Include template path is null");
            throw new TemplateModelException("Include template path is null");
        }
        logger.debug("Include template path is {}",uniquePath);
        boolean parse = getParseValue(params);
        env.include(uniquePath, env.getConfiguration().getDefaultEncoding(), parse);
    }

    /**
     * 从Freemarker得到站点对象
     * 
     * @param env freemarker环境
     * @return
     * @throws TemplateException
     */
    private Site getSiteValue(Environment env)throws TemplateException {
        Site site = (Site) FreemarkerUtil.getBean(env, GlobalVariable.SITE.toString());
        if(EmptyUtil.isNull(site)){
            logger.error("Site is null in freemarker variable");
            throw new TemplateModelException("Site is null in freemarker variable");
        }
        logger.debug("Site is {}",site);
        return site;
    }

    /**
     * 得到模板路径
     * 
     * @param params 标签参数
     * @return
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private String getPathValue(Map params)throws TemplateException {
        String path = FreemarkerUtil.getString(params, pathParam);
        logger.debug("Path is {}",path);
        return path;
    }
    
    /**
     * 得到指定频道编号
     * 
     * @param env freemarker环境
     * @param params 标签参数
     * @return 频道编号
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private Integer getChannelIdValue(Environment env,Map params)throws TemplateException{
        Channel channel = (Channel)FreemarkerUtil.getBean(params, channelParam);
        if (EmptyUtil.isNotNull(channel)) {
            logger.debug("Get channel is {}",channel);
            return channel.getId();
        }

        Integer id = FreemarkerUtil.getInteger(params, channelParam);
        if(EmptyUtil.isNotNull(id)){
            logger.debug("Get channel id is {}",id);
            return id;
        }
        
        String path= FreemarkerUtil.getString(params, channelParam);
        logger.debug("Get channel by {}",path);
        if(EmptyUtil.isNotNull(path)){
            //TODO loading channel by path
        }
       
        String name = GlobalVariable.CHANNEL.toString();
        logger.debug("Get value param is {}", name);
        channel = (Channel)FreemarkerUtil.getBean(env, name);
        
        if (EmptyUtil.isNull(channel)) {
            logger.error("Channel is null in freemarker variable");
            throw new TemplateModelException("Channel is null in freemarker variable");
        }
        
        return channel.getId();
    }
    
    /**
     * 得到模板名称
     * 
     * @param params 标签参数集合
     * @return
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private String getNameValue(Map params)throws TemplateException{
        String name = FreemarkerUtil.getString(params, nameParam);
        logger.debug("name is {}",name);
        return name;
    }
    
    /**
     * 得到是否解析模板
     * 
     * @param params 标签参数集合
     * @return
     * @throws TemplateModelException
     */
    @SuppressWarnings("rawtypes")
    private Boolean getParseValue(Map params) throws TemplateModelException {
        Boolean enabled = FreemarkerUtil.getBoolean(params, parseParam);
        return EmptyUtil.isNull(enabled) ? Boolean.TRUE : enabled;
    }

    /**
     * 模板唯一路径
     * 
     * <p>站点编号+模板路径</p>
     * 
     * @param siteId 站点编号
     * @param path 模板路径
     * @return
     */
    String getUniqueTemplatePath(Integer siteId, String path) {
        String nPath = StringUtils.removeStart(path, "/");
        String uPath = StringUtils.join(new Object[]{siteId,nPath}, "/");
        logger.debug("Include path is {}",uPath);
        return uPath;
    }
    
    /**
     * 模板唯一路径
     * 
     * @param siteId 站点编号
     * @param path 路径
     * @param name 模板名称
     * @return
     */
    String getChannelTemplatePath(Integer siteId,Integer channelId,String name){
        //TODO now only mock channel create
        String nName = StringUtils.removeStart(name, "/");
        return StringUtils.join(new Object[]{siteId,channelId,nName},"/");
    }

    /**
     * 设置模板参数名称
     * 
     * @param pathParam 路径参数名
     */
    public void setPathParam(String pathParam) {
        this.pathParam = pathParam;
    }

    /**
     * 设置解析参数名称
     * 
     * @param parseParam 解析参数名
     */
    public void setParseParam(String parseParam) {
        this.parseParam = parseParam;
    }

    /**
     * 设置频道参数名称
     * 
     * @param channelParam 频道参数名
     */
    public void setChannelParam(String channelParam) {
        this.channelParam = channelParam;
    }

    /**
     * 设置模板参数名称
     * 
     * @param nameParam 模板参数名
     */
    public void setNameParam(String nameParam) {
        this.nameParam = nameParam;
    }
}
