/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.vote.VoteFacable;
import com.opensymphony.xwork2.ActionSupport;

public class RecordAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private VoteFacable voteFac;
	
	private Long personId;
	private Long questionnaireId;
	private List<String> results;

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}

	@Override
	public String execute() throws Exception {
		if (getQuestionnaireId() != null && getPersonId() != null){
			setResults(voteFac.getRecordToHtml(getQuestionnaireId(), getPersonId()));
		}
		return SUCCESS;
	}
}
