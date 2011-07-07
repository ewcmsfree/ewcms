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
	public Long updQuestionnaire(Long questionnaireId, Questionnaire questionnaire) {
		Questionnaire questionnaire_old = questionnaireDAO.get(questionnaire.getId());
		questionnaire.setSubjects(questionnaire_old.getSubjects());
		questionnaireDAO.merge(questionnaire);
		return questionnaire.getId();
	}
	
	@Override
	public StringBuffer getQuestionnaireViewToHtml(Long questionnaireId, String servletContentName){
		StringBuffer js = new StringBuffer();
		
		Questionnaire questionnaire = questionnaireDAO.get(questionnaireId);
		if (questionnaire == null) {
			js.append("没有问卷调查");
			return js;
		}
		
		js.append("<div id='voteView' name='voteView'>调查：" + questionnaire.getTitle() + "\n");
		js.append("<link rel='stylesheet' type='text/css' href='/" + servletContentName + "/source/css/vote.css'/>\n");
		js.append("<script language='javascript' src='/" + servletContentName + "/source/js/vote.js'></script>\n");
		
		if (questionnaire.getVoteFlag() || (questionnaire.getEndTime() != null && questionnaire.getEndTime().getTime() < Calendar.getInstance().getTime().getTime())){
			js.append("<p>对不起，此调查已结束，不再接受投票</p>");
		}else{
			List<Subject> subjects = questionnaire.getSubjects();
			if (subjects == null){
				js.append("<p>没有调查主题</p>");
				js.append("</div>");
				return js;
			}
			
			js.append("<div id='vote_" + questionnaireId + "' class='votecontainer' style='text-align:left'>\n");
			js.append("  <form id='voteForm_" + questionnaireId + "' name='voteForm_" + questionnaireId + "' action='/" + servletContentName + "/submit.vote' method='post' target='_self'>\n");
			js.append("  <input type='hidden' id='questionnaireId' name='questionnaireId' value='" + questionnaireId + "'>\n");
			js.append("  <input type='hidden' id='voteFlag' name='voteFlag' value='" + questionnaire.getVoteFlag() + "'>\n");
			
			
			js.append("    <dl>\n");
			
			Long row = 1L;
			for (Subject subject : subjects){
				List<SubjectItem> subjectItems = subject.getSubjectItems();
				if (subjectItems.isEmpty()) continue;
				js.append("      <dt id='" + subject.getId() + "'>" + row + "." + subject.getTitle() + "</dt>\n");
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
					js.append("      <dd>\n");
					if (!subjectStatusValue.equals("text")){
						js.append("      <label><input name='Subject_" + subject.getId() + "' type='" + subjectStatusValue + "' value='" + subjectItem.getId() + "' id='Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "_Button'/>" + subjectItem.getTitle() + "</label>\n");
					}
					switch(subjectItemStatus){
						case CHOOSE :
							break;
						case SINGLETEXT :
							if (subjectStatusValue.equals("text")){
								js.append("      <input id='Subject_" + subject.getId() + "' name='Subject_" + subject.getId() + "' type='text' value=''/></dd>\n");
							}else{
								js.append("      <input id='Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "' name='Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "' type='text' value='' onClick=\"clickInput('Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "');\"/></dd>\n");
							}
							break;
						case MULTITEXT :
							if (subjectStatusValue.equals("text")){
								js.append("      <textarea style='height:60px;width:400px;vertical-align:top;' id='Subject_" + subject.getId() + "' name='Subject_" + subject.getId() + "'/></textarea></dd>\n");
							}else{
								js.append("      <textarea style='height:60px;width:400px;vertical-align:top;' id='Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "' name='Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "' onClick=\"clickInput('Subject_" + subject.getId() + "_Item_" + subjectItem.getId() + "');\"/></textarea></dd>\n");
							}
							break;
					}
					js.append("      </dd>\n");
				}
				row++;
			}
			
			js.append("    </dl>\n");
			if (questionnaire.getVerifiCode()){
				js.append("    <dl>\n");
				js.append("      <dd>\n");
				js.append("        <img id='id_checkcode' align='absmiddle' width='120px' src='/" + servletContentName + "/checkcode.jpg' alt='点击刷新验证码' title='看不清，换一张' onclick='codeRefresh(this,\"/" + servletContentName + "/checkcode.jpg\");' style='cursor:pointer;'/>\n");
				js.append("        <input type='text' name='j_checkcode' class='checkcode' size='10' maxlength='4' title='验证码不区分大小写'/>");
				js.append("      </dd>\n");
				js.append("    </dl>\n");
			}
			js.append("    <dl>\n");
			js.append("       <dd>\n");
			js.append("         <input type='submit' value='提交' onclick='return checkVote(" + questionnaireId + ");'>&nbsp;&nbsp;");
			if (questionnaire.getQuestionnaireStatus() != QuestionnaireStatus.NOVIEW){
				js.append("         <input type='button' value='查看' onclick='javascript:window.open(\"/" + servletContentName + "/result.vote?id=" + questionnaireId + "\",\"_blank\")'>\n");
			}
			js.append("       </dd>\n");
			js.append("    </dl>\n");
			js.append("  </form>\n");
			js.append("</div>\n");
		}
		js.append("</div>");
		logger.info(js.toString());
		
		return js;
	}
	
	@Override
	public StringBuffer getQuestionnaireResultClientToHtml(Long questionnaireId, String servletContentName, String ipAddr){
		return getQuestionnaireResultToHtml(questionnaireId, servletContentName, ipAddr, true);
	}
	
	@Override
	public StringBuffer getQuestionnaireResultToHtml(Long questionnaireId, String servletContentName, String ipAddr, Boolean isView){
		StringBuffer result = new StringBuffer();
		
		Questionnaire questionnaire = questionnaireDAO.get(questionnaireId);
		if (questionnaire == null) {
			result.append("没有问卷调查结果");
			return result;
		}
		
		if (isView){
			if (questionnaire.getQuestionnaireStatus() == QuestionnaireStatus.NOVIEW){
				result.append("不允许查看 " + questionnaire.getTitle() + " 结果");
				return result;
			}else if (questionnaire.getQuestionnaireStatus() == QuestionnaireStatus.VOTEVIEW){
				Boolean isEntity = personDAO.findPersonIsEntity(questionnaireId, ipAddr);
				if (!isEntity){
					result.append("只有对 " + questionnaire.getTitle() + " 进行投票后才能查看结果&nbsp;<a href='javascript:history.go(-1);'>返回<a>");
					return result;
				}
			}
		}
		
		result.append("<div id='voteresult' style='height:100%;overflow-y:auto;text-align:left;'>\n");
		result.append("<link rel='stylesheet' type='text/css' href='/" + servletContentName + "/source/css/voteresult.css'/>\n");
		result.append("  <div style='padding:10px;overflow:hidden;_overflow:visible;_height:1%;'>\n");
		result.append("    <h2 style='float:left;'>" + questionnaire.getTitle() + "：调查结果</h2>\n");
		result.append("    <h2 style='float:right;'>投票人数：" + questionnaire.getNumber() + "</h2>\n");
		result.append("  </div>\n");
		
		List<Subject> subjects = questionnaire.getSubjects();
		Long detailId = 1L;
		for (Subject subject : subjects){
			if (subject.getSubjectStatus() == SubjectStatus.INPUT) continue;
			result.append("  <div class='voteresultb'>\n");
			result.append("    <h3>" + detailId + "." + subject.getTitle() + "[" + subject.getSubjectStatusDescription() + "]</h3>\n");
			result.append("    <table name ='ChartTable'>\n");
			result.append("      <tbody>\n");
			result.append("        <tr class='row0'>\n");
			result.append("          <th width='552' class='col1' scope='col'>选项</th>\n");
			result.append("          <th width='264' class='col2' scope='col'>比例</th>\n");
			result.append("        </tr>\n");
				
			List<SubjectItem> subjectItems = subject.getSubjectItems();
			Long detailItemId = 1L;
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
				result.append("          <td class='col1'><span>" + detailItemId + "</span><p>" + subjectItem.getTitle() + "</p></td>\n");
				result.append("          <td class='col2'><div style='width:190px; line-height:33px;position:relative;'>" + subjectItem.getVoteNumber() + "票 比例:" + percentage + "<span class='percent_bg'><span class='percent' style='width:" + percentage + "'/></span></span></div>\n");
				result.append("        </tr>\n");
				detailItemId++;
			}
			result.append("      </tbody>\n");
			result.append("    </table>\n");
			result.append("  </div>\n");
				
			detailId++;
		}		
		
		return result;
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
