/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.service;

import java.util.List;

import com.ewcms.content.document.model.Article;

/**
 * 文章加载和操作接口
 * <br>
 * 提供生成文章所需要的数据，并更改以发布文章状态。
 * 
 * @author wangwei
 */
public interface ArticlePublishServiceable {

    /**
     * 得到指定的文章
     *
     * @param id 文章编号
     * @return
     */
    Article getArticle(Long id);

    /**
     * 发布文章成功
      * <br>
     * 标示文章为发布状态。
     * 
     * @param id 文章编号
     * @param url 文章链接地址
     */
    void publishArticleSuccess(Long id, String url);
    
    /**
     * 查询准备发布的文章
     * <br>
     * 再发布时会得到所有要发布的文章（PreRelease和Release）。
     * 
     * @param channelId 频道编号 
     * @param forceAgain 再发布 
     * @param limit 最大文章数
     * @return
     */
    List<Article> findPublishArticles(Integer channelId,Boolean forceAgain, Integer limit);
    
    /**
     * 得到频道指定页面文章
     * 
     * <br>
     * 查询的文章已经发布，按照文章定义的顺序显示，页数从0开始。
     * 
     * @param channelId  频道编号
     * @param page 页数
     * @param row 行数
     * @param top  顶置文章
     * @return
     */
    List<Article> findArticleReleasePage(Integer channelId,Integer page,Integer row,Boolean top);
    
    /**
     * 得到指定子频道的页面文章
     * 
     * @param channelId 频道编号
     * @param page 页数
     * @param row 行数
     * @param top 顶置文章
     * @return
     */
    List<Article> findChildChannelArticleReleasePage(Integer channelId,Integer page,Integer row,Boolean top);

    /**
     * 得到频道已经发布的文章总数
     * <br>
     * 如果已经发布文章总数大于频道最大显示记录数，则返回最大记录数
     * 
     * @param channelId 频道编号
     * @return 
     */
    int getArticleReleaseCount(Integer channelId);
}
