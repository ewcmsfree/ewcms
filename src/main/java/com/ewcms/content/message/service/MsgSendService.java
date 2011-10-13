/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.message.dao.MsgContentDAO;
import com.ewcms.content.message.dao.MsgReceiveDAO;
import com.ewcms.content.message.dao.MsgSendDAO;
import com.ewcms.content.message.model.MsgContent;
import com.ewcms.content.message.model.MsgReceiveUser;
import com.ewcms.content.message.model.MsgStatus;
import com.ewcms.content.message.model.MsgType;
import com.ewcms.content.message.model.MsgReceive;
import com.ewcms.content.message.model.MsgSend;
import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.service.UserServiceable;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 
 * @author wu_zhijun
 * 
 */
@Service
public class MsgSendService implements MsgSendServiceable {

	@Autowired
	private MsgSendDAO msgSendDAO;
	@Autowired
	private MsgReceiveDAO msgReceiveDAO;
	@Autowired
	private MsgContentDAO msgContentDAO;
	@Autowired
	private UserServiceable userService;

	@Override
	public Long addMsgSend(MsgSend msgSend, String content, List<String> userNames) {
		msgSend.setUserName(EwcmsContextUtil.getUserDetails().getUsername());
		msgSend.setStatus(MsgStatus.FAVORITE);
		MsgContent msgContent = new MsgContent();
		msgContent.setTitle(msgSend.getTitle());
		msgContent.setDetail(content);
		List<MsgContent> msgContents = new ArrayList<MsgContent>();
		msgContents.add(msgContent);
		msgSend.setMsgContents(msgContents);
		
		if (msgSend.getType() == MsgType.GENERAL){
			List<MsgReceiveUser> msgReceiveUsers = new ArrayList<MsgReceiveUser>();
			MsgReceiveUser msgReceiveUser;
			for (String userName : userNames){
				if (userName.equals(msgSend.getUserName())) continue;
				msgReceiveUser = new MsgReceiveUser();
				msgReceiveUser.setUserName(userName);
				msgReceiveUser.setRealName(findUserRealNameByUserName(userName));
				msgReceiveUsers.add(msgReceiveUser);
			}
			msgSend.setMsgReceiveUsers(msgReceiveUsers);
		}else if (msgSend.getType() == MsgType.NOTICE){
			msgSend.setMsgReceiveUsers(null);
		}else if (msgSend.getType() == MsgType.SUBSCRIPTION){
			msgSend.setMsgReceiveUsers(null);
		}
		
		msgSendDAO.persist(msgSend);
		msgSendDAO.flush(msgSend);
		
		if (msgSend.getType() == MsgType.GENERAL){
			for (String userName : userNames){
				if (userName.equals(msgSend.getUserName())) continue;
				MsgReceive msgReceive = new MsgReceive();
				msgReceive.setUserName(userName);
				msgReceive.setSendUserName(EwcmsContextUtil.getUserDetails().getUsername());
				msgReceive.setStatus(MsgStatus.FAVORITE);
				msgReceive.setMsgContent(msgContent);
				msgReceiveDAO.persist(msgReceive);
			}		
		}
		
		return msgSend.getId();
	}

	@Override
	public Long updMsgSend(MsgSend send) {
		return null;
	}

	@Override
	public void delMsgSend(Long msgSendId) {
		msgSendDAO.removeByPK(msgSendId);
	}

	@Override
	public MsgSend findMsgSend(Long msgSendId) {
		return msgSendDAO.get(msgSendId);
	}

	@Override
	public List<MsgSend> findMsgSendByUserName() {
		return msgSendDAO.findMsgSendByUserName(EwcmsContextUtil.getUserDetails().getUsername());
	}

	@Override
	public Long addSubscription(Long msgSendId, String title, String detail) {
		MsgSend msgSend = msgSendDAO.findMsgSendByUserNameAndId(EwcmsContextUtil.getUserDetails().getUsername(), msgSendId);
		Assert.notNull(msgSend);
		if (msgSend.getType() == MsgType.SUBSCRIPTION){
			List<MsgContent> msgContents = msgSend.getMsgContents();
			
			MsgContent msgContent = new MsgContent();
			msgContent.setTitle(title);
			msgContent.setDetail(detail);
			
			msgContents.add(msgContent);
			msgSend.setMsgContents(msgContents);
			
			msgSendDAO.merge(msgSend);
			
			List<MsgReceiveUser> msgReceiveUsers = msgSend.getMsgReceiveUsers();
			for (MsgReceiveUser msgReceiveUser : msgReceiveUsers){
				MsgReceive msgReceive = new MsgReceive();
				msgReceive.setMsgContent(msgContent);
				msgReceive.setSendUserName(EwcmsContextUtil.getUserDetails().getUsername());
				msgReceive.setSubscription(true);
				msgReceive.setUserName(msgReceiveUser.getUserName());
				msgReceive.setStatus(MsgStatus.FAVORITE);
				msgReceiveDAO.merge(msgReceive);
			}
			return msgSend.getId();
		}
		return null;
	}

	@Override
	public void delSubscription(Long msgContentId) {
		msgContentDAO.removeByPK(msgContentId);
	}

	@Override
	public List<MsgSend> findMsgSendByGeneral() {
		return msgSendDAO.findMsgSendByType(MsgType.GENERAL);
	}

	@Override
	public List<MsgSend> findMsgSendByNotice() {
		return msgSendDAO.findMsgSendByType(MsgType.NOTICE);
	}

	@Override
	public List<MsgSend> findMsgSendBySubscription() {
		return msgSendDAO.findMsgSendByType(MsgType.SUBSCRIPTION);
	}

	@Override
	public String subscribeMsg(Long msgSendId) {
		MsgSend msgSend = msgSendDAO.get(msgSendId);
		if (msgSend.getType() == MsgType.SUBSCRIPTION){
			String receiveUserName = EwcmsContextUtil.getUserDetails().getUsername();
			String realName = userService.getUserRealName();
			String sendUserName = msgSend.getUserName();
			if (receiveUserName.equals(sendUserName)){
				return "own";
			}
			if (!msgSendDAO.findUserHaveSubscribedByUserName(receiveUserName)){
				List<MsgReceiveUser> msgReceiveUsers = msgSend.getMsgReceiveUsers();
				MsgReceiveUser msgReceiveUser = new MsgReceiveUser();
				msgReceiveUser.setUserName(receiveUserName);
				msgReceiveUser.setRealName(realName);
				msgReceiveUsers.add(msgReceiveUser);
				msgSend.setMsgReceiveUsers(msgReceiveUsers);
				msgSendDAO.merge(msgSend);
				return "true";
			}else{
				return "exist";
			}
		}else{
			return "false";
		}
	}
	
	public String findUserRealNameByUserName(String userName){
		User user = userService.getUser(userName);
		return user.getUserInfo().getName();
	}
}
