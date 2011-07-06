/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote;

import java.util.List;

import com.ewcms.content.vote.model.Subject;
import com.ewcms.content.vote.model.SubjectItem;
import com.ewcms.content.vote.model.Questionnaire;
import com.ewcms.content.vote.model.Person;

/**
 * @author wu_zhijun
 */
public interface VoteFacable {
	
	public Long addPerson(Person person);
	
	public Boolean findPersonIsEntity(Long questionnaireId, String ip);

	public Long addQuestionnaire(Questionnaire questionnaire);
	
	public Long updQuestionnaire(Long questionnaireId, Questionnaire questionnaire);
	
	public void delQuestionnaire(Long questionnaireId);
	
	public Questionnaire findQuestionnaire(Long questionnaireId);
	
	public StringBuffer getQuestionnaireViewToHtml(Long questionnaireId, String servletContentName);
	
	public StringBuffer getQuestionnaireResultClientToHtml(Long questionnaireId, String servletContentName, String ipAddr);
	
	public StringBuffer getQuestionnaireResultToHtml(Long questionnaireId, String servletContentName, String ipAddr, Boolean isView);

	public Long addSubjectItem(Long subjectId, SubjectItem subjectItem);
	
	public Long updSubjectItem(SubjectItem subjectItem);
	
	public void delSubjectItem(Long subjectId, Long subjectItemId);
	
	public SubjectItem findSubjectItem(Long subjectItemId);
	
	public SubjectItem findSubjectItemBySubject(Long subjectId);
	
	public Long addSubject(Long questionnaireId, Subject subject);
	
	public Long updSubject(Long questionnaireId, Subject subject);
	
	public void delSubject(Long questionnaireId, Long subjectId);
	
	public Subject findSubject(Long subjectId);
	
	public void upSubject(Long questionnaireId, Long subjectId);
	
	public void downSubject(Long questionnaireId, Long subjectId);
	
	public void upSubjectItem(Long subjectId, Long subjectItemId);
	
	public void downSubjectItem(Long subjectId, Long subjectItemId);
	
	public void delPerson(Long personId);
	
	public List<String> getRecordToHtml(Long questionnaireId, Long personId);
}
