/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.content.document.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.document.dao.ArticleRmcDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.content.document.model.Recommend;
import com.ewcms.content.document.model.Related;
import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.release.ReleaseException;
import com.ewcms.history.History;

/**
 *
 * @author 吴智俊
 */
@Service()
public class ArticleRmcService implements ArticleRmcServiceable {

    @Autowired
    private ArticleRmcDAO articleRmcDAO;
    
    @Autowired
    private ChannelDAO channelDAO;
    
	@Autowired
	private ArticleRmcServiceWrapping wrapping;

	@Override
	@History(modelObjectIndex = 0)
	public Integer addArticleRmc(Article article, Integer channelId, Date published) {
		return wrapping.addArticleRmc(article, channelId, published);
	}
	
	@Override
	@History(modelObjectIndex = 1)
	public Integer updArticleRmc(Integer articleRmcId, Article article, Integer channelId, Date published){
		return wrapping.updArticleRmc(articleRmcId, article, channelId, published);
	}
	
	@Override
	public ArticleRmc getArticleRmc(Integer articleRmcId) {
		ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
		return wrapping.getArticleRmc(articleRmc, articleRmc.getChannel().getId());
	}
	
	@Override
	public void delArticleRmc(Integer articleRmcId) {
		ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
		wrapping.delArticleRmc(articleRmc, articleRmc.getChannel().getId());
	}
	
	@Override
	public void delArticleRmcToRecycleBin(Integer articleRmcId, String userName) {
		ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
		wrapping.delArticleRmcToRecycleBin(articleRmc, articleRmc.getChannel().getId(), userName);
	}
	
	@Override
	public void restoreArticleRmc(Integer articleRmcId, String userName) {
		ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
		wrapping.restoreArticleRmc(articleRmc, articleRmc.getChannel().getId(), userName);
	}
    
	@Override
	public Boolean preReleaseArticleRmc(Integer articleRmcId) {
		ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
		return wrapping.preReleaseArticleRmc(articleRmc, articleRmc.getChannel().getId(),true);
	}
	
	@Override
	public void preReleaseArticleRmcs(List<Integer> articleRmcIds){
		Assert.notNull(articleRmcIds);
		ArticleRmc articleRmc = null;
		for (Integer articleRmcId : articleRmcIds){
			articleRmc = articleRmcDAO.get(articleRmcId);
			wrapping.preReleaseArticleRmc(articleRmc, articleRmc.getChannel().getId(), false);
		}
	}
	
	@Override
	public Boolean copyArticleRmcToChannel(List<Integer> articleRmcIds, List<Integer> channelIds) {
		ArticleRmc articleRmc = null;
		Channel channel = null;
		for (Integer channelId : channelIds){
			channel = channelDAO.get(channelId);
			if (channel == null) continue;
			for (Integer articleRmcId : articleRmcIds){
				articleRmc = articleRmcDAO.get(articleRmcId);
				if (articleRmc == null) continue;
				wrapping.copyArticleRmcToChannel(articleRmc, channel);
			}
		}
		return true;
	}

	@Override
	public Boolean moveArticleRmcToChannel(List<Integer> articleRmcIds, List<Integer> channelIds) {
		ArticleRmc articleRmc = null;
		Channel channel = null;
		for (Integer channelId : channelIds){
			channel = channelDAO.get(channelId);
			if (channel == null) continue;
			for (Integer articleRmcId : articleRmcIds){
				articleRmc = articleRmcDAO.get(articleRmcId);
				if (articleRmc == null) continue;
				wrapping.moveArticleRmcToChannel(articleRmc, channel);
			}
		}
		return true;
	}
	
	@Override
	public List<Map<String,Object>> findContnetHistoryToPage(Integer articleId) {
		return articleRmcDAO.findContentHistoryToPage(articleId);
	}

	@Override
	public List<ArticleRmc> findArticleRmcByChannelId(Integer channelId) {
		return articleRmcDAO.findArticleRmcByChannelId(channelId);
	}

	@Override
	public List<Recommend> findRecommendByArticleId(Integer articleRmcId) {
		return articleRmcDAO.findRecommendByArticleRmcId(articleRmcId);
	}

	@Override
	public List<Related> findRelatedByArticleId(Integer articleRmcId) {
		return articleRmcDAO.findRelatedByArticleRmcId(articleRmcId);
	}
	
	@Override
	public void pubChannel(Integer channelId) throws ReleaseException {
		wrapping.pubChannel(channelId);
	}
}
