/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.ewcms.core.site.model.Channel;

/**
 * 文章对应关系表
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>article:文章对象</li>
 * <li>published:发布时间</li>
 * <li>modified:修改时间</li>
 * <li>status:状态</li>
 * <li>url:链接地址</li>
 * <li>channel:频道对象</li>
 * <li>deleteFlag:删除标志</li>
 * <li>deleteAuthor:删除人</li>
 * <li>deleteTime:删除时间</li>
 * <li>refChannel:所引用的频道对象集合</li>
 * <li>relatedArticles:相关文章</li>
 * <li>recommendArticles:推荐文章</li>
 * <li>restoreAuthor:恢复人</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "doc_articlermc")
@SequenceGenerator(name = "seq_doc_articlermc", sequenceName = "seq_doc_articlermc_id", allocationSize = 1)
public class ArticleRmc implements Serializable {
	
	private static final long serialVersionUID = 3741724155254570831L;
	
	@Id
	@GeneratedValue(generator = "seq_doc_articlermc", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Article.class)
	@JoinColumn(name = "article_id")
	private Article article;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "published")
	private Date published;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", nullable = false)
	private Date modified;
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private ArticleRmcStatus status;
	@Column(name = "url", columnDefinition = "text")
	private String url;
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY, targetEntity = Channel.class)
	@JoinColumn(name = "channel_id")
	private Channel channel;
	@Column(name = "delete_flag")
	private Boolean deleteFlag;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "delete_time")
	private Date deleteTime;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Channel.class)
	@JoinTable(name = "doc_articlermc_refchannel", joinColumns = @JoinColumn(name = "articlermc_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "channel_id", referencedColumnName = "id"))
	@OrderBy(value = "id")
	private List<Channel> refChannel;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Related.class)
	@JoinColumn(name = "article_id")
	@OrderBy("sort")
	private List<Related> relateds;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Recommend.class)
	@JoinColumn(name="article_id")
	@OrderBy("sort")
	private List<Recommend> recommends;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", nullable = false)
	private Date createTime;
	@Column(name = "delete_author")
	private String deleteAuthor;
	@Column(name = "restore_author")
	private String restoreAuthor;

	public ArticleRmc() {
		status = ArticleRmcStatus.DRAFT;
		modified = new Date(Calendar.getInstance().getTime().getTime());
		createTime = new Date(Calendar.getInstance().getTime().getTime());
		deleteFlag = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getStatusDescription(){
		return status.getDescription();
	}
	
	public ArticleRmcStatus getStatus() {
		return status;
	}

	public void setStatus(ArticleRmcStatus status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@JsonIgnore
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	
	@JsonIgnore
	public List<Channel> getRefChannel() {
		return refChannel;
	}

	public void setRefChannel(List<Channel> refChannel) {
		this.refChannel = refChannel;
	}

	@JsonIgnore
	public List<Related> getRelateds() {
		return relateds;
	}

	public void setRelateds(List<Related> relateds) {
		this.relateds = relateds;
	}

	@JsonIgnore
	public List<Recommend> getRecommends() {
		return recommends;
	}

	public void setRecommends(List<Recommend> recommends) {
		this.recommends = recommends;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDeleteAuthor() {
		return deleteAuthor;
	}

	public void setDeleteAuthor(String deleteAuthor) {
		this.deleteAuthor = deleteAuthor;
	}

	public String getRestoreAuthor() {
		return restoreAuthor;
	}

	public void setRestoreAuthor(String restoreAuthor) {
		this.restoreAuthor = restoreAuthor;
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
		ArticleRmc other = (ArticleRmc) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
