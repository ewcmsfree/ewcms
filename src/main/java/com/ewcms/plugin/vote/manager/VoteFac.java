/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.vote.manager.service.PersonServiceable;
import com.ewcms.plugin.vote.manager.service.QuestionnaireServiceable;
import com.ewcms.plugin.vote.manager.service.SubjectItemServiceable;
import com.ewcms.plugin.vote.manager.service.SubjectServiceable;
import com.ewcms.plugin.vote.model.Person;
import com.ewcms.plugin.vote.model.Questionnaire;
import com.ewcms.plugin.vote.model.Subject;
import com.ewcms.plugin.vote.model.SubjectItem;

/**
 * 调查投票Fac
 *  
 * @author 吴智俊
 */
@Service
public class VoteFac implements VoteFacable {

	@Autowired
	private QuestionnaireServiceable questionnaireService;
	@Autowired
	private SubjectServiceable subjectService;
	@Autowired
	private SubjectItemServiceable subjectItemService;
	@Autowired
	private PersonServiceable personService;

	@Override
	public Long addPerson(Person person) {
		return personService.addPerson(person);
	}

	@Override
	public Long addQuestionnaire(Questionnaire questionnaire) {
		return questionnaireService.addQuestionnaire(questionnaire);
	}

	@Override
	public Long addSubject(Long questionnaireId, Subject subject) {
		return subjectService.addSubject(questionnaireId, subject);
	}

	@Override
	public Long addSubjectItem(Long subjectId, SubjectItem subjectItem) {
		return subjectItemService.addSubjectItem(subjectId, subjectItem);
	}

	@Override
	public void delQuestionnaire(Long questionnaireId) {
		questionnaireService.delQuestionnaire(questionnaireId);
	}

	@Override
	public void delSubject(Long questionnaireId, Long subjectId) {
		subjectService.delSubject(questionnaireId, subjectId);
	}

	@Override
	public void delSubjectItem(Long subjectId, Long subjectItemId) {
		subjectItemService.delSubjectItem(subjectId, subjectItemId);
	}

	@Override
	public Boolean findPersonIsEntity(Long questionnaireId, String ip) {
		return personService.findPersonIsEntity(questionnaireId, ip);
	}

	@Override
	public Questionnaire findQuestionnaire(Long questionnaireId) {
		return questionnaireService.findQuestionnaire(questionnaireId);
	}

	@Override
	public Subject findSubject(Long subjectId) {
		return subjectService.findSubject(subjectId);
	}

	@Override
	public SubjectItem findSubjectItem(Long subjectItemId) {
		return subjectItemService.findSubjectItem(subjectItemId);
	}

	@Override
	public SubjectItem findSubjectItemBySubjectAndInputStatus(Long subjectId) {
		return subjectItemService.findSubjectItemBySubjectAndInputStatus(subjectId);
	}

	@Override
	public StringBuffer getQuestionnaireResultToHtml(Long questionnaireId, String servletContentName, String ipAddr, Boolean isView) {
		return questionnaireService.getQuestionnaireResultToHtml(questionnaireId, servletContentName, ipAddr, isView);
	}
	
	public StringBuffer getQuestionnaireResultClientToHtml(Long questionnaireId, String servletContentName, String ipAddr){
		return questionnaireService.getQuestionnaireResultClientToHtml(questionnaireId, servletContentName, ipAddr);
	}

	@Override
	public StringBuffer getQuestionnaireViewToHtml(Long questionnaireId, String servletContentName) {
		return questionnaireService.getQuestionnaireViewToHtml(questionnaireId, servletContentName);
	}

	@Override
	public Long updQuestionnaire(Questionnaire questionnaire) {
		return questionnaireService.updQuestionnaire(questionnaire);
	}

	@Override
	public Long updSubject(Long questionnaireId, Subject subject) {
		return subjectService.updSubject(questionnaireId, subject);
	}

	@Override
	public Long updSubjectItem(SubjectItem subjectItem) {
		return subjectItemService.updSubjectItem(subjectItem);
	}

	@Override
	public void downSubject(Long questionnaireId, Long subjectId) {
		subjectService.downSubject(questionnaireId, subjectId);
	}

	@Override
	public void downSubjectItem(Long subjectId, Long subjectItemId) {
		subjectItemService.downSubjectItem(subjectId, subjectItemId);
	}

	@Override
	public void upSubject(Long questionnaireId, Long subjectId) {
		subjectService.upSubject(questionnaireId, subjectId);
	}

	@Override
	public void upSubjectItem(Long subjectId, Long subjectItemId) {
		subjectItemService.upSubjectItem(subjectId, subjectItemId);
	}

	@Override
	public void delPerson(Long personId) {
		personService.delPerson(personId);
	}

	@Override
	public List<String> getRecordToHtml(Long questionnaireId, Long personId) {
		return personService.getRecordToHtml(questionnaireId, personId);
	}
}