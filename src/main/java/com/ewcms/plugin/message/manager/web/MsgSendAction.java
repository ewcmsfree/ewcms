/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.manager.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.message.manager.MessageFacable;
import com.ewcms.plugin.message.model.MsgReceiveUser;
import com.ewcms.plugin.message.model.MsgSend;
import com.ewcms.plugin.message.model.MsgSend.Type;
import com.ewcms.security.manage.model.User;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.ComboBoxString;

/**
 * 
 * @author wu_zhijun
 *
 */
public class MsgSendAction extends CrudBaseAction<MsgSend, Long> {

	private static final long serialVersionUID = 5056220633230582617L;

	@Autowired
	private MessageFacable messageFac;
	@Autowired
	private QueryFactory queryFactory;
	
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MsgSend getMsgSendVo(){
		return super.getVo();
	}
	
	public void setMsgSendVo(MsgSend msgSendVo){
		super.setVo(msgSendVo);
	}
	
	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(MsgSend vo) {
		return vo.getId();
	}

	@Override
	protected MsgSend getOperator(Long pk) {
		MsgSend msgSend = messageFac.findMsgSend(pk);
		List<MsgReceiveUser> msgReceiveUsers = msgSend.getMsgReceiveUsers();
		String[] receiveUserNames = new String[msgReceiveUsers.size()];
		for (int i = 0; i < msgReceiveUsers.size(); i++){
			receiveUserNames[i] = msgReceiveUsers.get(i).getUserName();
		}
		this.setReceiveUserNames(receiveUserNames);
		return msgSend;
	}

	@Override
	protected void deleteOperator(Long pk) {
		messageFac.delMsgSend(pk);
	}

	@Override
	protected Long saveOperator(MsgSend vo, boolean isUpdate) {
		List<String> userNames = new ArrayList<String>();
		if (vo.getType() == Type.GENERAL){
			if (isNotNull(receiveUserNames)){
				for (int i = 0; i< receiveUserNames.length; i++){
					String userName = receiveUserNames[i];
					userNames.add(userName);
				}
			}
		}
		if (isUpdate) {
			return messageFac.updMsgSend(vo);
		} else {
			return messageFac.addMsgSend(vo, getContent(), userNames);
		}
	}

	@Override
	protected MsgSend createEmptyVo() {
		return new MsgSend();
	}
	
	private String[] receiveUserNames;
	
	public String[] getReceiveUserNames() {
		return receiveUserNames;
	}

	public void setReceiveUserNames(String[] receiveUserNames) {
		this.receiveUserNames = receiveUserNames;
	}

	public void userInfo(){
		EntityQueryable query = queryFactory.createEntityQuery(User.class);
		List<Object> resultList = query.queryResult().getResultList();
		List<ComboBoxString> comboBoxUsers = new ArrayList<ComboBoxString>();
		ComboBoxString comboBoxUser = null;
		for (Object object : resultList){
			comboBoxUser = new ComboBoxString();
			User user = (User)object;
			comboBoxUser.setId(user.getUsername());
			comboBoxUser.setText(user.getUserInfo().getName());
			if (getReceiveUserNames() != null && getReceiveUserNames().length > 0){
				comboBoxUser.setSelected(true);
			}
			comboBoxUsers.add(comboBoxUser);
		}
		Struts2Util.renderJson(JSONUtil.toJSON(comboBoxUsers.toArray(new ComboBoxString[0])));
	}
}
