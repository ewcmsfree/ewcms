/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.service;

import com.ewcms.content.vote.model.Questionnaire;

/**
 * @author wu_zhijun
 */
public interface QuestionnaireServiceable {
	
	public Long addQuestionnaire(Questionnaire questionnaire);
	
	public Long updQuestionnaire(Long questionnaireId, Questionnaire questionnaire);
	
	public void delQuestionnaire(Long questionnaireId);
	
	public Questionnaire findQuestionnaire(Long questionnaireId);
	
	public StringBuffer getQuestionnaireViewToHtml(Long questionnaireId, String servletContentName);
	
	public StringBuffer getQuestionnaireResultToHtml(Long questionnaireId, String servletContentName, String ipAddr, Boolean isView);
	
	public StringBuffer getQuestionnaireResultClientToHtml(Long questionnaireId, String servletContentName, String ipAddr);
}
