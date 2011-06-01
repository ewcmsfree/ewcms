/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document;

import java.util.Date;
import java.util.List;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleCategory;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.Recommend;
import com.ewcms.content.document.model.Related;
import com.ewcms.generator.release.ReleaseException;

/**
 *
 * @author 吴智俊
 */
public interface DocumentFacable {
	/**
	 * 新增文章内容
	 * 
	 * @param article
	 * @param channelId
	 * @param published
	 * @return
	 */
	public Long addArticle(Article article, Integer channelId, Date published);
	
	/**
	 * 修改文章内容,并同时对文章历史进行记录
	 * @param article
	 * @param articleMainId
	 * @param channelId
	 * @param published
	 * 
	 * @return
	 */
	public Long updArticle(Article article, Long articleMainId, Integer channelId, Date published);
	
	/**
	 * 查询文章
	 *  
	 * @param articleMainId
	 * @return
	 */
	public ArticleMain findArticleMainByArticleMainAndChannel(Long articleMainId, Integer channelId);
	
	/**
	 * 直接从数据库中删除
	 * 
	 * @param articleMainId
	 */
	public void delArticleMain(Long articleMainId, Integer channelId);
	
	/**
	 * 可回收的删除,把Article对象中的deleteFlag属性置于True
	 * 
	 * @param articleMainId
	 * @param userName
	 */
	public void delArticleMainToRecycleBin(Long articleMainId, Integer channelId, String userName);
	
	/**
	 * 从回收站中恢复文章
	 * 
	 * @param articleMainId
	 * @param userName
	 */
	public void restoreArticleMain(Long articleMainId, Integer channelId, String userName);
	
	/**
	 * 根据sort对文章进行重排序
	 * @param articleMainId
	 * @param channelId
	 * @param sort
	 * @param isInsert 是否是插入(true:插入,false:替换)
	 * @param isTop 是否置顶(true:是,false:否)
	 */
	public void moveArticleMainSort(Long articleMainId, Integer channelId, Long sort, Integer isInsert, Boolean isTop);
	
	/**
	 * 
	 * 
	 * @param channelMainId
	 * @param sort
	 * @param isTop
	 */
	public Boolean findArticleMainByChannelAndEqualSort(Integer channelMainId, Long sort, Boolean isTop);
	
	/**
	 * 根据栏目查询文章列表
	 * 
	 * @param channelId
	 * @return
	 */
	public List<ArticleMain> findArticleMainByChannel(Integer channelId);
	
	/**
	 * 判断文章与文章属性是否有关联
	 * @param articleId
	 * @param articleCategoryId
	 * @return
	 */
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Integer articleCategoryId);
	
	/**
	 * 根据文章查询相关文章列表
	 * 
	 * @param articleId
	 * @return
	 */
	public List<Related> findRelatedByArticle(Long articleId);
	
	/**
	 * 根据文章查询推荐文章列表
	 * 
	 * @param articleId
	 * @return
	 */
	public List<Recommend> findRecommendByArticle(Long articleId);

	/**
	 * 提交审核文章(只对初稿和重新编辑状态的文章进行发布)
	 * 
	 * @param articleMainId
	 */
	public Boolean submitReviewArticleMain(Long articleMainId, Integer channelId);
	
	/**
	 * 提交审核文章(对任务状态的文章进行发布)
	 * 
	 * @param articleMainIds 文章编号列表
	 */
	public void submitReviewArticleMains(List<Long> articleMainIds, Integer channelId);

	/**
	 * 复制文章到其他的栏目
	 * 
	 * @param articleMainIds
	 * @param channelId
	 * @return Boolean
	 */
	public Boolean copyArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId);
	
	/**
	 * 移动文章到其他的栏目
	 * 
	 * @param articleMainIds
	 * @param channelId
	 * @return Boolean
	 */
	public Boolean moveArticleMainToChannel(List<Long> articleMainIds, List<Integer> channelIds, Integer source_channelId);
	
	/**
	 * 保存相关文章
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void saveRelated(Long articleId, Long[] relatedArticleIds);
	
	/**
	 * 删除相关文章
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void deleteRelated(Long articleId, Long[] relatedArticleIds);
	
	/**
	 * 相关文章向上移动一位
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void upRelated(Long articleId, Long[] relatedArticleIds);

	/**
	 * 相关文章向下移动一位
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void downRelated(Long articleId, Long[] relatedArticleIds);
	
	/**
	 * 保存推荐文章
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void saveRecommend(Long articleId, Long[] recommendArticleIds);
	
	/**
	 * 删除推荐文章
	 * 
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void deleteRecommend(Long articleId, Long[] recommendArticleIds);
	
	/**
	 * 推荐文章向上移动一位
	 * 
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void upRecommend(Long articleId, Long[] recommendArticleIds);

	/**
	 * 推荐文章向下移动一位
	 * 
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void downRecommend(Long articleId, Long[] recommendArticleIds);
	
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
	 * 新增文章分类属性
	 * 
	 * @param articleCategory
	 * @return Integer
	 */
	public Integer addArticleCategory(ArticleCategory articleCategory);
	
	/**
	 * 修改文章分类属性
	 * 
	 * @param articleCategory
	 * @return Integer
	 */
	public Integer updArticleCategory(ArticleCategory articleCategory);
	
	/**
	 * 删除文章分类属性
	 * 
	 * @param articleCategoryId
	 */
	public void delArticleCategory(Integer articleCategoryId);
	
	/**
	 * 查询文章分类属性
	 * 
	 * @param articleCategoryId
	 * @return
	 */
	public ArticleCategory findArticleCategory(Integer articleCategoryId);
	
	/**
	 * 查询所有文章分类属性
	 * 
	 * @return
	 */
	public List<ArticleCategory> findArticleCategoryAll();
}
