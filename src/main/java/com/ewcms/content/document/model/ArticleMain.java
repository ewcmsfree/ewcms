/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

/**
 * 文章主体
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>channelId:频道编号</li>
 * <li>article:文章信息</li>
 * <li>reference:是否引用</li>
 * <li>share:是否共享</li>
 * <li>sort:排序</li>
 * <li>top:新闻置顶</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "content_article_main")
@SequenceGenerator(name = "seq_content_article_main", sequenceName = "seq_content_article_main_id", allocationSize = 1)
public class ArticleMain implements Serializable {

	private static final long serialVersionUID = 2777654709107575772L;

	@Id
	@GeneratedValue(generator = "seq_content_article_main", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "channel_id", nullable = false)
	@Index(name="idx_articlemain_channel_id")
	private Integer channelId;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Article.class)
	@JoinColumn(name = "article_id")
	@Index(name = "idx_articlemain_article_id")
	private Article article;
	@Column(name = "reference")
	private Boolean reference;
	@Column(name = "sort")
	private Long sort;
	@Column(name = "top")
	@Index(name="idx_articlemain_top")
	private Boolean top;
	@Column(name = "share")
	private Boolean share;
	
	public ArticleMain(){
		reference = false;
		sort = null;
		top = false;
		share = false;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Boolean getReference() {
		return reference;
	}

	public void setReference(Boolean reference) {
		this.reference = reference;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Boolean getTop() {
		return top;
	}

	public void setTop(Boolean top) {
		this.top = top;
	}

	public Boolean getShare() {
		return share;
	}

	public void setShare(Boolean share) {
		this.share = share;
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
		ArticleMain other = (ArticleMain) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
