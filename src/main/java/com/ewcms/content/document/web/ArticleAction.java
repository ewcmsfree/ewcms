/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.content.document.model.Content;
import com.ewcms.content.document.search.ExtractKeywordAndSummary;
import com.ewcms.content.document.search.util.StringUtil;
import com.ewcms.history.fac.HistoryModelFacable;
import com.ewcms.history.model.HistoryModel;
import com.ewcms.history.util.ByteToObject;
import com.ewcms.security.manage.service.UserServiceable;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author 吴智俊
 */
public class ArticleAction extends CrudBaseAction<Article, Integer> {

	private static final long serialVersionUID = 7275967705688396524L;
	private SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat modDataFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private DocumentFacable documentFac;
	@Autowired
	private HistoryModelFacable historyModelFac;
	@Autowired
	private UserServiceable userService;

	private List<String> textAreaContent;
	
	private Integer channelId;
	
	private Integer articleRmcId;
	
	private Date modified;
	
	private String published;
	
	private String state;

	public Article getArticleVo() {
		return super.getVo();
	}

	public void setArticleVo(Article articleVo) {
		super.setVo(articleVo);
	}

	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}
	
	public Integer getChannelId(){
		return channelId;
	}
	
	public void setChannelId(Integer channelId){
		this.channelId = channelId;
	}

	public Integer getArticleRmcId() {
		return articleRmcId;
	}

	public void setArticleRmcId(Integer articleRmcId) {
		this.articleRmcId = articleRmcId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
	protected void deleteOperator(Integer articleRmcId) {
		documentFac.delArticleRmcToRecycleBin(articleRmcId, EwcmsContextUtil.getUserName());
	}

	@Override
	protected Article getOperator(Integer articleRmcId) {
		ArticleRmc articleRmc = documentFac.getArticleRmc(articleRmcId);
		List<String> textContent = new ArrayList<String>();
		Article article = articleRmc.getArticle();
		List<Content> contents = article.getContents();
		for (Content content : contents){
			textContent.add(content.getDetail());
		}
		this.setTextAreaContent(textContent);
		setArticleRmcId(articleRmc.getId());
		setModified(articleRmc.getModified());
		setState(articleRmc.getStatus().toString());
		if (articleRmc.getPublished() != null)
			setPublished(bartDateFormat.format(articleRmc.getPublished()));
		return article;
	}

	@Override
	protected Integer getPK(Article vo) {
		return vo.getId();
	}

	public List<String> getTextAreaContent() {
		return textAreaContent;
	}

	public void setTextAreaContent(List<String> textAreaContent) {
		this.textAreaContent = textAreaContent;
	}

	@Override
	protected Integer saveOperator(Article vo, boolean isUpdate) {
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
		String author = userService.getUserRealName();
		vo.setAuthor(author);
		if (isUpdate) {
			return documentFac.updArticleRmc(getArticleRmcId(), vo, getChannelId(), pub_date);
		} else {
			return documentFac.addArticleRmc(vo, getChannelId(), pub_date);
		} 
	}
	
	@Override
	public String save() throws Exception {
		try{
			Integer saveArticleRmcId  = null;
	        if (getPK(vo) == null) {
	            operatorState = OperatorState.ADD;
	            saveArticleRmcId = saveOperator(vo, false);
	        } else {
	            operatorState = OperatorState.UPDATE;
	            saveArticleRmcId = saveOperator(vo, true);
	        }
	        if (saveArticleRmcId != null){
		        ArticleRmc articleRmc = documentFac.getArticleRmc(saveArticleRmcId);
		        setArticleVo(articleRmc.getArticle());
		        setModified(articleRmc.getModified());
		        setState(articleRmc.getStatus().toString());
		        if (articleRmc.getPublished() != null)
		        	setPublished(bartDateFormat.format(articleRmc.getPublished()));
		        setArticleRmcId(articleRmc.getId());
		        Map<String,Object> jsonMap = new HashMap<String,Object>();
		        jsonMap.put("articleVoId", getArticleVo().getId());
		        jsonMap.put("articleRmcId", getArticleRmcId());
		        jsonMap.put("state", getState());
		        jsonMap.put("modified", modDataFormat.format(articleRmc.getModified()));
		        Struts2Util.renderJson(JSONUtil.toJSON(jsonMap));
			}else{
	        	Struts2Util.renderJson(JSONUtil.toJSON("false"));
	        }
		}catch(Exception e){
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
			String keyword = StringUtil.join(ExtractKeywordAndSummary.getKeyword(getTitle() + " " + getContent()), " ");
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
	
	private Long historyId;
	
	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public String history(){
		if (getHistoryId() != null){
			HistoryModel historyModel = historyModelFac.findByHistoryModel(getHistoryId());
			Object obj = ByteToObject.conversion(historyModel.getModelObject());
			if (obj != null){
				Article article = (Article)obj;
				List<Content> contents = article.getContents();
				String[] details;
				if (contents != null && contents.size() > 0){
					details = new String[contents.size()];
					for (int i=0;i<contents.size();i++){
						details[i] = contents.get(i).getDetail();
					}
					Struts2Util.renderJson(JSONUtil.toJSON(details));
				}
			}
		}
		return NONE;
	}
	
	public void prerelease(){
		try{
			if (getArticleRmcId() != null){
				Struts2Util.renderText(documentFac.preReleaseArticleRmc(getArticleRmcId()).toString());
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	public void prereleases(){
		try{
			documentFac.preReleaseArticleRmcs(getSelections());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	public void pubArticle(){
		try{
			documentFac.pubChannel(getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}catch(AccessDeniedException e){
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	private List<Integer> selectChannelIds;

	public List<Integer> getSelectChannelIds() {
		return selectChannelIds;
	}

	public void setSelectChannelIds(List<Integer> selectChannelIds) {
		this.selectChannelIds = selectChannelIds;
	}
	
	public String copy(){
		if (getSelections() != null && getSelections().size() > 0 && getSelectChannelIds() != null && getSelectChannelIds().size() > 0){
			Struts2Util.renderText(documentFac.copyArticleRmcToChannel(getSelections(), getSelectChannelIds()).toString());
		}
		return NONE;
	}
	
	public String move(){
		if (getSelections() != null && getSelections().size() > 0 && getSelectChannelIds() != null && getSelectChannelIds().size() > 0){
			Struts2Util.renderText(documentFac.moveArticleRmcToChannel(getSelections(), getSelectChannelIds()).toString());
		}
		return NONE;
	}
}
