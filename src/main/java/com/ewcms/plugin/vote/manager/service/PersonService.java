/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.plugin.vote.manager.dao.PersonDAO;
import com.ewcms.plugin.vote.manager.dao.QuestionnaireDAO;
import com.ewcms.plugin.vote.manager.dao.SubjectDAO;
import com.ewcms.plugin.vote.manager.dao.SubjectItemDAO;
import com.ewcms.plugin.vote.model.Person;
import com.ewcms.plugin.vote.model.Questionnaire;
import com.ewcms.plugin.vote.model.Record;
import com.ewcms.plugin.vote.model.Subject;
import com.ewcms.plugin.vote.model.SubjectItem;

/**
 * 投票人员信息Service
 * 
 * @author 吴智俊
 */
@Service
public class PersonService implements PersonServiceable {
	
	protected static final Logger logger = LoggerFactory.getLogger(PersonService.class);
	
	@Autowired
	private QuestionnaireDAO questionnaireDAO;
	@Autowired
	private PersonDAO personDAO;
	@Autowired
	private SubjectDAO subjectDAO;
	@Autowired
	private SubjectItemDAO subjectItemDAO;
	
	@Override
	public Long addPerson(Person person) {
		personDAO.persist(person);
		personDAO.flush(person);
		updVoteNumber(person);
		
		Questionnaire questionnaire = questionnaireDAO.get(person.getQuestionnaireId());
		questionnaire.setNumber(questionnaire.getNumber() + 1);
		questionnaireDAO.merge(questionnaire);
		
		return person.getId();
	}
	
	@Override
	public Boolean findPersonIsEntity(Long questionnaireId, String ip){
		return personDAO.findPersonIsEntity(questionnaireId, ip);
	}
	
	private void updVoteNumber(Person person){
		List<Record> records = person.getRecords();
		for (Record record : records){
			String subjectName = record.getSubjectName();
			String subjectValue = record.getSubjectValue();
			
			String[] recordNames = subjectName.split("_");
			if (recordNames.length == 2){
				if (recordNames[0].equals("Subject")){
					if (!recordNames[1].equals("") && StringUtils.isNumeric(recordNames[1])){
						Long subjectId = new Long(recordNames[1]);
						Subject subject = subjectDAO.get(subjectId);
						if (subject == null) continue;
						if (subject.getStatus() == Subject.Status.INPUT){
							SubjectItem subjectItem = subjectItemDAO.findSubjectItemBySubjectAndInputStatus(subjectId);
							if (subjectItem == null) continue;
							try{
								subjectItem.setVoteNumber(subjectItem.getVoteNumber() + 1);
							}catch(NullPointerException e){
								subjectItem.setVoteNumber(1L);
							}
							subjectItemDAO.merge(subjectItem);
						}else{
							if (!subjectValue.equals("") && StringUtils.isNumeric(subjectValue)){
								Long subjectItemId = new Long(subjectValue);
								SubjectItem subjectItem = subjectItemDAO.get(subjectItemId);
								try{
									subjectItem.setVoteNumber(subjectItem.getVoteNumber() + 1);
								}catch(NullPointerException e){
									subjectItem.setVoteNumber(1L);
								}
								subjectItemDAO.merge(subjectItem);
							}							
						}
					}
				}
			}else if (recordNames.length == 4){
				if (recordNames[0].equals("Subject") && recordNames[2].equals("Item")){
					if (!recordNames[3].equals("") && StringUtils.isNumeric(recordNames[3])){
						Long subjectItemId = new Long(recordNames[3]);
						SubjectItem subjectItem = subjectItemDAO.get(subjectItemId);
						try{
							subjectItem.setVoteNumber(subjectItem.getVoteNumber() + 1);
						}catch(NullPointerException e){
							subjectItem.setVoteNumber(1L);
						}
						subjectItemDAO.merge(subjectItem);
					}
				}
			}
		}
	}
	
	@Override
	public void delPerson(Long personId) {
		personDAO.removeByPK(personId);
	}

	@Override
	public List<String> getRecordToHtml(Long questionnaireId, Long personId){
		List<String> htmls = new ArrayList<String>();
		Questionnaire questionnaire = questionnaireDAO.get(questionnaireId);
		Assert.notNull(questionnaire);
		List<Subject> subjects = questionnaire.getSubjects();
		Assert.notNull(subjects);
		if (!subjects.isEmpty()){
			Long number = 1L;
			for (Subject subject : subjects){
				StringBuffer html = new StringBuffer();
				html.append(number + "." + subject.getTitle() + " : ");
				
				String subjectName = "Subject_" + subject.getId();
				
				List<Record> records = personDAO.findRecordBySubjectTitle(personId, subjectName);
				if (records == null || records.isEmpty()) continue;
				if (subject.getStatus() != Subject.Status.INPUT){
					for (Record record : records){
						String name = record.getSubjectName();
						String[] names = name.split("_");
						if (names.length == 2){
							if (!record.getSubjectValue().equals("") && StringUtils.isNumeric(record.getSubjectValue())){
								SubjectItem subjectItem = subjectItemDAO.get(new Long(record.getSubjectValue()));
								if (subjectItem == null) continue;
								html.append("【" + subjectItem.getTitle() + "】 ");
								String subjectItemName = subjectName + "_Item_" + subjectItem.getId();
								Record recordItem = personDAO.findRecordBySubjectItemTitle(personId, subjectItemName);
								if (recordItem == null) continue;
								html.append(recordItem.getSubjectValue() + " ");
							}else{
								html.append(record.getSubjectValue() + " ");
							}
						}else if (names.length == 4){
							html.append(record.getSubjectValue() + " ");
						}
					}
				}else{
					Record record = records.get(0);
					html.append(record.getSubjectValue());
				}
				htmls.add(html.toString());
				number++;
			}
		}
		return htmls;
	}

	@Override
	public Boolean findPersonIsEntityToDay(Long questionnaireId, String ip) {
		return personDAO.findPersonIsEntityToDay(questionnaireId, ip);
	}
}
