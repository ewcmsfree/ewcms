/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import com.ewcms.content.document.model.ReviewProcess;

/**
 * 审核流程操作接口
 * 
 * @author wu_zhijun
 *
 */
public interface ReviewProcessServiceable {
	/**
	 * 新增审核流程
	 * 
	 * @param channelId 频道编号
	 * @param reviewProcess 审核流程对象
	 * @param userNames 用户名集合
	 * @param groupNames 用户组集合
	 * @return Long 审核流程编号
	 */
	public Long addReviewProcess(Integer channelId, ReviewProcess reviewProcess, List<String> userNames, List<String> groupNames);
	
	/**
	 * 修改审核流程
	 * 
	 * @param reviewProcess 审核流程对象
	 * @param userNames 用户名集合
	 * @param groupNames 用户组集合
	 * @return Long 审核流程编号
	 */
	public Long updReviewProcess(ReviewProcess reviewProcess, List<String> userNames, List<String> groupNames);
	
	/**
	 * 删除审核流程
	 * 
	 * @param reviewProcessId 审核流程编号
	 */
	public void delReviewProcess(Long reviewProcessId);
	
	/**
	 * 审核流程上移一位
	 * 
	 * @param channelId 频道编号
	 * @param reviewProcessId 审核流程编号
	 */
	public void upReivewProcess(Integer channelId, Long reviewProcessId);
	
	/**
	 * 审核流程下移一位
	 * 
	 * @param channelId 频道编号
	 * @param reviewProcessId 审核流程编号
	 */
	public void downReviewProcess(Integer channelId, Long reviewProcessId);
	
	/**
	 * 查询审核流程
	 * 
	 * @param reviewProcessId 审核流程编号
	 * @return ReviewProcess 审核流程对象
	 */
	public ReviewProcess findReviewProcess(Long reviewProcessId);
	
	/**
	 * 查询频道下的所有审核流程
	 * 
	 * @param channelId 频道编号
	 * @return List 审核流程对象集合
	 */
	public List<ReviewProcess> findReviewProcessByChannel(Integer channelId);
	
	/**
	 * 查询频道下的审核流程第一个节点
	 * 
	 * @param channelId 频道编号
	 * @return ReviewProcess 审核流程对象
	 */
	public ReviewProcess findFirstReviewProcessByChannel(Integer channelId);
	
	/**
	 * 查询频道下的审核流程数
	 * 
	 * @param channelId 频道编号
	 * @return Long 个数
	 */
	public Long findReviewProcessCountByChannel(Integer channelId);
	
	
	public Boolean findReviewUserIsEntityByProcessIdAndUserName(Long reviewProcessId, String userName);

	public Boolean findReviewGroupIsEntityByProcessIdAndUserName(Long reviewProcessId, String goupName);
}
