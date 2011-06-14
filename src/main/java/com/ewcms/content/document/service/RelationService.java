/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.dao.ArticleDAO;
import com.ewcms.content.document.dao.RelationDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Relation;

/**
 *
 * @author 吴智俊
 */
@Service
public class RelationService implements RelationServiceable {
	
	@Autowired
	private ArticleDAO articleDAO;
	@Autowired
	private RelationDAO relationDAO;

	@Override
	public void deleteRelated(Long articleId, Long[] relatedArticleIds) {
		if (articleId != null && relatedArticleIds != null && relatedArticleIds.length > 0){
			HashSet<Long> hasSets = new HashSet<Long>(Arrays.asList(relatedArticleIds));
			Article article = articleDAO.get(articleId);
			List<Relation> relateds_old = article.getRelations();
			List<Relation> relateds_sort = relationDAO.findRelatedByArticle(articleId);
			for (Long relatedArticleId : hasSets){
				for (Relation related : relateds_old){
					if (relatedArticleId.longValue() == related.getArticle().getId().longValue()){
						relateds_sort.remove(related);
					}
				}
			}
			Integer sort = 1;
			List<Relation> relateds_new = new ArrayList<Relation>();
			for (Relation related : relateds_sort){
				related.setSort(sort);
				relateds_new.add(related);
				sort++;
			}
			article.setRelations(relateds_new);
			
			articleDAO.merge(article);
		}
	}
	

	@Override
	public void downRelated(Long articleId, Long[] relatedArticleIds) {
		if (articleId != null && relatedArticleIds != null && relatedArticleIds.length == 1){
			Article article = articleDAO.get(articleId);
			List<Relation> relateds_old = relationDAO.findRelatedByArticle(articleId);
			for (Relation related : relateds_old){
				Long related_article_id = related.getArticle().getId();
				if (related_article_id.longValue() == relatedArticleIds[0].longValue()){
					Integer sort = related.getSort();
					if (sort.longValue() < relateds_old.size()){
						sort = sort + 1;
						Relation related_prev = relationDAO.findRelatedByArticleAndSort(articleId, sort);
						related_prev.setSort(sort - 1);
						
						related.setSort(sort);
						relateds_old.add(related);
						relateds_old.add(related_prev);
						break;
					}
				}
			}
			article.setRelations(relateds_old);
			
			articleDAO.merge(article);
		}
	}

	@Override
	public void saveRelated(Long articleId, Long[] relatedArticleIds) {
		if (articleId != null && relatedArticleIds != null && relatedArticleIds.length > 0){
			HashSet<Long> hasSets = new HashSet<Long>(Arrays.asList(relatedArticleIds));
			Article article = articleDAO.get(articleId);
			List<Relation> relateds = article.getRelations();
			if (relateds.isEmpty()){
				relateds = new ArrayList<Relation>();
			}
			Integer related_count = relateds.size();
			Relation related = null;
			for (Long relatedArticleId : hasSets){
				related = relationDAO.findRelatedByArticleAndRelated(articleId, relatedArticleId);
				if (isNotNull(related)) continue;
				related_count++;
				related = new Relation();
				Article related_article = articleDAO.get(relatedArticleId);
				related.setSort(related_count);
				related.setArticle(related_article);
				relateds.add(related);
			}
			article.setRelations(relateds);
			
			articleDAO.merge(article);
		}
	}

	@Override
	public void upRelated(Long articleId, Long[] relatedArticleIds) {
		if (articleId != null && relatedArticleIds != null && relatedArticleIds.length == 1){
			Article article = articleDAO.get(articleId);
			List<Relation> relateds_old = relationDAO.findRelatedByArticle(articleId);
			for (Relation related : relateds_old){
				Long related_article_id = related.getArticle().getId();
				if (related_article_id.longValue() == relatedArticleIds[0].longValue()){
					Integer sort = related.getSort();
					if (sort.longValue() > 1){
						sort = sort - 1;
						Relation related_prev = relationDAO.findRelatedByArticleAndSort(articleId, sort);
						related_prev.setSort(sort + 1);
						
						related.setSort(sort);
						relateds_old.add(related);
						relateds_old.add(related_prev);
						break;
					}
				}
			}
			article.setRelations(relateds_old);
			
			articleDAO.merge(article);
		}
	}


	@Override
	public List<Relation> findRelatedByArticle(Long articleId) {
		return relationDAO.findRelatedByArticle(articleId);
	}
}
