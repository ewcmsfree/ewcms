/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import static com.ewcms.common.lang.EmptyUtil.isCollectionEmpty;
import static com.ewcms.common.lang.EmptyUtil.isNotNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.BaseException;
import com.ewcms.content.document.dao.ArticleMainDAO;
import com.ewcms.content.document.dao.ReviewProcessDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.content.document.model.ReviewGroup;
import com.ewcms.content.document.model.ReviewProcess;
import com.ewcms.content.document.model.ReviewUser;
import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.service.UserServiceable;

@Service
public class ReviewProcessService implements ReviewProcessServiceable {

	@Autowired
	private ReviewProcessDAO reviewProcessDAO;
	@Autowired
	private ArticleMainDAO articleMainDAO;
	@Autowired
	private UserServiceable userService;
	@Autowired
	private OperateTrackServiceable operateTrackService;
	
	@Override
	public Long addReviewProcess(Integer channelId, ReviewProcess reviewProcess, List<String> userNames, List<String> groupNames) throws BaseException {
		ReviewProcess reviewProcess_entity = reviewProcessDAO.findIsEntityReviewProcessByChannelAndName(channelId, reviewProcess.getName());
		if (isNotNull(reviewProcess_entity)){
			throw new BaseException("流程名称已定义，请重新输入其他名称！", "流程名称已定义，请重新输入其他名称！");
		}
		if (isCollectionEmpty(userNames) && isCollectionEmpty(groupNames)){
			throw new BaseException("用户组和用户不能同时为空，必须选择一项以上！", "用户组和用户不能同时为空，必须选择一项以上！");
		}
		List<ReviewProcess> vos = reviewProcessDAO.findReviewProcessByChannel(channelId);
		reviewProcess.setChannelId(channelId);
		
		setUpReviewUserAndGroup(reviewProcess, userNames, groupNames);
		
		if (vos == null){
			reviewProcess.setPrevProcess(null);
			reviewProcessDAO.persist(reviewProcess);
		}else{
			ReviewProcess lastVo = reviewProcessDAO.findLastReviewProcessByChannel(channelId);
			reviewProcess.setPrevProcess(lastVo);
			reviewProcess.setNextProcess(null);
			lastVo.setNextProcess(reviewProcess);
			
			reviewProcessDAO.merge(lastVo);
		}
		return reviewProcess.getId();
	}

	@Override
	public void delReviewProcess(Long reviewProcessId) {
		ReviewProcess vo = reviewProcessDAO.get(reviewProcessId);
		if (vo.getPrevProcess() == null){
			if (vo.getNextProcess() != null){ 
				ReviewProcess nextVo = reviewProcessDAO.get(vo.getNextProcess().getId());
				nextVo.setPrevProcess(null);
				reviewProcessDAO.merge(nextVo);
			}
		}else{
			if (vo.getNextProcess() == null){
				ReviewProcess prevVo = reviewProcessDAO.get(vo.getPrevProcess().getId());
				prevVo.setNextProcess(null);
				reviewProcessDAO.merge(prevVo);
			}else{
				ReviewProcess prevVo = reviewProcessDAO.get(vo.getPrevProcess().getId());
				ReviewProcess nextVo = reviewProcessDAO.get(vo.getNextProcess().getId());
				prevVo.setNextProcess(nextVo);
				nextVo.setPrevProcess(prevVo);
			
				reviewProcessDAO.merge(nextVo);
				reviewProcessDAO.merge(prevVo);
			}
		}
		
		List<ArticleMain> articleMains = reviewProcessDAO.findArticleMainByReviewProcess(vo.getChannelId(), reviewProcessId);
		if (articleMains != null && !articleMains.isEmpty()){
			for (ArticleMain articleMain : articleMains){
				Article article = articleMain.getArticle();
				if (article == null) continue;
				operateTrackService.addOperateTrack(articleMain.getId(), article.getStatusDescription(), "审核流程【" + vo.getName()  + "】已经被删除。", "");

				article.setReviewProcess(null);
				article.setStatus(ArticleStatus.REVIEWBREAK);
				
				articleMain.setArticle(article);
				articleMainDAO.merge(articleMain);
			}
		}
		
		reviewProcessDAO.removeByPK(reviewProcessId);
	}

	@Override
	public void downReviewProcess(Integer channelId, Long reviewProcessId) {
		ReviewProcess vo = reviewProcessDAO.get(reviewProcessId);
		if (vo.getNextProcess() != null){
			ReviewProcess prevVo = vo.getPrevProcess();
			ReviewProcess nextVo = vo.getNextProcess();
			
			ReviewProcess nextnextVo = nextVo.getNextProcess();
			if (nextnextVo != null){
				nextnextVo.setPrevProcess(vo);
				reviewProcessDAO.merge(nextnextVo);
			}
			if (prevVo != null){
				prevVo.setNextProcess(nextVo);
				reviewProcessDAO.merge(prevVo);
			}
			
			vo.setPrevProcess(nextVo);
			vo.setNextProcess(nextVo.getNextProcess());
			
			reviewProcessDAO.merge(vo);
			
			nextVo.setPrevProcess(prevVo);
			nextVo.setNextProcess(vo);
			
			reviewProcessDAO.merge(nextVo);
		}
	}

	@Override
	public void upReivewProcess(Integer channelId, Long reviewProcessId) {
		ReviewProcess vo = reviewProcessDAO.get(reviewProcessId);
		if (vo.getPrevProcess() != null){
			ReviewProcess prevVo = vo.getPrevProcess();
			ReviewProcess nextVo = vo.getNextProcess();
			
			ReviewProcess prevprevVo = prevVo.getPrevProcess();
			if (prevprevVo != null){
				prevprevVo.setNextProcess(vo);
				reviewProcessDAO.merge(prevprevVo);
			}
			
			if (nextVo != null){
				nextVo.setPrevProcess(prevVo);
				reviewProcessDAO.merge(nextVo);
			}
			
			vo.setPrevProcess(prevVo.getPrevProcess());
			vo.setNextProcess(prevVo);
			
			reviewProcessDAO.merge(vo);

			prevVo.setPrevProcess(vo);
			prevVo.setNextProcess(nextVo);
			
			reviewProcessDAO.merge(prevVo);
		}
	}

	@Override
	public Long updReviewProcess(ReviewProcess reviewProcess, List<String> userNames, List<String> groupNames) throws BaseException {
		ReviewProcess entity = reviewProcessDAO.findIsEntityReviewProcessByChannelAndName(reviewProcess.getChannelId(), reviewProcess.getName());
		if (isNotNull(entity) && reviewProcess.getId() != entity.getId()){
			throw new BaseException("流程名称已定义，请重新输入其他名称！", "流程名称已定义，请重新输入其他名称！");
		}
		if (isCollectionEmpty(userNames) && isCollectionEmpty(groupNames)){
			throw new BaseException("用户组和用户不能同时为空，必须选择一项以上！", "用户组和用户不能同时为空，必须选择一项以上！");
		}
		setUpReviewUserAndGroup(reviewProcess, userNames, groupNames);
		reviewProcessDAO.merge(reviewProcess);
		return reviewProcess.getId();
	}

	@Override
	public ReviewProcess findReviewProcess(Long reviewProcessId) {
		return reviewProcessDAO.get(reviewProcessId);
	}

	@Override
	public List<ReviewProcess> findReviewProcessByChannel(Integer channelId) {
		return reviewProcessDAO.findReviewProcessByChannel(channelId);
	}

	@Override
	public ReviewProcess findFirstReviewProcessByChannel(Integer channelId) {
		return reviewProcessDAO.findFirstReviewProcessByChannel(channelId);
	}

	@Override
	public Long findReviewProcessCountByChannel(Integer channelId) {
		return reviewProcessDAO.findReviewProcessCountByChannel(channelId);
	}

	private void setUpReviewUserAndGroup(ReviewProcess reviewProcess, List<String> userNames, List<String> groupNames){
		List<ReviewGroup> reviewGroups = new ArrayList<ReviewGroup>();
		ReviewGroup reviewGroup;
		for (String groupName : groupNames){
			reviewGroup = new ReviewGroup();
			reviewGroup.setGroupName(groupName);
			reviewGroups.add(reviewGroup);
		}
		reviewProcess.setReviewGroups(reviewGroups);
		
		List<ReviewUser> reviewUsers = new ArrayList<ReviewUser>();
		ReviewUser reviewUser;
		for (String userName : userNames){
			reviewUser = new ReviewUser();
			reviewUser.setUserName(userName);
			reviewUser.setRealName(findUserRealNameByUserName(userName));
			reviewUsers.add(reviewUser);
		}
		reviewProcess.setReviewUsers(reviewUsers);
	}
	
	private String findUserRealNameByUserName(String userName){
		User user = userService.getUser(userName);
		return user.getUserInfo().getName();
	}

	@Override
	public Boolean findReviewUserIsEntityByProcessIdAndUserName(Long reviewProcessId, String userName) {
		return reviewProcessDAO.findReviewUserIsEntityByProcessIdAndUserName(reviewProcessId, userName);
	}

	@Override
	public Boolean findReviewGroupIsEntityByProcessIdAndUserName(Long reviewProcessId, String goupName) {
		return reviewProcessDAO.findReviewGroupIsEntityByProcessIdAndUserName(reviewProcessId, goupName);
	}
}
