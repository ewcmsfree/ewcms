/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import com.ewcms.content.document.model.OperateTrack;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface OperateTrackServiceable {
	
	/**
	 * 保存文章操作过程(定时器和采集器操作)
	 * 
	 * @param articleMainId 文章主体编号
	 * @param statusDesc 文章当前状态
	 * @param description 描述
	 * @param reason 原因
	 * @param userName 用户名
	 * @param userRealName 真实姓名
	 */
	public void addOperateTrack(Long articleMainId, String statusDesc, String description, String reason, String userName, String userRealName);
	
	/**
	 * 保存文章操作过程(登录系统操作)
	 * 
	 * @param articleMainId 文章主体编号
	 * @param statusDesc 文章当前状态
	 * @param description 描述
	 * @param reason 原因
	 */
	public void addOperateTrack(Long articleMainId, String statusDesc, String description, String reason);
	
	/**
	 * 删除文章操作过程
	 * 
	 * @param articleMainId 文章主体编号
	 */
	public void delOperateTrack(Long articleMainId);
	
	/**
	 * 通过文章主体编号查询所有操作过程
	 * 
	 * @param articleMainId 文章主体编号
	 * @return List 操作过程集合
	 */
	public List<OperateTrack> findOperateTrackByArticleMainId(Long articleMainId);
	
	/**
	 * 通过编号查询操作过程
	 * 
	 * @param operateTrackId 操作过程 编号
	 * @return 操作过程对象
	 */
	public OperateTrack findOperateTrackById(Long operateTrackId);
}
