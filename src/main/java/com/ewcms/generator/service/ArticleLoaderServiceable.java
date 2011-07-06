/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.service;

import java.util.List;

import com.ewcms.content.document.model.Article;

/**
 * 文章加载和操作接口
 * <br>
 * 提供生成文章所需要的数据，并更改以发布文章状态。
 * 
 * @author wangwei
 */
public interface ArticleLoaderServiceable {

    /**
     * 得到指定的文章
     *
     * @param id 文章编号
     * @return
     */
    Article getArticle(Integer id);

    /**
     * 发布文章
     * 
     * @param id 文章编号
     * @param url 文章链接地址
     */
    void releaseArticle(Integer id, String url);
    
    /**
     * 查询准备发布的文章
     * 
     * @param channelId 频道编号 
     * @param limit 最大文件数
     * @return
     */
    List<Article> findReleaseArticles(Integer channelId,Integer limit);
    
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
    List<Article> findArticlePage(Integer channelId,Integer page,Integer row,Boolean top);

    /**
     * 得到频道已经发布的文章总数
     * <br>
     * 如果已经发布文章总数大于频道最大显示记录数，则返回最大记录数
     * 
     * @param channelId 频道编号
     * @return 
     */
    int getArticleCount(Integer channelId);
    
    /**
     * 重新发布已发布的文章
     * 
     * <br/>
     * 把已经发布的文章变成预发布文章。
     * 
     * @param channelId 频道编号
     */
     void againReleaseArticle(Integer channelId);
}
