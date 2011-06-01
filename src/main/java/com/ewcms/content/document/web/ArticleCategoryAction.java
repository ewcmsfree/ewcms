/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.ArticleCategory;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.ComboBox;

/**
 * @author 吴智俊
 */
public class ArticleCategoryAction extends CrudBaseAction<ArticleCategory, Integer> {

	private static final long serialVersionUID = 3929684985209263482L;

	@Autowired
	private DocumentFacable documentFac;

	public ArticleCategory getArticleCategoryVo() {
		return super.getVo();
	}

	public void setArticleCategoryVo(ArticleCategory articleCategoryVo) {
		super.setVo(articleCategoryVo);
	}

	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Integer getPK(ArticleCategory vo) {
		return vo.getId();
	}

	@Override
	protected ArticleCategory getOperator(Integer pk) {
		return documentFac.findArticleCategory(pk);
	}

	@Override
	protected void deleteOperator(Integer pk) {
		documentFac.delArticleCategory(pk);
	}

	@Override
	protected Integer saveOperator(ArticleCategory vo, boolean isUpdate) {
		if (isUpdate) {
			return documentFac.updArticleCategory(vo);
		} else {
			return documentFac.addArticleCategory(vo);
		}
	}

	@Override
	protected ArticleCategory createEmptyVo() {
		return new ArticleCategory();
	}

	private Long articleId;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public void findArticleCategoryAll() {
		List<ArticleCategory> articleCategories = documentFac.findArticleCategoryAll();
		if (articleCategories != null){
			List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
			ComboBox comboBox = null;
			for (ArticleCategory articleCategory : articleCategories){
				comboBox = new ComboBox();
				comboBox.setId(articleCategory.getId());
				comboBox.setText(articleCategory.getCategoryName());
				if (getArticleId() != null){
					Boolean isEntity = documentFac.findArticleIsEntityByArticleAndCategory(getArticleId(), articleCategory.getId());
					if (isEntity) comboBox.setSelected(true);
				}
				comboBoxs.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxs.toArray(new ComboBox[0])));
		}
	}
}