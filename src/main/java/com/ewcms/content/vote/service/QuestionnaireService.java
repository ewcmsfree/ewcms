/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.vote.dao.PersonDAO;
import com.ewcms.content.vote.dao.QuestionnaireDAO;
import com.ewcms.content.vote.model.QuestionnaireStatus;
import com.ewcms.content.vote.model.Subject;
import com.ewcms.content.vote.model.SubjectItem;
import com.ewcms.content.vote.model.SubjectItemStatus;
import com.ewcms.content.vote.model.Questionnaire;
import com.ewcms.content.vote.model.SubjectStatus;

/**
 * 问卷调查主体Service
 * 
 * @author 吴智俊
 */
@Service
public class QuestionnaireService implements QuestionnaireServiceable {
	
	protected static final Logger logger = LoggerFactory.getLogger(QuestionnaireService.class);

	@Autowired
	private QuestionnaireDAO questionnaireDAO;
	@Autowired
	private PersonDAO personDAO;
	
	public void setQuestionnaireDAO(QuestionnaireDAO questionnaireDAO){
		this.questionnaireDAO = questionnaireDAO;
	}
	
	public void setPersonDAO(PersonDAO personDAO){
		this.personDAO = personDAO;
	}
	
	@Override
	public Long addQuestionnaire(Questionnaire questionnaire) {
		Integer channelId = questionnaire.getChannelId();
		Assert.notNull(channelId);
		if (questionnaire.getStartTime() == null){
			questionnaire.setStartTime(new Date(Calendar.getInstance().getTime().getTime()));
		}
		Long maxSort = questionnaireDAO.findQuestionnaireMaxSort(channelId);
		questionnaire.setSort(maxSort + 1);
		questionnaireDAO.persist(questionnaire);
		return questionnaire.getId();
	}

	@Override
	public void delQuestionnaire(Long questionnaireId) {
		questionnaireDAO.removeByPK(questionnaireId);
	}

	@Override
	public Questionnaire findQuestionnaire(Long questionnaireId) {
		return questionnaireDAO.get(questionnaireId);
	}

	@Override
	public Long updQuestionnaire(Questionnaire questionnaire) {
		Long questionnaireId = questionnaire.getId();
		Assert.notNull(questionnaireId);
		Questionnaire questionnaire_old = questionnaireDAO.get(questionnaireId);
		Assert.notNull(questionnaire_old);
		questionnaire.setSubjects(questionnaire_old.getSubjects());
		questionnaireDAO.merge(questionnaire);
		return questionnaire.getId();
	}
	
	@Override
	public StringBuffer getQuestionnaireViewToHtml(Long questionnaireId, String servletContentName){
		try{
			Questionnaire questionnaire = questionnaireDAO.get(questionnaireId);
			if (questionnaire == null) return new StringBuffer("<p>没有问卷调查</p>");
			
			StringBuffer view = new StringBuffer();
			
			view.append("<div id='voteView' name='voteView'>调查：" + questionnaire.getTitle() + "\n");
			view.append("<link rel='stylesheet' type='text/css' href='/" + servletContentName + "/source/page/vote/vote.css'/>\n");
			view.append("<script language='javascript' src='/" + servletContentName + "/source/page/vote/vote.js'></script>\n");
			
			if (questionnaire.getVoteFlag() || (questionnaire.getEndTime() != null && questionnaire.getEndTime().getTime() < Calendar.getInstance().getTime().getTime())){
				view.append("<p>对不起，此调查已结束，不再接受投票</p>");
			}else{
				List<Subject> subjects = questionnaire.getSubjects();
				if (subjects == null || subjects.isEmpty())	return new StringBuffer("<p>没有问卷调查</p>");
				
				view.append("<div id='vote_" + questionnaireId + "' class='votecontainer' style='text-align:left'>\n");
				view.append("  <form id='voteForm_" + questionnaireId + "' name='voteForm_" + questionnaireId + "' action='/" + servletContentName + "/submit.vote' method='post' target='_self'>\n");
				view.append("  <input type='hidden' id='questionnaireId' name='questionnaireId' value='" + questionnaireId + "'>\n");
				view.append("  <input type='hidden' id='voteFlag' name='voteFlag' value='" + questionnaire.getVoteFlag() + "'>\n");
				view.append("    <dl>\n");
				
				Boolean isItemEntity = false;
				Long row = 1L;
				for (Subject subject : subjects){
					List<SubjectItem> subjectItems = subject.getSubjectItems();
					
					if (subjectItems == null || subjectItems.isEmpty()) continue;
					
					isItemEntity = true;
					
					view.append("      <dt id='" + subject.getId() + "'>" + row + "." + subject.getTitle() + "</dt>\n");
					SubjectStatus subjectStatus = subject.getSubjectStatus();
					String subjectStatusValue = "";
					switch(subjectStatus){
						case RADIO : 
							subjectStatusValue = "radio";
							break;
						case OPTION :
							subjectStatusValue = "checkbox";
							break;
						case INPUT :
							subjectStatusValue = "text";
							break;
					}
					for (SubjectItem subjectItem : subjectItems){
						SubjectItemStatus subjectItemStatus = subjectItem.getSubjectItemStatus();
						view.append("      <dd>\n");
						if (!subjectStatusValue.equals("text")){
							view.append("      <label><input name='Subject_" + subject.getId() + "' type='" + subjectStatusValue + "' value='" + subjectItem.getId() + "' id='Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "_Button'/>" + subjectItem.getTitle() + "</label>\n");
						}
						switch(subjectItemStatus){
							case CHOOSE :
								break;
							case SINGLETEXT :
								if (subjectStatusValue.equals("text")){
									view.append("      <input id='Subject_" + subject.getId() + "' name='Subject_" + subject.getId() + "' type='text' value=''/></dd>\n");
								}else{
									view.append("      <input id='Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "' name='Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "' type='text' value='' onClick=\"clickInput('Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "');\"/></dd>\n");
								}
								break;
							case MULTITEXT :
								if (subjectStatusValue.equals("text")){
									view.append("      <textarea style='height:60px;width:400px;vertical-align:top;' id='Subject_" + subject.getId() + "' name='Subject_" + subject.getId() + "'/></textarea></dd>\n");
								}else{
									view.append("      <textarea style='height:60px;width:400px;vertical-align:top;' id='Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "' name='Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "' onClick=\"clickInput('Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "');\"/></textarea></dd>\n");
								}
								break;
						}
						view.append("      </dd>\n");
					}
					row++;
				}
				
				if (!isItemEntity) return new StringBuffer("<p>没有问卷调查</p>");
				
				view.append("    </dl>\n");
				if (questionnaire.getVerifiCode()){
					view.append("    <dl>\n");
					view.append("      <dd>\n");
					view.append("        <img id='id_checkcode' align='absmiddle' width='120px' src='/" + servletContentName + "/checkcode.jpg' alt='点击刷新验证码' title='看不清，换一张' onclick='codeRefresh(this,\"/" + servletContentName + "/checkcode.jpg\");' style='cursor:pointer;'/>\n");
					view.append("        <input type='text' name='j_checkcode' class='checkcode' size='10' maxlength='4' title='验证码不区分大小写'/>");
					view.append("      </dd>\n");
					view.append("    </dl>\n");
				}
				view.append("    <dl>\n");
				view.append("       <dd>\n");
				view.append("         <input type='submit' value='提交' onclick='return checkVote(" + questionnaireId + ");'>&nbsp;&nbsp;");
				if (questionnaire.getQuestionnaireStatus() != QuestionnaireStatus.NOVIEW){
					view.append("         <input type='button' value='查看' onclick='javascript:window.open(\"/" + servletContentName + "/result.vote?id=" + questionnaireId + "\",\"_blank\")'>\n");
				}
				view.append("       </dd>\n");
				view.append("    </dl>\n");
				view.append("  </form>\n");
				view.append("</div>\n");
			}
			view.append("</div>");
			
			return view;
		}catch(Exception e){
			return new StringBuffer("<p>没有问卷调查</p>");
		}
	}
	
	@Override
	public StringBuffer getQuestionnaireResultClientToHtml(Long questionnaireId, String servletContentName, String ipAddr){
		return getQuestionnaireResultToHtml(questionnaireId, servletContentName, ipAddr, true);
	}
	
	@Override
	public StringBuffer getQuestionnaireResultToHtml(Long questionnaireId, String servletContentName, String ipAddr, Boolean isView){
		try{
			Questionnaire questionnaire = questionnaireDAO.get(questionnaireId);
			if (questionnaire == null) return new StringBuffer("<p>没有问卷调查结果</p>");
			
			if (isView){
				if (questionnaire.getQuestionnaireStatus() == QuestionnaireStatus.NOVIEW) return new StringBuffer("<p>不允许查看 " + questionnaire.getTitle() + " 结果</p>");
				if (questionnaire.getQuestionnaireStatus() == QuestionnaireStatus.VOTEVIEW){
					Boolean isEntity = personDAO.findPersonIsEntity(questionnaireId, ipAddr);
					if (!isEntity) return new StringBuffer("<p>只有对 " + questionnaire.getTitle() + " 进行投票后才能查看结果&nbsp;<a href='javascript:history.go(-1);'>返回<a></p>");
				}
			}
			
			StringBuffer result = new StringBuffer();
			
			result.append("<div id='voteresult' style='height:100%;overflow-y:auto;text-align:left;'>\n");
			result.append("<link rel='stylesheet' type='text/css' href='/" + servletContentName + "/source/page/vote/voteresult.css'/>\n");
			result.append("  <div style='padding:10px;overflow:hidden;_overflow:visible;_height:1%;'>\n");
			result.append("    <h2 style='float:left;'>" + questionnaire.getTitle() + "：调查结果</h2>\n");
			//result.append("    <h2 style='float:right;'>投票人数：" + questionnaire.getNumber() + "</h2>\n");
			result.append("  </div>\n");
			
			List<Subject> subjects = questionnaire.getSubjects();
			
			if (subjects == null || subjects.isEmpty()) return new StringBuffer("<p>没有问卷调查结果</p>");
			
			Boolean isItemEntity = false;
			Long subjectSort = 1L;
			for (Subject subject : subjects){
				if (subject.getSubjectStatus() == SubjectStatus.INPUT) continue;
				
				List<SubjectItem> subjectItems = subject.getSubjectItems();
				if (subjectItems == null || subjectItems.isEmpty()) continue; 
				isItemEntity = true;
				
				result.append("  <div class='voteresultb'>\n");
				result.append("    <h3>" + subjectSort + "." + subject.getTitle() + "[" + subject.getSubjectStatusDescription() + "]</h3>\n");
				result.append("    <table name ='ChartTable'>\n");
				result.append("      <tbody>\n");
				result.append("        <tr class='row0'>\n");
				result.append("          <th width='552' class='col1' scope='col'>选项</th>\n");
				result.append("          <th width='264' class='col2' scope='col'>比例</th>\n");
				result.append("        </tr>\n");
					
				Long subjectItemSort = 1L;
				Long sum = CalculateSum(subject);
				for (SubjectItem subjectItem : subjectItems){
					String percentage = "0%";
					if (sum > 0){
						double value = (double)subjectItem.getVoteNumber()/sum;
						DecimalFormat df = new DecimalFormat("##.00");
						String dfValue = df.format(value);
						value = Double.parseDouble(dfValue);
						
						NumberFormat nf = NumberFormat.getPercentInstance();
						percentage = nf.format(value);
					}
					
					result.append("        <tr class='row1'>\n");
					result.append("          <td class='col1'><span>" + subjectItemSort + "</span><p>" + subjectItem.getTitle() + "</p></td>\n");
					result.append("          <td class='col2'><div style='width:190px; line-height:33px;position:relative;'>" + subjectItem.getVoteNumber() + "票 比例:" + percentage + "<span class='percent_bg'><span class='percent' style='width:" + percentage + "'/></span></span></div>\n");
					result.append("        </tr>\n");
					subjectItemSort++;
				}
				result.append("      </tbody>\n");
				result.append("    </table>\n");
				result.append("  </div>\n");
					
				subjectSort++;
			}
			
			if (!isItemEntity) return new StringBuffer("<p>没有问卷调查结果</p>");
			
			return result;
		}catch(Exception e){
			return new StringBuffer("<p>没有问卷调查结果</p>");
		}
	}
	
	private Long CalculateSum(Subject subject){
		if (subject == null) return 0L;
		List<SubjectItem> subjectItems = subject.getSubjectItems();
		Long sum = 0L;
		for (SubjectItem subjectItem : subjectItems){
			sum += subjectItem.getVoteNumber();
		}
		return sum;
	}
}
