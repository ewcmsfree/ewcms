/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.generator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.uri.UriRuleable;
import com.ewcms.publication.uri.UriRules;

import freemarker.template.Configuration;

/**
 * 生成频道文章列表页
 * 
 * @author wangwei
 */
public class ListGenerator extends GeneratorBase {
    private final static Logger logger = LoggerFactory.getLogger(ListGenerator.class);
    private final static String DEFAULT_HOME_NAME = "index";
    
    private final int pageNumber;
    private final int pageCount;
    private final boolean createHome;
    
   /**
     * 生成List页面构造函数
     * 
     * @param cfg freemarker 配置对象
     * @param site 站点
     * @param channel 频道
     * @param pageNumber 页数
     * @param pageCount 总页数
     */
    public ListGenerator(Configuration cfg, Site site, Channel channel,int pageNumber,int pageCount) {
        this(cfg, site, channel,UriRules.newList(),pageNumber,pageCount);
    }
    
    /**
     * 生成List页面构造函数
     * 
     * @param cfg freemarker 配置对象
     * @param site 站点
     * @param channel 频道
     * @param rule uri生成规则
     * @param pageNumber 页数
     * @param pageCount 总页数
     */
    public ListGenerator(Configuration cfg, Site site, Channel channel,UriRuleable rule,int pageNumber,int pageCount) {
         this(cfg, site, channel,rule,pageNumber,pageCount,false);
    }
    
    /**
     * 生成List页面构造函数
     * 
     * @param cfg freemarker 配置对象
     * @param site 站点
     * @param channel 频道
     * @param rule uri生成规则
     * @param pageNumber 页数
     * @param pageCount 总页数
     * @param createHome 是否创建首页
     */
    public ListGenerator(Configuration cfg, Site site, Channel channel,UriRuleable rule,int pageNumber,int pageCount,boolean createHome) {
        super(cfg, site, channel,rule);
        this.pageCount = pageCount;
        this.pageNumber = pageNumber;
        this.createHome = createHome;
    }
    
    @Override
    protected Map<String,Object> constructParameters(Site site,Channel channel) {
        
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.SITE.toString(), site);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        params.put(GlobalVariable.PAGE_NUMBER.toString(), pageNumber);
        params.put(GlobalVariable.PAGE_COUNT.toString(), pageCount);
        params.put(GlobalVariable.DEBUG.toString(), debug);
        
        return params;
    }
    
    @Override
    public String[] getPublishAdditionUris()throws PublishException{
        String[] additionUris = new String[0];
        if(createHome && pageNumber == 0){
            logger.debug("Start create home uri");
            String uri = getPublishUri();
            logger.debug("List page first uri is {}",uri);
            String extension = FilenameUtils.getExtension(uri);
            if(StringUtils.isNotBlank(extension)){
                extension = "." + extension;
            }
            String homeUri = FilenameUtils.getFullPath(uri) + DEFAULT_HOME_NAME + extension;
            additionUris = new String[]{homeUri };
            logger.debug("Home page uri is {}",additionUris[0]);
        }
        return additionUris;
    }
}
