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
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.document.dao.ArticleMainDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.content.document.model.ArticleType;
import com.ewcms.content.document.model.Content;
import com.ewcms.generator.GeneratorServiceable;
import com.ewcms.generator.release.ReleaseException;
import com.ewcms.security.manage.service.UserServiceable;

/**
 * 
 * @author 吴智俊
 */
@Service
public class ArticleMainService implements ArticleMainServiceable {

	@Autowired
	private GeneratorServiceable generatorService;
	@Autowired
	private ArticleMainDAO articleMainDAO;
	@Autowired
	private UserServiceable userService;

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','READ') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public ArticleMain findArticleMainByArticleMainAndChannel(Long articleMainId, Integer channelId) {
		return articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void delArticleMain(Long articleMainId, Integer channelId) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		if (isNotNull(articleMain.getReference()) && articleMain.getReference()){
			articleMain.setArticle(null);
		}
		articleMainDAO.remove(articleMain);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void delArticleMainToRecycleBin(Long articleMainId, Integer channelId, String userName) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		if (isNotNull(articleMain.getReference()) && articleMain.getReference()){
			articleMain.setArticle(null);
			articleMainDAO.remove(articleMain);
		}else{
			Article article = articleMain.getArticle();
			Assert.notNull(article);
			article.setDeleteFlag(true);
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void restoreArticleMain(Long articleMainId, Integer channelId, String userName) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		Article article = articleMain.getArticle();
		Assert.notNull(article);
		article.setStatus(ArticleStatus.REEDIT);
		article.setDeleteFlag(false);
		articleMain.setArticle(article);
		articleMainDAO.merge(articleMain);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public Boolean submitReviewArticleMain(Long articleMainId, Integer channelId) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		Article article = articleMain.getArticle();
		Assert.notNull(article);
		if (article.getStatus() == ArticleStatus.DRAFT || article.getStatus() == ArticleStatus.REEDIT) {
			article.setStatus(ArticleStatus.REVIEW);
			if (article.getPublished() == null) {
				article.setPublished(new Date(Calendar.getInstance().getTime().getTime()));
			}
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);
			return true;
		}
		return false;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void submitReviewArticleMains(List<Long> articleMainIds, Integer channelId) {
		Assert.notNull(articleMainIds);
		for (Long articleMainId : articleMainIds) {
			submitReviewArticleMain(articleMainId, channelId);
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#target_channel,'WRITE') " + "or hasPermission(#target_channel,'PUBLISH') " + "or hasPermission(#chantarget_channelnel,'CREATE') " + "or hasPermission(#target_channel,'UPDATE') " + "or hasPermission(#target_channel,'DELETE') " + "or hasPermission(#target_channel,'ADMIN') ")
	public Boolean copyArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId) {
		ArticleMain articleMain = null;
		Article article = null;
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
					target_article.setDeleteFlag(article.getDeleteFlag());

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
					target_article.setTopFlag(article.getTopFlag());
					target_article.setCommentFlag(article.getCommentFlag());
					target_article.setType(article.getType());
					target_article.setModified(new Date(Calendar.getInstance().getTime().getTime()));

					ArticleMain articleMain_new = new ArticleMain();
					articleMain_new.setArticle(target_article);
					articleMain_new.setChannelId(target_channelId);
					articleMainDAO.persist(articleMain_new);

				}
			}
		}
		return true;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#target_channel,'WRITE') " + "or hasPermission(#target_channel,'PUBLISH') " + "or hasPermission(#target_channel,'CREATE') " + "or hasPermission(#target_channel,'UPDATE') " + "or hasPermission(#target_channel,'DELETE') " + "or hasPermission(#target_channel,'ADMIN') ")
	public Boolean moveArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId) {
		ArticleMain articleMain = null;
		for (Integer target_channelId : channelIds) {
			for (Long articleMainId : articleMainIds) {
				articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, source_channelId);
				if (isNull(articleMain)) continue;
				if (target_channelId != source_channelId) {
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
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void pubArticleMainByChannel(Integer channelId) throws ReleaseException {
		if (isNotNull(channelId)) {
			generatorService.generator(channelId);
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void reviewArticleMain(List<Long> articleMainIds, Integer channelId, Integer review, String audit) {
		ArticleMain articleMain = null;
		Article article = null;
		for (Long articleMainId : articleMainIds) {
			articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
			if (isNull(articleMain)) continue;
			article = articleMain.getArticle();
			if (isNull(article)) continue;
			if (review == 0 && article.getStatus() == ArticleStatus.REVIEW) {// 通过
				article.setStatus(ArticleStatus.PRERELEASE);
				article.setAudit(audit);
				article.setAuditReal(userService.getUserRealName());
			} else if (review == 1 && !(article.getStatus() == ArticleStatus.REEDIT || article.getStatus() == ArticleStatus.DRAFT)) {// 不通过
				article.setStatus(ArticleStatus.REEDIT);
				article.setAudit(audit);
				article.setAuditReal(userService.getUserRealName());
			}
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);
		}
	}

	@Override
	public void moveArticleMainSort(Long articleMainId, Integer channelId, Long sort, Integer isInsert, Boolean isTop) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByChannelAndEqualSort(channelId, sort, isTop);
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
				articleMainDAO.merge(articleMain_new);
			}else if (isInsert == 1){//替换
				if (articleMain_new.getId() != articleMain.getId()){
					articleMain.setSort(null);
					articleMainDAO.merge(articleMain);
					articleMainDAO.merge(articleMain_new);
				}
			}
		}
	}

	@Override
	public Boolean findArticleMainByChannelAndEqualSort(Integer channelId, Long sort, Boolean isTop) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByChannelAndEqualSort(channelId, sort, isTop);
		if (articleMain == null) return false;
		return true;
	}
	
	
}
