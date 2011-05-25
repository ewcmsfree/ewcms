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
import com.ewcms.content.document.model.Related;

/**
 *
 * @author 吴智俊
 */
@Service("relatedService")
public class RelatedService implements RelatedServiceable {
	
	@Autowired
	private ArticleRmcDAO articleRmcDAO;

	@Override
	public void deleteRelated(Integer articleRmcId, Integer[] relatedArticleIds) {
		if (articleRmcId != null && relatedArticleIds != null && relatedArticleIds.length > 0){
			ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
			List<Related> relateds_old = articleRmc.getRelateds();
			List<Related> relateds_sort = articleRmcDAO.findRelatedByArticleRmcId(articleRmcId);
			for (int i = 0; i < relatedArticleIds.length; i++){
				for (Related related : relateds_old){
					if (relatedArticleIds[i].intValue() == related.getArticleRmc().getId().intValue()){
						relateds_sort.remove(related);
					}
				}
			}
			Integer sort = 1;
			List<Related> relateds_new = new ArrayList<Related>();
			for (Related related : relateds_sort){
				related.setSort(sort);
				relateds_new.add(related);
				sort++;
			}
			articleRmc.setRelateds(relateds_new);
			
			articleRmcDAO.merge(articleRmc);
		}
	}
	

	@Override
	public void downRelated(Integer articleRmcId, Integer[] relatedArticleIds) {
		if (articleRmcId != null && relatedArticleIds != null && relatedArticleIds.length == 1){
			ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
			List<Related> relateds_old = articleRmcDAO.findRelatedByArticleRmcId(articleRmcId);
			for (Related related : relateds_old){
				Integer related_article_id = related.getArticleRmc().getId();
				if (related_article_id.intValue() == relatedArticleIds[0].intValue()){
					Integer sort = related.getSort();
					if (sort.intValue() < relateds_old.size()){
						sort = sort + 1;
						Related related_prev = articleRmcDAO.findRelatedByArticleRmcIdAndSort(articleRmcId, sort);
						related_prev.setSort(sort - 1);
						
						related.setSort(sort);
						relateds_old.add(related);
						relateds_old.add(related_prev);
						break;
					}
				}
			}
			articleRmc.setRelateds(relateds_old);
			
			articleRmcDAO.merge(articleRmc);
		}
	}

	@Override
	public void saveRelated(Integer articleRmcId, Integer[] relatedArticleIds) {
		if (articleRmcId != null && relatedArticleIds != null && relatedArticleIds.length > 0){
			ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
			List<Related> relateds = articleRmc.getRelateds();
			if (relateds.isEmpty()){
				relateds = new ArrayList<Related>();
			}
			Integer related_count = relateds.size();
			Related related = null;
			for (int i = 0; i < relatedArticleIds.length; i++){
				related_count++;
				related = new Related();
				ArticleRmc related_articleRmc = articleRmcDAO.get(relatedArticleIds[i]);
				related.setSort(related_count);
				related.setArticleRmc(related_articleRmc);
				relateds.add(related);
			}
			articleRmc.setRelateds(relateds);
			
			articleRmcDAO.merge(articleRmc);
		}
	}

	@Override
	public void upRelated(Integer articleRmcId, Integer[] relatedArticleIds) {
		if (articleRmcId != null && relatedArticleIds != null && relatedArticleIds.length == 1){
			ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
			List<Related> relateds_old = articleRmcDAO.findRelatedByArticleRmcId(articleRmcId);
			for (Related related : relateds_old){
				Integer related_article_id = related.getArticleRmc().getId();
				if (related_article_id.intValue() == relatedArticleIds[0].intValue()){
					Integer sort = related.getSort();
					if (sort.intValue() > 1){
						sort = sort - 1;
						Related related_prev = articleRmcDAO.findRelatedByArticleRmcIdAndSort(articleRmcId, sort);
						related_prev.setSort(sort + 1);
						
						related.setSort(sort);
						relateds_old.add(related);
						relateds_old.add(related_prev);
						break;
					}
				}
			}
			articleRmc.setRelateds(relateds_old);
			
			articleRmcDAO.merge(articleRmc);
		}
	}
}
