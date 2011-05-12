/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.leadingwindow.service;

import java.util.List;

import com.ewcms.plugin.leadingwindow.model.Leader;

/**
 * 领导信息服务接口
 * 
 * @author 吴智俊
 */

public interface LeaderServiceable {
	/**
	 * 新增领导信息
	 * 
	 * @param leader 领导信息
	 * @param channelId 频道编号
	 * @return 领导编号
	 */
	public Integer addLeader(Leader leader, Integer channelId);
	
	/**
	 * 修改领导信息
	 * 
	 * @param leader 领导信息
	 * @param channelId 频道编号
	 * @return 领导编号
	 */
	public Integer updLeader(Leader leader, Integer channelId);
	
	/**
	 * 查询领导信息
	 * 
	 * @param leaderId 领导编号
	 * @param channelId 频道编号
	 * @return 领导信息
	 */
	public Leader getLeader(Integer leaderId, Integer channelId);
	
	/**
	 * 删除领导信息
	 * 
	 * @param leaderId 领导编号
	 * @param channelId 频道编号
	 */
	public void delLeader(Integer leaderId, Integer channelId);
	
	/**
	 * 领导信息向上移动一位
	 * 
	 * @param leaderId 领导编号
	 * @param channelId 频道编号
	 */
	public void upLeader(Integer leaderId, Integer channelId);
	
	/**
	 * 领导信息向下移动一位
	 * 
	 * @param leaderId 领导编号
	 * @param channelId 频道编号
	 */
	public void downLeader(Integer leaderId, Integer channelId);
	
	/**
	 * 获得所有排序的领导信息集合(sort)
	 * 
	 * @param channelId 频道编号
	 * @return 领导信息集合
	 */
	public List<Leader> getLeaderAllOrderBySort(Integer channelId);
}
