/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.vote.manager.VoteFacable;
import com.ewcms.plugin.vote.model.Person;
import com.ewcms.web.CrudBaseAction;

/**
 * 
 * @author 吴智俊
 *
 */
public class PersonAction extends CrudBaseAction<Person, Long> {

	private static final long serialVersionUID = 6841903567671820584L;
	
	@Autowired
	private VoteFacable voteFac;
	
	private Long questionnaireId;

	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	@Override
	protected Person createEmptyVo() {
		return null;
	}

	@Override
	protected void deleteOperator(Long pk) {
		voteFac.delPerson(pk);
	}

	@Override
	protected Person getOperator(Long pk) {
		return null;
	}

	@Override
	protected Long getPK(Person vo) {
		return null;
	}

	@Override
	protected Long saveOperator(Person vo, boolean isUpdate) {
		return null;
	}
	
	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}
}
