/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.Category;
import com.ewcms.content.document.model.OperateTrack;
import com.ewcms.content.document.model.Relation;
import com.ewcms.content.document.model.ReviewProcess;
import com.ewcms.content.document.service.ArticleMainServiceable;
import com.ewcms.content.document.service.CategoryServiceable;
import com.ewcms.content.document.service.OperateTrackServiceable;
import com.ewcms.content.document.service.RelationServiceable;
import com.ewcms.content.document.service.ReviewProcessServiceable;
import com.ewcms.core.site.model.Channel;
import com.ewcms.publication.PublishException;

/**
 * 
 * @author 吴智俊
 */
@Service
public class DocumentFac implements DocumentFacable {

	@Autowired
	private CategoryServiceable categoryService;
	@Autowired
	private ArticleMainServiceable articleMainService;
	@Autowired
	private RelationServiceable relationService;
	@Autowired
	private ReviewProcessServiceable reviewProcessService;
	@Autowired
	private OperateTrackServiceable operateTrackService;

	@Override
	public Long addCategory(Category category) {
		return categoryService.addCategory(category);
	}

	@Override
	public Long updCategory(Category category) {
		return categoryService.updCategory(category);
	}

	@Override
	public void delCategory(Long categoryId) {
		categoryService.delCategory(categoryId);
	}

	@Override
	public Category findCategory(Long categoryId) {
		return categoryService.findCategory(categoryId);
	}

	@Override
	public List<Category> findCategoryAll() {
		return categoryService.findCategoryAll();
	}

	@Override
	public ArticleMain findArticleMainByArticleMainAndChannel(Long articleMainId, Integer channelId) {
		return articleMainService.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void delArticleMain(Long articleMainId, Integer channelId) {
		articleMainService.delArticleMain(articleMainId, channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void delArticleMainToRecycleBin(Long articleMainId, Integer channelId) {
		articleMainService.delArticleMainToRecycleBin(articleMainId, channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void restoreArticleMain(Long articleMainId, Integer channelId) {
		articleMainService.restoreArticleMain(articleMainId, channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void submitReviewArticleMain(Long articleMainId, Integer channelId) throws BaseException {
		articleMainService.submitReviewArticleMain(articleMainId, channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#source_channelId,'com.ewcms.core.site.model.Channel','WRITE') "
			+ "or hasPermission(#source_channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public Boolean copyArticleMainToChannel(List<Long> articleMainIds, List<Integer> target_channels, Integer source_channelId) {
		return articleMainService.copyArticleMainToChannel(articleMainIds, target_channels, source_channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#source_channelId,'com.ewcms.core.site.model.Channel','WRITE') "
			+ "or hasPermission(#source_channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public Boolean moveArticleMainToChannel(List<Long> articleMainIds, List<Integer> target_channels, Integer source_channelId) {
		return articleMainService.moveArticleMainToChannel(articleMainIds, target_channels, source_channelId);
	}

	@Override
	public List<ArticleMain> findArticleMainByChannel(Integer channelId) {
		return articleMainService.findArticleMainByChannel(channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void pubArticleMainByChannel(Integer channelId, Boolean recursion) throws PublishException {
		articleMainService.pubArticleMainByChannel(channelId, recursion);
	}

	@Override
	public Boolean reviewArticleMainIsEffective(Long articleMainId, Integer channelId){
		return articleMainService.reviewArticleMainIsEffective(articleMainId, channelId);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void reviewArticleMain(Long articleMainId, Integer channelId, Integer review, String reason) {
		articleMainService.reviewArticleMain(articleMainId, channelId, review, reason);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void moveArticleMainSort(Long articleMainId, Integer channelId, Long sort, Integer isInsert, Boolean isTop) {
		articleMainService.moveArticleMainSort(articleMainId, channelId, sort,
				isInsert, isTop);
	}

	public Boolean findArticleMainByChannelAndEqualSort(Integer channelId,	Long sort, Boolean isTop) {
		return articleMainService.findArticleMainByChannelAndEqualSort(channelId, sort, isTop);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void clearArticleMainSort(List<Long> articleMainIds,	Integer channelId) {
		articleMainService.clearArticleMainSort(articleMainIds, channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void breakArticleMain(Long articleMianId, Integer channelId) throws BaseException {
		articleMainService.breakArticleMain(articleMianId, channelId);
	}

	@Override
	public String getArticleOperateTrack(Long trackId){
		return articleMainService.getArticleOperateTrack(trackId);
	}
	
	@Override
	public Map<Channel, Long> findBeApprovalArticleMain(String userName){
		return articleMainService.findBeApprovalArticleMain(userName);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public Long addArticle(Article article, Integer channelId, Date published) {
		return articleMainService.addArticleMain(article, channelId, published);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public Long updArticle(Article article, Long articleMainId,	Integer channelId, Date published) {
		return articleMainService.updArticleMain(article, articleMainId, channelId, published);
	}

	@Override
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Long categoryId) {
		return articleMainService.findArticleIsEntityByArticleAndCategory(articleId, categoryId);
	}

	@Override
	public void saveRelation(Long articleId, Long[] relationArticleIds) {
		relationService.saveRelation(articleId, relationArticleIds);
	}

	@Override
	public void deleteRelation(Long articleId, Long[] relationArticleIds) {
		relationService.deleteRelation(articleId, relationArticleIds);
	}

	@Override
	public void upRelation(Long articleId, Long[] relationArticleIds) {
		relationService.upRelation(articleId, relationArticleIds);
	}

	@Override
	public void downRelation(Long articleId, Long[] relationArticleIds) {
		relationService.downRelation(articleId, relationArticleIds);
	}

	@Override
	public List<Relation> findRelationByArticle(Long articleId) {
		return relationService.findRelationByArticle(articleId);
	}

	@Override
	public Long addReviewProcess(Integer channelId, ReviewProcess reviewProcess, List<String> userNames, List<String> groupNames) throws BaseException {
		return reviewProcessService.addReviewProcess(channelId, reviewProcess, userNames, groupNames);
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
	public Long updReviewProcess(ReviewProcess reviewProcess, List<String> userNames, List<String> groupNames) throws BaseException {
		return reviewProcessService.updReviewProcess(reviewProcess, userNames, groupNames);
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

	@Override
	public Boolean findReviewUserIsEntityByProcessIdAndUserName(Long reviewProcessId, String userName) {
		return reviewProcessService.findReviewUserIsEntityByProcessIdAndUserName(reviewProcessId, userName);
	}

	@Override
	public Boolean findReviewGroupIsEntityByProcessIdAndUserName(Long reviewProcessId, String goupName) {
		return reviewProcessService.findReviewGroupIsEntityByProcessIdAndUserName(reviewProcessId, goupName);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void topArticleMain(List<Long> articleMainIds, Boolean top, Integer channelId) {
		articleMainService.topArticleMain(articleMainIds, top);
	}

	@Override
	public List<OperateTrack> findOperateTrackByArticleMainId(Long articleMainId) {
		return operateTrackService.findOperateTrackByArticleMainId(articleMainId);
	}

	@Override
	public Map<Integer, Long> findCreateArticleFcfChart(Integer year, Integer siteId) {
		return articleMainService.findCreateArticleFcfChart(year, siteId);
	}

	@Override
	public Map<Integer, Long> findReleaseArticleFcfChart(Integer year, Integer siteId) {
		return articleMainService.findReleaseArticleFcfChart(year, siteId);
	}

	@Override
	public Map<String, Long> findReleaseArticlePersonFcfChart(Integer year, Integer siteId) {
		return articleMainService.findReleaseArticlePersonFcfChart(year, siteId);
	}

	@Override
	public ArticleMain findArticleMainById(Long articleMainId) {
		return articleMainService.findArticleMainById(articleMainId);
	}

	@Override
	public void referArticleMain(Integer channelId, Long[] articleMainIds) {
		articleMainService.referArticleMain(channelId, articleMainIds);
	}

	@Override
	public void removeArticleMain(Long[] articleMainIds) {
		articleMainService.removeArticleMain(articleMainIds);
	}
}
