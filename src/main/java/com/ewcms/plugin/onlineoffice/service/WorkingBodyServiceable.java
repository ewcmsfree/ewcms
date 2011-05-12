/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.onlineoffice.service;

import java.util.Date;
import java.util.List;

import com.ewcms.core.document.model.Article;
import com.ewcms.plugin.onlineoffice.model.WorkingBody;
import com.ewcms.web.util.TreeNode;

/**
 *
 * @author 吴智俊
 */
public interface WorkingBodyServiceable {
	/**
	 * 新增在线办事主体信息
	 * 
	 * @param parentId 在线办事所属父编号
	 * @param name 办事名
	 * @param channelId 频道编号
	 * @return 办事主体编号
	 */
	public Integer addWorkingBody(Integer parentId, String name, Integer channelId);

	/**
	 * 重命名在线办事主体信息
	 * 
	 * @param workingBodyId 在线办事主体编号
	 * @param name 在线办事主体名
	 * @param channelId 频道编号
	 * @return 在线办事主体编号
	 */
	public Integer renameWorkingBody(Integer workingBodyId, String name, Integer channelId);

	/**
	 * 查询在线办事主体信息
	 * 
	 * @param workingBodyId 在线办事主体编号
	 * @param channelId 频道编号
	 * @return 在线办事主体信息
	 */
	public WorkingBody getWorkingBody(Integer workingBodyId, Integer channelId);

	/**
	 * 删除在线办事主体信息
	 * 
	 * @param workingBodyId 在线办事主体编号
	 * @param channelId 频道编号
	 */
	public void delWorkingBody(Integer workingBodyId, Integer channelId);

	/**
	 * 添加领导信息到在线办事主体信息内
	 * 
	 * @param workingBodyId 在线办事主体编号
	 * @param matterIds 领导编号集合
	 * @param channelId 频道编号
	 */
	public void addMatterToWorkingBody(Integer workingBodyId, List<Integer> matterIds, Integer channelId);
	
	/**
	 * 从在线办事主体信息中移除领导信息
	 * 
	 * @param workingBodyId 在线办事主体编号
	 * @param matterId 领导编号
	 * @param channelId 频道编号
	 */
	public void removeMatterFromWorkingBody(Integer workingBodyId, Integer matterId, Integer channelId);

	/**
	 * 在线办事主体信息向上移动一位
	 * 
	 * @param parentId 父在线办事主体编号
	 * @param workingBodyId 在线办事主体编号
	 * @param channelId 频道编号
	 */
	public void upWorkingBody(Integer parentId, Integer workingBodyId, Integer channelId);

	/**
	 * 在线办事主体信息向下移动一位
	 * 
	 * @param parentId 父在线办事主体编号
	 * @param workingBodyId 在线办事主体编号
	 * @param channelId 频道编号
	 */
	public void downWorkingBody(Integer parentId, Integer workingBodyId, Integer channelId);
	
	/**
	 * 获得窗树型
	 * 
	 * @param channelId 频道编号 
	 * @return TreeNode对象
	 */
	public TreeNode getWorkingBodyWindowTree(Integer channelId, Boolean isMatter);	

	/**
	 * 新增根节点
	 * 
	 * @param channelId
	 */
	public void addWorkingBodyRoot(Integer channelId);
	
	/**
	 * 新增办事文章
	 * 
	 * @param article 文章对象
	 * @param workingBodyId 办事主体编号
	 * @param channelId 频道编号
	 * @param published 发布日期
	 * @return 文章主体编号
	 */
	public Integer addArticleRmcToWorkingBody(Article article, Integer workingBodyId, Integer channelId, Date published);
	
	/**
	 * 修改办事文章
	 * 
	 * @param articleRmcId 文章主体编号
	 * @param article 文章对象
	 * @param published 发布日期
	 * @return 文章主体编号 
	 */
	public Integer updArticleRmcToWorkingBody(Integer articleRmcId, Article article, Date published);
	
	/**
	 * 删除办事文章
	 * 
	 * @param workingBodyId 办事主体编号
	 * @param articleRmcId 文章主体集合
	 * @param channelId 频道编号
	 */
	public void delArticleRmcToWorkingBody(Integer workingBodyId, Integer articleRmcId, Integer channelId);
	
	/**
	 * 关联组织机构到网上办事主体
	 * 
	 * @param workingBodyId 网上办事主体编号
	 * @param organIds 组织机构编号集合
	 */
	public void addOrganToWorkingBody(Integer workingBodyId, List<Integer> organIds);
	
	/**
	 * 从网上办事主体中移除关联组织机构
	 * 
	 * @param workingBodyId 网上办事主体编号
	 */
	public void removeOrganFromWorkingBOdy(Integer workingBodyId);
	
	/**
	 * 发布文章
	 * 
	 * @param articleRmcId
	 * @param channelId
	 * @return
	 */
	public Boolean preReleaseArticleRmc(Integer articleRmcId, Integer channelId);

	public Boolean moveArticleRmcToWorkingBody(List<Integer> articleRmcIds,	Integer workingBodyId, Integer oldWorkingBodyId);
}
