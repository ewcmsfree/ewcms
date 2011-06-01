/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleCategory;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.Recommend;
import com.ewcms.content.document.model.Related;
import com.ewcms.content.document.service.ArticleCategoryServiceable;
import com.ewcms.content.document.service.ArticleMainServiceable;
import com.ewcms.content.document.service.ArticleServiceable;
import com.ewcms.content.document.service.RecommendServiceable;
import com.ewcms.content.document.service.RelatedServiceable;
import com.ewcms.generator.release.ReleaseException;

/**
 *
 * @author 吴智俊
 */
@Service
public class DocumentFac implements DocumentFacable {
	
	@Autowired
	private ArticleCategoryServiceable articleCategoryService;
	@Autowired
	private ArticleMainServiceable articleMainService;
	@Autowired
	private ArticleServiceable articleService;
	@Autowired
	private RecommendServiceable recommendService;
	@Autowired
	private RelatedServiceable relatedService;
	
	@Override
	public Integer addArticleCategory(ArticleCategory articleCategory){
		return articleCategoryService.addArticleCategory(articleCategory);
	}
	
	@Override
	public Integer updArticleCategory(ArticleCategory articleCategory){
		return articleCategoryService.updArticleCategory(articleCategory);
	}
	
	@Override
	public void delArticleCategory(Integer articleCategoryId){
		articleCategoryService.delArticleCategory(articleCategoryId);
	}
	
	@Override
	public ArticleCategory findArticleCategory(Integer articleCategoryId){
		return articleCategoryService.findArticleCategory(articleCategoryId);
	}
	
	@Override
	public List<ArticleCategory> findArticleCategoryAll(){
		return articleCategoryService.findArticleCategoryAll();
	}
	
	@Override
	public ArticleMain findArticleMainByArticleMainAndChannel(Long articleMainId, Integer channelId){
		return articleMainService.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
	}

	@Override
	public void delArticleMain(Long articleMainId, Integer channelId){
		articleMainService.delArticleMain(articleMainId, channelId);
	}

	@Override
	public void delArticleMainToRecycleBin(Long articleMainId, Integer channelId, String userName){
		articleMainService.delArticleMainToRecycleBin(articleMainId, channelId, userName);
	}

	@Override
	public void restoreArticleMain(Long articleMainId, Integer channelId, String userName){
		articleMainService.restoreArticleMain(articleMainId, channelId, userName);
	}

	@Override
	public Boolean submitReviewArticleMain(Long articleMainId, Integer channelId){
		return articleMainService.submitReviewArticleMain(articleMainId, channelId);
	}
	
	@Override
	public void submitReviewArticleMains(List<Long> articleMainIds, Integer channelId){
		articleMainService.submitReviewArticleMains(articleMainIds, channelId);
	}
	
	@Override
	public Boolean copyArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId){
		return articleMainService.copyArticleMainToChannel(articleMainIds, channelIds, source_channelId);
	}

	@Override
	public Boolean moveArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId){
		return articleMainService.moveArticleMainToChannel(articleMainIds, channelIds, source_channelId);
	}

	@Override
	public List<ArticleMain> findArticleMainByChannel(Integer channelId){
		return articleMainService.findArticleMainByChannel(channelId);
	}

	@Override
	public void pubArticleMainByChannel(Integer channelId) throws ReleaseException{
		articleMainService.pubArticleMainByChannel(channelId);
	}
	
	@Override
	public void reviewArticleMain(List<Long> articleMainIds, Integer channelId, Integer review, String eauthor){
		articleMainService.reviewArticleMain(articleMainIds, channelId, review, eauthor);
	}
	
	@Override
	public void moveArticleMainSort(Long articleMainId, Integer channelId, Long sort, Integer isInsert, Boolean isTop){
		articleMainService.moveArticleMainSort(articleMainId, channelId, sort, isInsert, isTop);
	}
	
	@Override
	public Boolean findArticleMainByChannelAndEqualSort(Integer channelId, Long sort, Boolean isTop){
		return articleMainService.findArticleMainByChannelAndEqualSort(channelId, sort, isTop);
	}

	@Override
	public Long addArticle(Article article, Integer channelId, Date published){
		return articleService.addArticle(article, channelId, published);
	}
	
	@Override
	public Long updArticle(Article article, Long articleMainId, Integer channelId, Date published){
		return articleService.updArticle(article, articleMainId, channelId, published);
	}

	@Override
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Integer articleCategoryId){
		return articleService.findArticleIsEntityByArticleAndCategory(articleId, articleCategoryId);
	}

	@Override
	public void saveRecommend(Long articleId, Long[] recommendArticleIds){
		recommendService.saveRecommend(articleId, recommendArticleIds);
	}
	
	@Override	
	public void deleteRecommend(Long articleId, Long[] recommendArticleIds){
		recommendService.deleteRecommend(articleId, recommendArticleIds);
	}
	
	@Override
	public void upRecommend(Long articleId, Long[] recommendArticleIds){
		recommendService.upRecommend(articleId, recommendArticleIds);
	}

	@Override
	public void downRecommend(Long articleId, Long[] recommendArticleIds){
		recommendService.downRecommend(articleId, recommendArticleIds);
	}
	
	@Override
	public List<Recommend> findRecommendByArticle(Long articleId){
		return recommendService.findRecommendByArticle(articleId);
	}

	@Override
	public void saveRelated(Long articleId, Long[] relatedArticleIds){
		relatedService.saveRelated(articleId, relatedArticleIds);
	}
	
	@Override
	public void deleteRelated(Long articleId, Long[] relatedArticleIds){
		relatedService.deleteRelated(articleId, relatedArticleIds);
	}
	
	@Override
	public void upRelated(Long articleId, Long[] relatedArticleIds){
		relatedService.upRelated(articleId, relatedArticleIds);
	}

	@Override
	public void downRelated(Long articleId, Long[] relatedArticleIds){
		relatedService.downRelated(articleId, relatedArticleIds);
	}
	
	@Override
	public List<Related> findRelatedByArticle(Long articleId){
		return relatedService.findRelatedByArticle(articleId);
	}
}
