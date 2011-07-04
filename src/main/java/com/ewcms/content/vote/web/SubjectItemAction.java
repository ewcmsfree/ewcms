/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.vote.VoteFacable;
import com.ewcms.content.vote.model.SubjectItem;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

public class SubjectItemAction extends CrudBaseAction<SubjectItem, Long> {

	private static final long serialVersionUID = 2559743672480588136L;

	@Autowired
	private VoteFacable voteFac;
	
	private Long subjectId;
	private String subjectTitle;
	
	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectTitle() {
		return subjectTitle;
	}

	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}

	public SubjectItem getSubjectItemVo(){
		return super.getVo();
	}
	
	public void setSubjectItemVo(SubjectItem subjectItemVo){
		super.setVo(subjectItemVo);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected SubjectItem createEmptyVo() {
		return new SubjectItem();
	}

	@Override
	protected void deleteOperator(Long pk) {
		voteFac.delSubjectItem(getSubjectId(), pk);
	}

	@Override
	protected SubjectItem getOperator(Long pk) {
		return voteFac.findSubjectItem(pk);
	}

	@Override
	protected Long getPK(SubjectItem vo) {
		return vo.getId();
	}

	@Override
	protected Long saveOperator(SubjectItem vo, boolean isUpdate) {
		if (isUpdate) {
			return voteFac.updSubjectItem(vo);
		}else{
			return voteFac.addSubjectItem(getSubjectId(), vo);
		}
	}
	
	public String inputopt(){
		SubjectItem voteDetailItem = voteFac.findSubjectItemBySubject(getSubjectId());
		if (voteDetailItem == null) setSubjectItemVo(new SubjectItem());
		setSubjectItemVo(voteDetailItem);
		return "input";
	}
	
	public String saveopt(){
		if (getSubjectItemVo().getId() == null){
			voteFac.addSubjectItem(getSubjectId(), getSubjectItemVo());
		}else{
			voteFac.updSubjectItem(getSubjectItemVo());
		}
		return inputopt();
	}
	
	public void up(){
		try{
			if (getSubjectId() != null && getSelections() != null && getSelections().size() == 1){
				voteFac.upSubjectItem(getSubjectId(), getSelections().get(0));
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
			if (getSubjectId() != null && getSelections() != null && getSelections().size() == 1){
				voteFac.downSubjectItem(getSubjectId(), getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false-system"));
		}
	}
}
