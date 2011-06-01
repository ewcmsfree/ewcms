/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.document.dao.ArticleDAO;
import com.ewcms.content.document.dao.ArticleMainDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.content.document.model.ArticleType;
import com.ewcms.content.document.model.Content;
import com.ewcms.history.History;

/**
 * @author 吴智俊
 */
@Service
public class ArticleService implements ArticleServiceable {

	@Autowired
	private ArticleDAO articleDAO;
	@Autowired
	private ArticleMainDAO articleMainDAO;
	
	@Override
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Integer articleCategoryId) {
		return articleDAO.findArticleIsEntityByArticleAndCategory(articleId, articleCategoryId);
	}

	@Override
	@History(modelObjectIndex = 0)
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public Long addArticle(Article article, Integer channelId, Date published) {
		Assert.notNull(article);
		if (isNotNull(published)) {
			article.setPublished(published);
		}
		clearStyleSpace(article);

		article.setModified(new Date(Calendar.getInstance().getTime().getTime()));

		ArticleMain articleMain = new ArticleMain();
		articleMain.setArticle(article);
		articleMain.setChannelId(channelId);
		
		articleMainDAO.persist(articleMain);

		return articleMain.getId();
	}

	@Override
	@History(modelObjectIndex = 0)
	@PreAuthorize("hasRole('ROLE_ADMIN') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') " + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public Long updArticle(Article article, Long articleMainId, Integer channelId, Date published) {
		ArticleMain articleMain = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMainId, channelId);
		Assert.notNull(articleMain);
		
		if (isNotNull(published)) {
			article.setPublished(published);
		}

		clearStyleSpace(article);

		if (article.getStatus() == ArticleStatus.RELEASE || article.getStatus() == ArticleStatus.PRERELEASE || article.getStatus() == ArticleStatus.REVIEW) {
			// throw new
			// BaseException("error.document.article.notupdate","文章只能在初稿或重新编辑下才能修改");
		} else {
			ArticleMain articleMain_old = articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), channelId);
			Assert.notNull(articleMain_old);
			Article article_old = articleMain_old.getArticle();
			Assert.notNull(article_old);
			if (article_old.getType() == ArticleType.GENERAL) {
				// TODO 不做任务处理
			} else if (article_old.getType() == ArticleType.TITLE) {
				if (article_old.getContents() != null && !article_old.getContents().isEmpty()) {
					article.setContents(article_old.getContents());
				} else {
					article.setContents(new ArrayList<Content>());
				}
			}
			article.setModified(new Date(Calendar.getInstance().getTime().getTime()));
			article.setStatus(ArticleStatus.REEDIT);
			
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);
		}
		return articleMain.getId();
	}
	
	private void clearStyleSpace(Article article) {
		if (article.getTitleStyle() != null && article.getTitleStyle().length() > 0) {
			article.setTitleStyle(article.getTitleStyle().trim());
		}
		if (article.getShortTitleStyle() != null && article.getShortTitleStyle().length() > 0) {
			article.setShortTitleStyle(article.getShortTitleStyle().trim());
		}
		if (article.getSubTitleStyle() != null && article.getSubTitleStyle().length() > 0) {
			article.setSubTitleStyle(article.getSubTitleStyle().trim());
		}
	}

}
