/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.ReviewProcess;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.ComboBoxUserAndGroup;

/**
 * @author 吴智俊
 */
public class ReviewProcessAction extends CrudBaseAction<ReviewProcess, Long> {

	private static final long serialVersionUID = -8106374733257334311L;

	@Autowired
	private DocumentFacable documentFac;
	@Autowired
	private QueryFactory queryFactory;
	
	private Integer channelId;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public ReviewProcess getReviewProcessVo() {
		return super.getVo();
	}

	public void setReviewProcessVo(ReviewProcess reviewProcessVo) {
		super.setVo(reviewProcessVo);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(ReviewProcess vo) {
		return vo.getId();
	}

	@Override
	protected ReviewProcess getOperator(Long pk) {
		ReviewProcess reviewProcess = documentFac.findReviewProcess(pk);
		return reviewProcess;
	}

	@Override
	protected void deleteOperator(Long pk) {
		documentFac.delReviewProcess(pk);
	}

	@Override
	protected Long saveOperator(ReviewProcess vo, boolean isUpdate) {
		List<String> userNames = new ArrayList<String>();
		for (int i = 0; i< reviewUserNames.length; i++){
			String userName = reviewUserNames[i];
			userNames.add(userName);
		}
		List<String> groupNames = new ArrayList<String>();
		for (int i = 0; i< reviewGroupNames.length; i++){
			String groupName = reviewGroupNames[i];
			groupNames.add(groupName);
			
		}
		if (isUpdate) {
			return documentFac.updReviewProcess(vo, userNames, groupNames);
		} else {
			return documentFac.addReviewProcess(getChannelId(), vo, userNames, groupNames);
		}
	}

	@Override
	protected ReviewProcess createEmptyVo() {
		return new ReviewProcess();
	}
	
	public void up(){
		try{
			if (getChannelId() != null && getSelections() != null){
				documentFac.upReivewProcess(getChannelId(), getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void down(){
		try{
			if (getChannelId() != null && getSelections() != null){
				documentFac.downReviewProcess(getChannelId(), getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	private String[] reviewUserNames;
	private String[] reviewGroupNames;
	
	public String[] getReviewUserNames() {
		return reviewUserNames;
	}

	public void setReviewUserNames(String[] reviewUserNames) {
		this.reviewUserNames = reviewUserNames;
	}
	
	public String[] getReviewGroupNames() {
		return reviewGroupNames;
	}

	public void setReviewGroupNames(String[] reviewGroupNames) {
		this.reviewGroupNames = reviewGroupNames;
	}

	private Long processId;
	
	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public void userInfo(){
		EntityQueryable query = queryFactory.createEntityQuery(User.class);
		List<Object> resultList = query.queryResult().getResultList();
		List<ComboBoxUserAndGroup> comboBoxUsers = new ArrayList<ComboBoxUserAndGroup>();
		ComboBoxUserAndGroup comboBoxUser = null;
		for (Object object : resultList){
			comboBoxUser = new ComboBoxUserAndGroup();
			User user = (User)object;
			comboBoxUser.setId(user.getUsername());
			comboBoxUser.setText(user.getUserInfo().getName());
			if (getProcessId() != null){
				Boolean isEntity = documentFac.findReviewUserIsEntityByProcessIdAndUserName(getProcessId(), user.getUsername());
				if (isEntity) comboBoxUser.setSelected(true);
			}
			
			comboBoxUsers.add(comboBoxUser);
		}
		Struts2Util.renderJson(JSONUtil.toJSON(comboBoxUsers.toArray(new ComboBoxUserAndGroup[0])));
	}
	
	public void groupInfo(){
		EntityQueryable query = queryFactory.createEntityQuery(Group.class);
		List<Object> resultList = query.queryResult().getResultList();
		List<ComboBoxUserAndGroup> comboBoxGroups = new ArrayList<ComboBoxUserAndGroup>();
		ComboBoxUserAndGroup comboBoxGroup = null;
		for (Object object : resultList){
			comboBoxGroup = new ComboBoxUserAndGroup();
			Group group = (Group)object;
			comboBoxGroup.setId(group.getName());
			comboBoxGroup.setText(group.getName());
			if (getProcessId() != null){
				Boolean isEntity = documentFac.findReviewGroupIsEntityByProcessIdAndUserName(getProcessId(), group.getName());
				if (isEntity) comboBoxGroup.setSelected(true);
			}
			comboBoxGroups.add(comboBoxGroup);
		}
		Struts2Util.renderJson(JSONUtil.toJSON(comboBoxGroups.toArray(new ComboBoxUserAndGroup[0])));
	}
}