/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 文章分类
 * 
 * <ul>
 * <li>id:分类编号</li>
 * <li>categoryName:分类名称</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "doc_article_category")
@SequenceGenerator(name = "seq_doc_article_category", sequenceName = "seq_doc_article_category_id", allocationSize = 1)
public class ArticleCategory implements Serializable {

	private static final long serialVersionUID = -2075041245158111665L;

	@Id
	@GeneratedValue(generator = "seq_doc_article_category", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;
	@Column(name = "categroy_name", nullable = false)
	private String categoryName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
