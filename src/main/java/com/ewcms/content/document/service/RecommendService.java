/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.dao.ArticleDAO;
import com.ewcms.content.document.dao.RecommendDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Recommend;

/**
 *
 * @author 吴智俊
 */
@Service()
public class RecommendService implements RecommendServiceable {
	@Autowired
	private ArticleDAO articleDAO;
	@Autowired
	private RecommendDAO recommendDAO;

	@Override
	public void deleteRecommend(Long articleId, Long[] recommendArticleIds) {
		if (articleId != null && recommendArticleIds != null && recommendArticleIds.length > 0){
			Article article = articleDAO.get(articleId);
			List<Recommend> recommends_old = article.getRecommends();
			List<Recommend> recommends_sort = recommendDAO.findRecommendByArticle(articleId);
			for (int i = 0; i < recommendArticleIds.length; i++){
				for (Recommend recommend : recommends_old){
					if (recommendArticleIds[i].intValue() == recommend.getArticle().getId().intValue()){
						recommends_sort.remove(recommend);
					}
				}
			}
			Integer sort = 1;
			List<Recommend> recommends_new = new ArrayList<Recommend>();
			for (Recommend recommend : recommends_sort){
				recommend.setSort(sort);
				recommends_new.add(recommend);
				sort++;
			}
			article.setRecommends(recommends_new);
			
			articleDAO.merge(article);
		}
	}
	

	@Override
	public void downRecommend(Long articleId, Long[] recommendArticleIds) {
		if (articleId != null && recommendArticleIds != null && recommendArticleIds.length == 1){
			Article article = articleDAO.get(articleId);
			List<Recommend> recommends_old = recommendDAO.findRecommendByArticle(articleId);
			for (Recommend recommend : recommends_old){
				Long recommend_article_id = recommend.getArticle().getId();
				if (recommend_article_id.intValue() == recommendArticleIds[0].intValue()){
					Integer sort = recommend.getSort();
					if (sort.intValue() < recommends_old.size()){
						sort = sort + 1;
						Recommend recommend_prev = recommendDAO.findRecommendByArticleAndSort(articleId, sort);
						recommend_prev.setSort(sort - 1);
						
						recommend.setSort(sort);
						recommends_old.add(recommend);
						recommends_old.add(recommend_prev);
						break;
					}
				}
			}
			article.setRecommends(recommends_old);
			
			articleDAO.merge(article);
		}
	}

	@Override
	public void saveRecommend(Long articleId, Long[] recommendArticleIds) {
		if (articleId != null && recommendArticleIds != null && recommendArticleIds.length > 0){
			Article article = articleDAO.get(articleId);
			List<Recommend> recommends = article.getRecommends();
			if (recommends.isEmpty()){
				recommends = new ArrayList<Recommend>();
			}
			Integer related_count = recommends.size();
			Recommend recommend = null;
			for (int i = 0; i < recommendArticleIds.length; i++){
				recommend = recommendDAO.findRecommendByArticleAndRecommend(articleId, recommendArticleIds[i]);
				if (isNotNull(recommend)) continue;
				related_count++;
				recommend = new Recommend();
				Article recommend_article = articleDAO.get(recommendArticleIds[i]);
				recommend.setSort(related_count);
				recommend.setArticle(recommend_article);
				recommends.add(recommend);
			}
			article.setRecommends(recommends);

			articleDAO.merge(article);
		}
	}

	@Override
	public void upRecommend(Long articleId, Long[] recommendArticleIds) {
		if (articleId != null && recommendArticleIds != null && recommendArticleIds.length == 1){
			Article article = articleDAO.get(articleId);
			List<Recommend> recommends_old = recommendDAO.findRecommendByArticle(articleId);
			for (Recommend recommend : recommends_old){
				Long recommend_article_id = recommend.getArticle().getId();
				if (recommend_article_id.intValue() == recommendArticleIds[0].intValue()){
					Integer sort = recommend.getSort();
					if (sort.intValue() > 1){
						sort = sort - 1;
						Recommend recommend_prev = recommendDAO.findRecommendByArticleAndSort(articleId, sort);
						recommend_prev.setSort(sort + 1);
						
						recommend.setSort(sort);
						recommends_old.add(recommend);
						recommends_old.add(recommend_prev);
						break;
					}
				}
			}
			article.setRecommends(recommends_old);

			articleDAO.merge(article);
		}
	}


	@Override
	public List<Recommend> findRecommendByArticle(Long articleId) {
		return recommendDAO.findRecommendByArticle(articleId);
	}
}
