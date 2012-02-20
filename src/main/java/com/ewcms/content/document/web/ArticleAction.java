/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.ewcms.common.io.HtmlStringUtil;
import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Category;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.Content;
import com.ewcms.content.document.util.search.ExtractKeywordAndSummary;
import com.ewcms.content.history.fac.HistoryModelFacable;
import com.ewcms.content.history.model.HistoryModel;
import com.ewcms.content.history.util.ByteToObject;
import com.ewcms.security.manage.service.UserServiceable;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author 吴智俊
 */
public class ArticleAction extends CrudBaseAction<Article, Long> {

	private static final long serialVersionUID = 7275967705688396524L;

	private SimpleDateFormat modDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Autowired
	private DocumentFacable documentFac;
	@Autowired
	private HistoryModelFacable historyModelFac;
	@Autowired
	private UserServiceable userService;
	
	private Long articleMainId;
	private List<String> textAreaContent;
	private String state;
	private Integer channelId;
	private Date modified;
	private String published;
	private Long[] categories;
//	private List<Integer> selCategories = new ArrayList<Integer>();

	public Long getArticleMainId() {
		return articleMainId;
	}

	public void setArticleMainId(Long articleMainId) {
		this.articleMainId = articleMainId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public Long[] getCategories() {
		return categories;
	}

	public void setCategories(Long[] categories) {
		this.categories = categories;
	}

//	public List<Integer> getSelCategories() {
//		return selCategories;
//	}
//
//	public void setSelCategories(List<Integer> selCategories) {
//		this.selCategories = selCategories;
//	}

	public Article getArticleVo() {
		return super.getVo();
	}

	public void setArticleVo(Article articleVo) {
		super.setVo(articleVo);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Article createEmptyVo() {
		Article article = new Article();
		List<Content> contents = new ArrayList<Content>();
		Content content = new Content();
		content.setDetail("");
		contents.add(content);
		article.setContents(contents);
		return article;
	}

	@Override
	protected void deleteOperator(Long articleMainId) {
	}

	@Override
	protected Article getOperator(Long articleMainId) {
		ArticleMain articleMain = documentFac.findArticleMainByArticleMainAndChannel(articleMainId, getChannelId());
		Article article = articleMain.getArticle();
		List<String> textContent = new ArrayList<String>();
		List<Content> contents = articleMain.getArticle().getContents();
		if (contents != null){
			for (Content content : contents) {
				textContent.add(content.getDetail());
			}
		}
		this.setTextAreaContent(textContent);
		setState(articleMain.getArticle().getStatus().toString());
		setModified(articleMain.getArticle().getModified());
		setArticleMainId(articleMain.getId());
		if (articleMain.getArticle().getPublished() != null)
			setPublished(bartDateFormat.format(articleMain.getArticle().getPublished()));
		return article;
	}

	@Override
	protected Long getPK(Article vo) {
		return vo.getId();
	}

	public List<String> getTextAreaContent() {
		return textAreaContent;
	}

	public void setTextAreaContent(List<String> textAreaContent) {
		this.textAreaContent = textAreaContent;
	}

	@Override
	protected Long saveOperator(Article vo, boolean isUpdate) {
		if (getTextAreaContent() != null && !getTextAreaContent().isEmpty()) {
			List<Content> contentList = new ArrayList<Content>();
			Content contentVo = null;
			for (int i = 0; i < getTextAreaContent().size(); i++) {
				if (getTextAreaContent().get(i) != null && getTextAreaContent().get(i).length() > 0) {
					contentVo = new Content();
					contentVo.setDetail(getTextAreaContent().get(i));
					contentVo.setPage(i + 1);
					contentList.add(contentVo);
				}
			}
			vo.setContentTotal(contentList.size());
			vo.setContents(contentList);
		}
		if (isNotNull(getCategories())) {
			List<Category> articleCategories = new ArrayList<Category>();
			Category categoryVo = null;
			for (Long categoryId : categories) {
				categoryVo = documentFac.findCategory(categoryId);
				if (categoryVo == null)
					continue;
				articleCategories.add(categoryVo);
			}
			vo.setCategories(articleCategories);
		}

		Date pub_date = null;
		try {
			pub_date = bartDateFormat.parse(getPublished());
		} catch (ParseException e) {
		}
		String author = userService.getCurrentUserInfo().getName();
		vo.setAuthor(author);
		if (isUpdate) {
			return documentFac.updArticle(vo, getArticleMainId(), getChannelId(), pub_date);
		} else {
			return documentFac.addArticle(vo, getChannelId(), pub_date);
		}
	}

	@Override
	public String save() throws Exception {
		try {
			Long saveArticleMainId = null;
			if (getPK(vo) == null) {
				operatorState = OperatorState.ADD;
				saveArticleMainId = saveOperator(vo, false);
			} else {
				operatorState = OperatorState.UPDATE;
				saveArticleMainId = saveOperator(vo, true);
			}
			if (saveArticleMainId != null) {
				ArticleMain articleMain = documentFac.findArticleMainByArticleMainAndChannel(saveArticleMainId, getChannelId());
				setArticleVo(articleMain.getArticle());
				setState(articleMain.getArticle().getStatus().toString());
				if (articleMain.getArticle().getPublished() != null)
					setPublished(bartDateFormat.format(articleMain.getArticle().getPublished()));
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("articleMainId", articleMain.getId());
				jsonMap.put("state", getState());
				jsonMap.put("keyword", articleMain.getArticle().getKeyword());
				jsonMap.put("summary", articleMain.getArticle().getSummary());
				jsonMap.put("articleId", articleMain.getArticle().getId());
				if (articleMain.getArticle().getModified() != null) {
					jsonMap.put("modified", modDataFormat.format(articleMain.getArticle().getModified()));
				}
				Struts2Util.renderJson(JSONUtil.toJSON(jsonMap));
			} else {
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
		return NONE;
	}

	private String title;
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String keyword() {
		if (getTitle() != null && getTitle().length() > 0 && getContent() != null && getContent().length() > 0) {
			String keyword = HtmlStringUtil.join(ExtractKeywordAndSummary.getKeyword(getTitle() + " " + getContent()), " ");
			Struts2Util.renderText(keyword);
		}
		return NONE;
	}

	public String summary() {
		if (getTitle() != null && getTitle().length() > 0 && getContent() != null && getContent().length() > 0) {
			String summary = ExtractKeywordAndSummary.getTextAbstract(getTitle(), getContent());
			Struts2Util.renderText(summary);
		}
		return NONE;
	}

	private Long historyId;

	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public String history() {
		if (getHistoryId() != null) {
			HistoryModel historyModel = historyModelFac.findByHistoryModel(getHistoryId());
			Object obj = ByteToObject.conversion(historyModel.getModelObject());
			if (obj != null) {
				Article article = (Article) obj;
				List<Content> contents = article.getContents();
				String[] details;
				if (contents != null && contents.size() > 0) {
					details = new String[contents.size()];
					for (int i = 0; i < contents.size(); i++) {
						details[i] = contents.get(i).getDetail();
					}
					Struts2Util.renderJson(JSONUtil.toJSON(details));
				}
			}
		}
		return NONE;
	}
}
