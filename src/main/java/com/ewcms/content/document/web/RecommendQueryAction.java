/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.content.document.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.content.document.model.Recommend;
import com.ewcms.util.DataGrid;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.action.QueryBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("recommend")
public class RecommendQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = -6357351349673405169L;
	
	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");

	@Autowired
	private DocumentFacable documentFac;

	private Integer articleRmcId;

	public Integer getArticleRmcId() {
		return articleRmcId;
	}

	public void setArticleRmcId(Integer articleRmcId) {
		this.articleRmcId = articleRmcId;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructNewQuery(Order order) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructQuery(Order order) {
		return null;
	}
	
	@Override
	public String query() {
		List<Recommend> list = documentFac.findRecommendByArticleRmcId(getArticleRmcId());
		List<ArticleRmc> query = new ArrayList<ArticleRmc>();
		for (Recommend recommend : list){
			query.add(recommend.getArticleRmc());
		}
		DataGrid data = new DataGrid(query.size(), query);
		Struts2Util.renderJson(JSONUtil.toJSON(data, DATE_FORMAT));
		return NONE;
	}
}
