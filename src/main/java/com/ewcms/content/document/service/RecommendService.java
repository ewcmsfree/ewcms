/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.dao.ArticleRmcDAO;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.content.document.model.Recommend;

/**
 *
 * @author 吴智俊
 */
@Service("recommendService")
public class RecommendService implements RecommendServiceable {
	@Autowired
	private ArticleRmcDAO articleRmcDAO;

	@Override
	public void deleteRecommend(Integer articleRmcId, Integer[] recommendArticleIds) {
		if (articleRmcId != null && recommendArticleIds != null && recommendArticleIds.length > 0){
			ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
			List<Recommend> recommends_old = articleRmc.getRecommends();
			List<Recommend> recommends_sort = articleRmcDAO.findRecommendByArticleRmcId(articleRmcId);
			for (int i = 0; i < recommendArticleIds.length; i++){
				for (Recommend recommend : recommends_old){
					if (recommendArticleIds[i].intValue() == recommend.getArticleRmc().getId().intValue()){
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
			articleRmc.setRecommends(recommends_new);
			
			articleRmcDAO.merge(articleRmc);
		}
	}
	

	@Override
	public void downRecommend(Integer articleRmcId, Integer[] recommendArticleIds) {
		if (articleRmcId != null && recommendArticleIds != null && recommendArticleIds.length == 1){
			ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
			List<Recommend> recommends_old = articleRmcDAO.findRecommendByArticleRmcId(articleRmcId);
			for (Recommend recommend : recommends_old){
				Integer recommend_article_id = recommend.getArticleRmc().getId();
				if (recommend_article_id.intValue() == recommendArticleIds[0].intValue()){
					Integer sort = recommend.getSort();
					if (sort.intValue() < recommends_old.size()){
						sort = sort + 1;
						Recommend recommend_prev = articleRmcDAO.findRecommendByArticleRmcIdAndSort(articleRmcId, sort);
						recommend_prev.setSort(sort - 1);
						
						recommend.setSort(sort);
						recommends_old.add(recommend);
						recommends_old.add(recommend_prev);
						break;
					}
				}
			}
			articleRmc.setRecommends(recommends_old);
			
			articleRmcDAO.merge(articleRmc);
		}
	}

	@Override
	public void saveRecommend(Integer articleRmcId, Integer[] recommendArticleIds) {
		if (articleRmcId != null && recommendArticleIds != null && recommendArticleIds.length > 0){
			ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
			List<Recommend> recommends = articleRmc.getRecommends();
			if (recommends.isEmpty()){
				recommends = new ArrayList<Recommend>();
			}
			Integer related_count = recommends.size();
			Recommend recommend = null;
			for (int i = 0; i < recommendArticleIds.length; i++){
				related_count++;
				recommend = new Recommend();
				ArticleRmc recommend_articleRmc = articleRmcDAO.get(recommendArticleIds[i]);
				recommend.setSort(related_count);
				recommend.setArticleRmc(recommend_articleRmc);
				recommends.add(recommend);
			}
			articleRmc.setRecommends(recommends);

			articleRmcDAO.merge(articleRmc);
		}
	}

	@Override
	public void upRecommend(Integer articleRmcId, Integer[] recommendArticleIds) {
		if (articleRmcId != null && recommendArticleIds != null && recommendArticleIds.length == 1){
			ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
			List<Recommend> recommends_old = articleRmcDAO.findRecommendByArticleRmcId(articleRmcId);
			for (Recommend recommend : recommends_old){
				Integer recommend_article_id = recommend.getArticleRmc().getId();
				if (recommend_article_id.intValue() == recommendArticleIds[0].intValue()){
					Integer sort = recommend.getSort();
					if (sort.intValue() > 1){
						sort = sort - 1;
						Recommend recommend_prev = articleRmcDAO.findRecommendByArticleRmcIdAndSort(articleRmcId, sort);
						recommend_prev.setSort(sort + 1);
						
						recommend.setSort(sort);
						recommends_old.add(recommend);
						recommends_old.add(recommend_prev);
						break;
					}
				}
			}
			articleRmc.setRecommends(recommends_old);

			articleRmcDAO.merge(articleRmc);
		}
	}
}
