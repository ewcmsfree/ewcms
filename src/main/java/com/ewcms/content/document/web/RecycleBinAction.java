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
import com.ewcms.web.util.Struts2Util;

/**
 *
 * @author 吴智俊
 */
public class RecycleBinAction extends CrudBaseAction<ArticleMain, Long> {

	private static final long serialVersionUID = 2667146571103157163L;

	@Autowired
	private DocumentFacable documentFac;
	
	private Integer channelId;
	
	public List<Long> getSelections() {
        return super.getOperatorPK();
    }
	
	public void setSelections(List<Long> selections) {
        super.setOperatorPK(selections);
    }

    public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String query() throws Exception {
        return SUCCESS;
    }
	
	@Override
	protected ArticleMain createEmptyVo() {
		return null;
	}

	@Override
	protected void deleteOperator(Long pk) {
		documentFac.delArticleMain(pk, getChannelId());
	}

	@Override
	protected ArticleMain getOperator(Long pk) {
		return documentFac.findArticleMainByArticleMainAndChannel(pk, getChannelId());
	}

	@Override
	protected Long getPK(ArticleMain vo) {
		return vo.getId();
	}

	@Override
	protected Long saveOperator(ArticleMain vo, boolean isUpdate) {
		return null;
	}
	
	public String restore(){
        for (Long pk : operatorPK) {
        	documentFac.restoreArticleMain(pk, getChannelId());
        }
        Struts2Util.renderText(SUCCESS);
        return NONE;
	}

}
