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
import com.ewcms.content.document.model.Category;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.ComboBox;

/**
 * @author 吴智俊
 */
public class CategoryAction extends CrudBaseAction<Category, Integer> {

	private static final long serialVersionUID = 3929684985209263482L;

	@Autowired
	private DocumentFacable documentFac;

	public Category getCategoryVo() {
		return super.getVo();
	}

	public void setCategoryVo(Category categoryVo) {
		super.setVo(categoryVo);
	}

	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Integer getPK(Category vo) {
		return vo.getId();
	}

	@Override
	protected Category getOperator(Integer pk) {
		return documentFac.findCategory(pk);
	}

	@Override
	protected void deleteOperator(Integer pk) {
		documentFac.delCategory(pk);
	}

	@Override
	protected Integer saveOperator(Category vo, boolean isUpdate) {
		if (isUpdate) {
			return documentFac.updCategory(vo);
		} else {
			return documentFac.addCategory(vo);
		}
	}

	@Override
	protected Category createEmptyVo() {
		return new Category();
	}

	private Long articleId;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public void findCategoryAll() {
		List<Category> categories = documentFac.findCategoryAll();
		if (categories != null){
			List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
			ComboBox comboBox = null;
			for (Category category : categories){
				comboBox = new ComboBox();
				comboBox.setId(category.getId());
				comboBox.setText(category.getCategoryName());
				if (getArticleId() != null){
					Boolean isEntity = documentFac.findArticleIsEntityByArticleAndCategory(getArticleId(), category.getId());
					if (isEntity) comboBox.setSelected(true);
				}
				comboBoxs.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxs.toArray(new ComboBox[0])));
		}
	}
}