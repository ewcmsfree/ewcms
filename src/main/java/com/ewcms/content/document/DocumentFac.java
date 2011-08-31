/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleCategory;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.Relation;
import com.ewcms.content.document.model.ReviewProcess;
import com.ewcms.content.document.service.ArticleCategoryServiceable;
import com.ewcms.content.document.service.ArticleMainServiceable;
import com.ewcms.content.document.service.ArticleServiceable;
import com.ewcms.content.document.service.RelationServiceable;
import com.ewcms.content.document.service.ReviewProcessServiceable;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.service.ArticlePublishServiceable;

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
	private RelationServiceable relationService;
	@Autowired
	private ArticlePublishServiceable articlePublishService;
	@Autowired
	private ReviewProcessServiceable reviewProcessService;
	
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
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','READ') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public ArticleMain findArticleMainByArticleMainAndChannel(Long articleMainId, Integer channelId){
		return articleMainService.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void delArticleMain(Long articleMainId, Integer channelId){
		articleMainService.delArticleMain(articleMainId, channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void delArticleMainToRecycleBin(Long articleMainId, Integer channelId, String userName){
		articleMainService.delArticleMainToRecycleBin(articleMainId, channelId, userName);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void restoreArticleMain(Long articleMainId, Integer channelId, String userName){
		articleMainService.restoreArticleMain(articleMainId, channelId, userName);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public Boolean submitReviewArticleMain(Long articleMainId, Integer channelId){
		return articleMainService.submitReviewArticleMain(articleMainId, channelId);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void submitReviewArticleMains(List<Long> articleMainIds, Integer channelId){
		articleMainService.submitReviewArticleMains(articleMainIds, channelId);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#target_channel,'WRITE') " + "or hasPermission(#target_channel,'PUBLISH') " + "or hasPermission(#chantarget_channelnel,'CREATE') " + "or hasPermission(#target_channel,'UPDATE') " + "or hasPermission(#target_channel,'DELETE') " + "or hasPermission(#target_channel,'ADMIN') ")
	public Boolean copyArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId){
		return articleMainService.copyArticleMainToChannel(articleMainIds, channelIds, source_channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#target_channel,'WRITE') " + "or hasPermission(#target_channel,'PUBLISH') " + "or hasPermission(#target_channel,'CREATE') " + "or hasPermission(#target_channel,'UPDATE') " + "or hasPermission(#target_channel,'DELETE') " + "or hasPermission(#target_channel,'ADMIN') ")
	public Boolean moveArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId){
		return articleMainService.moveArticleMainToChannel(articleMainIds, channelIds, source_channelId);
	}

	@Override
	public List<ArticleMain> findArticleMainByChannel(Integer channelId){
		return articleMainService.findArticleMainByChannel(channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void pubArticleMainByChannel(Integer channelId) throws PublishException{
		articleMainService.pubArticleMainByChannel(channelId);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void reviewArticleMain(List<Long> articleMainIds, Integer channelId, Integer review, String eauthor, String description){
		articleMainService.reviewArticleMain(articleMainIds, channelId, review, eauthor, description);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void moveArticleMainSort(Long articleMainId, Integer channelId, Long sort, Integer isInsert, Boolean isTop){
		articleMainService.moveArticleMainSort(articleMainId, channelId, sort, isInsert, isTop);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public Boolean findArticleMainByChannelAndEqualSort(Integer channelId, Long sort, Boolean isTop){
		return articleMainService.findArticleMainByChannelAndEqualSort(channelId, sort, isTop);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void clearArticleMainSort(List<Long> articleMainIds, Integer channelId){
		articleMainService.clearArticleMainSort(articleMainIds, channelId);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public Long addArticle(Article article, Integer channelId, Date published){
		return articleService.addArticle(article, channelId, published);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public Long updArticle(Article article, Long articleMainId, Integer channelId, Date published, String userName){
		return articleService.updArticle(article, articleMainId, channelId, published, userName);
	}

	@Override
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Integer articleCategoryId){
		return articleService.findArticleIsEntityByArticleAndCategory(articleId, articleCategoryId);
	}

	@Override
	public void saveRelation(Long articleId, Long[] relationArticleIds){
		relationService.saveRelation(articleId, relationArticleIds);
	}
	
	@Override
	public void deleteRelation(Long articleId, Long[] relationArticleIds){
		relationService.deleteRelation(articleId, relationArticleIds);
	}
	
	@Override
	public void upRelation(Long articleId, Long[] relationArticleIds){
		relationService.upRelation(articleId, relationArticleIds);
	}

	@Override
	public void downRelation(Long articleId, Long[] relationArticleIds){
		relationService.downRelation(articleId, relationArticleIds);
	}
	
	@Override
	public List<Relation> findRelationByArticle(Long articleId){
		return relationService.findRelationByArticle(articleId);
	}

	@Override
	public List<Article> findPreReleaseArticles(Integer channelId, Integer limit) {
		return articlePublishService.findPreReleaseArticles(channelId, limit);
	}

	@Override
	public List<Article> findReleaseArticlePage(Integer channelId,	Integer page, Integer row, Boolean top) {
		return articlePublishService.findReleaseArticlePage(channelId, page, row, top);
	}

	@Override
	public Article getArticle(Long id) {
		return articlePublishService.getArticle(id);
	}

	@Override
	public int getArticleCount(Integer channelId) {
		return articleService.getArticleCount(channelId);
	}

	@Override
	public void publishArticle(Long id, String url) {
		articlePublishService.publishArticle(id, url);
	}

	@Override
	public void updatePreRelease(Integer channelId) {
		articlePublishService.updatePreRelease(channelId);
	}

	@Override
	public Long addReviewProcess(Integer channelId, ReviewProcess reviewProcess) {
		return reviewProcessService.addReviewProcess(channelId, reviewProcess);
	}

	@Override
	public void delReviewProcess(Long reviewProcessId) {
		reviewProcessService.delReviewProcess(reviewProcessId);
	}

	@Override
	public void downReviewProcess(Integer channelId, Long reviewProcessId) {
		reviewProcessService.downReviewProcess(channelId, reviewProcessId);
	}

	@Override
	public void upReivewProcess(Integer channelId, Long reviewProcessId) {
		reviewProcessService.upReivewProcess(channelId, reviewProcessId);
	}

	@Override
	public Long updReviewProcess(ReviewProcess reviewProcess) {
		return reviewProcessService.updReviewProcess(reviewProcess);
	}

	@Override
	public ReviewProcess findReviewProcess(Long reviewProcessId) {
		return reviewProcessService.findReviewProcess(reviewProcessId);
	}

	@Override
	public List<ReviewProcess> findReviewProcessByChannel(Integer channelId) {
		return reviewProcessService.findReviewProcessByChannel(channelId);
	}

	@Override
	public ReviewProcess findFirstReviewProcessByChannel(Integer channelId) {
		return reviewProcessService.findFirstReviewProcessByChannel(channelId);
	}

	@Override
	public Long findReviewProcessCountByChannel(Integer channelId) {
		return reviewProcessService.findReviewProcessCountByChannel(channelId);
	}
}
