/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.vote.model.Subject.Status;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author 吴智俊
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
    	String status = getParameterValue(String.class, "status");
		if (isStringNotEmpty(status) && !status.equals("-1")){
			hql += " And s.status=:status";
			countHql += " And s.status=:status";
		}
		
		hql += " Order By s.sort Asc ";
		hql += " Limit " + rows + " OffSet " + (rows * (page + 1));
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		if (isStringNotEmpty(status) && !status.equals("-1")){
			query.setParameter("status", Status.valueOf(status));
		}
		query.setParameter("questionnaireId", getQuestionnaireId());
    	
    	return query.setRow(rows).setPage(page).queryResult();
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
