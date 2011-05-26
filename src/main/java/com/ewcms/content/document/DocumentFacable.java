/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleRmc;
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
	public Integer addArticleRmc(Article article, Integer channelId, Date published);
	
	/**
	 * 修改文章内容,并同时对文章历史进行记录
	 * 
	 * @param articleRmcId
	 * @param article
	 * @param channelId
	 * @param published
	 * @return
	 */
	public Integer updArticleRmc(Integer articleRmcId, Article article, Integer channelId, Date published);
	
	/**
	 * 查询文章
	 *  
	 * @param articleRmcId
	 * @return
	 */
	public ArticleRmc getArticleRmc(Integer articleRmcId);
	
	/**
	 * 直接从数据库中删除
	 * 
	 * @param articleRmcId
	 */
	public void delArticleRmc(Integer articleRmcId);
	
	/**
	 * 可回收的删除,把Article对象中的deleteFlag属性置于True
	 * 
	 * @param articleId
	 * @param userName
	 */
	public void delArticleRmcToRecycleBin(Integer articleRmcId, String userName);
	
	/**
	 * 从回收站中恢复文章
	 * 
	 * @param articleId
	 * @param userName
	 */
	public void restoreArticleRmc(Integer articleRmcId, String userName);
	
	/**
	 * 根据文章编号查询历史记录
	 * 
	 * @param articleId
	 * @return
	 */
	public List<Map<String, Object>> findContnetHistoryToPage(Integer articleId);
	
	/**
	 * 根据栏目查询文章列表
	 * 
	 * @param channelId
	 * @return
	 */
	public List<ArticleRmc> findArticleRmcByChannel(Integer channelId);
	
	/**
	 * 根据文章查询相关文章列表
	 * 
	 * @param articleRmcId
	 * @return
	 */
	public List<Related> findRelatedByArticleRmcId(Integer articleRmcId);
	
	/**
	 * 根据文章查询推荐文章列表
	 * 
	 * @param articleRmcId
	 * @return
	 */
	public List<Recommend> findRecommendByArticleRmcId(Integer articleRmcId);

	/**
	 * 提交审核文章(只对初稿和重新编辑状态的文章进行发布)
	 * 
	 * @param articleId
	 */
	public Boolean submitReviewArticleRmc(Integer articleRmcId);
	
	/**
	 * 提交审核文章(对任务状态的文章进行发布)
	 * 
	 * @param articleRmcIds 文章编号列表
	 */
	public void submitReviewArticleRmcs(List<Integer> articleRmcIds);

	/**
	 * 复制文章到其他的栏目
	 * 
	 * @param articleIds
	 * @param channelId
	 * @return Boolean
	 */
	public Boolean copyArticleRmcToChannel(List<Integer> articleRmcIds, List<Integer> channelIds);
	
	/**
	 * 移动文章到其他的栏目
	 * 
	 * @param articleIds
	 * @param channelId
	 * @return Boolean
	 */
	public Boolean moveArticleRmcToChannel(List<Integer> articleRmcIds, List<Integer> channelIds);
	
	/**
	 * 保存相关文章
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void saveRelated(Integer articleId, Integer[] relatedArticleIds);
	
	/**
	 * 删除相关文章
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void deleteRelated(Integer articleId, Integer[] relatedArticleIds);
	
	/**
	 * 相关文章向上移动一位
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void upRelated(Integer articleId, Integer[] relatedArticleIds);

	/**
	 * 相关文章向下移动一位
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void downRelated(Integer articleId, Integer[] relatedArticleIds);
	
	/**
	 * 保存推荐文章
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void saveRecommend(Integer articleId, Integer[] recommendArticleIds);
	
	/**
	 * 删除推荐文章
	 * 
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void deleteRecommend(Integer articleId, Integer[] recommendArticleIds);
	
	/**
	 * 推荐文章向上移动一位
	 * 
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void upRecommend(Integer articleId, Integer[] recommendArticleIds);

	/**
	 * 推荐文章向下移动一位
	 * 
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void downRecommend(Integer articleId, Integer[] recommendArticleIds);
	
	/**
	 * 发布文章
	 * @param channelId 频道编号
	 * @throws ReleaseException
	 */
	public void pubChannel(Integer channelId) throws ReleaseException;
	
	/**
	 * 审核文章
	 * 
	 * @param articleRmcIds 文章列表
	 * @param review 审核标志(0:通过,1:未通过)
	 * @param eauthor 审核人
	 */
	public void reviewArticle(List<Integer> articleRmcIds, Integer review, String eauthor);
}
