/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.core.document.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 相关文章
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "doc_related")
@SequenceGenerator(name = "seq_doc_related", sequenceName = "seq_doc_related_id", allocationSize = 1)
public class Related implements Serializable {

	private static final long serialVersionUID = -4281309365714981737L;

	@Id
	@GeneratedValue(generator = "seq_doc_related", strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column(name = "sort")
	private Integer sort;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = ArticleRmc.class)
	@JoinColumn(name="related_articlermc_id")
	private ArticleRmc articleRmc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@JsonIgnore
	public ArticleRmc getArticleRmc() {
		return articleRmc;
	}

	public void setArticleRmc(ArticleRmc article) {
		this.articleRmc = article;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Related other = (Related) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
