/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.vote.dao.SubjectDAO;
import com.ewcms.content.vote.dao.QuestionnaireDAO;
import com.ewcms.content.vote.model.Subject;
import com.ewcms.content.vote.model.Questionnaire;
import com.ewcms.content.vote.model.SubjectStatus;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class SubjectService implements SubjectServiceable {

	@Autowired
	private QuestionnaireDAO questionnaireDAO;
	@Autowired
	private SubjectDAO subjectDAO;
	
	@Override
	public Long addSubject(Long questionnaireId, Subject subject) {
		Assert.notNull(questionnaireId);
		Long maxSort = subjectDAO.findSubjectMaxSort(questionnaireId);
		Questionnaire questionnaire = questionnaireDAO.get(questionnaireId);
		Assert.notNull(questionnaire);
		List<Subject> subjects = questionnaire.getSubjects();
		subject.setSort(maxSort + 1);
		subjects.add(subject);
		questionnaire.setSubjects(subjects);
		questionnaireDAO.merge(questionnaire);
		questionnaireDAO.flush(questionnaire);
		return subject.getId();
	}

	@Override
	public void delSubject(Long questionnaireId, Long subjectId) {
		Subject subject = subjectDAO.get(subjectId);
		Assert.notNull(subject);
		Questionnaire questionnaire = questionnaireDAO.get(questionnaireId);
		Assert.notNull(questionnaire);
		List<Subject> subjects = questionnaire.getSubjects();
		subjects.remove(subject);
		questionnaire.setSubjects(subjects);
		questionnaireDAO.merge(questionnaire);
	}

	@Override
	public Subject findSubject(Long subjectId) {
		return subjectDAO.get(subjectId);
	}

	@Override
	public Long updSubject(Long questionnaireId, Subject subject) {
		Subject subject_old = subjectDAO.get(subject.getId());
		if (subject.getSubjectStatus() != SubjectStatus.INPUT){
			subject.setSubjectItems(subject_old.getSubjectItems());
		}
		subjectDAO.merge(subject);
		return subject.getId();
	}

	@Override
	public void downSubject(Long questionnaireId, Long subjectId) {
		Assert.notNull(questionnaireId);
		Subject subject = subjectDAO.get(subjectId);
		Assert.notNull(subject);
		Long sort = subject.getSort();
		if (sort == null){
			sort = subjectDAO.findSubjectMaxSort(questionnaireId);
			subject.setSort(sort + 1);
			subjectDAO.merge(subject);
		}else{
			Subject subject_next = subjectDAO.findSubjectBySort(questionnaireId, sort + 1);
			if (subject_next != null){
				subject_next.setSort(sort);
				subjectDAO.merge(subject_next);
				subject.setSort(sort + 1);
				subjectDAO.merge(subject);
			}
		}
	}

	@Override
	public void upSubject(Long questionnaireId, Long subjectId) {
		Assert.notNull(questionnaireId);
		Subject subject = subjectDAO.get(subjectId);
		Assert.notNull(subject);
		Long sort = subject.getSort();
		if (sort == null){
			sort = subjectDAO.findSubjectMaxSort(questionnaireId);
			subject.setSort(sort + 1);
			subjectDAO.merge(subject);
		}else{
			Subject subject_prv = subjectDAO.findSubjectBySort(questionnaireId, sort - 1);
			if (subject_prv != null){
				subject_prv.setSort(sort);
				subjectDAO.merge(subject_prv);
				subject.setSort(sort - 1);
				subjectDAO.merge(subject);
			}
		}
	}
}
