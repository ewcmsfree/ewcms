/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.generator.release.ReleaseException;

/**
 *
 * @author 吴智俊
 */
public interface ArticleMainServiceable {
	/**
	 * 查询文章
	 * 
	 * @param articleMainId
	 * @param channelId
	 * @return
	 */
	public ArticleMain findArticleMainByArticleMainAndChannel(Long articleMainId, Integer channelId);

	/**
	 * 删除文章
	 * 
	 * @param articleMainId
	 * @param channelId
	 */
	public void delArticleMain(Long articleMainId, Integer channelId);

	/**
	 * 删除文章到回收站
	 * 
	 * @param articleMainId
	 * @param channelId
	 * @param userName
	 */
	public void delArticleMainToRecycleBin(Long articleMainId, Integer channelId, String userName);

	/**
	 * 恢复文章
	 * 
	 * @param articleMainId
	 * @param channelId
	 * @param userName
	 */
	public void restoreArticleMain(Long articleMainId, Integer channelId, String userName);

	/**
	 * 提交审核文章(只对初稿和重新编辑状态的文章进行发布)
	 * 
	 * @param articleMainId
	 * @param channelId
	 * @return
	 */
	public Boolean submitReviewArticleMain(Long articleMainId, Integer channelId);
	
	/**
	 * 提交审核文章(对任务状态的文章进行发布)
	 * 
	 * @param articleMainIds 文章编号列表
	 * @param channelId
	 */
	public void submitReviewArticleMains(List<Long> articleMainIds, Integer channelId);

	/**
	 * 拷贝文章
	 * 
	 * @param articleMainIds
	 * @param channelIds
	 * @return
	 */
	public Boolean copyArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId);

	/**
	 * 移动文章
	 * 
	 * @param articleMainIds
	 * @param channelIds
	 * @return
	 */
	public Boolean moveArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId);

	/**
	 * 通过频道编号查询文章
	 * 
	 * @param channelId
	 * @return
	 */
	public List<ArticleMain> findArticleMainByChannel(Integer channelId);

	/**
	 * 发布文章
	 * @param channelId 频道编号
	 * @throws ReleaseException
	 */
	public void pubArticleMainByChannel(Integer channelId) throws ReleaseException;
	
	/**
	 * 审核文章
	 * 
	 * @param articleMainIds 文章列表
	 * @param review 审核标志(0:通过,1:未通过)
	 * @param eauthor 审核人
	 */
	public void reviewArticleMain(List<Long> articleMainIds, Integer channelId, Integer review, String eauthor);
	
	/**
	 * 根据sort对文章进行重排序
	 * @param articleMainId
	 * @param channelId
	 * @param sort
	 * @param isInsert 是否是插入(0:插入,1:替换)
	 * @param isTop 是否置顶(true:是,false:否)
	 */
	public void moveArticleMainSort(Long articleMainId, Integer channelId, Long sort, Integer isInsert, Boolean isTop);
	
	/**
	 * 
	 * 
	 * @param channelId
	 * @param sort
	 * @param isTop
	 */
	public Boolean findArticleMainByChannelAndEqualSort(Integer channelId, Long sort, Boolean isTop);
}
