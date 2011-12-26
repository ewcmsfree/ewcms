/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.document.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.document.dao.ArticleDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.publication.service.ArticlePublishServiceable;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class ArticleService implements ArticlePublishServiceable {

	@Autowired
	private ArticleDAO articleDAO;
	@Autowired
	private ChannelDAO channelDAO;
	@Autowired
	private OperateTrackServiceable operateTrackService;
	
	@Override
	public Article getArticle(Long articleId) {
		return articleDAO.get(articleId);
	}
	
	@Override
	public int getArticleCount(Integer channelId){
		Channel channel = channelDAO.get(channelId);
		Assert.notNull(channel);
		int maxSize = channel.getMaxSize();
		long releaseMaxSize = articleDAO.findArticleReleseMaxSize(channelId);
		if (maxSize < releaseMaxSize){
			return maxSize;
		}else{
			return (int)releaseMaxSize;
		}
	}

	@Override
	public List<Article> findPreReleaseArticles(Integer channelId, Integer limit) {
		Channel channel = channelDAO.get(channelId);
		Assert.notNull(channel);
		return articleDAO.findArticlePreReleaseByChannelAndLimit(channelId, limit); 
	}

	@Override
	public List<Article> findReleaseArticlePage(Integer channelId, Integer page, Integer row, Boolean top) {
		return articleDAO.findArticleReleasePage(channelId, page, row, top);
	}

	@Override
	public void publishArticle(Long id, String url) {
		Article article = articleDAO.get(id);
		Assert.notNull(article);
		article.setUrl(url);
		articleDAO.merge(article);
	}

	@Override
	public void updatePreRelease(Integer channelId) {
		List<ArticleMain> articleMains = articleDAO.findArticleMainRelease(channelId);
		for (ArticleMain articleMain : articleMains){
			Article article = articleMain.getArticle();
			if (article == null) continue;
			operateTrackService.addOperateTrack(articleMain.getId(), article.getStatusDescription(), "从发布变成预发布。", "");
			
			article.setUrl("");
			article.setStatus(ArticleStatus.PRERELEASE);
			
			articleDAO.merge(article);
		}
	}
}
