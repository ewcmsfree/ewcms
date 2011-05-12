/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.plugin.onlineoffice.article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.core.document.DocumentFacable;
import com.ewcms.core.document.model.Article;
import com.ewcms.core.document.model.ArticleRmc;
import com.ewcms.core.document.model.Content;
import com.ewcms.core.document.search.ExtractKeywordAndSummary;
import com.ewcms.core.document.search.util.StringUtil;
import com.ewcms.plugin.onlineoffice.OnlineOfficeFacable;
import com.ewcms.security.manage.service.UserServiceable;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.action.CrudBaseAction;

/**
 * 
 * @author 吴智俊
 */
@Controller("plugin.onlineoffice.article.index")
public class ArticleAction extends CrudBaseAction<Article, Integer> {

	private static final long serialVersionUID = -1589913315987891402L;
	private SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Autowired
	private DocumentFacable documentFac;
	@Autowired
	private OnlineOfficeFacable onlineOfficeFac;
	@Autowired
	private UserServiceable userService;

	private List<String> textAreaContent;
	
	private Integer workingBodyId;
	
	private Integer articleRmcId;
	
	private Integer channelId;
	
	private Date modified;
	
	private String published;

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
	
	public Integer getWorkingBodyId(){
		return workingBodyId;
	}
	
	public void setWorkingBodyId(Integer workingBodyId){
		this.workingBodyId = workingBodyId;
	}

	public Integer getArticleRmcId() {
		return articleRmcId;
	}

	public void setArticleRmcId(Integer articleRmcId) {
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
	protected void deleteOperator(Integer articleRmcId) {
		onlineOfficeFac.delArticleRmcToWorkingBody(getWorkingBodyId(), articleRmcId, getChannelId());
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
			return onlineOfficeFac.updArticleRmcToWorkingBody(getArticleRmcId(), vo, pub_date);
		} else {
			return onlineOfficeFac.addArticleRmcToWorkingBody(vo, getWorkingBodyId(), getChannelId(),pub_date);
		}
	}
	
	@Override
	public String save() throws Exception {
		try{
			Integer saveArticleRmcId = null;
	        if (getPK(vo) == null) {
	            operatorState = OperatorState.ADD;
	            saveArticleRmcId = saveOperator(vo, false);
	        } else {
	            operatorState = OperatorState.UPDATE;
	            saveArticleRmcId = saveOperator(vo, true);
	        }
	        if (saveArticleRmcId != null){
		        addActionMessage("数据保存成功!");
		        ArticleRmc articleRmc = documentFac.getArticleRmc(saveArticleRmcId);
		        setArticleVo(articleRmc.getArticle());
		        setModified(articleRmc.getModified());
		        if (articleRmc.getPublished() != null)
		        	setPublished(bartDateFormat.format(articleRmc.getPublished()));
		        setArticleRmcId(articleRmc.getId());
	        }else{
	        	addActionMessage("数据保存失败");
	        }
		}catch(Exception e){
	        addActionMessage("数据保存失败!");
		}
        return SUCCESS;
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
