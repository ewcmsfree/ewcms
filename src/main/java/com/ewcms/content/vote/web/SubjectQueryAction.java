/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author wu_zhijun
 *
 */
public class SubjectQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 5539246522711259327L;
	
	private Long questionnaireId;
	
	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
		String hql = "Select s From Questionnaire As q Left Join q.subjects AS s Where q.id=:questionnaireId ";
		String countHql = "Select count(s.id) From Questionnaire As q Left Join q.subjects AS s Where q.id=:questionnaireId ";
    	
		Long id = getParameterValue(Long.class, "id", "查询编号错误，应该是整型");
		if (isNotNull(id)){
			hql += " And s.id=:id ";
			countHql += " And s.id=:id";
		}
		String title = getParameterValue(String.class, "title", "");
		if (isStringNotEmpty(title)){
			hql += " And s.title Like :title";
			countHql += " And s.title Like :title";
		}
		
		hql += " Order By s.sort";

		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		query.setParameter("questionnaireId", getQuestionnaireId());
    	
    	return query.setRow(rows).setPage(page).queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String hql = "Select s From Questionnaire As q Left Join q.subjects AS s Where q.id=:questionnaireId And s.id In (:id) Order By s.sort ";
		String countHql = "Select count(s.id) From Questionnaire As q Left Join q.subjects AS s Where q.id=:questionnaireId And s.id In (:id) ";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);

		query.setParameter("id", getIds(Long.class));
		query.setParameter("questionnaireId", getQuestionnaireId());
    	
        return query.setRow(rows).setPage(page).queryResult();
	}

}
