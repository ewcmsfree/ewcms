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
	public void deleteRelation(Long articleId, Long[] relationArticleIds) {
		if (articleId != null && relationArticleIds != null && relationArticleIds.length > 0){
			HashSet<Long> hasSets = new HashSet<Long>(Arrays.asList(relationArticleIds));
			Article article = articleDAO.get(articleId);
			List<Relation> relations_old = article.getRelations();
			List<Relation> relations_sort = relationDAO.findRelationByArticle(articleId);
			for (Long relationArticleId : hasSets){
				for (Relation relation : relations_old){
					if (relationArticleId.longValue() == relation.getArticle().getId().longValue()){
						relations_sort.remove(relation);
					}
				}
			}
			Integer sort = 1;
			List<Relation> relations_new = new ArrayList<Relation>();
			for (Relation relation : relations_sort){
				relation.setSort(sort);
				relations_new.add(relation);
				sort++;
			}
			article.setRelations(relations_new);
			
			articleDAO.merge(article);
		}
	}
	

	@Override
	public void downRelation(Long articleId, Long[] relationArticleIds) {
		if (articleId != null && relationArticleIds != null && relationArticleIds.length == 1){
			Article article = articleDAO.get(articleId);
			List<Relation> relations_old = relationDAO.findRelationByArticle(articleId);
			for (Relation relation : relations_old){
				Long relation_article_id = relation.getArticle().getId();
				if (relation_article_id.longValue() == relationArticleIds[0].longValue()){
					Integer sort = relation.getSort();
					if (sort.longValue() < relations_old.size()){
						sort = sort + 1;
						Relation relation_prev = relationDAO.findRelationByArticleAndSort(articleId, sort);
						relation_prev.setSort(sort - 1);
						
						relation.setSort(sort);
						relations_old.add(relation);
						relations_old.add(relation_prev);
						break;
					}
				}
			}
			article.setRelations(relations_old);
			
			articleDAO.merge(article);
		}
	}

	@Override
	public void saveRelation(Long articleId, Long[] relationArticleIds) {
		if (articleId != null && relationArticleIds != null && relationArticleIds.length > 0){
			HashSet<Long> hasSets = new HashSet<Long>(Arrays.asList(relationArticleIds));
			Article article = articleDAO.get(articleId);
			List<Relation> relations = article.getRelations();
			if (relations.isEmpty()){
				relations = new ArrayList<Relation>();
			}
			Integer relation_count = relations.size();
			Relation relation = null;
			for (Long relationArticleId : hasSets){
				relation = relationDAO.findRelationByArticleAndRelation(articleId, relationArticleId);
				if (isNotNull(relation)) continue;
				relation_count++;
				relation = new Relation();
				Article relation_article = articleDAO.get(relationArticleId);
				relation.setSort(relation_count);
				relation.setArticle(relation_article);
				relations.add(relation);
			}
			article.setRelations(relations);
			
			articleDAO.merge(article);
		}
	}

	@Override
	public void upRelation(Long articleId, Long[] relationArticleIds) {
		if (articleId != null && relationArticleIds != null && relationArticleIds.length == 1){
			Article article = articleDAO.get(articleId);
			List<Relation> relations_old = relationDAO.findRelationByArticle(articleId);
			for (Relation relation : relations_old){
				Long relation_article_id = relation.getArticle().getId();
				if (relation_article_id.longValue() == relationArticleIds[0].longValue()){
					Integer sort = relation.getSort();
					if (sort.longValue() > 1){
						sort = sort - 1;
						Relation relation_prev = relationDAO.findRelationByArticleAndSort(articleId, sort);
						relation_prev.setSort(sort + 1);
						
						relation.setSort(sort);
						relations_old.add(relation);
						relations_old.add(relation_prev);
						break;
					}
				}
			}
			article.setRelations(relations_old);
			
			articleDAO.merge(article);
		}
	}


	@Override
	public List<Relation> findRelationByArticle(Long articleId) {
		return relationDAO.findRelationByArticle(articleId);
	}
}
