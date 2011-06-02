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
import com.ewcms.content.document.dao.RelatedDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Related;

/**
 *
 * @author 吴智俊
 */
@Service
public class RelatedService implements RelatedServiceable {
	
	@Autowired
	private ArticleDAO articleDAO;
	@Autowired
	private RelatedDAO relatedDAO;

	@Override
	public void deleteRelated(Long articleId, Long[] relatedArticleIds) {
		if (articleId != null && relatedArticleIds != null && relatedArticleIds.length > 0){
			HashSet<Long> hasSets = new HashSet<Long>(Arrays.asList(relatedArticleIds));
			Article article = articleDAO.get(articleId);
			List<Related> relateds_old = article.getRelateds();
			List<Related> relateds_sort = relatedDAO.findRelatedByArticle(articleId);
			for (Long relatedArticleId : hasSets){
				for (Related related : relateds_old){
					if (relatedArticleId.longValue() == related.getArticle().getId().longValue()){
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
			article.setRelateds(relateds_new);
			
			articleDAO.merge(article);
		}
	}
	

	@Override
	public void downRelated(Long articleId, Long[] relatedArticleIds) {
		if (articleId != null && relatedArticleIds != null && relatedArticleIds.length == 1){
			Article article = articleDAO.get(articleId);
			List<Related> relateds_old = relatedDAO.findRelatedByArticle(articleId);
			for (Related related : relateds_old){
				Long related_article_id = related.getArticle().getId();
				if (related_article_id.longValue() == relatedArticleIds[0].longValue()){
					Integer sort = related.getSort();
					if (sort.longValue() < relateds_old.size()){
						sort = sort + 1;
						Related related_prev = relatedDAO.findRelatedByArticleAndSort(articleId, sort);
						related_prev.setSort(sort - 1);
						
						related.setSort(sort);
						relateds_old.add(related);
						relateds_old.add(related_prev);
						break;
					}
				}
			}
			article.setRelateds(relateds_old);
			
			articleDAO.merge(article);
		}
	}

	@Override
	public void saveRelated(Long articleId, Long[] relatedArticleIds) {
		if (articleId != null && relatedArticleIds != null && relatedArticleIds.length > 0){
			HashSet<Long> hasSets = new HashSet<Long>(Arrays.asList(relatedArticleIds));
			Article article = articleDAO.get(articleId);
			List<Related> relateds = article.getRelateds();
			if (relateds.isEmpty()){
				relateds = new ArrayList<Related>();
			}
			Integer related_count = relateds.size();
			Related related = null;
			for (Long relatedArticleId : hasSets){
				related = relatedDAO.findRelatedByArticleAndRelated(articleId, relatedArticleId);
				if (isNotNull(related)) continue;
				related_count++;
				related = new Related();
				Article related_article = articleDAO.get(relatedArticleId);
				related.setSort(related_count);
				related.setArticle(related_article);
				relateds.add(related);
			}
			article.setRelateds(relateds);
			
			articleDAO.merge(article);
		}
	}

	@Override
	public void upRelated(Long articleId, Long[] relatedArticleIds) {
		if (articleId != null && relatedArticleIds != null && relatedArticleIds.length == 1){
			Article article = articleDAO.get(articleId);
			List<Related> relateds_old = relatedDAO.findRelatedByArticle(articleId);
			for (Related related : relateds_old){
				Long related_article_id = related.getArticle().getId();
				if (related_article_id.longValue() == relatedArticleIds[0].longValue()){
					Integer sort = related.getSort();
					if (sort.longValue() > 1){
						sort = sort - 1;
						Related related_prev = relatedDAO.findRelatedByArticleAndSort(articleId, sort);
						related_prev.setSort(sort + 1);
						
						related.setSort(sort);
						relateds_old.add(related);
						relateds_old.add(related_prev);
						break;
					}
				}
			}
			article.setRelateds(relateds_old);
			
			articleDAO.merge(article);
		}
	}


	@Override
	public List<Related> findRelatedByArticle(Long articleId) {
		return relatedDAO.findRelatedByArticle(articleId);
	}
}
