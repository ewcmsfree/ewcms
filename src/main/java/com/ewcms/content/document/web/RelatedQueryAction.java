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

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.content.document.model.Related;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

/**
 *
 * @author 吴智俊
 */
@Controller("related")
public class RelatedQueryAction extends QueryBaseAction {
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
	
    @Override
    protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
        return null;
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
        return null;
    }

	@Override
	public String query() {
		List<Related> list = documentFac.findRelatedByArticleRmcId(getArticleRmcId());
		List<ArticleRmc> query = new ArrayList<ArticleRmc>();
		for (Related related : list){
			query.add(related.getArticleRmc());
		}
		DataGrid data = new DataGrid(query.size(), query);
		Struts2Util.renderJson(JSONUtil.toJSON(data, DATE_FORMAT));
		return NONE;
	}
}
