/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online;

import java.util.List;

import com.ewcms.plugin.citizen.model.Citizen;
import com.ewcms.plugin.online.model.Advisor;
import com.ewcms.plugin.online.model.Matter;
import com.ewcms.plugin.online.model.WorkingBody;
import com.ewcms.web.vo.TreeNode;

/**
 * 
 * @author 吴智俊
 */
public interface OnlineFacable {
	/**
	 * 新增在线办事主体信息
	 * 
	 * @param parentId
	 *            在线办事所属父编号
	 * @param name
	 *            办事名
	 * @param channelId
	 *            频道编号
	 * @return 办事主体编号
	 */
	public Integer addWorkingBody(Integer parentId, String name,
			Integer channelId);

	/**
	 * 重命名在线办事主体信息
	 * 
	 * @param workingBodyId
	 *            在线办事主体编号
	 * @param name
	 *            在线办事主体名
	 * @param channelId
	 *            频道编号
	 * @return 在线办事主体编号
	 */
	public Integer renameWorkingBody(Integer workingBodyId, String name,
			Integer channelId);

	/**
	 * 查询在线办事主体信息
	 * 
	 * @param workingBodyId
	 *            在线办事主体编号
	 * @param channelId
	 *            频道编号
	 * @return 在线办事主体信息
	 */
	public WorkingBody getWorkingBody(Integer workingBodyId, Integer channelId);

	/**
	 * 删除在线办事主体信息
	 * 
	 * @param workingBodyId
	 *            在线办事主体编号
	 * @param channelId
	 *            频道编号
	 */
	public void delWorkingBody(Integer workingBodyId, Integer channelId);

	/**
	 * 添加领导信息到在线办事主体信息内
	 * 
	 * @param workingBodyId
	 *            在线办事主体编号
	 * @param matterIds
	 *            领导编号集合
	 * @param channelId
	 *            频道编号
	 */
	public List<Integer> addMatterToWorkingBody(Integer workingBodyId,
			List<Integer> matterIds, Integer channelId);

	/**
	 * 从在线办事主体信息中移除领导信息
	 * 
	 * @param workingBodyId
	 *            在线办事主体编号
	 * @param matterId
	 *            领导编号
	 * @param channelId
	 *            频道编号
	 */
	public void removeMatterFromWorkingBody(Integer workingBodyId,
			Integer matterId, Integer channelId);

	/**
	 * 在线办事主体信息向上移动一位
	 * 
	 * @param parentId
	 *            父在线办事主体编号
	 * @param workingBodyId
	 *            在线办事主体编号
	 * @param channelId
	 *            频道编号
	 */
	public void upWorkingBody(Integer parentId, Integer workingBodyId,
			Integer channelId);

	/**
	 * 在线办事主体信息向下移动一位
	 * 
	 * @param parentId
	 *            父在线办事主体编号
	 * @param workingBodyId
	 *            在线办事主体编号
	 * @param channelId
	 *            频道编号
	 */
	public void downWorkingBody(Integer parentId, Integer workingBodyId,
			Integer channelId);

	/**
	 * 获得窗树型
	 * 
	 * @param channelId
	 *            频道编号
	 * @return TreeNode对象
	 */
	public TreeNode getWorkingBodyWindowTree(Integer channelId, Boolean isMatter);

	/**
	 * 新增事项基本信息
	 * 
	 * @param matter
	 *            事项基本信息
	 * @param filePaths
	 *            文件路径集合
	 * @param legends
	 *            文件说明集合
	 * @return 事项基本编号
	 */
	public Integer addMatter(Matter matter, List<String> filePaths,
			List<String> legends);

	/**
	 * 修改事项基本信息
	 * 
	 * @param matter
	 *            事项基本信息
	 * @param matterAnnexsIds
	 *            事项附件编号集合
	 * @param filePaths
	 *            文件路径集合
	 * @param legends
	 *            文件说明集合
	 * @return 事项基本编号
	 */
	public Integer updMatter(Matter matter, List<Integer> matterAnnexIds,
			List<String> filePaths, List<String> legends);

	/**
	 * 查询事项基本信息
	 * 
	 * @param matterId
	 *            事项基本编号
	 * @return 事项基本信息
	 */
	public Matter getMatter(Integer matterId);

	/**
	 * 删除事项基本信息
	 * 
	 * @param matterId
	 *            事项基本编号
	 */
	public void delMatter(Integer matterId);

	/**
	 * 事项基本信息向上移动一位
	 * 
	 * @param matterId
	 *            事项基本编号
	 */
	public void upMatter(Integer matterId);

	/**
	 * 事项基本信息向下移动一位
	 * 
	 * @param matterId
	 *            事项基本编号
	 */
	public void downMatter(Integer matterId);

	/**
	 * 获得所有排序的事项基本信息集合(sort)
	 * 
	 * @return 事项基本信息集合
	 */
	public List<Matter> getMatterAllOrderBySort();

	/**
	 * 新增根节点
	 * 
	 * @param channelId
	 * @return
	 */
	public void addWorkingBodyRoot(Integer channelId);

	/**
	 * 关联组织机构到网上办事主体
	 * 
	 * @param workingBodyId
	 *            网上办事主体编号
	 * @param organIds
	 *            组织机构编号集合
	 */
	public String addOrganToWorkingBody(Integer workingBodyId, List<Integer> organIds);

	/**
	 * 从网上办事主体中移除关联组织机构
	 * 
	 * @param workingBodyId 网上办事主体编号
	 * @param String 网上办事名称
	 */
	public String removeOrganFromWorkingBody(Integer workingBodyId);

	/**
	 * 关联组织机构到事项基本信息
	 * 
	 * @param matterId
	 *            事项基本信息编号
	 * @param organId
	 *            组织机构编号
	 */
	public String addOrganToMatter(Integer matterId, Integer organId);

	/**
	 * 从事项基本信息中移除关联组织
	 * 
	 * @param matterId
	 *            事项基本信息编号
	 */
	public String removeOrganFromMatter(Integer matterId);

	/**
	 * 关联公民信息到事项基本信息
	 * 
	 * @param matterId
	 *            事项基本信息编号
	 * @param citizenIds
	 *            公民编号集合
	 */
	public String addCitizenToMatter(Integer matterId, List<Integer> citizenIds);

	/**
	 * 从事项基本信息中移除关联公民
	 * 
	 * @param matterId
	 *            事项基本信息编号
	 */
	public String removeCitizenFromMatter(Integer matterId);

	/**
	 * 查询所有公民对象
	 * 
	 * @return 公民对象集合
	 */
	public List<Citizen> getAllCitizen();

	/**
	 * 在线咨询回复
	 * 
	 * @param id
	 * @param replay
	 */
	public void advisorReplay(Integer id, String replay);

	public Advisor getAdvisor(Integer id);

    public void releaseAdvisor(Integer id,boolean pub);
}
