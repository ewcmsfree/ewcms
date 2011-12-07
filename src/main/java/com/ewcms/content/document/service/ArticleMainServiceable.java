/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ewcms.content.document.BaseException;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.core.site.model.Channel;
import com.ewcms.publication.PublishException;

/**
 * 文章主体操作接口
 *
 * @author 吴智俊
 */
public interface ArticleMainServiceable {
	/**
	 * 新增文章主体信息
	 * 
	 * @param article 文章信息对象
	 * @param channelId 频道编号
	 * @param published 发布时间
	 * 
	 * @return Long 文章主体编号
	 */
	public Long addArticleMain(Article article, Integer channelId, Date published);
	
	/**
	 * 新增文章主体信息(通过采集器采集数据)
	 * 
	 * @param article 文章信息对象
	 * @param userName 用户名
	 * @param channelId 频道编号
	 * @return Long 文章主体编号
	 */
	public Long addArticleMainByCrawler(Article article, String userName, Integer channelId);
	
	/**
	 * 修改文章主体信息
	 * 
	 * @param article 文章信息对象
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @param published 发布时间
	 * 
	 * @return Long 文章主体编号
	 */
	public Long updArticleMain(Article article, Long articleMainId, Integer channelId, Date published);

	/**
	 * 文章退回到重新编辑状态
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @throws BaseException
	 */
	public void breakArticleMain(Long articleMainId, Integer channelId) throws BaseException;

	/**
	 * 清除文章主体排序
	 * 
	 * @param articleMainIds 文章主体编号集合
	 * @param channelId 频道编号
	 */
	public void clearArticleMainSort(List<Long> articleMainIds, Integer channelId);
	
	/**
	 * 拷贝文章主体
	 * 
	 * @param articleMainIds 文章主体编号集合
	 * @param channelIds 频道编号集合
	 * @return Boolean true:拷贝成功,false:拷贝失败
	 */
	public Boolean copyArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId);

	/**
	 * 删除文章主体
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 */
	public void delArticleMain(Long articleMainId, Integer channelId);
	
	/**
	 * 删除通过采集器采集的文章主体数据
	 * 
	 * @param channelId 频道编号
	 * @param userName 采集者
	 */
	public void delArticleMainByCrawler(Integer channelId, String userName);
	
	/**
	 * 删除文章主体到回收站
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 */
	public void delArticleMainToRecycleBin(Long articleMainId, Integer channelId);

	/**
	 * 文章与文章分类属性是否有关联
	 * 
	 * @param articleId 文章信息编号
	 * @param categoryId 文章分类属性编号
	 * 
	 * @return Boolean (true:是,false:否)
	 */
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Long categoryId);

	/**
	 * 查询文章主体
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @return ArticleMain 文章主体对象
	 */
	public ArticleMain findArticleMainByArticleMainAndChannel(Long articleMainId, Integer channelId);

	/**
	 * 查询文章主体集合
	 * 
	 * @param channelId 频道编号
	 * @return List 文章主体集合
	 */
	public List<ArticleMain> findArticleMainByChannel(Integer channelId);

	/**
	 * 查询文章主体
	 * 
	 * @param channelId 频道编号
	 * @param sort 排序号
	 * @param isTop 是否置顶(true:是,false:否)
	 * @return Boolean true:存在,false:不存在
	 */
	public Boolean findArticleMainByChannelAndEqualSort(Integer channelId, Long sort, Boolean isTop);
	
	/**
	 * 查询操作轨迹原因
	 * 
	 * @param trackId 操作轨迹编号
	 * @return String 原因
	 */
	public String getArticleOperateTrack(Long trackId);

	/**
	 * 文章主体进行排序
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @param sort 排序号
	 * @param isInsert 是否插入(0:插入,1:替换)
	 * @param isTop 是否置顶(true:是,false:否)
	 */
	public void moveArticleMainSort(Long articleMainId, Integer channelId, Long sort, Integer isInsert, Boolean isTop);

	/**
	 * 移动文章主体
	 * 
	 * @param articleMainIds 文章主体编号集合
	 * @param channelIds 频道编号集合
	 * @return Boolean true:移动成功,false:移动失败
	 */
	public Boolean moveArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId);
	
	/**
	 * 发布文章主体
	 * 
	 * @param channelId 频道编号
	 * @param recursion 是否递归发布
	 * @throws PublishException 
	 */
	public void pubArticleMainByChannel(Integer channelId, Boolean recursion) throws PublishException;
	
	/**
	 * 恢复文章主体
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @param userName 操作用户
	 */
	public void restoreArticleMain(Long articleMainId, Integer channelId);
	
	/**
	 * 审核是否可以进行，通过流程定义的用户或用户组判断
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @Param Boolean true:是,false:否
	 */
	public Boolean reviewArticleMainIsEffective(Long articleMainId, Integer channelId);
	
	/**
	 * 审核文章
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @param review 审核标志(0:通过,1:未通过)
	 * @param reason 原因
	 */
	public void reviewArticleMain(Long articleMainId, Integer channelId, Integer review, String reason);
	
	/**
	 * 提交审核文章主体(只对初稿和重新编辑状态的文章进行发布)
	 * 
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @return Boolean true:提交成功,false:提交失败
	 * @throws BaseException
	 */
	public void submitReviewArticleMain(Long articleMainId, Integer channelId) throws BaseException;
	
	/**
	 * 文章主体是否置顶
	 * 
	 * @param articleMainIds 文章主体编号集合
	 * @param top 是否置顶(true:置顶,false:不置顶)
	 */
	public void topArticleMain(List<Long> articleMainIds, Boolean top);
	
	/**
	 * 待审核文章显示
	 * 
	 * @param userName 用户名
	 * @param groupNames 用户组
	 * @return Map
	 */
	public Map<Channel, Long> findBeApprovalArticleMain(String userName, List<String> groupNames);
}
