/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author 吴智俊
 */
public class ArticleMainAction extends CrudBaseAction<ArticleMain, Long> {

	private static final long serialVersionUID = 7275967705688396524L;

	@Autowired
	private DocumentFacable documentFac;
	
	private Integer channelId;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public ArticleMain getArticleMainVo() {
		return super.getVo();
	}

	public void setArticleMainVo(ArticleMain articleMainVo) {
		super.setVo(articleMainVo);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected ArticleMain createEmptyVo() {
		return null;
	}

	@Override
	protected void deleteOperator(Long articleMainId) {
		documentFac.delArticleMainToRecycleBin(articleMainId, getChannelId(), EwcmsContextUtil.getUserName());
	}

	@Override
	protected Long getPK(ArticleMain vo) {
		return vo.getId();
	}

	@Override
	protected Long saveOperator(ArticleMain vo, boolean isUpdate) {
		return null;
	}

	public void submitReviews() {
		try {
			documentFac.submitReviewArticleMains(getSelections(), getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}

	public void pubArticle() {
		try {
			documentFac.pubArticleMainByChannel(getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		} catch (Exception e) {
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

	public String copy() {
		if (getSelections() != null && getSelections().size() > 0 && getSelectChannelIds() != null && getSelectChannelIds().size() > 0) {
			Struts2Util.renderText(documentFac.copyArticleMainToChannel(getSelections(), getSelectChannelIds(), getChannelId()).toString());
		}
		return NONE;
	}

	public String move() {
		if (getSelections() != null && getSelections().size() > 0 && getSelectChannelIds() != null && getSelectChannelIds().size() > 0) {
			Struts2Util.renderText(documentFac.moveArticleMainToChannel(getSelections(), getSelectChannelIds(), getChannelId()).toString());
		}
		return NONE;
	}

	private Integer review;

	public Integer getReview() {
		return review;
	}

	public void setReview(Integer review) {
		this.review = review;
	}

	public void reviewArticle() {
		try {
			documentFac.reviewArticleMain(getSelections(), getChannelId(), getReview(), EwcmsContextUtil.getUserName());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	private Long sort;
	private Boolean isTop;
	private Integer isInsert;
	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public Integer getIsInsert() {
		return isInsert;
	}

	public void setIsInsert(Integer isInsert) {
		this.isInsert = isInsert;
	}

	public void sortArticle(){
		try{
			documentFac.moveArticleMainSort(getSelections().get(0), getChannelId(), getSort(), getIsInsert(), getIsTop());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	public void isSortArticle(){
		try{
			Boolean isSort = documentFac.findArticleMainByChannelAndEqualSort(getChannelId(), getSort(), getIsTop());
			Struts2Util.renderJson(JSONUtil.toJSON(isSort.toString()));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}

	@Override
	protected ArticleMain getOperator(Long pk) {
		return null;
	}
	
	public void clearSortArticle(){
		try{
			documentFac.clearArticleMainSort(getSelections(), getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
}
