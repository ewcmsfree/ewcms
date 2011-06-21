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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.common.io.HtmlStringUtil;
import com.ewcms.content.document.dao.ArticleDAO;
import com.ewcms.content.document.dao.ArticleMainDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.content.document.model.ArticleType;
import com.ewcms.content.document.model.Content;
import com.ewcms.content.document.search.ExtractKeywordAndSummary;
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
	
	public void setArticleDAO(ArticleDAO articleDAO){
		this.articleDAO = articleDAO;
	}
	
	public void setArticleMainDAO(ArticleMainDAO articleMainDAO){
		this.articleMainDAO = articleMainDAO;
	}
	
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

		if (article.getStatus() == ArticleStatus.RELEASE || article.getStatus() == ArticleStatus.PRERELEASE || article.getStatus() == ArticleStatus.REVIEW) {
			// throw new BaseException("error.document.article.notupdate","文章只能在初稿或重新编辑下才能修改");
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
			article.setModified(new Date(Calendar.getInstance().getTime().getTime()));
			article.setStatus(article_old.getStatus());
			
			articleMain.setArticle(article);
			articleMainDAO.merge(articleMain);
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
