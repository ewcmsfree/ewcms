/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online.web;

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
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.Content;
import com.ewcms.content.document.util.search.ExtractKeywordAndSummary;
import com.ewcms.plugin.online.OnlineFacable;
import com.ewcms.security.manage.service.UserServiceable;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;


/**
 * 
 * @author 吴智俊
 */
public class MatterArticleAction extends CrudBaseAction<Article, Long> {

	private static final long serialVersionUID = -1589913315987891402L;
	private SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat modDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private DocumentFacable documentFac;
	@Autowired
	private OnlineFacable onlineOfficeFac;
	@Autowired
	private UserServiceable userService;

	private List<String> textAreaContent;
	
	private Integer workingBodyId;
	
	private Long articleRmcId;
	
	private Integer channelId;
	
	private Date modified;
	
	private String published;

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
	
	public Integer getWorkingBodyId(){
		return workingBodyId;
	}
	
	public void setWorkingBodyId(Integer workingBodyId){
		this.workingBodyId = workingBodyId;
	}

	public Long getArticleRmcId() {
		return articleRmcId;
	}

	public void setArticleRmcId(Long articleRmcId) {
		this.articleRmcId = articleRmcId;
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

	public String query() throws Exception {
		return SUCCESS;
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
	protected void deleteOperator(Long articleRmcId) {
		onlineOfficeFac.delArticleRmcToWorkingBody(getWorkingBodyId(), articleRmcId, getChannelId());
	}

	@Override
	protected Article getOperator(Long articleRmcId) {
		ArticleMain articleMain = documentFac.findArticleMainByArticleMainAndChannel(articleRmcId, getChannelId());
		Article article = articleMain.getArticle();
		List<String> textContent = new ArrayList<String>();
		List<Content> contents = articleMain.getArticle().getContents();
		if (contents != null){
			for (Content content : contents) {
				textContent.add(content.getDetail());
			}
		}
		this.setTextAreaContent(textContent);
		//setState(articleMain.getArticle().getStatus().toString());
		setModified(articleMain.getArticle().getModified());
		//setArticleMainId(articleMain.getId());
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
		if (getTextAreaContent() != null && !getTextAreaContent().isEmpty()){
			List<Content> contentList = new ArrayList<Content>();
			Content contentVo = null;
			for (int i = 0; i < getTextAreaContent().size(); i++) {
				if (getTextAreaContent().get(i) != null
						&& getTextAreaContent().get(i).length() > 0) {
					contentVo = new Content();
					contentVo.setDetail(getTextAreaContent().get(i));
					contentVo.setPage(i + 1);
					contentList.add(contentVo);
				}
			}
			vo.setContents(contentList);
		}
		Date pub_date = null;
		try {
			pub_date = bartDateFormat.parse(getPublished());
		}catch (ParseException e) {
		}
		String author = userService.getCurrentUserInfo().getName();
		vo.setAuthor(author);
		if (isUpdate) {
			return onlineOfficeFac.updArticleRmcToWorkingBody(getArticleRmcId(), vo, pub_date); 
		} else {
			return onlineOfficeFac.addArticleRmcToWorkingBody(vo, getWorkingBodyId(), getChannelId(),pub_date);
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
			
				if (articleMain.getArticle().getPublished() != null)
					setPublished(bartDateFormat.format(articleMain.getArticle().getPublished()));
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("articleMainId", articleMain.getId());
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
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}

	public String keyword() {
		if (getTitle() != null && getTitle().length() > 0 && getContent() != null && getContent().length() > 0) {
			String keyword = HtmlStringUtil.join(ExtractKeywordAndSummary.getKeyword(getTitle() + " " + getContent()), " ");
			Struts2Util.renderText(keyword);
		}
		return NONE;
	}
	
	public String summary(){
		if (getTitle() != null && getTitle().length() > 0 && getContent() != null && getContent().length() > 0) {
			String summary = ExtractKeywordAndSummary.getTextAbstract(getTitle(),getContent());
			Struts2Util.renderText(summary);
		}
		return NONE;
	}
	
	private Integer articleId;
	private Integer version;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public String history(){
		if (getArticleId() != null && getVersion() != null){
		    //TODO contenthistory
//			String[] details = documentFac.getContentHistoryDetail(getArticleId(), getVersion());
			Struts2Util.renderJson("{}");
		}
		return NONE;
	}
	
	public void prerelease(){
		try{
			if (getArticleRmcId() != null && getChannelId() != null){
				Struts2Util.renderText(onlineOfficeFac.preReleaseArticleRmc(getArticleRmcId(), getChannelId()).toString());
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	private Integer moveWorkingBodyId;
	
	public Integer getMoveWorkingBodyId() {
		return moveWorkingBodyId;
	}

	public void setMoveWorkingBodyId(Integer moveWorkingBodyId) {
		this.moveWorkingBodyId = moveWorkingBodyId;
	}

	public void move(){
		try{
			if (getMoveWorkingBodyId() != null && getWorkingBodyId() != null){
				Struts2Util.renderText(onlineOfficeFac.moveArticleRmcToWorkingBody(getSelections(), getWorkingBodyId(), getMoveWorkingBodyId()).toString());
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
//	private List<Integer> selectChannelIds;
//
//	public List<Integer> getSelectChannelIds() {
//		return selectChannelIds;
//	}
//
//	public void setSelectChannelIds(List<Integer> selectChannelIds) {
//		this.selectChannelIds = selectChannelIds;
//	}
//	
//	public String copy(){
//		if (getSelections() != null && getSelections().size() > 0 && getSelectChannelIds() != null && getSelectChannelIds().size() > 0){
//			onlineOfficeFac.copyArticleRmcToLeaderChannel(getSelections(), getSelectChannelIds(), getChannelId());
//		}
//		return NONE;
//	}
//	
//	public String move(){
//		if (getSelections() != null && getSelections().size() > 0 && getSelectChannelIds() != null && getSelectChannelIds().size() > 0){
//			onlineOfficeFac.moveArticleRmcToLeaderChannel(getSelections(), getSelectChannelIds(), getChannelId());
//		}
//		return NONE;
//	}
}
