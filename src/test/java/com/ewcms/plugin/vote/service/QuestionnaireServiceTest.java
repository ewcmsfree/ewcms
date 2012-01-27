/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ewcms.plugin.vote.manager.dao.PersonDAO;
import com.ewcms.plugin.vote.manager.dao.QuestionnaireDAO;
import com.ewcms.plugin.vote.manager.service.QuestionnaireService;
import com.ewcms.plugin.vote.model.Questionnaire;

/**
 * 
 * @author wu_zhijun
 *
 */
public class QuestionnaireServiceTest {
	private QuestionnaireService questionnaireService;
	private QuestionnaireDAO questionnaireDAO;
	private PersonDAO personDAO;
	
	@Before
	public void setUp() {
		questionnaireService = new QuestionnaireService();
		questionnaireDAO = mock(QuestionnaireDAO.class);
		personDAO = mock(PersonDAO.class);
		questionnaireService.setQuestionnaireDAO(questionnaireDAO);
		questionnaireService.setPersonDAO(personDAO);
	}
	
	@Test
	public void addQuestionnaire(){
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setId(1L);
		questionnaire.setChannelId(1);
		
		when(questionnaireDAO.findQuestionnaireMaxSort(questionnaire.getChannelId())).thenReturn(0L);
		questionnaireService.addQuestionnaire(questionnaire);
		ArgumentCaptor<Questionnaire> argument = ArgumentCaptor.forClass(Questionnaire.class);
		verify(questionnaireDAO).persist(argument.capture());
		
		assertNotNull(questionnaire.getStartTime());
		assertEquals(questionnaire.getSort().longValue(), 1L);
		assertEquals(questionnaire.getNumber().longValue(), 0L);
		assertFalse(questionnaire.getVerifiCode());
		assertFalse(questionnaire.getVoteEnd());
		assertEquals(questionnaire.getStatus(), Questionnaire.Status.VIEW);
	}
}
