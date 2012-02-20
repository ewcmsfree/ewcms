/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.vote.manager.VoteFacable;
import com.ewcms.plugin.vote.model.Questionnaire;
import com.ewcms.web.CrudBaseAction;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author 吴智俊
 * 
 */
public class QuestionnaireAction extends CrudBaseAction<Questionnaire, Long> {

	private static final long serialVersionUID = -7265319890134674763L;

	private SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private VoteFacable voteFac;
	
	private Integer channelId;
	
	private String startTime;
	
	private String endTime;
	
	public String getStartTime() {
		return startTime;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Questionnaire getQuestionnaireVo(){
		return super.getVo();
	}
	
	public void setQuestionnaireVo(Questionnaire questionnaireVo){
		super.setVo(questionnaireVo);
	}
	
	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Questionnaire createEmptyVo() {
		return new Questionnaire();
	}

	@Override
	protected void deleteOperator(Long pk) {
		voteFac.delQuestionnaire(pk);
	}

	@Override
	protected Questionnaire getOperator(Long pk) {
		Questionnaire questionnaire = voteFac.findQuestionnaire(pk);
		setChannelId(questionnaire.getChannelId());
		if (questionnaire.getStartTime() != null){
			setStartTime(bartDateFormat.format(questionnaire.getStartTime()));
		}
		if (questionnaire.getEndTime() != null){ 
			setEndTime(bartDateFormat.format(questionnaire.getEndTime()));
		}
		return questionnaire;
	}

	@Override
	protected Long getPK(Questionnaire vo) {
		return vo.getId();
	}

	@Override
	protected Long saveOperator(Questionnaire vo, boolean isUpdate) {
		try {
			if (getStartTime() != null){
				vo.setStartTime(bartDateFormat.parse(getStartTime()));
			}else{
				vo.setStartTime(new Date(Calendar.getInstance().getTime().getTime()));
			}
			if (getEndTime() != null){
				vo.setEndTime(bartDateFormat.parse(getEndTime()));
			}
		} catch (ParseException e) {
		}
		vo.setChannelId(getChannelId());
		if (isUpdate) {
			return voteFac.updQuestionnaire(vo);
		}else{
			return voteFac.addQuestionnaire(vo);
		}
	}
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void resultVote(){
		if (getId() != null){
			ServletOutputStream out = null;
			StringBuffer output = new StringBuffer();
			try{
				output = voteFac.getQuestionnaireResultToHtml(getId(), ServletActionContext.getServletContext().getContextPath(), null, false);
				
				ActionContext context = ActionContext.getContext();
				HttpServletResponse response = (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE); 
				out = response.getOutputStream();
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html; charset=utf-8");
		    	out.write(output.toString().getBytes());
		    	out.flush();
			}catch(IOException e){
			}finally{
				if (out != null){
	    			try {
						out.close();
					} catch (IOException e) {
					}
	    			out = null;
	    		}
			}
		}
	}
}
