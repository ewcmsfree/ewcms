/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * @author 吴智俊
 */
public class ShareAction extends CrudBaseAction<ArticleMain, Long> {

	private static final long serialVersionUID = 3929684985209263482L;

	@Autowired
	private DocumentFacable documentFac;

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
	protected Long getPK(ArticleMain vo) {
		return vo.getId();
	}

	@Override
	protected ArticleMain getOperator(Long pk) {
		return documentFac.findArticleMainById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
	}

	@Override
	protected Long saveOperator(ArticleMain vo, boolean isUpdate) {
		return null;
	}

	@Override
	protected ArticleMain createEmptyVo() {
		return null;
	}
	
	private List<Integer> selectChannelIds;

	public List<Integer> getSelectChannelIds() {
		return selectChannelIds;
	}

	public void setSelectChannelIds(List<Integer> selectChannelIds) {
		this.selectChannelIds = selectChannelIds;
	}

	public void copy() {
		try{
			if (getSelections() != null && getSelections().size() > 0 && getSelectChannelIds() != null && getSelectChannelIds().size() > 0) {
				documentFac.copyArticleMainFromShare(getSelections(), getSelectChannelIds());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
}