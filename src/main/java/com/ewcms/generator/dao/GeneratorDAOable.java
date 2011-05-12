/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.dao;

import java.util.List;

import com.ewcms.core.document.model.ArticleRmc;
import com.ewcms.core.resource.model.Resource;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.TemplateSource;

/**
 *
 * @author wangwei
 */
public interface GeneratorDAOable {

    /**
     * 得到指定的文章
     *
     * @param id 文章编号
     * @return
     */
    ArticleRmc getArticle(int id);

    /**
     * 设置文章发布信息
     * 
     * @param id 文章编号
     * @param url 生成文章地址
     */
    void articlePublish(int id,String url);

    /**
     * 得到指定的频道
     *
     * @param id 频道编号
     * @return
     */
    Channel getChannel(int id);

    /**
     * 得到频道中准备发布的文章
     * 
     * @param channelId
     * @return
     */
    List<ArticleRmc> findPreReleaseArticle(int channelId);
    
    /**
     * 得到指定频道指定位置的中文章记录
     *
     * @param id  频道编号
     * @param page 页数
     * @param rows 记录数
     * @return
     */
    List<ArticleRmc> findArticlePage(int id,int page,int rows);

    /**
     * 得到指定频道定制文章记录
     * 
     * @param channelId
     * @param rows
     * @param top
     * @return
     */
    List<ArticleRmc> findArticlePageTop(int channelId,int rows,boolean top);

    /**
     * 得到显示文章记录数
     * 
     * @param id
     * @return
     */
    int getArticleCount(int id);
    
    /**
     * 通过频道url得到频道对象
     *
     * @siteId 站点编号
     * @param url 频道url
     * @return
     */
    Channel getChannelByUrlOrDir(int siteId,String url);

    /**
     * 得到站点目录名
     * 
     * @param siteId 站点编号
     * @return
     */
    String getSiteServerDir(int siteId);

    /**
     * 需要发布资源
     * 
     * @param siteId
     * @param time
     */
    List<Resource> getReleaseResource(int siteId);

    /**
     * 得到资源
     * 
     * @param id
     * @return
     */
    Resource getResource(int id);

    /**
     * 标示发布资源
     * 
     * @param resource
     */
    void releaseResource(Resource resource);

    /**
     * 清除频道下已经发布的文章
     * 
     * 重新发布频道下的文章，需要把发布的文章状态变成预发布状态。
     * 
     * @param channelId 频道编号
     */
    void cleanArticlePublish(int channelId);
    
    /**
     * 查询需未发布的模板资源
     * 
     * @param siteId 站点编号
     * @return 模板资源列表
     */
    List<TemplateSource> findNotReleaseTemplateSources(int siteId);
    
    /**
     * 得到模板资源
     * 
     * @param id 模板资源编号
     * @return 模板资源
     */
    TemplateSource getTemplateSource(int id);
    
    /**
     * 更新模板资源为发布状态
     * 
     * @param id 模板资源编号
     */
    void releaseTemplateSource(int id);
    
    /**
     * 得到子目录模板资源
     * 
     * @param parentId 父目录编号
     * @return
     */
    List<TemplateSource> findChildrenTemplateSource(int parentId);
}
