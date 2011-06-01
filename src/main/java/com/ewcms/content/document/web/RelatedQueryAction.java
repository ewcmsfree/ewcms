/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Related;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

/**
 *
 * @author 吴智俊
 */
public class RelatedQueryAction extends QueryBaseAction {
	private static final long serialVersionUID = -6357351349673405169L;
	
	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	@Autowired
	private DocumentFacable documentFac;
	
	private Long articleId;
	
	public Long getArticleId() {
		return articleId;
	}
	
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
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
		List<Related> list = documentFac.findRelatedByArticle(getArticleId());
		List<Article> query = new ArrayList<Article>();
		for (Related related : list){
			query.add(related.getArticle());
		}
		DataGrid data = new DataGrid(query.size(), query);
		Struts2Util.renderJson(JSONUtil.toJSON(data, DATE_FORMAT));
		return NONE;
	}
}
