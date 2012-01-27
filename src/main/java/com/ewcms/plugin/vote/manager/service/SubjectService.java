/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.plugin.vote.manager.dao.QuestionnaireDAO;
import com.ewcms.plugin.vote.manager.dao.SubjectDAO;
import com.ewcms.plugin.vote.model.Questionnaire;
import com.ewcms.plugin.vote.model.Subject;

/**
 * 问卷调查主题Service
 * 
 * @author 吴智俊
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
		
		subjectDAO.persist(subject);
		subjectDAO.flush(subject);
		
		subjects.add(subject);
		questionnaire.setSubjects(subjects);
		
		questionnaireDAO.merge(questionnaire);
		
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
		if (subject.getStatus() != Subject.Status.INPUT){
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
