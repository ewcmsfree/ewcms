/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler;

import java.util.List;

import com.ewcms.crawler.model.FilterBlock;
import com.ewcms.crawler.model.Gather;
import com.ewcms.crawler.model.MatchBlock;
import com.ewcms.crawler.model.Domain;
import com.ewcms.crawler.web.BlockTreeGridNode;

/**
 * 
 * @author wuzhijun
 *
 */
public interface CrawlerFacable {
	/**
	 * 新增采集器信息
	 * 
	 * @param gather 采集器信息对象
	 * @return Long 采集器信息编号
	 */
	public Long addGather(Gather gather);
	
	/**
	 * 修改采集器信息
	 * 
	 * @param gather 采集器信息对象
	 * @return Long 采集器信息编号
	 */
	public Long updGather(Gather gather);
	
	/**
	 * 删除采集器信息
	 * 
	 * @param gatherId 采集器信息编号
	 */
	public void delGather(Long gatherId);
	
	/**
	 * 查询采集器信息
	 * 
	 * @param gatherId 采集器信息编号
	 * @return Gather 采集器信息对象
	 */
	public Gather findGather(Long gatherId);
	
	/**
	 * 新增/修改URL层级(域名)
	 * 
	 * @param gatherId 采集器信息编号
	 * @param domain URL层级(域名)
	 * @return Long URL层级(域名)编号
	 * @throws BaseException
	 */
	public Long addAndUpdDomain(Long gatherId, Domain domain) throws BaseException;
	
	/**
	 * 删除URL层级(域名)
	 * 
	 * @param gatherId 采集器信息编号
	 * @param domainId URL层级(域名)
	 */
	public void delDomain(Long gatherId, Long domainId);
	
	/**
	 * 查询URL层级(域名)
	 * 
	 * @param domainId URL层级(域名)
	 * @return Domain URL层级(域名)对象
	 */
	public Domain findDomain(Long domainId);
	
	/**
	 * URL层级(域名)对象向上移动一位
	 * 
	 * @param gatherId 采集器信息编号
	 * @param domainId URL层级(域名)编号
	 */
	public void upDomain(Long gatherId, Long domainId);
	
	/**
	 * URL层级(域名)对象向下移动一位
	 * 
	 * @param gatherId 采集器信息编号
	 * @param domainId URL层级(域名)编号
	 */
	public void downDomain(Long gatherId, Long domainId);
	
	/**
	 * 新增/修改匹配块对象
	 * 
	 * @param gatherId 采集器信息编号
	 * @param parentId 匹配块父对象编号
	 * @param matchBlock 匹配块对象
	 * @return Long 匹配块编号
	 */
	public Long addAndUpdMatchBlock(Long gatherId, Long parentId, MatchBlock matchBlock);
	
	/**
	 * 删除匹配块对象
	 * 
	 * @param gatherId 采集器信息编号
	 * @param matchBlockId 匹配块编号
	 */
	public void delMatchBlock(Long gatherId, Long matchBlockId);
	
	/**
	 * 查询匹配块对象
	 * 
	 * @param matchBlockId 匹配块编号
	 * @return MatchBlock 匹配块对象
	 */
	public MatchBlock findMatchBlock(Long matchBlockId);
	
	/**
	 * 匹配块对象向上移动一位
	 * 
	 * @param gatherId 采集器信息编号
	 * @param matchBlockId 匹配块编号
	 */
	public void upMatchBlock(Long gatherId, Long matchBlockId);
	
	/**
	 * 匹配块对象向下移动一位
	 * 
	 * @param gatherId 采集器信息编号
	 * @param matchBlockId 匹配块编号
	 */
	public void downMatchBlock(Long gatherId, Long matchBlockId);
	
	/**
	 * 查询匹配块父对象集合
	 * 
	 * @param gatherId 采集器信息编号
	 * @return List 匹配块对象集合
	 */
	public List<MatchBlock> findParentMatchBlockByGatherId(Long gatherId);
	
	/**
	 * 查询匹配块子对象集合
	 * 
	 * @param gatherId 采集器信息编号
	 * @param parentId 匹配块父对象编号
	 * @return List 匹配块对象集合
	 */
	public List<MatchBlock> findChildMatchBlockByParentId(Long gatherId, Long parentId);
	
	/**
	 * 新增/修改过滤块对象
	 * 
	 * @param gatherId 采集器信息编号
	 * @param parentId 过滤块父对象编号
	 * @param filterBlock 过滤块对象
	 * @return Long 过滤块编号
	 */
	public Long addAndUpdFilterBlock(Long gatherId, Long parentId, FilterBlock filterBlock);
	
	/**
	 * 删除过滤块对象
	 * 
	 * @param gatherId 采集器信息编号
	 * @param filterBlockId 过滤块编号
	 */
	public void delFilterBlock(Long gatherId, Long filterBlockId);
	
	/**
	 * 查询过滤块对象
	 * 
	 * @param filterBlockId 过滤块编号
	 * @return FilterBlock 过滤块对象
	 */
	public FilterBlock findFilterBlock(Long filterBlockId);
	
	/**
	 * 过滤块对象向上移动一位
	 * 
	 * @param gatherId 采集器信息编号
	 * @param filterBlockId 过滤块编号
	 */
	public void upFilterBlock(Long gatherId, Long filterBlockId);
	
	/**
	 * 过滤块对象向下移动一位
	 * 
	 * @param gatherId 采集器信息编号
	 * @param filterBlockId 过滤块编号
	 */
	public void downFilterBlock(Long gatherId, Long filterBlockId);
	
	/**
	 * 查询过滤块父对象集合
	 * 
	 * @param gatherId 采集器编号
	 * @return List 过滤块对象集合
	 */
	public List<FilterBlock> findParentFilterBlockByGatherId(Long gatherId);
	
	/**
	 * 查询过滤块子对象集合
	 * 
	 * @param gatherId 采集器编号
	 * @param parentId 过滤块父对象编号
	 * @return List 过滤块对象集合
	 */
	public List<FilterBlock> findChildFilterBlockByParentId(Long gatherId, Long parentId);
	
	/**
	 * 匹配块对象集合转换成树型结构对象集合
	 * 
	 * @param gatherId 采集器信息编号
	 * @return List 树型结构对象集合
	 */
	public List<BlockTreeGridNode> findMatchBlockTransformTreeGrid(Long gatherId);
	
	/**
	 * 过滤过对象集合转换成树型结构对象集合
	 * 
	 * @param gatherId 采集器信息编号
	 * @return List 树型结构对象集合
	 */
	public List<BlockTreeGridNode> findFilterBlockTransformTreeGrid(Long gatherId);
	
	/**
	 * 删除采集器采集到的文章
	 * 
	 * @param gatherId 采集器编号
	 */
	public void delGatherData(Long gatherId);
}
