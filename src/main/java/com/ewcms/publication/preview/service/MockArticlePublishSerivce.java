/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.preview.service;

import java.util.List;

import com.ewcms.content.document.model.Article;
import com.ewcms.publication.service.ArticlePublishServiceable;

/**
 * 模拟ArticlePublishSerivceable实现，为模板预览提供文章模拟数据。
 * 
 * @author wangwei
 */
public class MockArticlePublishSerivce implements ArticlePublishServiceable{

    @Override
    public Article getArticle(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void publishArticle(Long id, String url) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Article> findPreReleaseArticles(Integer channelId, Integer limit) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Article> findReleaseArticlePage(Integer channelId, Integer page, Integer row, Boolean top) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getArticleCount(Integer channelId) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void updatePreRelease(Integer channelId) {
        // TODO Auto-generated method stub
    }
}
