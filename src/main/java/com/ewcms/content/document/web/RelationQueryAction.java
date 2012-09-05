/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;

/**
 *
 * @author 吴智俊
 */
public class RelationQueryAction extends QueryBaseAction {
	private static final long serialVersionUID = -6357351349673405169L;
	
	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	private Long articleId;
	
	public Long getArticleId() {
		return articleId;
	}
	
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	
    @Override
    protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
        String hql = "Select r.article From Article AS o Right Join o.relations AS r Where o.id=:articleId Order By r.sort ";
        String countHql = "Select Count(r.id) From Article AS o Right Join o.relations AS r WHERE o.id=:articleId ";
        
        hql += " Limit " + rows + " OffSet " + (rows * (page + 1));
        
        HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		query.setParameter("articleId", getArticleId());
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryResult();

    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
        String hql = "Select r.article From Article AS o Right Join o.relations AS r Where o.id=:articleId Order By r.sort ";
        String countHql = "Select Count(r.id) From Article AS o Right Join o.relations AS r Where o.id=:articleId ";
        
        HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		query.setParameter("articleId", getArticleId());
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryResult();
    }
}
