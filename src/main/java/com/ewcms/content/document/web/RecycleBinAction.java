/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.content.document.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.web.action.CrudBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;
import com.ewcms.web.util.Struts2Util;

/**
 *
 * @author 吴智俊
 */
public class RecycleBinAction extends CrudBaseAction<ArticleRmc, Integer> {

	private static final long serialVersionUID = 2667146571103157163L;

	@Autowired
	private DocumentFacable documentFac;
	
	public List<Integer> getSelections() {
        return super.getOperatorPK();
    }
	
	public void setSelections(List<Integer> selections) {
        super.setOperatorPK(selections);
    }

    public String query() throws Exception {
        return SUCCESS;
    }
	
	@Override
	protected ArticleRmc createEmptyVo() {
		return null;
	}

	@Override
	protected void deleteOperator(Integer pk) {
		documentFac.delArticleRmc(pk);
	}

	@Override
	protected ArticleRmc getOperator(Integer pk) {
		return documentFac.getArticleRmc(pk);
	}

	@Override
	protected Integer getPK(ArticleRmc vo) {
		return vo.getId();
	}

	@Override
	protected Integer saveOperator(ArticleRmc vo, boolean isUpdate) {
		return null;
	}
	
	public String restore(){
        for (Integer pk : operatorPK) {
        	documentFac.restoreArticleRmc(pk, EwcmsContextUtil.getUserName());
        }
        
        Struts2Util.renderText(SUCCESS);
        return NONE;
	}

}
