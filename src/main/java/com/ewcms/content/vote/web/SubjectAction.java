/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.vote.VoteFacable;
import com.ewcms.content.vote.model.Subject;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author wu_zhijun
 *
 */
public class SubjectAction extends CrudBaseAction<Subject, Long> {

	private static final long serialVersionUID = 2559743672480588136L;

	@Autowired
	private VoteFacable voteFac;
	
	private Long questionnaireId;
	
	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public Subject getSubjectVo(){
		return super.getVo();
	}
	
	public void setSubjectVo(Subject subjectVo){
		super.setVo(subjectVo);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Subject createEmptyVo() {
		return new Subject();
	}

	@Override
	protected void deleteOperator(Long pk) {
		voteFac.delSubject(getQuestionnaireId(), pk);
	}

	@Override
	protected Subject getOperator(Long pk) {
		return voteFac.findSubject(pk);
	}

	@Override
	protected Long getPK(Subject vo) {
		return vo.getId();
	}

	@Override
	protected Long saveOperator(Subject vo, boolean isUpdate) {
		if (isUpdate) {
			return voteFac.updSubject(getQuestionnaireId(), vo);
		}else{
			return voteFac.addSubject(getQuestionnaireId(), vo);
		}
	}
	
	public void up(){
		try{
			if (getQuestionnaireId() != null && getSelections() != null && getSelections().size() == 1){
				voteFac.upSubject(getQuestionnaireId(), getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false-system"));
		}
	}
	
	public void down(){
		try{
			if (getQuestionnaireId() != null && getSelections() != null && getSelections().size() == 1){
				voteFac.downSubject(getQuestionnaireId(), getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false-system"));
		}
	}
}
