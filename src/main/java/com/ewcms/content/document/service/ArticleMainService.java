/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.common.io.HtmlStringUtil;
import com.ewcms.content.document.BaseException;
import com.ewcms.content.document.dao.ArticleMainDAO;
import com.ewcms.content.document.dao.ReviewProcessDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.OperateTrack;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.content.document.model.ArticleType;
import com.ewcms.content.document.model.Content;
import com.ewcms.content.document.model.ReviewGroup;
import com.ewcms.content.document.model.ReviewProcess;
import com.ewcms.content.document.model.ReviewUser;
import com.ewcms.content.document.search.ExtractKeywordAndSummary;
import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.history.History;
import com.ewcms.plugin.crawler.util.CrawlerUserName;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.WebPublishable;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 
 * @author 吴智俊
 */
@Service
public class ArticleMainService implements ArticleMainServiceable {

	@Autowired
	private ArticleMainDAO articleMainDAO;
	@Autowired
	private ReviewProcessDAO reviewProcessDAO;
	@Autowired
	private OperateTrackServiceable operateTrackService;
	@Autowired
	private WebPublishable webPublish;
	@Autowired
	private ChannelDAO channelDAO;
	
	public void setWebPublish(WebPublishable webPublish) {
		this.webPublish = webPublish;
	}

	public void setArticleMainDAO(ArticleMainDAO articleMainDAO){
		this.articleMainDAO = articleMainDAO;
	}
	
	@Override
	public ArticleMain findArticleMainByArticleMainAndChannel(Long articleMainId, Integer channelId) {
		return articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
	}

	@Override
	public void delArticleMain(Long articleMainId, Integer channelId) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		if (isNotNull(articleMain.getReference()) && articleMain.getReference()){
			articleMain.setArticle(null);
		}
		articleMainDAO.remove(articleMain);
		operateTrackService.delOperateTrack(articleMainId);
	}

	@Override
	public void delArticleMainToRecycleBin(Long articleMainId, Integer channelId) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		if (isNotNull(articleMain.getReference()) && articleMain.getReference()){
			articleMain.setArticle(null);
			articleMainDAO.remove(articleMain);
		}else{
			Article article = articleMain.getArticle();
			Assert.notNull(article);
			
			operateTrackService.addOperateTrack(articleMainId, article.getStatusDescription(), "放入内容回收站。", "");
			
			article.setDelete(true);
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);
		}
	}

	@Override
	public void restoreArticleMain(Long articleMainId, Integer channelId) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		Article article = articleMain.getArticle();
		Assert.notNull(article);		
		article.setDelete(false);
		articleMain.setArticle(article);
		articleMainDAO.merge(articleMain);
		
		operateTrackService.addOperateTrack(articleMainId, article.getStatusDescription(), "从内容回收站还原。", "");
	}

	@Override
	public void submitReviewArticleMain(Long articleMainId, Integer channelId) throws BaseException {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		Article article = articleMain.getArticle();
		Assert.notNull(article);
		if (article.getStatus() == ArticleStatus.DRAFT || article.getStatus() == ArticleStatus.REEDIT) {
			ReviewProcess reviewProcess = reviewProcessDAO.findFirstReviewProcessByChannel(channelId);
			if (reviewProcess == null ){
				operateTrackService.addOperateTrack(articleMainId, article.getStatusDescription(), "发布版。", "");

				article.setStatus(ArticleStatus.PRERELEASE);
//				article.setReviewProcessId(null);
				article.setReviewProcess(null);
			}else{
				operateTrackService.addOperateTrack(articleMainId, article.getStatusDescription(),"已提交到【" + reviewProcess.getName() + "】进行审核。", "");

				article.setStatus(ArticleStatus.REVIEW);
//				article.setReviewProcessId(reviewProcess.getId());
				article.setReviewProcess(reviewProcess);
			}
			
			if (article.getPublished() == null) {
				article.setPublished(new Date(Calendar.getInstance().getTime().getTime()));
			}
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);
		}else{
			throw new BaseException("","只有在初稿或重新编辑状态下才能提交审核");
		}
	}

	@Override
	public Boolean copyArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId) {
		ArticleMain articleMain = null;
		Article article = null;
		Channel channel = channelDAO.get(source_channelId);
		Assert.notNull(channel);
		String source_channelName = channel.getName();
		for (Integer target_channelId : channelIds) {
			for (Long articleMainId : articleMainIds) {
				articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, source_channelId);
				if (isNull(articleMain)) continue;
				if (isNotNull(articleMain.getReference()) && articleMain.getReference()) continue;
				article = articleMain.getArticle();
				if (isNull(article)) continue;
				if (target_channelId != source_channelId) {
					Article target_article = new Article();

					target_article.setStatus(ArticleStatus.DRAFT);
					target_article.setPublished(null);
					if (article.getType() == ArticleType.TITLE){
						target_article.setUrl(article.getUrl());
					}else{
						target_article.setUrl(null);
					}
					target_article.setDelete(article.getDelete());

					List<Content> contents = article.getContents();
					List<Content> contents_target = new ArrayList<Content>();
					for (Content content : contents) {
						Content content_target = new Content();
						content_target.setDetail(content.getDetail());
						content_target.setPage(content.getPage());

						contents_target.add(content_target);
					}
					target_article.setContents(contents_target);

					target_article.setTitle(article.getTitle());
					target_article.setShortTitle(article.getShortTitle());
					target_article.setSubTitle(article.getSubTitle());
					target_article.setAuthor(article.getAuthor());
					target_article.setOrigin(article.getOrigin());
					target_article.setKeyword(article.getKeyword());
					target_article.setTag(article.getTag());
					target_article.setSummary(article.getSummary());
					target_article.setImage(article.getImage());
					target_article.setComment(article.getComment());
					target_article.setType(article.getType());
					target_article.setModified(new Date(Calendar.getInstance().getTime().getTime()));
					target_article.setInside(article.getInside());
					target_article.setOwner(EwcmsContextUtil.getUserName());

					
					ArticleMain articleMain_new = new ArticleMain();
					articleMain_new.setArticle(target_article);
					articleMain_new.setChannelId(target_channelId);
					
					articleMainDAO.persist(articleMain_new);
					articleMainDAO.flush(articleMain_new);
					
					String target_channelName = channelDAO.get(target_channelId).getName();
					operateTrackService.addOperateTrack(articleMainId, article.getStatusDescription(), "复制到『" + target_channelName + "』栏目。", "");
					operateTrackService.addOperateTrack(articleMain_new.getId(), target_article.getStatusDescription(),"从『" + source_channelName + "』栏目复制。", "");
				}
			}
		}
		return true;
	}

	@Override
	public Boolean moveArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId) {
		ArticleMain articleMain = null;
		Channel channel = channelDAO.get(source_channelId);
		Assert.notNull(channel);
		String source_channelName = channel.getName();
		for (Integer target_channelId : channelIds) {
			for (Long articleMainId : articleMainIds) {
				articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, source_channelId);
				if (isNull(articleMain)) continue;
				if (target_channelId != source_channelId) {
					Article article = articleMain.getArticle();
					
					operateTrackService.addOperateTrack(articleMainId, article.getStatusDescription(), "从『" + source_channelName + "』栏目移动。", "");
					
					articleMain.setArticle(article);
					articleMain.setChannelId(target_channelId);
					articleMainDAO.merge(articleMain);
				}
			}
		}
		return true;
	}

	@Override
	public List<ArticleMain> findArticleMainByChannel(Integer channelId) {
		return articleMainDAO.findArticleMainByChannel(channelId);
	}

	@Override
	public void pubArticleMainByChannel(Integer channelId, Boolean recursion) throws PublishException {
		if (isNotNull(channelId)) {
			webPublish.publishChannel(channelId, recursion);
		}
	}
	
	@Override
	public Boolean reviewArticleMainIsEffective(Long articleMainId, Integer channelId){
		if (isNull(articleMainId) && isNull(channelId)) return false;
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		if (isNull(articleMain)) return false;
		Article article = articleMain.getArticle();
		if (isNull(article)) return false;
		if (article.getStatus() == ArticleStatus.REVIEW){
			ReviewProcess rp = reviewProcessDAO.findReviewProcessByIdAndChannel(article.getReviewProcess().getId(), channelId);
			List<ReviewUser> reviewUsers = rp.getReviewUsers();
			List<ReviewGroup> reviewGroups = rp.getReviewGroups();
			if (reviewUsers.isEmpty() && reviewGroups.isEmpty()){
				return false;
			}
			String userName = EwcmsContextUtil.getUserName();
			if (isNull(userName) || userName.length() == 0) return false;
			for (ReviewUser reviewUser : reviewUsers){
				if (reviewUser.getUserName().equals(userName)){
					return true;
				}
			}
			Collection<String> grouptNames = EwcmsContextUtil.getGroupnames();
			if (isNull(grouptNames)) return false;
			for (ReviewGroup reviewGroup : reviewGroups){
				for (String groupName : grouptNames){
					if (reviewGroup.getGroupName().equals(groupName)){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void reviewArticleMain(Long articleMainId, Integer channelId, Integer review, String reason) {
		ArticleMain articleMain = null;
		Article article = null;
		Assert.notNull(articleMainId);
		articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		if (isNull(articleMain)) return;
		article = articleMain.getArticle();
		if (isNull(article)) return;
		if (article.getStatus() == ArticleStatus.REVIEW) {
			ReviewProcess rp = reviewProcessDAO.findReviewProcessByIdAndChannel(article.getReviewProcess().getId(), channelId);
			String currentStatus = article.getStatusDescription();
			String caption = "";
			if (review == 0){// 通过
				if (rp != null){
					caption = "【" + rp.getName() + "】<span style='color:blue;'>通过</span>";
					if (rp.getNextProcess() != null) {
//						Long nextReviewProcessId = rp.getNextProcess().getId();
//						article.setReviewProcessId(nextReviewProcessId);
						article.setReviewProcess(rp.getNextProcess());
						caption += "，已提交到【" + rp.getNextProcess().getName() + "】进行审核。";
					}else{
						article.setStatus(ArticleStatus.PRERELEASE);
						//article.setReviewProcessId(null);
						article.setReviewProcess(null);
						caption += "，可以进行发布。";
					}
				}else{
					article.setStatus(ArticleStatus.REVIEWBREAK);
					caption = "审核流程已改变，不能再进行审核。请联系频道管理员恢复到重新编辑状态。";
				}
			}else if (review == 1){// 不通过
				if (rp != null){
					caption = "【" + rp.getName() + "】<span style='color:red;'>不通过</span>";
					if (rp.getPrevProcess() != null){
//						Long parentId = rp.getPrevProcess().getId();
//						article.setReviewProcessId(parentId);
						article.setReviewProcess(rp.getPrevProcess());
						caption += "，已退回到【" + rp.getPrevProcess().getName() + "】进行重新审核。";
					}else{
						article.setStatus(ArticleStatus.REEDIT);
//						article.setReviewProcessId(null);
						article.setReviewProcess(null);
						caption += "，已退回到重新编辑状态。";
					}
				}else{
					article.setStatus(ArticleStatus.REVIEWBREAK);
					caption = "审核流程已改变，不能再进行审核。请联系频道管理员章恢复到重新编辑状态。";
				}
			}
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);
			
			operateTrackService.addOperateTrack(articleMainId, currentStatus, caption, reason);
		}
	}

	@Override
	public void moveArticleMainSort(Long articleMainId, Integer channelId, Long sort, Integer isInsert, Boolean isTop) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByChannelAndEqualSort(channelId, sort, isTop);
		String desc = "设置排序号为：" + sort + "。";
		if (articleMain == null){
			articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
			Assert.notNull(articleMain);
			articleMain.setSort(sort);
			articleMainDAO.merge(articleMain);
		}else{
			ArticleMain articleMain_new = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
			Assert.notNull(articleMain_new);
			articleMain_new.setSort(sort);
			if (isInsert == 0){//插入
				List<ArticleMain> articleMains = articleMainDAO.findArticleMainByChannelAndThanSort(channelId, sort, isTop);
				if (!articleMains.isEmpty()){
					for (ArticleMain articleMain_old : articleMains){
						if (articleMain_new.getId() == articleMain_old.getId()) continue;
						articleMain_old.setSort(articleMain_old.getSort() + 1);
						articleMainDAO.merge(articleMain_old);
					}
				}
				desc = "插入排序号为：" + sort + "。";
				articleMainDAO.merge(articleMain_new);
			}else if (isInsert == 1){//替换
				if (articleMain_new.getId() != articleMain.getId()){
					articleMain.setSort(null);
					articleMainDAO.merge(articleMain);
					desc = "替换排序号为：" + sort + "。";
					articleMainDAO.merge(articleMain_new);
				}
			}
		}
		operateTrackService.addOperateTrack(articleMainId, articleMain.getArticle().getStatusDescription(), desc, "");
	}

	@Override
	public Boolean findArticleMainByChannelAndEqualSort(Integer channelId, Long sort, Boolean isTop) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByChannelAndEqualSort(channelId, sort, isTop);
		if (articleMain == null) return false;
		return true;
	}

	@Override
	public void clearArticleMainSort(List<Long> articleMainIds, Integer channelId) {
		Assert.notNull(articleMainIds);
		for (Long articleMainId : articleMainIds){
			ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
			Assert.notNull(articleMain);
			if (articleMain.getSort() != null){
				articleMain.setSort(null);
				articleMainDAO.merge(articleMain);

				operateTrackService.addOperateTrack(articleMainId, articleMain.getArticle().getStatusDescription(), "清除排序号。", "");
			}
		}
	}

	@Override
	public void breakArticleMain(Long articleMainId, Integer channelId) throws BaseException {
		ArticleMain articleMain = articleMainDAO.get(articleMainId);
		Assert.notNull(articleMain);
		Article article = articleMain.getArticle();
		Assert.notNull(article);
		if (article.getStatus() == ArticleStatus.PRERELEASE || article.getStatus() == ArticleStatus.RELEASE || article.getStatus() == ArticleStatus.REVIEWBREAK){

			operateTrackService.addOperateTrack(articleMainId, article.getStatusDescription(), "已退回到重新编辑状态。", "");
			
			article.setStatus(ArticleStatus.REEDIT);
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);
		}else{
			throw new BaseException("","只有在审核中断、发布版、已发布版状态下才能退回");
		}
	}
	
	@Override
	public String getArticleOperateTrack(Long trackId){
		OperateTrack track = operateTrackService.findOperateTrackById(trackId);
		if (track == null) return "";
		return (track.getReason() == null)? "" : track.getReason();
	}

	@Override
	public void delArticleMainByCrawler(Integer channelId, String userName) {
		List<ArticleMain> articleMains = articleMainDAO.findArticleMainByChannelIdAndUserName(channelId, userName);
		for (ArticleMain articleMain : articleMains){
			if (articleMain.getArticle().getStatus() == ArticleStatus.RELEASE) continue;
			articleMainDAO.remove(articleMain);
		}
	}

	@Override
	public void topArticleMain(List<Long> articleMainIds, Boolean top) {
		Assert.notNull(articleMainIds);
		for (Long articleMainId : articleMainIds){
			ArticleMain articleMain = articleMainDAO.get(articleMainId);
			if (articleMain == null) continue;
			if (articleMain.getTop() != top){
				articleMain.setTop(top);
				articleMainDAO.merge(articleMain);

				String desc = "设为置顶。";
				if (!top) desc = "取消置顶。";
				operateTrackService.addOperateTrack(articleMainId, articleMain.getArticle().getStatusDescription(), desc, "");
			}
		}
	}
	
	@Override
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Long categoryId) {
		return articleMainDAO.findArticleIsEntityByArticleAndCategory(articleId, categoryId);
	}

	@Override
	@History(modelObjectIndex = 0)
	public Long addArticleMain(Article article, Integer channelId, Date published) {
		Assert.notNull(article);
		if (isNotNull(published)) {
			article.setPublished(published);
		}
		if (isNull(article.getOwner())){
			article.setOwner(EwcmsContextUtil.getUserName());
		}

		article.setModified(new Date(Calendar.getInstance().getTime().getTime()));
		if (article.getType() == ArticleType.TITLE){
			titleArticleContentNull(article);
		}else{
			keywordAndSummary(article);
		}
		
		ArticleMain articleMain = new ArticleMain();
		articleMain.setArticle(article);
		articleMain.setChannelId(channelId);
		
		articleMainDAO.persist(articleMain);
		articleMainDAO.flush(articleMain);
		
		operateTrackService.addOperateTrack(articleMain.getId(), article.getStatusDescription(), "创建。", "");
		
		return articleMain.getId();
	}
	
	@Override
	public Long addArticleMainByCrawler(Article article, String userName, Integer channelId){
		Assert.notNull(article);
		
		article.setOwner(userName);
		article.setModified(new Date(Calendar.getInstance().getTime().getTime()));
		//keywordAndSummary(article);

		ArticleMain articleMain = new ArticleMain();
		
		articleMain.setArticle(article);
		articleMain.setChannelId(channelId);
		
		articleMainDAO.persist(articleMain);
		articleMainDAO.flush(articleMain);
		
		operateTrackService.addOperateTrack(articleMain.getId(), article.getStatusDescription(), "通过采集器创建。", "", CrawlerUserName.USER_NAME, "网络爬虫");

		return articleMain.getId();
	}

	@Override
	@History(modelObjectIndex = 0)
	public Long updArticleMain(Article article, Long articleMainId, Integer channelId, Date published) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		
		if (isNotNull(published)) {
			article.setPublished(published);
		}

		if (article.getStatus() == ArticleStatus.RELEASE || article.getStatus() == ArticleStatus.PRERELEASE || article.getStatus() == ArticleStatus.REVIEW) {
			// throw new BaseException("error.document.article.notupdate","只能在初稿或重新编辑下才能修改");
		} else {
			Article article_old = articleMain.getArticle();
			Assert.notNull(article_old);
			if (article.getType() == ArticleType.GENERAL) {
				article.setUrl(null);
				keywordAndSummary(article);
			} else if (article.getType() == ArticleType.TITLE) {
				article.setKeyword("");
				article.setSummary("");
				if (article_old.getContents() != null && !article_old.getContents().isEmpty()) {
					article.setContents(article_old.getContents());
				} else {
					titleArticleContentNull(article);
				}
			}
			
			Date modNow = new Date(Calendar.getInstance().getTime().getTime());
			
			article.setOwner(article_old.getOwner());
			article.setModified(modNow);
			article.setStatus(article_old.getStatus());
			
			article.setRelations(article_old.getRelations());
			
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);

			operateTrackService.addOperateTrack(articleMainId, article.getStatusDescription(), "修改。", "");
		}
		return articleMain.getId();
	}
	
	private void keywordAndSummary(Article article){
		List<Content> contents = article.getContents();
		String title = article.getTitle();
		if (contents != null && !contents.isEmpty() && title != null && title.length()>0){
			String contentAll = "";
			for (Content content : contents){
				contentAll += content.getDetail();
			}
			if (article.getKeyword() == null || article.getKeyword().length() == 0){
				String keyword = HtmlStringUtil.join(ExtractKeywordAndSummary.getKeyword(title + " " + contentAll), " ");	
				article.setKeyword(keyword);
			}
			if (article.getSummary() == null || article.getSummary().length() == 0){
				String summary = ExtractKeywordAndSummary.getTextAbstract(title, contentAll);
				article.setSummary(summary);
			}
		}
	}
	
	private void titleArticleContentNull(Article article){
		Content content = new Content();
		content.setDetail("");
		content.setPage(1);
		List<Content> contents = new ArrayList<Content>();
		contents.add(content);
		article.setContents(contents);
	}
}
